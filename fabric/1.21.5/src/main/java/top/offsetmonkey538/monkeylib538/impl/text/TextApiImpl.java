package top.offsetmonkey538.monkeylib538.impl.text;

import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;
import top.offsetmonkey538.monkeylib538.api.text.TextApi;

public class TextApiImpl implements TextApi {
    @Override
    public @NotNull MonkeyLibText of(final @NotNull String text) {
        return new MonkeyLibTextImpl(text);
    }
}
