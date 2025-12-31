package top.offsetmonkey538.monkeylib538.modded.v1219.impl.text;

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
        return new HoverEvent.ShowText(value);
    }

    @Override
    public HoverEvent createShowItemImpl(final ItemStack value) {
        return new HoverEvent.ShowItem(value);
    }

    @Override
    public HoverEvent createShowEntityImpl(final HoverEvent.EntityContent value) {
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
