package top.offsetmonkey538.monkeylib538.fabric.api.text;

import net.minecraft.text.Text;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;

public interface FabricMonkeyLibText extends MonkeyLibText {

    static FabricMonkeyLibText of(final MonkeyLibText text) {
        return (FabricMonkeyLibText) text;
    }

    Text getText();
}
