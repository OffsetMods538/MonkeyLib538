package top.offsetmonkey538.monkeylib538.utils;

import net.minecraft.entity.Entity;

public class EntityUtilsImpl implements EntityUtils {
    @Override
    public void setOnFireFor(Entity entity, int seconds) {
        entity.setOnFireFor(seconds);
    }
}
