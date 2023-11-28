package helpers;
import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Utils {
    public static final int FAIL_STATUS = -1;
    public static final String DELIMITER = "=";

    public static HashMap<String, String> readCredentials(String filename) {
        /*
         * Utility function to read some private credentials
         * from file.
         */

        HashMap<String, String> credentials = new HashMap<String, String>();

        try {
            File fileObject = new File(filename);
            Scanner fileReader = new Scanner(fileObject);

            while (fileReader.hasNextLine()) {
                String[] content = fileReader.nextLine().split(DELIMITER);
                credentials.put(content[0], content[1]);
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(filename + " is not found.");
            System.exit(FAIL_STATUS);
        }

        return credentials;
    }
}
