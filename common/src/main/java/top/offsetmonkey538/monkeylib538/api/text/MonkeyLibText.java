package top.offsetmonkey538.monkeylib538.api.text;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

/**
 * Abstraction around a vanilla Text. Use {@link #of(String)} to create instances.
 * <p>
 *     Platform-specific implementation should store their MutableText equivalent and modify it when these methods are called.
 * </p>
 */
public interface MonkeyLibText {
    /**
     * Appends another text to this one.
     *
     * @param other the text to append to this one
     * @return this.
     */
    @NotNull MonkeyLibText append(final @NotNull MonkeyLibText other);

    /**
     * Sets if this text should be underlined.
     *
     * @param newValue if the text should be underlined
     * @return this.
     */
    @NotNull MonkeyLibText setUnderlined(final boolean newValue);

    /**
     * Sets the text to show when the user hovers over this text.
     *
     * @param text the text to show
     * @return this.
     */
    @NotNull MonkeyLibText showTextOnHover(final @NotNull MonkeyLibText text);

    /**
     * Sets the string to copy to clipboard when the user clicks this text.
     *
     * @param string the string to copy
     * @return this.
     */
    @NotNull MonkeyLibText copyStringOnClick(final @NotNull String string);


    /**
     * Creates a literal text
     *
     * @param text the text it should contain.
     * @return a literal text
     */
    static @NotNull MonkeyLibText of(final @NotNull String text) {
        return Provider.INSTANCE.of(text);
    }

    /**
     * Provides a method for creating a literal {@link MonkeyLibText}
     */
    @ApiStatus.Internal
    interface Provider {
        /**
         * The instance
         */
        Provider INSTANCE = load(Provider.class);

        /**
         * Creates a literal {@link MonkeyLibText}
         *
         * @param text the text it should contain.
         * @return a literal {@link MonkeyLibText}
         */
        @NotNull MonkeyLibText of(final @NotNull String text);
    }
}
