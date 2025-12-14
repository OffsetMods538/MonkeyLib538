package top.offsetmonkey538.monkeylib538.f1219.impl.text;

import net.minecraft.item.ItemStack;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.fabric.api.text.TextApi;

import java.net.URI;
import java.nio.file.Path;

public final class TextApiImpl implements TextApi {
    @Override
    public @NotNull HoverEvent createShowTextImpl(final @NotNull Text value) {
        return new HoverEvent.ShowText(value);
    }

    @Override
    public @NotNull HoverEvent createShowItemImpl(final @NotNull ItemStack value) {
        return new HoverEvent.ShowItem(value);
    }

    @Override
    public @NotNull HoverEvent createShowEntityImpl(final @NotNull HoverEvent.EntityContent value) {
        return new HoverEvent.ShowEntity(value);
    }


    @Override
    public @NotNull ClickEvent createOpenUrlImpl(final @NotNull URI value) {
        return new ClickEvent.OpenUrl(value);
    }

    @Override
    public @NotNull ClickEvent createOpenFileImpl(final @NotNull Path value) {
        return new ClickEvent.OpenFile(value);
    }

    @Override
    public @NotNull ClickEvent createRunCommandImpl(final @NotNull String value) {
        return new ClickEvent.RunCommand(value);
    }

    @Override
    public @NotNull ClickEvent createSuggestCommandImpl(final @NotNull String value) {
        return new ClickEvent.SuggestCommand(value);
    }

    @Override
    public @NotNull ClickEvent createCopyToClipboardImpl(final @NotNull String value) {
        return new ClickEvent.CopyToClipboard(value);
    }
}
