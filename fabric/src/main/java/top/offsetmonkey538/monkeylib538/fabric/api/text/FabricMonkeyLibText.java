package top.offsetmonkey538.monkeylib538.fabric.api.text;

import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;

public interface FabricMonkeyLibText extends MonkeyLibText {

    static @NotNull FabricMonkeyLibText of(final @NotNull MonkeyLibText text) {
        return (FabricMonkeyLibText) text;
    }

    @NotNull Text getText();
}
