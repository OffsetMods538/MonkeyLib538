package top.offsetmonkey538.monkeylib538.neoforge.api.text;

import net.minecraft.network.chat.Style;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibStyle;

public interface NeoforgeMonkeyLibStyle extends MonkeyLibStyle {

    static NeoforgeMonkeyLibStyle of(final MonkeyLibStyle style) {
        return (NeoforgeMonkeyLibStyle) style;
    }

    Style getStyle();
}
