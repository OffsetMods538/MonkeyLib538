package top.offsetmonkey538.monkeylib538.paper.impl.text;

import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.common.api.text.MonkeyLibStyle;
import top.offsetmonkey538.monkeylib538.common.api.text.MonkeyLibText;
import top.offsetmonkey538.monkeylib538.paper.api.text.PaperMonkeyLibStyle;
import top.offsetmonkey538.monkeylib538.paper.api.text.PaperMonkeyLibText;

import java.util.ArrayList;
import java.util.List;

public final class MonkeyLibTextImpl implements PaperMonkeyLibText {
    private final Component text;

    private MonkeyLibTextImpl(final Component text) {
        this.text = text;
    }

    @Override
    public @Nullable MonkeyLibText getLastSibling() {
        return text.children() == null ? null : text.children().isEmpty() ? null : new MonkeyLibTextImpl(text.children().getLast());
    }

    @Override
    public MonkeyLibText setLastSibling(MonkeyLibText newSibling) {
        final List<Component> siblings = new ArrayList<>(text.children());
        siblings.set(siblings.size() - 1, PaperMonkeyLibText.of(newSibling).getText());
        return new MonkeyLibTextImpl(text.children(siblings));
    }

    @Override
    public MonkeyLibText append(final MonkeyLibText other) {
        return new MonkeyLibTextImpl(text.append(PaperMonkeyLibText.of(other).getText()));
    }

    @Override
    public MonkeyLibStyle getStyle() {
        return new MonkeyLibStyleImpl(text.style());
    }

    @Override
    public MonkeyLibText setStyle(MonkeyLibStyle style) {
        return new MonkeyLibTextImpl(text.style(PaperMonkeyLibStyle.of(style).style()));
    }

    @Override
    public String getString() {
        return text.toString();
    }

    @Override
    public Component getText() {
        return text;
    }

    public static final class ProviderImpl implements Provider {
        @Override
        public MonkeyLibText of(String text) {
            return new MonkeyLibTextImpl(Component.text(text));
        }

        @Override
        public MonkeyLibText empty() {
            return new MonkeyLibTextImpl(Component.empty());
        }
    }
}
