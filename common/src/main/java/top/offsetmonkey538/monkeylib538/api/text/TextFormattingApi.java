package top.offsetmonkey538.monkeylib538.api.text;

import org.jetbrains.annotations.NotNull;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

public interface TextFormattingApi {
    TextFormattingApi INSTANCE = load(TextFormattingApi.class);

    static @NotNull MonkeyLibText[] styleTextMultiline(final @NotNull String text) throws Exception {
        return INSTANCE.styleTextMultilineImpl(text);
    }

    static @NotNull MonkeyLibText styleText(final @NotNull String text) throws Exception {
        return INSTANCE.styleTextImpl(text);
    }

    @NotNull MonkeyLibText[] styleTextMultilineImpl(final @NotNull String text) throws Exception;
    @NotNull MonkeyLibText styleTextImpl(final @NotNull String text) throws Exception;
}
