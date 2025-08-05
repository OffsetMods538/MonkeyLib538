package top.offsetmonkey538.monkeylib538.f1205.impl.text;

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
        return new HoverEvent(HoverEvent.Action.SHOW_TEXT, value);
    }

    @Override
    public @NotNull HoverEvent createShowItemImpl(final @NotNull ItemStack value) {
        return new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(value));
    }

    @Override
    public @NotNull HoverEvent createShowEntityImpl(final @NotNull HoverEvent.EntityContent value) {
        return new HoverEvent(HoverEvent.Action.SHOW_ENTITY, value);
    }


    @Override
    public @NotNull ClickEvent createOpenUrlImpl(final @NotNull URI value) {
        return new ClickEvent(ClickEvent.Action.OPEN_URL, value.toString());
    }

    @Override
    public @NotNull ClickEvent createOpenFileImpl(final @NotNull Path value) {
        return new ClickEvent(ClickEvent.Action.OPEN_FILE, value.toAbsolutePath().toString());
    }

    @Override
    public @NotNull ClickEvent createRunCommandImpl(final @NotNull String value) {
        return new ClickEvent(ClickEvent.Action.RUN_COMMAND, value);
    }

    @Override
    public @NotNull ClickEvent createSuggestCommandImpl(final @NotNull String value) {
        return new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, value);
    }

    @Override
    public @NotNull ClickEvent createCopyToClipboardImpl(final @NotNull String value) {
        return new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, value);
    }
}
