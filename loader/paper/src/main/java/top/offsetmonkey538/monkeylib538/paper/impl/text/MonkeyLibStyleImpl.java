package top.offsetmonkey538.monkeylib538.paper.impl.text;

import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import top.offsetmonkey538.monkeylib538.common.api.text.MonkeyLibStyle;
import top.offsetmonkey538.monkeylib538.common.api.text.MonkeyLibText;
import top.offsetmonkey538.monkeylib538.paper.api.text.PaperMonkeyLibStyle;
import top.offsetmonkey538.monkeylib538.paper.api.text.PaperMonkeyLibText;

import java.net.URI;
import java.nio.file.Path;

public record MonkeyLibStyleImpl(Style style) implements PaperMonkeyLibStyle {

    @Override
    public MonkeyLibStyle withShowText(MonkeyLibText value) {
        return new MonkeyLibStyleImpl(style.hoverEvent(HoverEvent.showText(PaperMonkeyLibText.of(value).getText())));
    }

    @Override
    public MonkeyLibStyle withOpenUrl(URI value) {
        return new MonkeyLibStyleImpl(style.clickEvent(ClickEvent.openUrl(value.toString())));
    }

    @Override
    public MonkeyLibStyle withOpenFile(Path value) {
        return new MonkeyLibStyleImpl(style.clickEvent(ClickEvent.openFile(value.toString())));
    }

    @Override
    public MonkeyLibStyle withRunCommand(String value) {
        return new MonkeyLibStyleImpl(style.clickEvent(ClickEvent.runCommand(value)));
    }

    @Override
    public MonkeyLibStyle withSuggestCommand(String value) {
        return new MonkeyLibStyleImpl(style.clickEvent(ClickEvent.suggestCommand(value)));
    }

    @Override
    public MonkeyLibStyle withCopyToClipboard(String value) {
        return new MonkeyLibStyleImpl(style.clickEvent(ClickEvent.copyToClipboard(value)));
    }

    @Override
    public MonkeyLibStyle copyEventsFrom(MonkeyLibStyle from) {
        final Style fromStyle = PaperMonkeyLibStyle.of(from).style();
        return new MonkeyLibStyleImpl(style
                .hoverEvent(fromStyle.hoverEvent())
                .clickEvent(fromStyle.clickEvent())
        );
    }

    @Override
    public MonkeyLibStyle withObfuscated(boolean obfuscated) {
        return withDecoration(TextDecoration.OBFUSCATED, obfuscated);
    }

    @Override
    public MonkeyLibStyle withBold(boolean bold) {
        return withDecoration(TextDecoration.BOLD, bold);
    }

    @Override
    public MonkeyLibStyle withStrikethrough(boolean strikethrough) {
        return withDecoration(TextDecoration.STRIKETHROUGH, strikethrough);
    }

    @Override
    public MonkeyLibStyle withUnderline(boolean underline) {
        return withDecoration(TextDecoration.UNDERLINED, underline);
    }

    @Override
    public MonkeyLibStyle withItalic(final boolean italic) {
        return withDecoration(TextDecoration.ITALIC, italic);
    }

    private MonkeyLibStyle withDecoration(final TextDecoration decoration, final boolean value) {
        return value == style.hasDecoration(decoration) ? this : new MonkeyLibStyleImpl(style.decoration(decoration, TextDecoration.State.byBoolean(value)));
    }

    @Override
    public MonkeyLibStyle withColor(final int rgbColor) {
        //noinspection DataFlowIssue: IDE doesn't understand that VVV down there, style.color() can't be null because it's an OR after the null check
        return style.color() == null || rgbColor != style.color().value() ? new MonkeyLibStyleImpl(style.color(TextColor.color(rgbColor))) : this;
    }

    @Override
    public boolean equals(final MonkeyLibStyle other) {
        return this.style.equals(PaperMonkeyLibStyle.of(other).style());
    }

    public static final class ProviderImpl implements Provider {
        @Override
        public MonkeyLibStyle empty() {
            return new MonkeyLibStyleImpl(Style.empty());
        }
    }
}
