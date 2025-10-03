package top.offsetmonkey538.monkeylib538.neoforge.api.text;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;

public interface NeoforgeMonkeyLibText extends MonkeyLibText {

    static @NotNull NeoforgeMonkeyLibText of(final @NotNull MonkeyLibText text) {
        return (NeoforgeMonkeyLibText) text;
    }

    @NotNull Component getText();
}
