package top.offsetmonkey538.monkeylib538.common.api.text;

import top.offsetmonkey538.monkeylib538.common.impl.text.TextFormattingImpl;
import top.offsetmonkey538.offsetutils538.api.annotation.Internal;

public interface TextFormattingApi {
    // Can't use load cause logger is used in load and logger depends on this
    @Internal
    TextFormattingApi INSTANCE = new TextFormattingImpl();

    static MonkeyLibText[] styleTextMultiline(final String text) throws Exception {
        return INSTANCE.styleTextMultilineImpl(text);
    }

    static MonkeyLibText styleText(final String text) throws Exception {
        return INSTANCE.styleTextImpl(text);
    }

    @Internal MonkeyLibText[] styleTextMultilineImpl(final String text) throws Exception;
    @Internal
    MonkeyLibText styleTextImpl(final String text) throws Exception;
}
