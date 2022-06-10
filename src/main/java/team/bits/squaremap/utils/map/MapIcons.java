package team.bits.squaremap.utils.map;

import net.minecraft.util.DyeColor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.jpenilla.squaremap.api.Key;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class MapIcons {
    private static final List<Key> ICONS_LIST = new LinkedList<>();
    private static final String ICONS_PATH = "map-icons";
    private static final String REGISTER_ICON_ERR = "An error occurred registering icon %s. Skipping this icon.";
    private static final Logger LOGGER = LogManager.getLogger();

    private static final Key BLACK_BANNER = addIcon("black-banner.png");
    private static final Key BLUE_BANNER = addIcon("blue-banner.png");
    private static final Key BROWN_BANNER = addIcon("brown-banner.png");
    private static final Key CYAN_BANNER = addIcon("cyan-banner.png");
    private static final Key GRAY_BANNER = addIcon("gray-banner.png");
    private static final Key GREEN_BANNER = addIcon("green-banner.png");
    private static final Key LIGHT_BLUE_BANNER = addIcon("light-blue-banner.png");
    private static final Key LIGHT_GRAY_BANNER = addIcon("light-gray-banner.png");
    private static final Key LIME_BANNER = addIcon("lime-banner.png");
    private static final Key MAGENTA_BANNER = addIcon("magenta-banner.png");
    private static final Key ORANGE_BANNER = addIcon("orange-banner.png");
    private static final Key PINK_BANNER = addIcon("pink-banner.png");
    private static final Key PURPLE_BANNER = addIcon("purple-banner.png");
    private static final Key RED_BANNER = addIcon("red-banner.png");
    private static final Key WHITE_BANNER = addIcon("white-banner.png");
    private static final Key YELLOW_BANNER = addIcon("yellow-banner.png");

    /**
     * Adds an icon to the list of icons
     *
     * @param iconFile the filename of the icon, that is present in the correct file path (resources/map-icons/)
     * @return the identifyng Key of this icon
     */
    private static Key addIcon(String iconFile) {
        Key key = Key.of(iconFile);
        ICONS_LIST.add(key);
        return key;
    }

    /**
     * Registers all the icons in the list into the map's icon registry
     */
    protected static void loadIcons() {
        LOGGER.info("Registering map icons...");
        for (Key icon : ICONS_LIST) {
            String iconFile = icon.getKey();
            try {
                String uri = ICONS_PATH + "/" + iconFile;

                InputStream iconIs = MapIcons.class.getClassLoader().getResourceAsStream(uri);
                if (iconIs == null) {
                    throw new IOException();
                }

                BufferedImage image = ImageIO.read(iconIs);

                MapManager.api.iconRegistry().register(icon, image);
            } catch (IOException ex) {
                LOGGER.error(String.format(REGISTER_ICON_ERR, iconFile));
            }
        }
    }

    /**
     * Get the icon key for the provided DyeColor
     *
     * @param dyeColor the colour to get the banner for
     * @return the icon corresponding to the provided colour
     */
    protected static Key getIconForDyeColour(DyeColor dyeColor) {
        return switch (dyeColor) {
            case RED -> RED_BANNER;
            case BLUE -> BLUE_BANNER;
            case CYAN -> CYAN_BANNER;
            case GRAY -> GRAY_BANNER;
            case LIME -> LIME_BANNER;
            case PINK -> PINK_BANNER;
            case BLACK -> BLACK_BANNER;
            case BROWN -> BROWN_BANNER;
            case GREEN -> GREEN_BANNER;
            case WHITE -> WHITE_BANNER;
            case ORANGE -> ORANGE_BANNER;
            case PURPLE -> PURPLE_BANNER;
            case YELLOW -> YELLOW_BANNER;
            case MAGENTA -> MAGENTA_BANNER;
            case LIGHT_BLUE -> LIGHT_BLUE_BANNER;
            case LIGHT_GRAY -> LIGHT_GRAY_BANNER;
        };
    }
}
