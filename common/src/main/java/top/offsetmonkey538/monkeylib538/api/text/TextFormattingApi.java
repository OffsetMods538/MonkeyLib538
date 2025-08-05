package top.offsetmonkey538.monkeylib538.api.text;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

@ApiStatus.NonExtendable
public interface TextFormattingApi {

    @ApiStatus.Internal
    TextFormattingApi INSTANCE = load(TextFormattingApi.class);

    static @NotNull MonkeyLibText styleText(final @NotNull String text) throws Exception {
        return INSTANCE.styleTextImpl(text);
    }

    static @NotNull MonkeyLibText[] styleTextMultiline(final @NotNull String text) throws Exception {
        return INSTANCE.styleTextMultilineImpl(text);
    }

    @ApiStatus.Internal
    @NotNull MonkeyLibText styleTextImpl(final @NotNull String text) throws Exception;
    @ApiStatus.Internal
    @NotNull MonkeyLibText[] styleTextMultilineImpl(final @NotNull String text) throws Exception;
}
