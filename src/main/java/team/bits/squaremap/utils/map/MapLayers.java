package team.bits.squaremap.utils.map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.jpenilla.squaremap.api.Key;
import xyz.jpenilla.squaremap.api.MapWorld;
import xyz.jpenilla.squaremap.api.SimpleLayerProvider;
import xyz.jpenilla.squaremap.api.WorldIdentifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MapLayers {
    private static final Map<Key, Layer> OVERWORLD_LAYERS = new HashMap<>();
    protected static final Key OVERWORLD_WAYPOINTS = addOverworldLayer(new Layer(
            Key.of("overworld_markers"),
            createLayerProvider("Waypoints")
    ));

    private static final Map<Key, Layer> NETHER_LAYERS = new HashMap<>();
    protected static final Key NETHER_WAYPOINTS = addNetherLayer(new Layer(
            Key.of("nether_markers"),
            createLayerProvider("Waypoints")
    ));

    private static final Map<Key, Layer> END_LAYERS = new HashMap<>();
    protected static final Key END_WAYPOINTS = addEndLayer(new Layer(
            Key.of("end_markers"),
            createLayerProvider("Waypoints")
    ));
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Gets the layer with the provided key
     *
     * @param key the key of the layer to get
     * @return the found layer. null if no layer is found
     */
    static Layer getLayerForKey(Key key) {
        Layer layer = OVERWORLD_LAYERS.get(key);

        if (layer == null) {
            layer = NETHER_LAYERS.get(key);
        }

        if (layer == null) {
            layer = END_LAYERS.get(key);
        }

        return layer;
    }

    /**
     * Adds an overworld layer to the map
     *
     * @param layer the layer to add
     * @return the key of the layer that has been added
     */
    private static Key addOverworldLayer(Layer layer) {
        layer.setDimension(Dimension.OVERWORLD);

        OVERWORLD_LAYERS.put(layer.getKey(), layer);

        return layer.getKey();
    }

    /**
     * Adds an nether layer to the map
     *
     * @param layer the layer to add
     * @return the key of the layer that has been added
     */
    private static Key addNetherLayer(Layer layer) {
        layer.setDimension(Dimension.THE_NETHER);

        NETHER_LAYERS.put(layer.getKey(), layer);

        return layer.getKey();
    }

    /**
     * Adds an end layer to the map
     *
     * @param layer the layer to add
     * @return the key of the layer that has been added
     */
    private static Key addEndLayer(Layer layer) {
        layer.setDimension(Dimension.THE_END);

        END_LAYERS.put(layer.getKey(), layer);

        return layer.getKey();
    }

    /**
     * Loads all the layers in the HashMaps, into the map's layer registry
     */
    protected static void loadLayers() {
        LOGGER.info("Registering map layers...");
        for (Layer layer : OVERWORLD_LAYERS.values()) {
            registerLayer(layer);
        }

        for (Layer layer : NETHER_LAYERS.values()) {
            registerLayer(layer);
        }

        for (Layer layer : END_LAYERS.values()) {
            registerLayer(layer);
        }
    }

    /**
     * Adds the provided layer, to the map's layer registry
     */
    private static void registerLayer(Layer layer) {
        WorldIdentifier worldId = layer.getWorldId();
        Key key = layer.getKey();
        SimpleLayerProvider provider = layer.getProvider();

        Optional<MapWorld> optMapWorld = MapManager.api.getWorldIfEnabled(worldId);
        optMapWorld.ifPresent(mapWorld -> mapWorld.layerRegistry().register(key, provider));
    }

    /**
     * Creates a new layer provider
     *
     * @param name the name of the layer
     * @return a SimpleLayerProvider for this layer
     */
    private static SimpleLayerProvider createLayerProvider(String name) {
        return SimpleLayerProvider.builder(name)
                .showControls(true)
                .defaultHidden(false)
                .layerPriority(5)
                .zIndex(250)
                .build();
    }

}

/**
 * A simple class to represent a layer
 */
class Layer {
    private final Key key;
    private final SimpleLayerProvider provider;
    private WorldIdentifier worldId;

    protected Layer(Key key, SimpleLayerProvider provider) {
        this.key = key;
        this.provider = provider;
    }

    /**
     * Sets the world identifier for this layer according to the passed dimension
     *
     * @param dimension the dimension to get the identifier for
     */
    protected void setDimension(Dimension dimension) {
        this.worldId = switch (dimension) {
            case OVERWORLD -> MapManager.OVERWORLD_IDENTIFIER;
            case THE_END -> MapManager.THE_END_IDENTIFIER;
            case THE_NETHER -> MapManager.NETHER_IDENTIFIER;
        };
    }


    protected Key getKey() {
        return key;
    }

    protected WorldIdentifier getWorldId() {
        return worldId;
    }

    protected SimpleLayerProvider getProvider() {
        return provider;
    }
}
