package team.bits.squaremap.utils.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import team.bits.squaremap.utils.map.MapManager;

@Mixin(World.class)
public class BreakWaypointMixin {
    @Inject(
            method = "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;II)Z",
            at = @At("HEAD")
    )
    public void stateChanged(BlockPos pos, BlockState state, int flags, int maxUpdateDepth, CallbackInfoReturnable<Boolean> cir) {
        World world = (World) (Object) this;
        BlockState blockState = world.getBlockState(pos);
        // If block having state updated could be a waypoint
        if (MapManager.isBlockTypeOfWaypoint(blockState)) {

            // If state is being updated with air (being broken)
            if (state.isAir()) {

                // Remove the waypoint, if it is present
                MapManager.removeMarkerAtPosIfPresent(pos);
            }
        }
    }
}
