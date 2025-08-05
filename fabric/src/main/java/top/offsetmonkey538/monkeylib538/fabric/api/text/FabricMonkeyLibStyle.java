package top.offsetmonkey538.monkeylib538.fabric.api.text;

import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibStyle;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;

public interface FabricMonkeyLibStyle extends MonkeyLibStyle {

    static @NotNull FabricMonkeyLibStyle of(final @NotNull MonkeyLibStyle style) {
        return (FabricMonkeyLibStyle) style;
    }

    @NotNull Style getStyle();
}
