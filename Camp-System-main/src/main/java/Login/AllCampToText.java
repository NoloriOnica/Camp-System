package Login;

import java.io.*;
import java.util.ArrayList;
import Camp.Camp;

public class AllCampToText {

    private static String filePath = "././././data/camp.txt";

    public static void writeCampsToFile(ArrayList<Camp> allCamps) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(allCamps);
            System.out.println("Camps written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Camp> readCampsFromFile() {
        ArrayList<Camp> allCamps = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            // Check if the file is not empty before reading
            if (ois.available() > 0) {
                Object obj = ois.readObject();
                if (obj instanceof ArrayList) {
                    allCamps = (ArrayList<Camp>) obj;
                    System.out.println("Camps read from file successfully.");
                }
            } else {
                System.out.println("File is empty.");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return allCamps;
    }
}
