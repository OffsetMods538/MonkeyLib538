package top.offsetmonkey538.monkeylib538.common.impl.text;

import com.google.common.base.Suppliers;
import net.kyori.adventure.text.Component;
import top.offsetmonkey538.monkeylib538.common.api.text.TextFormattingApi;

import java.net.URI;
import java.nio.file.Path;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static top.offsetmonkey538.offsetutils538.api.text.ArgReplacer.replaceArgs;

@SuppressWarnings("deprecation")
public final class TextFormattingImpl implements TextFormattingApi {
    private static final Supplier<MonkeyLibStyle> DEFAULT_STYLE = Suppliers.memoize(() -> MonkeyLibStyle.empty().withItalic(false).withColor(0xFFFFFF));

    @Override
    public Component styleTextImpl(final String text) throws Exception {
        MonkeyLibText result = MonkeyLibText.empty();

        final Context context = new Context(
                false,
                false,
                DEFAULT_STYLE.get(),
                text.toCharArray(),
                0
        );

        for (; context.characterIndex < context.characters.length; context.characterIndex++) {
            context.currentChar = context.characters[context.characterIndex];
            result = handleChar(result, context);
        }

        return result.get().compact();
    }

    private static MonkeyLibText handleChar(final MonkeyLibText result, final Context context) throws Exception {
        // Previous character specified this one as a formatting code
        if (context.isFormattingCode) switch (context.currentChar) {
            case '#':
                context.isFormattingCode = false;
                handleHexCode(context);
                return result;
            case '{':
                context.isFormattingCode = false;
                return handleAction(result, context);
            default:
                context.isFormattingCode = false;
                handleNormalFormattingCode(context);
                return result;
        }

        // When this character hasn't been escaped, check if it is a formatting code or escapes the next one.
        if (!context.isEscaped) switch (context.currentChar) {
            case '&':
                context.isFormattingCode = true;
                return result;
            case '\\':
                context.isEscaped = true;
                return result;
        }
        context.isEscaped = false;


        // Handle adding this character to the result
        final MonkeyLibText lastSibling = result.getLastSibling();
        if (lastSibling != null && lastSibling.getStyle().equals(context.style)) {
            return result.setLastSibling(MonkeyLibText.of(lastSibling.getString() + context.currentChar).setStyle(context.style));
        } else {
            return result.append(MonkeyLibText.of(String.valueOf(context.currentChar)).setStyle(context.style));
        }
    }

    private static void handleHexCode(final Context context) throws Exception {
        final String hexCodeString = readChars(context, 6, "Unfinished hex code starting at character nr %s!");

        final int hexCode;
        try {
            hexCode = Integer.parseInt(hexCodeString, 16);
        } catch (NumberFormatException e) {
            throw new Exception(replaceArgs("Invalid hex color '#%s' starting at character nr %s", hexCodeString, context.characterIndex), e);
        }

        context.style = context.style.withColor(hexCode);
    }

