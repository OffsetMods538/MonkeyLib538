package testmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.EnchantmentEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testmod.config.ConfigTestmod;
import top.offsetmonkey538.monkeylib538.utils.TextUtils;

public class Testmod implements ModInitializer {
    public static final String MOD_ID = "testmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        new ConfigTestmod().onInitialize();
        ServerMessageEvents.CHAT_MESSAGE.register((message, sender, params) -> {
            try {
                sender.server.getPlayerManager().broadcast(TextUtils.INSTANCE.getStyledText("&lThis is bold&r and &othis is italic."), false);
                sender.server.getPlayerManager().broadcast(TextUtils.INSTANCE.getStyledText("The meaning of life is &knever gonna give you up&r."), false);
                sender.server.getPlayerManager().broadcast(TextUtils.INSTANCE.getStyledText("&mThis is strikethrough&r and &nthis is underlined."), false);
                sender.server.getPlayerManager().broadcast(TextUtils.INSTANCE.getStyledText("Roses are &cred&r and violets are &9blue&r"), false);
                sender.server.getPlayerManager().broadcast(TextUtils.INSTANCE.getStyledText("&#FF0000t&#FF4000h&#FF7F00i&#FFBF00s &#FFFF00i&#80FF00s &#00FF00r&#008080a&#0000FFi&#2600C1n&#4B0082b&#7000ABo&#9400D3w&r and so are you <3"), false);
            } catch (Exception e) {
                throw new RuntimeException("Failed to stylize text!", e);
            }
        });
    }
}
