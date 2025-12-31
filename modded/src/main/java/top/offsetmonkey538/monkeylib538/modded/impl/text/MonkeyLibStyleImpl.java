package top.offsetmonkey538.monkeylib538.modded.impl.text;

import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import org.jspecify.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.common.api.text.MonkeyLibStyle;
import top.offsetmonkey538.monkeylib538.common.api.text.MonkeyLibText;
import top.offsetmonkey538.monkeylib538.modded.api.text.ModdedMonkeyLibStyle;
import top.offsetmonkey538.monkeylib538.modded.api.text.ModdedMonkeyLibText;
import top.offsetmonkey538.monkeylib538.modded.api.text.ModdedTextApi;

import java.net.URI;
import java.nio.file.Path;

public final class MonkeyLibStyleImpl implements ModdedMonkeyLibStyle {
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
        return new MonkeyLibStyleImpl(style.withHoverEvent(ModdedTextApi.createShowText(ModdedMonkeyLibText.of(value).getText())));
    }

    @Override
    public MonkeyLibStyle withOpenUrl(URI value) {
        return new MonkeyLibStyleImpl(style.withClickEvent(ModdedTextApi.createOpenUrl(value)));
    }

    @Override
    public MonkeyLibStyle withOpenFile(Path value) {
        return new MonkeyLibStyleImpl(style.withClickEvent(ModdedTextApi.createOpenFile(value)));
    }

    @Override
    public MonkeyLibStyle withRunCommand(String value) {
        return new MonkeyLibStyleImpl(style.withClickEvent(ModdedTextApi.createRunCommand(value)));
    }

    @Override
    public MonkeyLibStyle withSuggestCommand(String value) {
        return new MonkeyLibStyleImpl(style.withClickEvent(ModdedTextApi.createSuggestCommand(value)));
    }

    @Override
    public MonkeyLibStyle withCopyToClipboard(String value) {
        return new MonkeyLibStyleImpl(style.withClickEvent(ModdedTextApi.createCopyToClipboard(value)));
    }

    @Override
    public MonkeyLibStyle copyEventsFrom(MonkeyLibStyle from) {
        final Style fromStyle = ModdedMonkeyLibStyle.of(from).getStyle();
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
        return underline == style.isUnderlined() ? this : new MonkeyLibStyleImpl(style.withUnderline(underline));
    }

    @Override
    public MonkeyLibStyle withColor(final int rgbColor) {
        return style.getColor() == null || rgbColor != style.getColor().getRgb() ? new MonkeyLibStyleImpl(style.withColor(rgbColor)) : this;
    }

    @Override
    public @Nullable MonkeyLibStyle withFormattingCode(char code) {
        final Formatting formatting = Formatting.byCode(code);
        if (formatting == null) return null;

        return new MonkeyLibStyleImpl(style.withFormatting(formatting));
    }

    @Override
    public boolean equals(final MonkeyLibStyle other) {
        return this.style.equals(ModdedMonkeyLibStyle.of(other).getStyle());
    }

    public static final class ProviderImpl implements Provider {
        @Override
        public MonkeyLibStyle empty() {
            return new MonkeyLibStyleImpl(Style.EMPTY);
        }
    }
}
