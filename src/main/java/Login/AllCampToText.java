package Login;

import java.io.*;
import java.util.ArrayList;
import Camp.Camp;

/**
 * Handles reading and writing Camp objects to a text file using serialization.
 */

public class AllCampToText {

    private static String filePath = "././././data/camp.txt";

    /**
     * Writes the ArrayList of Camp objects to a file using serialization.
     *
     * @param allCamps The ArrayList of Camp objects to be written to the file.
     */
    
    public static void writeCampsToFile(ArrayList<Camp> allCamps) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(allCamps);
           // System.out.println("Camps written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads ArrayList of Camp objects from a file using deserialization.
     *
     * @return The ArrayList of Camp objects read from the file.
     */
    
    @SuppressWarnings("unchecked")
	public static ArrayList<Camp> readCampsFromFile() {
        ArrayList<Camp> allCamps = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Object obj = ois.readObject();
            while (obj != null) {
                if (obj instanceof ArrayList) {
                    allCamps = (ArrayList<Camp>) obj;
                    //System.out.println("Camps read from file successfully.");
                }
                obj = ois.readObject();
            }
        } catch (EOFException e) {
            
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return allCamps;
    }

}
