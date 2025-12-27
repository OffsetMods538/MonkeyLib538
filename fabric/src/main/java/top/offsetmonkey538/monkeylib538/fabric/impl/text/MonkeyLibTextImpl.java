package top.offsetmonkey538.monkeylib538.fabric.impl.text;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.jspecify.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibStyle;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;
import top.offsetmonkey538.monkeylib538.fabric.api.text.FabricMonkeyLibStyle;
import top.offsetmonkey538.monkeylib538.fabric.api.text.FabricMonkeyLibText;

import java.util.List;

public final class MonkeyLibTextImpl implements FabricMonkeyLibText {
    private final Text text;

    private MonkeyLibTextImpl(final Text text) {
        this.text = text;
    }

    @Override
    public @Nullable MonkeyLibText getLastSibling() {
        return text.getSiblings() == null ? null : text.getSiblings().isEmpty() ? null : new MonkeyLibTextImpl(text.getSiblings().getLast());
    }

    @Override
    public MonkeyLibText setLastSibling(MonkeyLibText newSibling) {
        final List<Text> siblings = text.getSiblings();
        siblings.set(siblings.size() - 1, FabricMonkeyLibText.of(newSibling).getText());
        return this;
    }

    @Override
    public MonkeyLibText append(final MonkeyLibText other) {
        ensureMutable().append(FabricMonkeyLibText.of(other).getText());
        return this;
    }

    @Override
    public MonkeyLibStyle getStyle() {
        return new MonkeyLibStyleImpl(this.text.getStyle());
    }

    @Override
    public MonkeyLibText setStyle(MonkeyLibStyle style) {
        ensureMutable().setStyle(FabricMonkeyLibStyle.of(style).getStyle());
        return this;
    }

    @Override
    public String getString() {
        return this.text.getString();
    }

    @Override
    public Text getText() {
        return text;
    }

    private MutableText ensureMutable() {
        if (this.text instanceof MutableText mutable) return mutable;
        throw new IllegalStateException("Tried modifying unmodifiable text!");
    }

    public static final class ProviderImpl implements Provider {
        @Override
        public MonkeyLibText of(String text) {
            return new MonkeyLibTextImpl(Text.literal(text));
        }

        @Override
        public MonkeyLibText empty() {
            return new MonkeyLibTextImpl(Text.empty());
        }
    }
}
