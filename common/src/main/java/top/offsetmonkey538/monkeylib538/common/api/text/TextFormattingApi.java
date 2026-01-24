package top.offsetmonkey538.monkeylib538.common.api.text;


import org.jspecify.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.common.api.annotation.Internal;
import top.offsetmonkey538.monkeylib538.common.impl.text.TextFormattingImpl;

public interface TextFormattingApi {
    // Can't use load cause logger is used in load and logger depends on this
    @Internal
    TextFormattingApi INSTANCE = new TextFormattingImpl();

    static String replaceArgs(String text, @Nullable Object arg1) {
        return INSTANCE.replaceArgsImpl(text, arg1);
    }

    static String replaceArgs(String text, @Nullable Object arg1, @Nullable Object arg2) {
        return INSTANCE.replaceArgsImpl(text, arg1, arg2);
    }

    static String replaceArgs(String text, @Nullable Object... args) {
        return INSTANCE.replaceArgsImpl(text, args);
    }

    static MonkeyLibText[] styleTextMultiline(final String text) throws Exception {
        return INSTANCE.styleTextMultilineImpl(text);
    }

    static MonkeyLibText styleText(final String text) throws Exception {
        return INSTANCE.styleTextImpl(text);
    }

    @Internal String replaceArgsImpl(String text, @Nullable Object arg1);
    @Internal String replaceArgsImpl(String text, @Nullable Object arg1, @Nullable Object arg2);
    @Internal String replaceArgsImpl(String text, @Nullable Object... args);
    @Internal MonkeyLibText[] styleTextMultilineImpl(final String text) throws Exception;
    @Internal MonkeyLibText styleTextImpl(final String text) throws Exception;
}
