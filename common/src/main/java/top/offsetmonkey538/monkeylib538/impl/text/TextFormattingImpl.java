package top.offsetmonkey538.monkeylib538.impl.text;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibStyle;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;
import top.offsetmonkey538.monkeylib538.api.text.TextFormattingApi;

import java.util.function.BiFunction;

public final class TextFormattingImpl implements TextFormattingApi {
    private static final MonkeyLibStyle DEFAULT_STYLE = MonkeyLibStyle.empty().withItalic(false).withColor(0xFFFFFF);

    @Override
    public @NotNull MonkeyLibText[] styleTextMultilineImpl(@NotNull String text) throws Exception {
        final String[] splitText = text.split("\n");
        final MonkeyLibText[] result = new MonkeyLibText[splitText.length];

        for (int lineNumber = 0; lineNumber < splitText.length; lineNumber++) try {
            result[lineNumber] = styleTextImpl(splitText[lineNumber]);
        } catch (Exception e) {
            throw new Exception("Failed to style text at line nr '%s'!".formatted(lineNumber), e);
        }

        return result;
    }

    @Override
    public @NotNull MonkeyLibText styleTextImpl(final @NotNull String text) throws Exception {
        final MonkeyLibText result = MonkeyLibText.empty();

        final Context context = new Context(
                false,
                false,
                DEFAULT_STYLE,
                text.toCharArray(),
                0
        );

        for (; context.characterIndex < context.characters.length; context.characterIndex++) {
            context.currentChar = context.characters[context.characterIndex];
            handleChar(result, context);
        }

        return result;
    }

    private static void handleChar(final @NotNull MonkeyLibText result, final @NotNull Context context) throws Exception {
        // Previous character specified this one as a formatting code
        if (context.isFormattingCode) switch (context.currentChar) {
            case '#':
                context.isFormattingCode = false;
                handleHexCode(context);
                return;
            case '{':
                context.isFormattingCode = false;
                handleAction(result, context);
                return;
            default:
                context.isFormattingCode = false;
                handleNormalFormattingCode(context);
                return;
        }

        // When this character hasn't been escaped, check if it is a formatting code or escapes the next one.
        if (!context.isEscaped) switch (context.currentChar) {
            case '&':
                context.isFormattingCode = true;
                return;
            case '\\':
                context.isEscaped = true;
                return;
        }
        context.isEscaped = false;


        // Handle adding this character to the result
        final MonkeyLibText lastSibling = result.getLastSibling();
        if (lastSibling != null && lastSibling.getStyle().equals(context.style)) {
            result.setLastSibling(MonkeyLibText.of(lastSibling.getString() + context.currentChar).setStyle(context.style));
        } else {
            result.append(MonkeyLibText.of(String.valueOf(context.currentChar)).setStyle(context.style));
        }
    }

    private static void handleHexCode(final @NotNull Context context) throws Exception {
        final String hexCodeString = readChars(context, 6, "Unfinished hex code starting at character nr %s!");

        final int hexCode;
        try {
            hexCode = Integer.parseInt(hexCodeString, 16);
        } catch (NumberFormatException e) {
            throw new Exception("Invalid hex color '#%s' starting at character nr %s".formatted(hexCodeString, context.characterIndex), e);
        }

        context.style = context.style.withColor(hexCode);
    }

