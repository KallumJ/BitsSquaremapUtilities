package team.bits.squaremap.utils.map;

import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import xyz.jpenilla.squaremap.api.Key;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MapMarkerList implements Iterable<MapMarker> {
    private final List<MapMarker> list = new LinkedList<>();

    /**
     * Adds the provided marker to the list
     *
     * @param marker the marker to add
     */
    protected void add(MapMarker marker) {
        // Remove any marker from list and map with same key already in the list
        removeIfPresent(marker.getKey());

        // Add it to the list and render it
        list.add(marker);
        marker.render();

        // Save the current state of the map, as it has now changed
        MapManager.save();
    }

    /**
     * Removes the marker from the list
     *
     * @param marker the marker to remove from the list
     */
    protected void remove(MapMarker marker) {
        // Removes the marker from list and map
        removeIfPresent(marker.getKey());

        // Save the current state of the map, as it has now changed
        MapManager.save();
    }

    @NotNull
    @Override
    public Iterator<MapMarker> iterator() {
        return list.iterator();
    }

    /**
     * Removes and unrenders any marker with the key provided
     *
     * @param key the key to find
     */
    private void removeIfPresent(Key key) {
        // Use this method instead of HashMap, as GSON falls over when using a map here for some reason
        int indexToRemove = -1;
        for (int i = 0; i < list.size(); i++) {
            MapMarker marker = list.get(i);

            if (marker.getKey().equals(key)) {
                indexToRemove = i;
                break;
            }
        }

        if (indexToRemove > -1) {
            MapMarker marker = list.get(indexToRemove);
            marker.unrender();
            list.remove(indexToRemove);
        }
    }

    /**
     * Removes the marker at the position, if it is present in the list
     *
     * @param pos the position to remove
     */
    protected void removeMarkerAtPositionIfPresent(BlockPos pos) {
        for (MapMarker marker : list) {
            if (marker.getBlockPos().equals(pos)) {
                remove(marker);
                break;
            }
        }
    }
}
