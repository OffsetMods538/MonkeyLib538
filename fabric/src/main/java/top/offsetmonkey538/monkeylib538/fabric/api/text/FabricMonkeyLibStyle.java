package top.offsetmonkey538.monkeylib538.fabric.api.text;

import net.minecraft.text.Style;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibStyle;

public interface FabricMonkeyLibStyle extends MonkeyLibStyle {

    static FabricMonkeyLibStyle of(final MonkeyLibStyle style) {
        return (FabricMonkeyLibStyle) style;
    }

    Style getStyle();
}
