package top.offsetmonkey538.monkeylib538.impl.text;

import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;

public final class MonkeyLibTextImpl implements MonkeyLibText {
    private final MutableText text;

    public MonkeyLibTextImpl(final @NotNull String text) {
        this.text = Text.literal(text);
    }

    @Override
    public @NotNull MonkeyLibText append(final @NotNull MonkeyLibText other) {
        this.text.append(((MonkeyLibTextImpl) other).getText());
        return this;
    }

    @Override
    public @NotNull MonkeyLibText setUnderlined(final boolean newValue) {
        this.text.setStyle(this.text.getStyle().withUnderline(newValue));
        return this;
    }

    @Override
    public @NotNull MonkeyLibText showTextOnHover(final @NotNull MonkeyLibText text) {
        // todo: Create a "common" fabric module and have an api there for creating hover and click events (initialization changed in one of the stupid drops)
        //  Also I guess most of what is currently the 1.21.5 one isn't specific to 1.21.5 and could be put in the "common" fabric one?
        this.text.setStyle(this.text.getStyle().withHoverEvent(new HoverEvent.ShowText(((MonkeyLibTextImpl) text).getText())));
        return this;
    }

    @Override
    public @NotNull MonkeyLibText copyStringOnClick(final @NotNull String string) {
        this.text.setStyle(this.text.getStyle().withClickEvent(new ClickEvent.CopyToClipboard(string)));
        return this;
    }

    public @NotNull Text getText() {
        return text;
    }
}
