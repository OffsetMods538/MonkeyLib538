package top.offsetmonkey538.monkeylib538.common.api.text;


import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.load;

public interface TextFormattingApi {
    TextFormattingApi INSTANCE = load(TextFormattingApi.class);

    static MonkeyLibText[] styleTextMultiline(final String text) throws Exception {
        return INSTANCE.styleTextMultilineImpl(text);
    }

    static MonkeyLibText styleText(final String text) throws Exception {
        return INSTANCE.styleTextImpl(text);
    }

    MonkeyLibText[] styleTextMultilineImpl(final String text) throws Exception;
    MonkeyLibText styleTextImpl(final String text) throws Exception;
}
