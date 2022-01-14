package resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class Resources {

    private static final String configPath = FileSystems.getDefault().getPath("src", "resources", "config.properties").toString();
    public static Locale locale = Locale.getDefault();
    public static ResourceBundle rb;

    public static void setLocale(String language){
        locale = new Locale(language);
        rb = ResourceBundle.getBundle("ResourceBundle", locale);
    }

    public static boolean setup() {
        Properties properties = new Properties();
        try {
            System.out.println(configPath);
            properties.load(new FileInputStream(configPath));

            setLocale(properties.getProperty("language"));
        } catch (IOException ex) {
            System.err.println("Ошибка! Файл с настройками не найден!\nError! Resources file didn't find!");
            return false;
        }
        return true;
    }
}
