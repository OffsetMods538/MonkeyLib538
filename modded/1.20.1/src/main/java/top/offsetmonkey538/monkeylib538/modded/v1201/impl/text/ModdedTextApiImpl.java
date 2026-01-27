package top.offsetmonkey538.monkeylib538.modded.v1201.impl.text;

import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.world.item.ItemStack;
import top.offsetmonkey538.monkeylib538.modded.api.text.ModdedTextApi;

import java.net.URI;
import java.nio.file.Path;

public final class ModdedTextApiImpl implements ModdedTextApi {
    @Override
    public HoverEvent createShowTextImpl(final Component value) {
        return new HoverEvent(HoverEvent.Action.SHOW_TEXT, value);
    }

    @Override
    public HoverEvent createShowItemImpl(final ItemStack value) {
        return new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackInfo(value));
    }

    @Override
    public HoverEvent createShowEntityImpl(final HoverEvent.EntityTooltipInfo value) {
        return new HoverEvent(HoverEvent.Action.SHOW_ENTITY, value);
    }


    @Override
    public ClickEvent createOpenUrlImpl(final URI value) {
        return new ClickEvent(ClickEvent.Action.OPEN_URL, value.toString());
    }

    @Override
    public ClickEvent createOpenFileImpl(final Path value) {
        return new ClickEvent(ClickEvent.Action.OPEN_FILE, value.toAbsolutePath().toString());
    }

    @Override
    public ClickEvent createRunCommandImpl(final String value) {
        return new ClickEvent(ClickEvent.Action.RUN_COMMAND, value);
    }

    @Override
    public ClickEvent createSuggestCommandImpl(final String value) {
        return new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, value);
    }

    @Override
    public ClickEvent createCopyToClipboardImpl(final String value) {
        return new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, value);
    }
}
