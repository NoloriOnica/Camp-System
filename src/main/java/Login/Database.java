package Login;

import java.io.*;	
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.Scanner;


/**
 * Class for handling user data and authentication using Excel files.
 */

class Database {
	private static final String DATABASE_FILE = "././././data/database.xlsx";
	private static final String STUDENTLIST = "././././data/student_list.xlsx";
	private static final String STAFFLIST = "././././data/staff_list.xlsx";
	
	/**
     * Initializes the database by updating it with user data from student and staff lists.
     */
	
	public static void initializeDatabase() {
		try {
			List<List<String>> existingUsers = readExistingUsers();

			List<List<String>> studentList = readUserDataFromExcel(STUDENTLIST, "S");

			List<List<String>> staffList = readUserDataFromExcel(STAFFLIST, "T");

			List<List<String>> toAddDatabase = new ArrayList<>();
			//
			for (List<String> student : studentList) {
				if (!isUserInDatabase(existingUsers, student.get(3))) {
					toAddDatabase.add(student);
				}
			}

			for (List<String> staff : staffList) {
				if (!isUserInDatabase(existingUsers, staff.get(3))) {
					toAddDatabase.add(staff);
				}
			}


			writeUserDataToExcel(DATABASE_FILE, toAddDatabase);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


    /**
     * Reads existing user data from the database.xlsx Excel file.
     *
     * @return List of Lists containing user data.
     * @throws IOException If an I/O error occurs.
     */
	
	private static List<List<String>> readExistingUsers() throws IOException {
		List<List<String>> existingUsers = new ArrayList<>();

		FileInputStream fis = new FileInputStream(DATABASE_FILE);
		Workbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);

		int rows = sheet.getPhysicalNumberOfRows();;


		for (int rowIndex = 1; rowIndex < rows; rowIndex++) {
			Row row = sheet.getRow(rowIndex);
			String userID = row.getCell(0).getStringCellValue();
			String password = row.getCell(1).getStringCellValue();
			String name = row.getCell(2).getStringCellValue();
			String email = row.getCell(3).getStringCellValue();
			String faculty = row.getCell(4).getStringCellValue();;
			String userType = row.getCell(5).getStringCellValue();;

			List<String> entry = new ArrayList<>();
			entry.add(userID);
			entry.add(password);
			entry.add(name);
			entry.add(email);
			entry.add(faculty);
			entry.add(userType);

			existingUsers.add(entry);
		}

		workbook.close();
		fis.close();
		return existingUsers;
	}

	/**
     * Reads user data from an Excel file based on student_list.xlsx or staff_list.xlsx type.
     *
     * @param file     Path to the Excel file containing user data.
     * @param userType Type of user (student or staff).
     * @return List of Lists containing user data.
     * @throws IOException If an I/O error occurs.
     */
	
	
	private static List<List<String>> readUserDataFromExcel(String file, String userType) throws IOException {
		List<List<String>> userData = new ArrayList<>();
		FileInputStream fis = new FileInputStream(file);
		Workbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);

		int rows = sheet.getPhysicalNumberOfRows();;


		for (int rowIndex = 1; rowIndex < rows; rowIndex++) {
			Row row = sheet.getRow(rowIndex);
			String name = row.getCell(0).getStringCellValue();
			String email = row.getCell(1).getStringCellValue();
			String faculty = row.getCell(2).getStringCellValue();
			String userID = email.split("@")[0];
			List<String> entry = new ArrayList<>();
			entry.add(userID);
			entry.add("password");
			entry.add(name);
			entry.add(email);
			entry.add(faculty);
			entry.add(userType);
			userData.add(entry);
		}

		workbook.close();
		fis.close();
		return userData;
	}

	/**
     * Checks if a user is present in the database.
     *
     * @param database List of Lists containing user data in the database.
     * @param email    Email of the user to check.
     * @return True if the user is in the database, otherwise false.
     */
	
	private static boolean isUserInDatabase(List<List<String>> database, String email) {
		for (List<String> user : database) {

			if (user.get(3).equals(email)) {
				return true;
			}
		}
		return false;
	}

	/**
     * Writes user data to an Excel file (database.xlsx)
     *
     * @param file     Path to the Excel file to write user data.
     * @param userData List of Lists containing user data to write.
     * @throws IOException If an I/O error occurs.
     */
	
	private static void writeUserDataToExcel(String file, List<List<String>> userData) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		Workbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);

		int existingRows = sheet.getPhysicalNumberOfRows();

		// Calculate the starting row for new data
		int rowNum = existingRows;

		for (List<String> entry : userData) {
			Row row = sheet.createRow(rowNum++);
			int cellNum = 0;
			for (String data : entry) {
				Cell cell = row.createCell(cellNum++);
				cell.setCellValue(data);
			}
		}

		try (FileOutputStream fos = new FileOutputStream(file)) {
			workbook.write(fos);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.close();
			}
			fis.close();
		}
	}
	
	/**
     * Changes the password for a given user ID.
     *
     * @param userID User ID for which the password is to be changed.
     */

	public static void changePassword(String userID) {
		Scanner scanner = new Scanner(System.in);
		String newPassword = null;
		
		boolean notValid = true;
		
		do {
		System.out.print("Enter your new password: ");
		newPassword = scanner.nextLine();
		
		if (newPassword.equals("password")) {
			System.out.println("You cant use this password! Change to something new.");
		}
		else
			notValid = false;
		} while (notValid);

		try {
			FileInputStream fis = new FileInputStream(DATABASE_FILE);
			Workbook workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheetAt(0);

			for (int rowIndex = 1; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				Cell userIDCell = row.getCell(0);
				Cell passwordCell = row.getCell(1);

				if (userIDCell.getStringCellValue().equals(userID)) {
					passwordCell.setCellValue(newPassword);
					break; // Exit the loop after finding the user
				}
			}

			try (FileOutputStream fos = new FileOutputStream(DATABASE_FILE)) {
				workbook.write(fos);
				System.out.println("Password changed successfully.");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				workbook.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
     * Checks if a user ID exists in the database.
     *
     * @param userID User ID to check.
     * @return True if the user ID exists, otherwise false.
     * @throws IOException If an I/O error occurs.
     */

	public static boolean checkUserID(String userID) throws IOException {

		List<List<String>> existingUsers = readExistingUsers();
		for (List<String> user : existingUsers) {

			if (user.get(0).equals(userID)) {
				return true;
			}
		}
		return false;
	}
	
	/**
     * Checks if the provided password matches the user's password.
     *
     * @param userID   User ID for which the password is checked.
     * @param password Password to be checked.
     * @return True if the password matches, otherwise false.
     * @throws IOException If an I/O error occurs.
     */

	public static boolean checkPassword(String userID, String password) throws IOException {

		List<List<String>> existingUsers = readExistingUsers();
		for (List<String> user : existingUsers) {
			if (user.get(0).equals(userID)) {
				if (user.get(1).equals(password)) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		return false;
	}

	/**
     * Retrieves user information based on the user ID.
     *
     * @param userID User ID for which information is to be retrieved.
     * @return List containing user information.
     * @throws IOException If an I/O error occurs.
     */
	
	public static List<String> getUser(String userID) throws IOException{

		List<List<String>> existingUsers = readExistingUsers();
		for (List<String> user : existingUsers) {

			if (user.get(0).equals(userID)) {
				return user;
			}
		}
		return new ArrayList<>();
	}
}