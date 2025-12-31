package top.offsetmonkey538.monkeylib538.modded.api.text;

import java.net.URI;
import java.nio.file.Path;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.world.item.ItemStack;

import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.load;

public interface ModdedTextApi {
    ModdedTextApi INSTANCE = load(ModdedTextApi.class);


    static HoverEvent createShowText(final Component value) {
        return INSTANCE.createShowTextImpl(value);
    }
    static HoverEvent createShowItem(final ItemStack value) {
        return INSTANCE.createShowItemImpl(value);
    }
    static HoverEvent createShowEntity(final HoverEvent.EntityTooltipInfo value) {
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


    HoverEvent createShowTextImpl(final Component value);
    HoverEvent createShowItemImpl(final ItemStack value);
    HoverEvent createShowEntityImpl(final HoverEvent.EntityTooltipInfo value);

    ClickEvent createOpenUrlImpl(final URI value);
    ClickEvent createOpenFileImpl(final Path value);
    ClickEvent createRunCommandImpl(final String value);
    ClickEvent createSuggestCommandImpl(final String value);
    ClickEvent createCopyToClipboardImpl(final String value);
}
