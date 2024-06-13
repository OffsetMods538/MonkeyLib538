package top.offsetmonkey538.monkeylib538.utils;

import net.minecraft.util.Identifier;

public class IdentifierUtilsImpl implements IdentifierUtils {
    @Override
    public Identifier of(String namespace, String path) {
        return new Identifier(namespace, path);
    }

    @Override
    public Identifier of(String path) {
        return new Identifier(path);
    }
}
