package top.offsetmonkey538.monkeylib538.common.api.text;

import net.kyori.adventure.text.Component;
import top.offsetmonkey538.monkeylib538.common.impl.text.TextFormattingImpl;
import top.offsetmonkey538.offsetutils538.api.annotation.Internal;

/**
 * Parses my stupid custom format into a {@link Component}.
 * @deprecated Not deprecated for removal, but this format is ass and shouldn't be used except for converting existing strings in configs and then serializing them with something more sensible like MiniMessage.
 */
@SuppressWarnings("DeprecatedIsStillUsed")
@Deprecated
public interface TextFormattingApi {
    @Internal
    TextFormattingApi INSTANCE = new TextFormattingImpl();

    static Component styleText(final String text) throws Exception {
        return INSTANCE.styleTextImpl(text);
    }

    @Internal
    Component styleTextImpl(final String text) throws Exception;
}
