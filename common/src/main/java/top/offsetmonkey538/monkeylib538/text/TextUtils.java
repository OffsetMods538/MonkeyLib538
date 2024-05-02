package top.offsetmonkey538.monkeylib538.text;

import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

import java.util.List;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

public interface TextUtils {
    Style DEFAULT_STYLE = Style.EMPTY.withItalic(false).withColor(Formatting.WHITE);
    TextUtils INSTANCE = load(TextUtils.class);

    default MutableText getStyledText(String text) throws Exception {
        final MutableText result = Text.empty();
        Style style = DEFAULT_STYLE;

        boolean isFormattingCode = false;
        boolean isEscaped = false;
        char[] characters = text.toCharArray();
        for (int characterIndex = 0; characterIndex < characters.length; characterIndex++) {
            char currentChar = characters[characterIndex];

            if (isFormattingCode) {
                // Hex color
                if (currentChar == '#') {
                    if (characterIndex + 7 >= characters.length) throw new Exception("Unfinished hex code starting at character number '" + characterIndex + "'!");

                    try {
                        style = style.withColor(parseHexColor(text.substring(characterIndex, characterIndex + 7)));
                    } catch (Exception e) {
                        throw new Exception("Failed to parse hex color starting at character number '" + characterIndex + "'!", e);
                    }

                    // Move pointer 6 characters ahead as we already read the whole hex code
                    characterIndex += 6;
                    isFormattingCode = false;
                    continue;
                }

                style = getStyleForFormattingCode(currentChar, style);

                if (style == null) throw new Exception("Invalid formatting code at character number '" + characterIndex + "'!");

                isFormattingCode = false;
                continue;
            }

            if (!isEscaped){
                switch (currentChar){
                    case '&':
                        isFormattingCode = true;
                        continue;
                    case '\\':
                        isEscaped = true;
                        continue;
                }
            }
            isEscaped = false;


            final List<Text> siblings = result.getSiblings();
            final int lastSiblingIndex = siblings.size() - 1;
            final Text lastSibling = siblings.isEmpty() ? Text.empty() : siblings.get(lastSiblingIndex);

            // Check if the style of the last sibling is the same as the current one
            if (!siblings.isEmpty() && lastSibling.getStyle().equals(style)) {
                // If so, set the last sibling to itself plus the new character
                siblings.set(lastSiblingIndex, Text.literal(lastSibling.getString() + currentChar).setStyle(style));
            } else {
                // Otherwise, just append a new sibling to the result
                result.append(Text.literal(String.valueOf(currentChar)).setStyle(style));
            }
        }

        return result;
    }

    private Style getStyleForFormattingCode(char formattingCode, Style currentStyle) {
        if (formattingCode == 'r') return DEFAULT_STYLE;

        final Formatting formatting = Formatting.byCode(formattingCode);
        if (formatting == null) return null;

        return currentStyle.withFormatting(formatting);
    }

    TextColor parseHexColor(String hexColor) throws Exception;
}
