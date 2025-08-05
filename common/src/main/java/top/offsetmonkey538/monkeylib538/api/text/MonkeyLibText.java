package top.offsetmonkey538.monkeylib538.api.text;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.UnaryOperator;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

/**
 * Abstraction around a vanilla Text. Use {@link #of(String)} to create instances.
 * <p>
 *     Platform-specific implementation should store their MutableText equivalent and modify it when these methods are called.
 * </p>
 */
public interface MonkeyLibText {
    /**
     * Returns the last sibling of this text.
     *
     * @return the last sibling of this text
     */
    @Nullable MonkeyLibText getLastSibling();

    /**
     * Sets the last sibling of this text.
     *
     * @return this.
     */
    @NotNull MonkeyLibText setLastSibling(@NotNull MonkeyLibText newSibling);

    /**
     * Appends another text to this one.
     *
     * @param other the text to append to this one
     * @return this.
     */
    @NotNull MonkeyLibText append(final @NotNull MonkeyLibText other);

    /**
     * Gets the style of this text.
     *
     * @return The style of this text
     */
    @NotNull MonkeyLibStyle getStyle();

    /**
     * Sets the style of this text to the provided style.
     *
     * @param style the style to use.
     * @return this.
     */
    @NotNull MonkeyLibText setStyle(final @NotNull MonkeyLibStyle style);

    /**
     * Applies the provided operations to this text's style.
     *
     * @param styleModifier the operations to apply.
     * @return this.
     */
    default @NotNull MonkeyLibText applyStyle(final @NotNull UnaryOperator<MonkeyLibStyle> styleModifier) {
        return setStyle(styleModifier.apply(getStyle()));
    }

    /**
     * Gets the content of this text
     *
     * @return the content of this text
     */
    @NotNull String getString();


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
     * Creates an empty literal text
     *
     * @return an empty text
     */
    static @NotNull MonkeyLibText empty() {
        return Provider.INSTANCE.empty();
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

        /**
         * Creates an empty literal {@link MonkeyLibText}
         *
         * @return an empty {@link MonkeyLibText}
         */
        @NotNull MonkeyLibText empty();
    }
}
