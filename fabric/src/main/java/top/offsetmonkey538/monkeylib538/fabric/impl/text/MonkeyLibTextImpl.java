package top.offsetmonkey538.monkeylib538.fabric.impl.text;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibStyle;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;
import top.offsetmonkey538.monkeylib538.fabric.api.text.FabricMonkeyLibStyle;
import top.offsetmonkey538.monkeylib538.fabric.api.text.FabricMonkeyLibText;
import top.offsetmonkey538.monkeylib538.fabric.api.text.TextApi;

import java.util.List;

public final class MonkeyLibTextImpl implements FabricMonkeyLibText {
    private final @NotNull Text text;

    private MonkeyLibTextImpl(final @NotNull Text text) {
        this.text = text;
    }

    @Override
    public @Nullable MonkeyLibText getLastSibling() {
        return text.getSiblings() == null ? null : new MonkeyLibTextImpl(text.getSiblings().getLast());
    }

    @Override
    public @NotNull MonkeyLibText setLastSibling(@NotNull MonkeyLibText newSibling) {
        final List<Text> siblings = text.getSiblings();
        siblings.set(siblings.size() - 1, FabricMonkeyLibText.of(newSibling).getText());
        return this;
    }

    @Override
    public @NotNull MonkeyLibText append(final @NotNull MonkeyLibText other) {
        ensureMutable().append(FabricMonkeyLibText.of(other).getText());
        return this;
    }

    @Override
    public @NotNull MonkeyLibStyle getStyle() {
        return new MonkeyLibStyleImpl(this.text.getStyle());
    }

    @Override
    public @NotNull MonkeyLibText setStyle(@NotNull MonkeyLibStyle style) {
        ensureMutable().setStyle(FabricMonkeyLibStyle.of(style).getStyle());
        return this;
    }

    @Override
    public @NotNull String getString() {
        return this.text.getString();
    }

    @Override
    public @NotNull Text getText() {
        return text;
    }

    private MutableText ensureMutable() {
        if (this.text instanceof MutableText mutable) return mutable;
        throw new IllegalStateException("Tried modifying unmodifiable text!");
    }

    public static final class ProviderImpl implements Provider {
        @Override
        public @NotNull MonkeyLibText of(@NotNull String text) {
            return new MonkeyLibTextImpl(Text.literal(text));
        }

        @Override
        public @NotNull MonkeyLibText empty() {
            return new MonkeyLibTextImpl(Text.empty());
        }
    }
}
