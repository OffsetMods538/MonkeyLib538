package testmod.config;

import blue.endless.jankson.Comment;
import top.offsetmonkey538.monkeylib538.config.Config;

import java.util.HashMap;
import java.util.Map;

import static testmod.Testmod.*;

public class ModConfig extends Config<ModConfig> {

    public String hello = "Hello!";

    @Comment("Now that's nice!")
    public int Number = 69;


    @Override
    protected String getName() {
        return MOD_ID;
    }

    @Override
    public ModConfig getDefaultConfig() {
        return new ModConfig();
    }
}
