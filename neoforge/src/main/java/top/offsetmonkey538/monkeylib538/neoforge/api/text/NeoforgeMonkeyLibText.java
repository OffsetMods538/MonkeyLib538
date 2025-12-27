package top.offsetmonkey538.monkeylib538.neoforge.api.text;

import net.minecraft.network.chat.Component;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;

public interface NeoforgeMonkeyLibText extends MonkeyLibText {

    static NeoforgeMonkeyLibText of(final MonkeyLibText text) {
        return (NeoforgeMonkeyLibText) text;
    }

    Component getText();
}
