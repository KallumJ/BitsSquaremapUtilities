package team.bits.squaremap.utils.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import team.bits.squaremap.utils.map.MapManager;


@Mixin(MinecraftServer.class)
public class AutosaveMixin {

    @Inject(
            method = "saveAll",
            at = @At("TAIL")
    )
    public void onAutoSave(boolean suppressLogs, boolean flush, boolean force, CallbackInfoReturnable<Boolean> cir) {
        MapManager.save();
    }
}