    private static void handleAction(final @NotNull MonkeyLibText result, final @NotNull Context context) throws Exception {
        // Read until coma, todo: decide how many more and what args there should be
        final int startIndex = context.characterIndex;
        final String actionNameBoxed = readUntil(context, ','); // This has the current { char and the ending , char. So "{hoverText,"
        final String actionName = actionNameBoxed.substring(1, actionNameBoxed.length() - 1); // Only action name itself, without the { and ,. So "hoverText"
        final Action action;
        try {
            action = Action.valueOf(actionName);
        } catch (IllegalArgumentException e) {
            throw new Exception("Unknown action name '%s' starting at character nr %s!".formatted(actionName, startIndex));
        }

        context.readNext();
        if (context.currentChar != '\'') throw new Exception("Expected character nr %s to be ' (single quote). Got %s".formatted(context.characterIndex, context.currentChar));

        final Object arg;
        final MonkeyLibStyle originalStyle = context.style;
        if (action.isArgStyled) {
            arg = MonkeyLibText.empty();
            for (context.readNext(); context.characterIndex < context.characters.length; context.characterIndex++) {
                context.currentChar = context.characters[context.characterIndex];

                if (context.currentChar == '\'' && !context.isEscaped) break;

                handleChar((MonkeyLibText) arg, context);
            }
        } else {
            final StringBuilder builder = new StringBuilder();

            for (context.readNext(); context.characterIndex < context.characters.length; context.characterIndex++) {
                context.currentChar = context.characters[context.characterIndex];

                if (context.isEscaped) {
                    context.isEscaped = false;
                    builder.append(context.currentChar);
                    continue;
                }
                if (context.currentChar == '\'') break;
                if (context.currentChar == '\\') context.isEscaped = true;

                builder.append(context.currentChar);
            }

            arg = builder.toString();
        }

        context.style = action.modifier.apply(originalStyle, arg);


        context.readNext();
        if (context.currentChar != ',') throw new Exception("Expected character nr %s to be , (coma). Got %s".formatted(context.characterIndex, context.currentChar));
        context.readNext();
        if (context.currentChar != '\'') throw new Exception("Expected character nr %s to be ' (single quote). Got %s".formatted(context.characterIndex, context.currentChar));

        for (context.readNext(); context.characterIndex < context.characters.length; context.characterIndex++) {
            context.currentChar = context.characters[context.characterIndex];

            if (context.currentChar == '\'' && !context.isEscaped) break;

            handleChar(result, context);
        }
        context.style = originalStyle;

        context.readNext();
        if (context.currentChar != '}') throw new Exception("Expected character nr %s to be } (closed curly bracket). Got %s".formatted(context.characterIndex, context.currentChar));
    }
    private static void handleNormalFormattingCode(final @NotNull Context context) throws Exception {
        if (context.currentChar == 'r') {
            context.style = DEFAULT_STYLE;
            return;
        }

        final @Nullable MonkeyLibStyle newStyle = context.style.withFormattingCode(context.currentChar);
        if (newStyle == null) throw new Exception("Invalid formatting code '%s' at character nr %s'!".formatted(context.currentChar, context.characterIndex));
        context.style = newStyle;
    }

    @SuppressWarnings("SameParameterValue")
    private static @NotNull String readChars(final @NotNull Context context, final int numChars, final @NotNull String error) throws Exception {
        final int endIndex = context.characterIndex + numChars;
        if (endIndex >= context.characters.length) throw new Exception(error.formatted(context.characterIndex));

        final StringBuilder builder = new StringBuilder(numChars);
        for (context.characterIndex++; context.characterIndex <= endIndex; context.characterIndex++) {
            context.currentChar = context.characters[context.characterIndex];
            builder.append(context.currentChar);
        }
        context.characterIndex--;
        return builder.toString();
    }

    @SuppressWarnings("SameParameterValue")
    private static @NotNull String readUntil(final @NotNull Context context, final char expectedChar) throws Exception {
        final StringBuilder builder = new StringBuilder();
        final int startIndex = context.characterIndex;
        final char startChar = context.currentChar;

        while (context.currentChar != expectedChar) {
            builder.append(context.currentChar);

            context.characterIndex++;
            if (context.characterIndex >= context.characters.length) throw new Exception("Expected character '%s' at some point after character nr %s ('%s')!".formatted(expectedChar, startIndex, startChar));

            context.currentChar = context.characters[context.characterIndex];
        }
        builder.append(context.currentChar);

        return builder.toString();
    }

    private static final class Context {
        private boolean isFormattingCode;
        private boolean isEscaped;
        private @NotNull MonkeyLibStyle style;
        private final char[] characters;
        private int characterIndex;
        private char currentChar;


        private Context(
                final boolean isFormattingCode,
                final boolean isEscaped,
                final @NotNull MonkeyLibStyle style,
                final char[] characters,
                int characterIndex
        ) {
            this.isFormattingCode = isFormattingCode;
            this.isEscaped = isEscaped;
            this.style = style;
            this.characters = characters;
            this.characterIndex = characterIndex;
        }

        private void readNext() {
            this.characterIndex++;
            this.currentChar = this.characters[this.characterIndex];
        }
    }

    private enum Action {
        hoverText(true, MonkeyLibStyle::withShowText),

        runCommand(MonkeyLibStyle::withRunCommand);


        public final boolean isArgStyled;
        public final @NotNull BiFunction<MonkeyLibStyle, Object, MonkeyLibStyle> modifier;

        Action(final boolean isArgStyled, final @NotNull BiFunction<MonkeyLibStyle, MonkeyLibText, MonkeyLibStyle> modifier) {
            this.isArgStyled = true;
            this.modifier = (style, arg) -> modifier.apply(style, (MonkeyLibText) arg);
        }
        Action(final @NotNull BiFunction<MonkeyLibStyle, String, MonkeyLibStyle> modifier) {
            this.isArgStyled = false;
            this.modifier = (style, arg) -> modifier.apply(style, (String) arg);
        }
    }
}
