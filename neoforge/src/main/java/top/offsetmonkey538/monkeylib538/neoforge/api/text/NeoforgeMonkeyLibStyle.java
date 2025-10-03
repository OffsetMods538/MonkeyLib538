package top.offsetmonkey538.monkeylib538.neoforge.api.text;

import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibStyle;

public interface NeoforgeMonkeyLibStyle extends MonkeyLibStyle {

    static @NotNull NeoforgeMonkeyLibStyle of(final @NotNull MonkeyLibStyle style) {
        return (NeoforgeMonkeyLibStyle) style;
    }

    @NotNull Style getStyle();
}
