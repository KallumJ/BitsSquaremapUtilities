package team.bits.squaremap.utils.map;

import com.google.gson.Gson;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class MapMarkers {
    private static final Gson GSON = new Gson();
    private static final File MARKERS_FILE = new File("markers.json");
    private static final String SAVE_ERR = "An error occurred saving map markers to file";
    private static final Logger LOGGER = LogManager.getLogger();
    private static MapMarkerList markers = new MapMarkerList();

    /**
     * Loads the markers from the file
     */
    protected static void loadMarkers() {
        LOGGER.info("Loading map markers...");
        // Get the markers from file
        try {
            markers = GSON.fromJson(new FileReader(MARKERS_FILE), MapMarkerList.class);
        } catch (IOException e) {
            LOGGER.info("No map markers found to load.");
        }

        // If markers is still somehow null, create a new list
        if (markers == null) {
            markers = new MapMarkerList();
        }

        // Render any markers that are loaded
        for (MapMarker marker : markers) {
            marker.render();
        }
    }

    /**
     * Saves the markers currently in the list to the markers file
     */
    protected static void saveMarkers() {
        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream(MARKERS_FILE));
            writer.write(GSON.toJson(markers));
            writer.close();
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(SAVE_ERR);
        }
    }

    /**
     * Adds a marker to the list
     *
     * @param marker the marker to add
     */
    protected static void addMarker(MapMarker marker) {
        markers.add(marker);
    }

    /**
     * Removes a marker at the provided position if it is present in the list
     *
     * @param pos the position to remove
     */
    protected static void removeMarkerAtPosIfPresent(BlockPos pos) {
        markers.removeMarkerAtPositionIfPresent(pos);
    }
}
