package top.offsetmonkey538.monkeylib538.text;

import net.minecraft.text.TextColor;

import java.util.concurrent.atomic.AtomicReference;

public class TextUtilsImpl implements TextUtils {

    @Override
    public TextColor parseHexColor(String hexColor) throws Exception {
        final AtomicReference<Exception> error = new AtomicReference<>();

        final TextColor result = TextColor.parse(hexColor).getOrThrow(false, (errorString) -> error.set(new Exception(errorString)));
        if (error.get() != null) throw error.get();

        return result;
    }
}
