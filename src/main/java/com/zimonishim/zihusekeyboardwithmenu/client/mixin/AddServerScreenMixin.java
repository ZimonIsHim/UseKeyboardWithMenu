package com.zimonishim.zihusekeyboardwithmenu.client.mixin;

import net.minecraft.client.gui.screen.AddServerScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AddServerScreen.class)
public abstract class AddServerScreenMixin extends Screen {

    @Shadow
    private TextFieldWidget addressField;

    @Shadow
    private TextFieldWidget serverNameField;

    @Shadow
    protected abstract void addAndClose();

    protected AddServerScreenMixin(Text title) {
        super(title);
    }

    // NOTE: Based on keyPressed() in the CreateWorldScreen class.
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }

        if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {

            if (isValidInput()) { // If fields are filled in.
                this.addAndClose(); // Add server to the serverList and close the menu.
            }

            return true;
        }
        return false;
    }

    private boolean isValidInput() {
        return ServerAddress.isValid(this.addressField.getText()) && !this.serverNameField.getText().isEmpty();
    }
}
