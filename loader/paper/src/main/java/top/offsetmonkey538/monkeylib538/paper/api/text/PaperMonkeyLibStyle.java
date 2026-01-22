package top.offsetmonkey538.monkeylib538.paper.api.text;

import net.kyori.adventure.text.format.Style;
import top.offsetmonkey538.monkeylib538.common.api.text.MonkeyLibStyle;

public interface PaperMonkeyLibStyle extends MonkeyLibStyle {
    static PaperMonkeyLibStyle of(final MonkeyLibStyle style) {
        return (PaperMonkeyLibStyle) style;
    }

    Style style();
}
