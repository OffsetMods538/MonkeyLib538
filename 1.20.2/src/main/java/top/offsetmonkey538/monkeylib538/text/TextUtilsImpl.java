package top.offsetmonkey538.monkeylib538.text;

import net.minecraft.text.TextColor;

public class TextUtilsImpl implements TextUtils {
    @Override
    public TextColor parseHexColor(String hexColor) throws Exception {
        try {
            int hex = Integer.parseInt(hexColor.substring(1), 16);
            return TextColor.fromRgb(hex);
        } catch (NumberFormatException e) {
            throw new Exception(e);
        }
    }
}
