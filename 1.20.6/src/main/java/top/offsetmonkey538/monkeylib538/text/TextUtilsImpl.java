package top.offsetmonkey538.monkeylib538.text;

import net.minecraft.text.TextColor;

public class TextUtilsImpl implements TextUtils {

    @Override
    public TextColor parseHexColor(String hexColor) throws Exception {
        return TextColor.parse(hexColor).getOrThrow(Exception::new);
    }
}
