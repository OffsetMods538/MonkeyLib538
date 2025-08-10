package top.offsetmonkey538.monkeylib538.fabric.impl.text;

import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibStyle;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;
import top.offsetmonkey538.monkeylib538.fabric.api.text.FabricMonkeyLibStyle;
import top.offsetmonkey538.monkeylib538.fabric.api.text.FabricMonkeyLibText;
import top.offsetmonkey538.monkeylib538.fabric.api.text.TextApi;

import java.net.URI;
import java.nio.file.Path;

public final class MonkeyLibStyleImpl implements FabricMonkeyLibStyle {
    private final @NotNull Style style;

    @ApiStatus.Internal
    public MonkeyLibStyleImpl(final @NotNull Style style) {
        this.style = style;
    }

    @Override
    public @NotNull Style getStyle() {
        return style;
    }

    @Override
    public @NotNull MonkeyLibStyle withShowText(@NotNull MonkeyLibText value) {
        return new MonkeyLibStyleImpl(style.withHoverEvent(TextApi.createShowText(FabricMonkeyLibText.of(value).getText())));
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
        final Style fromStyle = FabricMonkeyLibStyle.of(from).getStyle();
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
        return underline == style.isUnderlined() ? this : new MonkeyLibStyleImpl(style.withUnderline(underline));
    }

    @Override
    public @NotNull MonkeyLibStyle withColor(final int rgbColor) {
        return style.getColor() == null || rgbColor != style.getColor().getRgb() ? new MonkeyLibStyleImpl(style.withColor(rgbColor)) : this;
    }

    @Override
    public @Nullable MonkeyLibStyle withFormattingCode(char code) {
        final @Nullable Formatting formatting = Formatting.byCode(code);
        if (formatting == null) return null;

        return new MonkeyLibStyleImpl(style.withFormatting(formatting));
    }

    @Override
    public boolean equals(final @NotNull MonkeyLibStyle other) {
        return this.style.equals(FabricMonkeyLibStyle.of(other).getStyle());
    }

    public static final class ProviderImpl implements Provider {
        @Override
        public @NotNull MonkeyLibStyle empty() {
            return new MonkeyLibStyleImpl(Style.EMPTY);
        }
    }
}
