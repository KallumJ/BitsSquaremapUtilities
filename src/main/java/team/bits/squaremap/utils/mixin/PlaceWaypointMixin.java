package team.bits.squaremap.utils.mixin;

import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.bits.squaremap.utils.map.Dimension;
import team.bits.squaremap.utils.map.MapManager;
import team.bits.squaremap.utils.util.DimensionUtils;

@Mixin(AbstractBannerBlock.class)
public class PlaceWaypointMixin {

    @Inject(
            method = "onPlaced",
            at = @At("TAIL")
    )
    public void onPlace(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        AbstractBannerBlock bannerBlock = (AbstractBannerBlock) (Object) this;

        // If a player has placed a waypoint
        if (itemStack.hasCustomName() && placer instanceof ServerPlayerEntity player) {
            String waypointName = itemStack.getName().getString();

            // Ominous banners have an empty custom name
            if (!waypointName.equals("")) {
                DyeColor color = bannerBlock.getColor();
                Dimension dimension = DimensionUtils.getDimensionForRegistryKey(world.getRegistryKey());

                MapManager.addWaypointMarker(player, pos, dimension, waypointName, color);
            }
        }
    }
}
