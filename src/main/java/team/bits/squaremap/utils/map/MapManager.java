package team.bits.squaremap.utils.map;

import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.jpenilla.squaremap.api.Key;
import xyz.jpenilla.squaremap.api.Squaremap;
import xyz.jpenilla.squaremap.api.SquaremapProvider;
import xyz.jpenilla.squaremap.api.WorldIdentifier;

public final class MapManager {
    public static final WorldIdentifier OVERWORLD_IDENTIFIER = WorldIdentifier.create("minecraft", "overworld");
    public static final WorldIdentifier NETHER_IDENTIFIER = WorldIdentifier.create("minecraft", "the_nether");
    public static final WorldIdentifier THE_END_IDENTIFIER = WorldIdentifier.create("minecraft", "the_end");

    private static final String INIT_SUCC = "Successfully initalised the Map Manager";
    private static final String INIT_ERR = "Map Manager failed to initalise. Is Squaremap installed? All map operations will be ignored";
    private static final Logger LOGGER = LogManager.getLogger();
    public static Squaremap api;
    private static boolean initalised;

    static {
        try {
            api = SquaremapProvider.get();
            initalised = true;
        } catch (IllegalStateException ex) {
            initalised = false;
        }
    }

    private MapManager() {
    }

    /**
     * Loads everything required for running the map
     */
    public static void load() {
        if (initalised) {
            MapIcons.loadIcons();
            MapLayers.loadLayers();
            MapMarkers.loadMarkers();
            LOGGER.warn(INIT_SUCC);
        } else {
            LOGGER.warn(INIT_ERR);
        }
    }

    /**
     * Saves the current state of the map
     */
    public static void save() {
        if (initalised) {
            MapMarkers.saveMarkers();
        }
    }

    /**
     * Adds a waypoint marker
     *
     * @param player       the player who placed the waypoint
     * @param pos          the position the waypoint was placed
     * @param dimension    the dimension the waypoint is in
     * @param waypointName the name to give the waypoint
     * @param color        the colour of the waypoint
     */
    public static void addWaypointMarker(ServerPlayerEntity player, BlockPos pos, Dimension dimension, String waypointName, DyeColor color) {
        if (initalised) {
            String username = player.getName().getString();

            Key layer = switch (dimension) {
                case OVERWORLD -> MapLayers.OVERWORLD_WAYPOINTS;
                case THE_NETHER -> MapLayers.NETHER_WAYPOINTS;
                case THE_END -> MapLayers.END_WAYPOINTS;
            };

            // Incorporate username in key so players cant override someone else's identically named banner
            Key key = Key.of(username + "_" + waypointName.replaceAll("[^A-Za-z0-9]", ""));

            MapMarker marker = new MapMarker(key, MapIcons.getIconForDyeColour(color), waypointName, layer, pos);
            MapMarkers.addMarker(marker);
        }
    }

    /**
     * Finds whether the provided block is a block that can be used to create markers
     *
     * @param blockState the block state to check
     * @return true if can be used to create markers, false otherwise
     */
    public static boolean isBlockTypeOfWaypoint(BlockState blockState   ) {
        return blockState.isIn(BlockTags.BANNERS);
    }

    /**
     * Removes a marker at the provided position if it is present in the list
     *
     * @param pos the position to remove
     */
    public static void removeMarkerAtPosIfPresent(BlockPos pos) {
        if (initalised) {
            MapMarkers.removeMarkerAtPosIfPresent(pos);
        }
    }
}
