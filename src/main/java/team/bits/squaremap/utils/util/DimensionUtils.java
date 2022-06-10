package team.bits.squaremap.utils.util;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import team.bits.squaremap.utils.map.Dimension;

public class DimensionUtils {
    public static Dimension getDimensionForRegistryKey(RegistryKey<World> dimension) {
        String dimensionStr = dimension.getValue().toString();

        return switch (dimensionStr) {
            case "minecraft:the_nether" -> Dimension.THE_NETHER;
            case "minecraft:the_end" -> Dimension.THE_END;
            default -> Dimension.OVERWORLD;
        };

    }
}
