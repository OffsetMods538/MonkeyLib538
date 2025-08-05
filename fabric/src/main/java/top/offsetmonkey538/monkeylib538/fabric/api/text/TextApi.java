package top.offsetmonkey538.monkeylib538.fabric.api.text;

import net.minecraft.item.ItemStack;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.nio.file.Path;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

public interface TextApi {
    @ApiStatus.Internal
    TextApi INSTANCE = load(TextApi.class);


    static @NotNull HoverEvent createShowText(final @NotNull Text value) {
        return INSTANCE.createShowTextImpl(value);
    }
    static @NotNull HoverEvent createShowItem(final @NotNull ItemStack value) {
        return INSTANCE.createShowItemImpl(value);
    }
    static @NotNull HoverEvent createShowEntity(final @NotNull HoverEvent.EntityContent value) {
        return INSTANCE.createShowEntityImpl(value);
    }

    static @NotNull ClickEvent createOpenUrl(final @NotNull URI value) {
        return INSTANCE.createOpenUrlImpl(value);
    }
    static @NotNull ClickEvent createOpenFile(final @NotNull Path value) {
        return INSTANCE.createOpenFileImpl(value);
    }
    static @NotNull ClickEvent createRunCommand(final @NotNull String value) {
        return INSTANCE.createRunCommandImpl(value);
    }
    static @NotNull ClickEvent createSuggestCommand(final @NotNull String value) {
        return INSTANCE.createSuggestCommandImpl(value);
    }
    static @NotNull ClickEvent createCopyToClipboard(final @NotNull String value) {
        return INSTANCE.createCopyToClipboardImpl(value);
    }


    @ApiStatus.Internal
    @NotNull HoverEvent createShowTextImpl(final @NotNull Text value);
    @ApiStatus.Internal
    @NotNull HoverEvent createShowItemImpl(final @NotNull ItemStack value);
    @ApiStatus.Internal
    @NotNull HoverEvent createShowEntityImpl(final @NotNull HoverEvent.EntityContent value);

    @ApiStatus.Internal
    @NotNull ClickEvent createOpenUrlImpl(final @NotNull URI value);
    @ApiStatus.Internal
    @NotNull ClickEvent createOpenFileImpl(final @NotNull Path value);
    @ApiStatus.Internal
    @NotNull ClickEvent createRunCommandImpl(final @NotNull String value);
    @ApiStatus.Internal
    @NotNull ClickEvent createSuggestCommandImpl(final @NotNull String value);
    @ApiStatus.Internal
    @NotNull ClickEvent createCopyToClipboardImpl(final @NotNull String value);
}
