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
            Object obj = ois.readObject();
            while (obj != null) {
                if (obj instanceof ArrayList) {
                    allCamps = (ArrayList<Camp>) obj;
                    System.out.println("Camps read from file successfully.");
                }
                obj = ois.readObject();
            }
        } catch (EOFException e) {
            // End of file reached, do nothing
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return allCamps;
    }
}
