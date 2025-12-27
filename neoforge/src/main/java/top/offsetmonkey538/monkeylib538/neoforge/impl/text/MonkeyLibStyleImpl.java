package top.offsetmonkey538.monkeylib538.neoforge.impl.text;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibStyle;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;
import top.offsetmonkey538.monkeylib538.neoforge.api.text.NeoforgeMonkeyLibStyle;
import top.offsetmonkey538.monkeylib538.neoforge.api.text.NeoforgeMonkeyLibText;
import top.offsetmonkey538.monkeylib538.neoforge.api.text.TextApi;

import java.net.URI;
import java.nio.file.Path;

public final class MonkeyLibStyleImpl implements NeoforgeMonkeyLibStyle {
    private final @NotNull Style style;

    public MonkeyLibStyleImpl(final @NotNull Style style) {
        this.style = style;
    }

    @Override
    public @NotNull Style getStyle() {
        return style;
    }

    @Override
    public @NotNull MonkeyLibStyle withShowText(@NotNull MonkeyLibText value) {
        return new MonkeyLibStyleImpl(style.withHoverEvent(TextApi.createShowText(NeoforgeMonkeyLibText.of(value).getText())));
    }

    @Override
    public @NotNull MonkeyLibStyle withOpenUrl(@NotNull URI value) {
        return new MonkeyLibStyleImpl(style.withClickEvent(TextApi.createOpenUrl(value)));
    }

    @Override
    public @NotNull MonkeyLibStyle withOpenFile(@NotNull Path value) {
        return new MonkeyLibStyleImpl(style.withClickEvent(TextApi.createOpenFile(value)));
    }

    @Override
    public @NotNull MonkeyLibStyle withRunCommand(@NotNull String value) {
        return new MonkeyLibStyleImpl(style.withClickEvent(TextApi.createRunCommand(value)));
    }

    @Override
    public @NotNull MonkeyLibStyle withSuggestCommand(@NotNull String value) {
        return new MonkeyLibStyleImpl(style.withClickEvent(TextApi.createSuggestCommand(value)));
    }

    @Override
    public @NotNull MonkeyLibStyle withCopyToClipboard(@NotNull String value) {
        return new MonkeyLibStyleImpl(style.withClickEvent(TextApi.createCopyToClipboard(value)));
    }

    @Override
    public @NotNull MonkeyLibStyle copyEventsFrom(@NotNull MonkeyLibStyle from) {
        final Style fromStyle = NeoforgeMonkeyLibStyle.of(from).getStyle();
        return new MonkeyLibStyleImpl(style
                .withHoverEvent(fromStyle.getHoverEvent())
                .withClickEvent(fromStyle.getClickEvent())
        );
    }

    @Override
    public @NotNull MonkeyLibStyle withItalic(final boolean italic) {
        return italic == style.isItalic() ? this : new MonkeyLibStyleImpl(style.withItalic(italic));
    }

    @Override
    public @NotNull MonkeyLibStyle withUnderline(boolean underline) {
        return underline == style.isUnderlined() ? this : new MonkeyLibStyleImpl(style.withUnderlined(underline));
    }

    @Override
    public @NotNull MonkeyLibStyle withColor(final int rgbColor) {
        return style.getColor() == null || rgbColor != style.getColor().getValue() ? new MonkeyLibStyleImpl(style.withColor(rgbColor)) : this;
    }

    @Override
    public @Nullable MonkeyLibStyle withFormattingCode(char code) {
        final @Nullable ChatFormatting formatting = ChatFormatting.getByCode(code);
        if (formatting == null) return null;

        return new MonkeyLibStyleImpl(style.applyFormat(formatting));
    }

    @Override
    public boolean equals(final @NotNull MonkeyLibStyle other) {
        return this.style.equals(NeoforgeMonkeyLibStyle.of(other).getStyle());
    }

    public static final class ProviderImpl implements Provider {
        @Override
        public @NotNull MonkeyLibStyle empty() {
            return new MonkeyLibStyleImpl(Style.EMPTY);
        }
    }
}
