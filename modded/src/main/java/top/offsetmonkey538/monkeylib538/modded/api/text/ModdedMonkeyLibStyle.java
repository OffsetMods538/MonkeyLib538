package top.offsetmonkey538.monkeylib538.modded.api.text;

import net.minecraft.text.Style;
import top.offsetmonkey538.monkeylib538.common.api.text.MonkeyLibStyle;

public interface ModdedMonkeyLibStyle extends MonkeyLibStyle {

    static ModdedMonkeyLibStyle of(final MonkeyLibStyle style) {
        return (ModdedMonkeyLibStyle) style;
    }

    Style getStyle();
}
