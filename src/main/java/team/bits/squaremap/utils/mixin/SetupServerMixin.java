package team.bits.squaremap.utils.mixin;

import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import team.bits.squaremap.utils.map.MapManager;

@Mixin(MinecraftDedicatedServer.class)
public class SetupServerMixin {

    @Inject(
            method = "setupServer",
            at = @At("TAIL")
    )
    public void onFinishSetup(CallbackInfoReturnable<Boolean> cir) {
        boolean successfullyStarted = cir.getReturnValue();
        if (successfullyStarted) {
            MapManager.load();
        }
    }
}
