package top.offsetmonkey538.monkeylib538.neoforge.impl.text;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jspecify.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibStyle;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;
import top.offsetmonkey538.monkeylib538.neoforge.api.text.NeoforgeMonkeyLibStyle;
import top.offsetmonkey538.monkeylib538.neoforge.api.text.NeoforgeMonkeyLibText;

import java.util.List;

public final class MonkeyLibTextImpl implements NeoforgeMonkeyLibText {
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
        siblings.set(siblings.size() - 1, NeoforgeMonkeyLibText.of(newSibling).getText());
        return this;
    }

    @Override
    public MonkeyLibText append(final MonkeyLibText other) {
        ensureMutable().append(NeoforgeMonkeyLibText.of(other).getText());
        return this;
    }

    @Override
    public MonkeyLibStyle getStyle() {
        return new MonkeyLibStyleImpl(this.text.getStyle());
    }

    @Override
    public MonkeyLibText setStyle(MonkeyLibStyle style) {
        ensureMutable().setStyle(NeoforgeMonkeyLibStyle.of(style).getStyle());
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
