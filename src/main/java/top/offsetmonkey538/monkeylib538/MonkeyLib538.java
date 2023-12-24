package top.offsetmonkey538.monkeylib538;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonkeyLib538 implements ModInitializer {
	public static final String MOD_ID = "monkeylib538";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// Do stuff
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}
