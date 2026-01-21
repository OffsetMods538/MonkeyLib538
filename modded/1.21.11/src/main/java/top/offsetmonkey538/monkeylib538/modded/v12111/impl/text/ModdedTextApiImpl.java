package top.offsetmonkey538.monkeylib538.modded.v12111.impl.text;

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
        return new HoverEvent.ShowText(value);
    }

    @Override
    public HoverEvent createShowItemImpl(final ItemStack value) {
        return new HoverEvent.ShowItem(value);
    }

    @Override
    public HoverEvent createShowEntityImpl(final HoverEvent.EntityTooltipInfo value) {
        return new HoverEvent.ShowEntity(value);
    }


    @Override
    public ClickEvent createOpenUrlImpl(final URI value) {
        return new ClickEvent.OpenUrl(value);
    }

    @Override
    public ClickEvent createOpenFileImpl(final Path value) {
        return new ClickEvent.OpenFile(value);
    }

    @Override
    public ClickEvent createRunCommandImpl(final String value) {
        return new ClickEvent.RunCommand(value);
    }

    @Override
    public ClickEvent createSuggestCommandImpl(final String value) {
        return new ClickEvent.SuggestCommand(value);
    }

    @Override
    public ClickEvent createCopyToClipboardImpl(final String value) {
        return new ClickEvent.CopyToClipboard(value);
    }
}
