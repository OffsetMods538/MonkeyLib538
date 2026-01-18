package top.offsetmonkey538.monkeylib538.common.api.text;

import org.jspecify.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.common.api.annotation.Internal;

import java.util.function.UnaryOperator;

import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.load;

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
    MonkeyLibText setLastSibling(MonkeyLibText newSibling);

    /**
     * Appends another text to this one.
     *
     * @param other the text to append to this one
     * @return this.
     */
    MonkeyLibText append(final MonkeyLibText other);

    /**
     * Gets the style of this text.
     *
     * @return The style of this text
     */
    MonkeyLibStyle getStyle();

    /**
     * Sets the style of this text to the provided style.
     *
     * @param style the style to use.
     * @return this.
     */
    MonkeyLibText setStyle(final MonkeyLibStyle style);

    /**
     * Applies the provided operations to this text's style.
     *
     * @param styleModifier the operations to apply.
     * @return this.
     */
    default MonkeyLibText applyStyle(final UnaryOperator<MonkeyLibStyle> styleModifier) {
        return setStyle(styleModifier.apply(getStyle()));
    }

    /**
     * Gets the content of this text
     *
     * @return the content of this text
     */
    String getString();


    /**
     * Creates a literal text
     *
     * @param text the text it should contain.
     * @return a literal text
     */
    static MonkeyLibText of(final String text) {
        return Provider.INSTANCE.of(text);
    }

    /**
     * Creates an empty literal text
     *
     * @return an empty text
     */
    static MonkeyLibText empty() {
        return Provider.INSTANCE.empty();
    }

    @Internal
    interface Provider {
        Provider INSTANCE = load(Provider.class);

        MonkeyLibText of(final String text);
        MonkeyLibText empty();
    }
}
