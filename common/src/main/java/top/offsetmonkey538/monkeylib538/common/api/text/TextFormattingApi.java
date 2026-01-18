package top.offsetmonkey538.monkeylib538.common.api.text;


import top.offsetmonkey538.monkeylib538.common.api.annotation.Internal;

import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.load;

public interface TextFormattingApi {
    @Internal
    TextFormattingApi INSTANCE = load(TextFormattingApi.class);

    static MonkeyLibText[] styleTextMultiline(final String text) throws Exception {
        return INSTANCE.styleTextMultilineImpl(text);
    }

    static MonkeyLibText styleText(final String text) throws Exception {
        return INSTANCE.styleTextImpl(text);
    }

    @Internal MonkeyLibText[] styleTextMultilineImpl(final String text) throws Exception;
    @Internal MonkeyLibText styleTextImpl(final String text) throws Exception;
}
