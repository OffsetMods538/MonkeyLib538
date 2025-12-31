package top.offsetmonkey538.monkeylib538.modded.api.text;

import net.minecraft.text.Text;
import top.offsetmonkey538.monkeylib538.common.api.text.MonkeyLibText;

public interface ModdedMonkeyLibText extends MonkeyLibText {

    static ModdedMonkeyLibText of(final MonkeyLibText text) {
        return (ModdedMonkeyLibText) text;
    }

    Text getText();
}
