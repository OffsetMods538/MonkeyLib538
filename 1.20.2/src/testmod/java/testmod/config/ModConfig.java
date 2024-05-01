package testmod.config;

import blue.endless.jankson.Comment;
import top.offsetmonkey538.monkeylib538.config.Config;

import static testmod.Testmod.*;

public class ModConfig extends Config {

    public String hello = "Hello!";

    @Comment("Now that's nice!")
    public int Number = 69;


    @Override
    protected String getName() {
        return MOD_ID;
    }
}
