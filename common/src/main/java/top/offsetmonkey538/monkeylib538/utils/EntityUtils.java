package top.offsetmonkey538.monkeylib538.utils;

import net.minecraft.entity.Entity;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

public interface EntityUtils {
    EntityUtils INSTANCE = load(EntityUtils.class);

    void setOnFireFor(Entity entity, int seconds);
}
