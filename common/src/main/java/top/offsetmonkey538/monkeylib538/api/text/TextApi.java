package top.offsetmonkey538.monkeylib538.api.text;

import org.jetbrains.annotations.NotNull;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

public interface TextApi {
    TextApi INSTANCE = load(TextApi.class);

    @NotNull MonkeyLibText of(final @NotNull String text);
}
