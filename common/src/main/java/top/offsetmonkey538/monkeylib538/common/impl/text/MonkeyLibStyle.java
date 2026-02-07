package top.offsetmonkey538.monkeylib538.common.impl.text;

import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.jspecify.annotations.Nullable;

import java.net.URI;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Function;

record MonkeyLibStyle(Style style) {
    private static final Map<Character, Function<MonkeyLibStyle, MonkeyLibStyle>> FORMATTING_CODES = Map.ofEntries(
            Map.entry('0', style -> style.withColor(NamedTextColor.BLACK.value())),
            Map.entry('1', style -> style.withColor(NamedTextColor.DARK_BLUE.value())),
            Map.entry('2', style -> style.withColor(NamedTextColor.DARK_GREEN.value())),
            Map.entry('3', style -> style.withColor(NamedTextColor.DARK_AQUA.value())),
            Map.entry('4', style -> style.withColor(NamedTextColor.DARK_RED.value())),
            Map.entry('5', style -> style.withColor(NamedTextColor.DARK_PURPLE.value())),
            Map.entry('6', style -> style.withColor(NamedTextColor.GOLD.value())),
            Map.entry('7', style -> style.withColor(NamedTextColor.GRAY.value())),
            Map.entry('8', style -> style.withColor(NamedTextColor.DARK_GRAY.value())),
            Map.entry('9', style -> style.withColor(NamedTextColor.BLUE.value())),
            Map.entry('a', style -> style.withColor(NamedTextColor.GREEN.value())),
            Map.entry('b', style -> style.withColor(NamedTextColor.AQUA.value())),
            Map.entry('c', style -> style.withColor(NamedTextColor.RED.value())),
            Map.entry('d', style -> style.withColor(NamedTextColor.LIGHT_PURPLE.value())),
            Map.entry('e', style -> style.withColor(NamedTextColor.YELLOW.value())),
            Map.entry('f', style -> style.withColor(NamedTextColor.WHITE.value())),
            Map.entry('k', style -> style.withObfuscated(true)),
            Map.entry('l', style -> style.withBold(true)),
            Map.entry('m', style -> style.withStrikethrough(true)),
            Map.entry('n', style -> style.withUnderline(true)),
            Map.entry('o', style -> style.withItalic(true)),
            Map.entry('r', style -> MonkeyLibStyle.empty())
    );

    public Style get() {
        return style;
    }

    public MonkeyLibStyle withShowText(MonkeyLibText value) {
        return new top.offsetmonkey538.monkeylib538.common.impl.text.MonkeyLibStyle(style.hoverEvent(HoverEvent.showText(value.get())));
    }

    public MonkeyLibStyle withOpenUrl(URI value) {
        return new top.offsetmonkey538.monkeylib538.common.impl.text.MonkeyLibStyle(style.clickEvent(ClickEvent.openUrl(value.toString())));
    }

    public MonkeyLibStyle withOpenFile(Path value) {
        return new top.offsetmonkey538.monkeylib538.common.impl.text.MonkeyLibStyle(style.clickEvent(ClickEvent.openFile(value.toString())));
    }

    public MonkeyLibStyle withRunCommand(String value) {
        return new top.offsetmonkey538.monkeylib538.common.impl.text.MonkeyLibStyle(style.clickEvent(ClickEvent.runCommand(value)));
    }

    public MonkeyLibStyle withSuggestCommand(String value) {
        return new top.offsetmonkey538.monkeylib538.common.impl.text.MonkeyLibStyle(style.clickEvent(ClickEvent.suggestCommand(value)));
    }

    public MonkeyLibStyle withCopyToClipboard(String value) {
        return new top.offsetmonkey538.monkeylib538.common.impl.text.MonkeyLibStyle(style.clickEvent(ClickEvent.copyToClipboard(value)));
    }

    public MonkeyLibStyle copyEventsFrom(MonkeyLibStyle from) {
        final Style fromStyle = from.get();
        return new top.offsetmonkey538.monkeylib538.common.impl.text.MonkeyLibStyle(style
                .hoverEvent(fromStyle.hoverEvent())
                .clickEvent(fromStyle.clickEvent())
        );
    }

    public MonkeyLibStyle withObfuscated(boolean obfuscated) {
        return withDecoration(TextDecoration.OBFUSCATED, obfuscated);
    }

    public MonkeyLibStyle withBold(boolean bold) {
        return withDecoration(TextDecoration.BOLD, bold);
    }

    public MonkeyLibStyle withStrikethrough(boolean strikethrough) {
        return withDecoration(TextDecoration.STRIKETHROUGH, strikethrough);
    }

    public MonkeyLibStyle withUnderline(boolean underline) {
        return withDecoration(TextDecoration.UNDERLINED, underline);
    }

    public MonkeyLibStyle withItalic(final boolean italic) {
        return withDecoration(TextDecoration.ITALIC, italic);
    }

    private MonkeyLibStyle withDecoration(final TextDecoration decoration, final boolean value) {
        return value == style.hasDecoration(decoration) ? this : new top.offsetmonkey538.monkeylib538.common.impl.text.MonkeyLibStyle(style.decoration(decoration, TextDecoration.State.byBoolean(value)));
    }

    public MonkeyLibStyle withColor(final int rgbColor) {
        //noinspection DataFlowIssue: IDE doesn't understand that VVV down there, style.color() can't be null because it's an OR after the null check
        return style.color() == null || rgbColor != style.color().value() ? new top.offsetmonkey538.monkeylib538.common.impl.text.MonkeyLibStyle(style.color(TextColor.color(rgbColor))) : this;
    }

    public @Nullable MonkeyLibStyle withFormattingCode(final char code) {
        final Function<MonkeyLibStyle, MonkeyLibStyle> action = FORMATTING_CODES.get(code);

        if (action == null) return null;
        return action.apply(this);
    }

    public boolean equals(final MonkeyLibStyle other) {
        return this.style.equals(other.get());
    }

    static MonkeyLibStyle empty() {
        return new MonkeyLibStyle(Style.empty());
    }
}
