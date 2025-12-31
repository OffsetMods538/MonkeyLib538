package top.offsetmonkey538.monkeylib538.modded.impl.text;

import org.jspecify.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.common.api.text.MonkeyLibStyle;
import top.offsetmonkey538.monkeylib538.common.api.text.MonkeyLibText;
import top.offsetmonkey538.monkeylib538.modded.api.text.ModdedMonkeyLibStyle;
import top.offsetmonkey538.monkeylib538.modded.api.text.ModdedMonkeyLibText;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public final class MonkeyLibTextImpl implements ModdedMonkeyLibText {
    private final Component text;

    private MonkeyLibTextImpl(final Component text) {
        this.text = text;
    }

    @Override
    public @Nullable MonkeyLibText getLastSibling() {
        return text.getSiblings() == null ? null : text.getSiblings().isEmpty() ? null : new MonkeyLibTextImpl(text.getSiblings().getLast());
    }

    @Override
    public MonkeyLibText setLastSibling(MonkeyLibText newSibling) {
        final List<Component> siblings = text.getSiblings();
        siblings.set(siblings.size() - 1, ModdedMonkeyLibText.of(newSibling).getText());
        return this;
    }

    @Override
    public MonkeyLibText append(final MonkeyLibText other) {
        ensureMutable().append(ModdedMonkeyLibText.of(other).getText());
        return this;
    }

    @Override
    public MonkeyLibStyle getStyle() {
        return new MonkeyLibStyleImpl(this.text.getStyle());
    }

    @Override
    public MonkeyLibText setStyle(MonkeyLibStyle style) {
        ensureMutable().setStyle(ModdedMonkeyLibStyle.of(style).getStyle());
        return this;
    }

    @Override
    public String getString() {
        return this.text.getString();
    }

    @Override
    public Component getText() {
        return text;
    }

    private MutableComponent ensureMutable() {
        if (this.text instanceof MutableComponent mutable) return mutable;
        throw new IllegalStateException("Tried modifying unmodifiable text!");
    }

    public static final class ProviderImpl implements Provider {
        @Override
        public MonkeyLibText of(String text) {
            return new MonkeyLibTextImpl(Component.literal(text));
        }

        @Override
        public MonkeyLibText empty() {
            return new MonkeyLibTextImpl(Component.empty());
        }
    }
}
