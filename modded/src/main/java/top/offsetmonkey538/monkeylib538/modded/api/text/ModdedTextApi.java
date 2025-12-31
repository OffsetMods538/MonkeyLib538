package top.offsetmonkey538.monkeylib538.modded.api.text;

import net.minecraft.item.ItemStack;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;

import java.net.URI;
import java.nio.file.Path;

import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.load;

public interface ModdedTextApi {
    ModdedTextApi INSTANCE = load(ModdedTextApi.class);


    static HoverEvent createShowText(final Text value) {
        return INSTANCE.createShowTextImpl(value);
    }
    static HoverEvent createShowItem(final ItemStack value) {
        return INSTANCE.createShowItemImpl(value);
    }
    static HoverEvent createShowEntity(final HoverEvent.EntityContent value) {
        return INSTANCE.createShowEntityImpl(value);
    }

    static ClickEvent createOpenUrl(final URI value) {
        return INSTANCE.createOpenUrlImpl(value);
    }
    static ClickEvent createOpenFile(final Path value) {
        return INSTANCE.createOpenFileImpl(value);
    }
    static ClickEvent createRunCommand(final String value) {
        return INSTANCE.createRunCommandImpl(value);
    }
    static ClickEvent createSuggestCommand(final String value) {
        return INSTANCE.createSuggestCommandImpl(value);
    }
    static ClickEvent createCopyToClipboard(final String value) {
        return INSTANCE.createCopyToClipboardImpl(value);
    }


    HoverEvent createShowTextImpl(final Text value);
    HoverEvent createShowItemImpl(final ItemStack value);
    HoverEvent createShowEntityImpl(final HoverEvent.EntityContent value);

    ClickEvent createOpenUrlImpl(final URI value);
    ClickEvent createOpenFileImpl(final Path value);
    ClickEvent createRunCommandImpl(final String value);
    ClickEvent createSuggestCommandImpl(final String value);
    ClickEvent createCopyToClipboardImpl(final String value);
}
