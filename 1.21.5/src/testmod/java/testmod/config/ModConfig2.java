package testmod.config;

import blue.endless.jankson.Comment;
import top.offsetmonkey538.monkeylib538.config.Config;

import static testmod.Testmod.MOD_ID;

public class ModConfig2 extends Config<ModConfig2> {

    public String anotherString = "Bye!";

    @Comment("Now that's nice!")
    public int niceNummer = 69;


    @Override
    protected String getName() {
        return MOD_ID + "2";
    }

    @Override
    public ModConfig2 getDefaultConfig() {
        return new ModConfig2();
    }
}
