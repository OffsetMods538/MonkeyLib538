package top.offsetmonkey538.monkeylib538.modded.v1205.impl.text;

import net.minecraft.item.ItemStack;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import top.offsetmonkey538.monkeylib538.modded.api.text.ModdedTextApi;

import java.net.URI;
import java.nio.file.Path;

public final class ModdedTextApiImpl implements ModdedTextApi {
    @Override
    public HoverEvent createShowTextImpl(final Text value) {
        return new HoverEvent(HoverEvent.Action.SHOW_TEXT, value);
    }

    @Override
    public HoverEvent createShowItemImpl(final ItemStack value) {
        return new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(value));
    }

    @Override
    public HoverEvent createShowEntityImpl(final HoverEvent.EntityContent value) {
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
