package top.offsetmonkey538.monkeylib538.common.impl.text;

import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

final class MonkeyLibText {
    private final Component text;

    private MonkeyLibText(final Component text) {
        this.text = text.compact();
    }
    
    public Component get() {
        return text;
    }

    public @Nullable MonkeyLibText getLastSibling() {
        return text.children() == null ? null : text.children().isEmpty() ? null : new MonkeyLibText(text.children().getLast());
    }

    public MonkeyLibText setLastSibling(MonkeyLibText newSibling) {
        final List<Component> siblings = new ArrayList<>(text.children());
        siblings.set(siblings.size() - 1, newSibling.get());
        return new MonkeyLibText(text.children(siblings));
    }

    public MonkeyLibText append(final MonkeyLibText other) {
        return new MonkeyLibText(text.append(other.get()));
    }

    public MonkeyLibStyle getStyle() {
        return new MonkeyLibStyle(text.style());
    }

    public MonkeyLibText setStyle(MonkeyLibStyle style) {
        return new MonkeyLibText(text.style(style.get()));
    }

    public String getString() {
        return text.toString();
    }

    public static MonkeyLibText of(final String text) {
        return new MonkeyLibText(Component.text(text));
    }

    public static MonkeyLibText empty() {
        return new MonkeyLibText(Component.empty());
    }
}
