package top.offsetmonkey538.monkeylib538.n1206.impl.text;

import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.neoforge.api.text.TextApi;

import java.net.URI;
import java.nio.file.Path;

public final class TextApiImpl implements TextApi {
    @Override
    public @NotNull HoverEvent createShowTextImpl(final @NotNull Component value) {
        return new HoverEvent(HoverEvent.Action.SHOW_TEXT, value);
    }

    @Override
    public @NotNull HoverEvent createShowItemImpl(final @NotNull ItemStack value) {
        return new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackInfo(value));
    }

    @Override
    public @NotNull HoverEvent createShowEntityImpl(final @NotNull HoverEvent.EntityTooltipInfo value) {
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
