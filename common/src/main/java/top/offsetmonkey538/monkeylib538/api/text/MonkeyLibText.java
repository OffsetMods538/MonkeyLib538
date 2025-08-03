package top.offsetmonkey538.monkeylib538.api.text;

import org.jetbrains.annotations.NotNull;

/**
 * Abstraction around a vanilla Text.
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
}
