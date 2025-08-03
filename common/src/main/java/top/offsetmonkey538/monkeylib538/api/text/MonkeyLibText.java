package top.offsetmonkey538.monkeylib538.api.text;

import org.jetbrains.annotations.NotNull;

public interface MonkeyLibText {
    @NotNull MonkeyLibText append(final @NotNull MonkeyLibText other);
    @NotNull MonkeyLibText setUnderlined(final boolean newValue);
    @NotNull MonkeyLibText showTextOnHover(final @NotNull MonkeyLibText text);
    @NotNull MonkeyLibText copyStringOnClick(final @NotNull String string);
}
