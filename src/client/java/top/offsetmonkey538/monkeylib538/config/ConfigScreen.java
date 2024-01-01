package top.offsetmonkey538.monkeylib538.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.OptionListWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.function.BiConsumer;

public abstract class ConfigScreen<T extends Config> extends GameOptionsScreen {
    protected OptionListWidget list;
    protected final BiConsumer<String, Exception> errorHandler;

    public ConfigScreen(Screen parent, BiConsumer<String, Exception> errorHandler) {
        super(parent, MinecraftClient.getInstance().options, Text.translatable("options.title"));
        this.errorHandler = errorHandler;
    }

    @Override
    protected void init() {
        this.list = new OptionListWidget(this.client, this.width, this.height - 64, 32, 25);
        //TODO: this.list.addAll(
        //TODO:
        //TODO: );
        this.addSelectableChild(this.list);

        final int buttonHeight = this.height - 27;

        // Reset to default values button
        this.addDrawableChild(
                ButtonWidget.builder(Text.translatable("monkeylib538.default"), (button) -> {
                    resetConfig();
                    this.clearAndInit();
                })
                        .position(this.width / 4 - 50, buttonHeight)
                        .size(100, 20)
                        .tooltip(Tooltip.of(Text.translatable("monkeylib538.default.tooltip").withColor(Formatting.RED.getColorValue())))
                        .build()
        );

        // Cancel the changes button
        this.addDrawableChild(
                ButtonWidget.builder(ScreenTexts.CANCEL, (button) -> {
                    ConfigManager.load(getConfig(), this.errorHandler);
                    this.client.setScreen(this.parent);
                })
                        .position(this.width / 2 - 50, buttonHeight)
                        .size(100, 20)
                        .build()
        );

        // Save the changes button
        this.addDrawableChild(
                ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
                    ConfigManager.save(getConfig(), this.errorHandler);
                    this.client.setScreen(this.parent);
                })
                        .position((this.width / 4) * 3 - 50, buttonHeight)
                        .size(100, 20)
                        .build()
        );
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.list.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 5, 0xffffff);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackgroundTexture(context);
    }

    @Override
    public void removed() {
        ConfigManager.save(getConfig(), errorHandler);
    }

    protected abstract void resetConfig();

    protected abstract T getConfig();
}
