package top.offsetmonkey538.monkeylib538.utils;

import net.minecraft.util.Identifier;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

public interface IdentifierUtils {
    IdentifierUtils INSTANCE = load(IdentifierUtils.class);

    Identifier of(String namespace, String path);
    Identifier of(String path);
}
