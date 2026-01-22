package top.offsetmonkey538.monkeylib538.paper.api.text;

import net.kyori.adventure.text.Component;
import top.offsetmonkey538.monkeylib538.common.api.text.MonkeyLibText;

public interface PaperMonkeyLibText extends MonkeyLibText {
    static PaperMonkeyLibText of(final MonkeyLibText text) {
        return (PaperMonkeyLibText) text;
    }

    Component getText();
}
