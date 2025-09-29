package fn10.desktopClicker.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;


public class SettingsManager {

    private transient static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public transient static final String MainFolderPath = Path.of(System.getProperty("user.home"), "/UnistoreCreator/")
            .toString();
    public transient static final Path SettingsPath = Path.of(MainFolderPath, "settings.json");

    

    /**
     * Returns a SettingsManager instance from the saved file. Or a blank one if it
     * doesnt exist
     * 
     * @return a (new) SettingsManager
     * @throws JsonSyntaxException If user modified, and broke the file
     * @throws IOException         If reading the file failed.
     */
    public static SettingsManager load() throws JsonSyntaxException, IOException {
        if (SettingsPath.toFile().exists()) {
            return gson.fromJson(Files.readString(SettingsPath), SettingsManager.class);
        } else {
            return new SettingsManager();
        }
    }

    /**
     * Saves this instance to a file. Make sure to do this whenever you change a
     * value.
     * 
     * @throws IOException If writing the file failed.
     */
    public void save() throws IOException {
        Files.writeString(SettingsPath, gson.toJson(this), StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }

}
