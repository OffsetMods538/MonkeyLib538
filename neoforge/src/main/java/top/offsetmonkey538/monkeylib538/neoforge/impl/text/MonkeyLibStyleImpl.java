package top.offsetmonkey538.monkeylib538.neoforge.impl.text;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import org.jspecify.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibStyle;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;
import top.offsetmonkey538.monkeylib538.neoforge.api.text.NeoforgeMonkeyLibStyle;
import top.offsetmonkey538.monkeylib538.neoforge.api.text.NeoforgeMonkeyLibText;
import top.offsetmonkey538.monkeylib538.neoforge.api.text.TextApi;

import java.net.URI;
import java.nio.file.Path;

public final class MonkeyLibStyleImpl implements NeoforgeMonkeyLibStyle {
    private final Style style;

    public MonkeyLibStyleImpl(final Style style) {
        this.style = style;
    }

    @Override
    public Style getStyle() {
        return style;
    }

    @Override
    public MonkeyLibStyle withShowText(MonkeyLibText value) {
        return new MonkeyLibStyleImpl(style.withHoverEvent(TextApi.createShowText(NeoforgeMonkeyLibText.of(value).getText())));
    }

    @Override
    public MonkeyLibStyle withOpenUrl(URI value) {
        return new MonkeyLibStyleImpl(style.withClickEvent(TextApi.createOpenUrl(value)));
    }

    @Override
    public MonkeyLibStyle withOpenFile(Path value) {
        return new MonkeyLibStyleImpl(style.withClickEvent(TextApi.createOpenFile(value)));
    }

    @Override
    public MonkeyLibStyle withRunCommand(String value) {
        return new MonkeyLibStyleImpl(style.withClickEvent(TextApi.createRunCommand(value)));
    }

    @Override
    public MonkeyLibStyle withSuggestCommand(String value) {
        return new MonkeyLibStyleImpl(style.withClickEvent(TextApi.createSuggestCommand(value)));
    }

    @Override
    public MonkeyLibStyle withCopyToClipboard(String value) {
        return new MonkeyLibStyleImpl(style.withClickEvent(TextApi.createCopyToClipboard(value)));
    }

    @Override
    public MonkeyLibStyle copyEventsFrom(MonkeyLibStyle from) {
        final Style fromStyle = NeoforgeMonkeyLibStyle.of(from).getStyle();
        return new MonkeyLibStyleImpl(style
                .withHoverEvent(fromStyle.getHoverEvent())
                .withClickEvent(fromStyle.getClickEvent())
        );
    }

    @Override
    public MonkeyLibStyle withItalic(final boolean italic) {
        return italic == style.isItalic() ? this : new MonkeyLibStyleImpl(style.withItalic(italic));
    }

    @Override
    public MonkeyLibStyle withUnderline(boolean underline) {
        return underline == style.isUnderlined() ? this : new MonkeyLibStyleImpl(style.withUnderlined(underline));
    }

    @Override
    public MonkeyLibStyle withColor(final int rgbColor) {
        return style.getColor() == null || rgbColor != style.getColor().getValue() ? new MonkeyLibStyleImpl(style.withColor(rgbColor)) : this;
    }

    @Override
    public @Nullable MonkeyLibStyle withFormattingCode(char code) {
        final ChatFormatting formatting = ChatFormatting.getByCode(code);
        if (formatting == null) return null;

        return new MonkeyLibStyleImpl(style.applyFormat(formatting));
    }

    @Override
    public boolean equals(final MonkeyLibStyle other) {
        return this.style.equals(NeoforgeMonkeyLibStyle.of(other).getStyle());
    }

    public static final class ProviderImpl implements Provider {
        @Override
        public MonkeyLibStyle empty() {
            return new MonkeyLibStyleImpl(Style.EMPTY);
        }
    }
}
