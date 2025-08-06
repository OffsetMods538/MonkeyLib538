package top.offsetmonkey538.monkeylib538.impl.text;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibStyle;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;
import top.offsetmonkey538.monkeylib538.api.text.TextFormattingApi;

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

            // Previous character specified this one as a formatting code
            if (context.isFormattingCode) switch (context.currentChar) {
                case '#':
                    handleHexCode(context);
                    context.isFormattingCode = false;
                    continue;
                case '{':
                    handleWhateverThisIs(context);
                    context.isFormattingCode = false;
                    continue;
                default:
                    handleNormalFormattingCode(context);
                    context.isFormattingCode = false;
                    continue;
            }

            // When this character hasn't been escaped, check if it is a formatting code or escapes the next one.
            if (!context.isEscaped) switch (context.currentChar) {
                case '&':
                    context.isFormattingCode = true;
                    continue;
                case '\\':
                    context.isEscaped = true;
                    continue;
            }
            context.isEscaped = false;


            // Handle adding this character to the result
            final MonkeyLibText lastSibling = result.getLastSibling();
            if (lastSibling != null && lastSibling.getStyle().equals(context.style)) {
                result.setLastSibling(MonkeyLibText.of(lastSibling.getString() + context.currentChar).setStyle(context.style));
            } else {
                result.append(MonkeyLibText.of(String.valueOf(context.currentChar))).setStyle(context.style);
            }
        }

        return result;
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
    private static void handleWhateverThisIs(final @NotNull Context context) throws Exception {
        // TODO: hover and click events
    }
    private static void handleNormalFormattingCode(final @NotNull Context context) throws Exception {
        if (context.currentChar == 'r') {
            context.style = DEFAULT_STYLE;
            return;
        }

        final @Nullable MonkeyLibStyle newStyle = context.style.withFormattingCode(context.currentChar);
        if (newStyle == null) throw new Exception("Invalid formatting code at character nr %s'!".formatted(context.characterIndex));
        context.style = newStyle;
    }

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

    private static class Context {
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
    }
}
