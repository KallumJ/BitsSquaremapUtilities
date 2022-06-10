package team.bits.squaremap.utils.map;

import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.jpenilla.squaremap.api.Key;
import xyz.jpenilla.squaremap.api.Point;
import xyz.jpenilla.squaremap.api.marker.Icon;
import xyz.jpenilla.squaremap.api.marker.Marker;
import xyz.jpenilla.squaremap.api.marker.MarkerOptions;

public class MapMarker {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String RENDER_ERR = "Tried to render marker %s, but the provided layer was not found";

    private Key key;
    private Key icon;
    private String tooltip;
    private Key layer;
    private BlockPos blockPos;

    protected MapMarker(Key key, Key icon, String tooltip, Key layer, BlockPos blockpos) {
        this.key = key;
        this.icon = icon;
        this.tooltip = tooltip;
        this.layer = layer;
        this.blockPos = blockpos;
    }

    public MapMarker() {
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Key getIcon() {
        return icon;
    }

    public void setIcon(Key icon) {
        this.icon = icon;
    }

    public Point getMapPos() {
        return Point.of(blockPos.getX(), blockPos.getZ());
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public Key getLayer() {
        return layer;
    }

    public void setLayer(Key layer) {
        this.layer = layer;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public void setBlockPos(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    /**
     * Renders this marker on the map
     */
    protected void render() {
        MarkerOptions markerOptions = MarkerOptions.defaultOptions()
                .asBuilder()
                .hoverTooltip(tooltip)
                .build();

        Icon iconMarker = Marker.icon(getMapPos(), icon, 25);
        iconMarker.markerOptions(markerOptions);

        Layer layerObj = MapLayers.getLayerForKey(layer);
        if (layerObj != null) {
            layerObj.getProvider().addMarker(key, iconMarker);
        } else {
            LOGGER.warn(String.format(RENDER_ERR, tooltip));
        }

    }

    /**
     * Unrenders this marker on the map
     */
    protected void unrender() {
        Layer layerObj = MapLayers.getLayerForKey(layer);
        if (layerObj != null) {
            layerObj.getProvider().removeMarker(key);
        } else {
            LOGGER.warn(String.format(RENDER_ERR, tooltip));
        }
    }
}
