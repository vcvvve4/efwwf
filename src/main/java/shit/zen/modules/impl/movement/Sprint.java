package shit.zen.modules.impl.movement;

import net.minecraft.client.gui.screens.ChatScreen;
import shit.zen.event.EventTarget;
import shit.zen.event.impl.RotationEvent;
import shit.zen.modules.Category;
import shit.zen.modules.Module;
import shit.zen.modules.impl.player.InventoryManager;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", Category.MOVEMENT);
        setEnabled(true);
    }

    @EventTarget
    public void onRotation(RotationEvent event) {
        if (mc.player == null || mc.level == null || !mc.player.isAlive() || mc.player.isSpectator()) return;
        GuiMove guiMove = GuiMove.INSTANCE;
        if (guiMove != null && guiMove.isEnabled() && InventoryManager.isPerformingAction) return;
        if (mc.screen != null && (mc.screen instanceof ChatScreen || guiMove == null || !guiMove.isEnabled())) return;

        float threshold = mc.player.isUnderWater() ? 1.0E-5F : 0.8F;
        boolean hasFood = mc.player.getFoodData().getFoodLevel() > 6 || mc.player.getAbilities().mayfly;
        if (mc.player.zza > threshold && hasFood && !mc.player.isPassenger() && !mc.player.isShiftKeyDown()
            && !mc.player.isUsingItem() && !mc.player.isFallFlying()
            && (!mc.player.horizontalCollision || mc.player.minorHorizontalCollision)) {
            mc.player.setSprinting(true);
        }
    }
}
