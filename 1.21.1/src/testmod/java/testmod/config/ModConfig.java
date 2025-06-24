package testmod.config;

import blue.endless.jankson.Comment;
import top.offsetmonkey538.monkeylib538.config.Config;
import top.offsetmonkey538.monkeylib538.config.Datafixer;

import java.util.List;

import static testmod.Testmod.*;

public class ModConfig extends Config<ModConfig> {

    public String hello = "Hello!";

    //@Comment("Now that's nice!")
    //public int Number = 69;

    //@Comment("this is the new number")
    //public int coolNumber = 9;

    @Comment("This is an even better number!")
    public int wowSuchNumber = 42;


    @Override
    protected String getName() {
        return MOD_ID;
    }

    @Override
    public ModConfig getDefaultConfig() {
        return null;
    }

    @Override
    protected int getConfigVersion() {
        return 2;
    }

    @Override
    protected List<Datafixer> getDatafixers() {
        return List.of(
                (original, jankson) -> original.put("coolNumber", original.get("Number")),
                (original, jankson) -> original.put("wowSuchNumber", original.get("coolNumber"))
        );
    }
}
