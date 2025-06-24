package testmod.config;

import blue.endless.jankson.Comment;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.offsetconfig538.api.config.Config;

import java.nio.file.Path;

import static testmod.Testmod.MOD_ID;

public class ModConfig2 implements Config {

    public String anotherString = "Bye!";

    @Comment("Now that's nice!")
    public int niceNummer = 69;

    @Override
    public @NotNull Path getFilePath() {
        return FabricLoader.getInstance().getConfigDir().resolve(MOD_ID + "2.json");
    }
}