    private static MonkeyLibText handleAction(MonkeyLibText result, final Context context) throws Exception {
        final int startIndex = context.characterIndex;
        final String actionNameBoxed = readUntil(context, ','); // This has the current { char and the ending , char. So "{hoverText,"
        final String actionName = actionNameBoxed.substring(1, actionNameBoxed.length() - 1); // Only action name itself, without the { and ,. So "hoverText"
        final Action action;
        try {
            action = Action.valueOf(actionName);
        } catch (IllegalArgumentException e) {
            throw new Exception(replaceArgs("Unknown action name '%s' starting at character nr %s!", actionName, startIndex));
        }

        context.readNext();
        if (context.currentChar != '\'') throw new Exception(replaceArgs("Expected character nr %s to be ' (single quote). Got %s", context.characterIndex, context.currentChar));

        Object arg;
        final MonkeyLibStyle originalStyle = context.style;
        if (action.isArgStyled) {
            arg = MonkeyLibText.empty();
            for (context.readNext(); context.characterIndex < context.characters.length; context.characterIndex++) {
                context.currentChar = context.characters[context.characterIndex];

                if (context.currentChar == '\'' && !context.isEscaped) break;

                arg = handleChar((MonkeyLibText) arg, context);
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
        if (context.currentChar != ',') throw new Exception(replaceArgs("Expected character nr %s to be , (coma). Got %s", context.characterIndex, context.currentChar));
        context.readNext();
        if (context.currentChar != '\'') throw new Exception(replaceArgs("Expected character nr %s to be ' (single quote). Got %s", context.characterIndex, context.currentChar));

        for (context.readNext(); context.characterIndex < context.characters.length; context.characterIndex++) {
            context.currentChar = context.characters[context.characterIndex];

            if (context.currentChar == '\'' && !context.isEscaped) break;

            result = handleChar(result, context);
        }
        context.style = originalStyle;

        context.readNext();
        if (context.currentChar != '}') throw new Exception(replaceArgs("Expected character nr %s to be } (closed curly bracket). Got %s", context.characterIndex, context.currentChar));
        return result;
    }
    private static void handleNormalFormattingCode(final Context context) throws Exception {
        if (context.currentChar == 'r') {
            context.style = DEFAULT_STYLE.get().copyEventsFrom(context.style);
            return;
        }

        final MonkeyLibStyle newStyle = context.style.withFormattingCode(context.currentChar);
        if (newStyle == null) throw new Exception(replaceArgs("Invalid formatting code '%s' at character nr %s'!", context.currentChar, context.characterIndex));
        context.style = newStyle;
    }

    @SuppressWarnings("SameParameterValue")
    private static String readChars(final Context context, final int numChars, final String error) throws Exception {
        final int endIndex = context.characterIndex + numChars;
        if (endIndex >= context.characters.length) throw new Exception(replaceArgs(error, context.characterIndex));

        final StringBuilder builder = new StringBuilder(numChars);
        for (context.characterIndex++; context.characterIndex <= endIndex; context.characterIndex++) {
            context.currentChar = context.characters[context.characterIndex];
            builder.append(context.currentChar);
        }
        context.characterIndex--;
        return builder.toString();
    }

    @SuppressWarnings("SameParameterValue")
    private static String readUntil(final Context context, final char expectedChar) throws Exception {
        final StringBuilder builder = new StringBuilder();
        final int startIndex = context.characterIndex;
        final char startChar = context.currentChar;

        while (context.currentChar != expectedChar) {
            builder.append(context.currentChar);

            context.characterIndex++;
            if (context.characterIndex >= context.characters.length) throw new Exception(replaceArgs("Expected character '%s' at some point after character nr %s ('%s')!", expectedChar, startIndex, startChar));

            context.currentChar = context.characters[context.characterIndex];
        }
        builder.append(context.currentChar);

        return builder.toString();
    }

    private static final class Context {
        private boolean isFormattingCode;
        private boolean isEscaped;
        private MonkeyLibStyle style;
        private final char[] characters;
        private int characterIndex;
        private char currentChar;


        private Context(
                final boolean isFormattingCode,
                final boolean isEscaped,
                final MonkeyLibStyle style,
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

        openUrl((style, arg) -> style.withOpenUrl(URI.create(arg))),
        openFile((style, arg) -> style.withOpenFile(Path.of(arg))),
        runCommand(MonkeyLibStyle::withRunCommand),
        suggestCommand(MonkeyLibStyle::withSuggestCommand),
        copyToClipboard(MonkeyLibStyle::withCopyToClipboard);


        public final boolean isArgStyled;
        public final BiFunction<MonkeyLibStyle, Object, MonkeyLibStyle> modifier;

        Action(@SuppressWarnings("unused") final boolean isArgStyled, final BiFunction<MonkeyLibStyle, MonkeyLibText, MonkeyLibStyle> modifier) {
            this.isArgStyled = true;
            this.modifier = (style, arg) -> modifier.apply(style, (MonkeyLibText) arg);
        }
        Action(final BiFunction<MonkeyLibStyle, String, MonkeyLibStyle> modifier) {
            this.isArgStyled = false;
            this.modifier = (style, arg) -> modifier.apply(style, (String) arg);
        }
    }
}
