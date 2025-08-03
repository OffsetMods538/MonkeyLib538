package top.offsetmonkey538.monkeylib538.api.text;

import org.jetbrains.annotations.NotNull;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

/**
 * Provides a method for creating a literal {@link MonkeyLibText}
 */
public interface TextApi {
    /**
     * The instance
     */
    TextApi INSTANCE = load(TextApi.class);

    /**
     * Creates a literal {@link MonkeyLibText}
     *
     * @param text the text it should contain.
     * @return a literal {@link MonkeyLibText}
     */
    @NotNull MonkeyLibText of(final @NotNull String text);
}
