package top.offsetmonkey538.monkeylib538.fabric.impl.text;

import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;
import top.offsetmonkey538.monkeylib538.fabric.api.text.TextApi;

public final class MonkeyLibTextImpl implements MonkeyLibText {
    private final MutableText text;

    private MonkeyLibTextImpl(final @NotNull String text) {
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
        this.text.setStyle(this.text.getStyle().withHoverEvent(TextApi.createShowText(((MonkeyLibTextImpl) text).getText())));
        return this;
    }

    @Override
    public @NotNull MonkeyLibText copyStringOnClick(final @NotNull String string) {
        this.text.setStyle(this.text.getStyle().withClickEvent(TextApi.createCopyToClipboard(string)));
        return this;
    }

    public @NotNull Text getText() {
        return text;
    }

    public static final class ProviderImpl implements Provider {
        @Override
        public @NotNull MonkeyLibText of(@NotNull String text) {
            return new MonkeyLibTextImpl(text);
        }
    }
}
