package top.offsetmonkey538.monkeylib538.api.text;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.nio.file.Path;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

public interface MonkeyLibStyle {
    @NotNull MonkeyLibStyle withShowText(final @NotNull MonkeyLibText value);

    @NotNull MonkeyLibStyle withOpenUrl(final @NotNull URI value);
    @NotNull MonkeyLibStyle withOpenFile(final @NotNull Path value);
    @NotNull MonkeyLibStyle withRunCommand(final @NotNull String value);
    @NotNull MonkeyLibStyle withSuggestCommand(final @NotNull String value);
    @NotNull MonkeyLibStyle withCopyToClipboard(final @NotNull String value);

    @NotNull MonkeyLibStyle copyEventsFrom(final @NotNull MonkeyLibStyle from);


    /**
     * Returns a new style with the provided italic attribute.
     *
     * @param italic if the new style should be italic
     * @return a new style with the provided italic attribute
     */
    @NotNull MonkeyLibStyle withItalic(final boolean italic);
    /**
     * Returns a new style with the provided underline attribute.
     *
     * @param underline if the new style should be underlined
     * @return a new style with the provided underline attribute
     */
    @NotNull MonkeyLibStyle withUnderline(final boolean underline);
    /**
     * Returns a new style with the provided rgb color.
     *
     * @param rgbColor the color in the 0xRRGGBB format
     * @return a new style with the provided color attribute
     */
    @NotNull MonkeyLibStyle withColor(final int rgbColor);
    /**
     * Returns a new style with the formatting code.
     *
     * @param code the formatting code to use
     * @return a new style with the formatting code
     */
    @Nullable MonkeyLibStyle withFormattingCode(final char code);

    /**
     * Checks if this style is equal to the provided one.
     *
     * @param other the style to check against
     * @return if this style is equal to the provided one
     */
    boolean equals(final @NotNull MonkeyLibStyle other);


    /**
     * Creates an empty style
     *
     * @return an empty style
     */
    static @NotNull MonkeyLibStyle empty() {
        return Provider.INSTANCE.empty();
    }

    /**
     * Provides a method for creating a {@link MonkeyLibStyle}
     */
    @ApiStatus.Internal
    interface Provider {
        /**
         * The instance
         */
        Provider INSTANCE = load(Provider.class);

        /**
         * Creates an empty {@link MonkeyLibStyle}
         *
         * @return an empty {@link MonkeyLibStyle}
         */
        @NotNull MonkeyLibStyle empty();
    }

    @SuppressWarnings("unused")
    final class Color {
        public static final int BLACK        = 0x000000;
        public static final int DARK_BLUE    = 0x0000AA ;
        public static final int DARK_GREEN   = 0x00AA00;
        public static final int DARK_AQUA    = 0x00AAAA;
        public static final int DARK_RED     = 0xAA0000;
        public static final int DARK_PURPLE  = 0xAA00AA;
        public static final int GOLD         = 0xFFAA00;
        public static final int GRAY         = 0xAAAAAA;
        public static final int DARK_GRAY    = 0x555555;
        public static final int BLUE         = 0x5555FF;
        public static final int GREEN        = 0x55FF55;
        public static final int AQUA         = 0x55FFFF;
        public static final int RED          = 0xFF5555;
        public static final int LIGHT_PURPLE = 0xFF55FF;
        public static final int YELLOW       = 0xFFFF55;
        public static final int WHITE        = 0xFFFFFF;
    }
}
