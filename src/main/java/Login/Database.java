package Login;

import java.io.*;	
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.Scanner;

class Database {
	private static final String DATABASE_FILE = "././././data/database.xlsx";
	private static final String STUDENTLIST = "././././data/student_list.xlsx";
	private static final String STAFFLIST = "././././data/staff_list.xlsx";
	
	public static void initializeDatabase() {
		try {
			// Load existing database data
			//2d array where each element is [userId, password, name, email, faculty, userType]
			List<List<String>> existingUsers = readExistingUsers();

			// Load student list data
			//2d array where each element is [userId, password, name, email, faculty, userType]
			List<List<String>> studentList = readUserDataFromExcel(STUDENTLIST, "S");

			// Load staff list data
			//2d array where each element is [userId, password, name, email, faculty, userType]
			List<List<String>> staffList = readUserDataFromExcel(STAFFLIST, "T");

			// Update the database
			//2d array where each element is [userId, password, name, email, faculty, userType]
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

	private static boolean isUserInDatabase(List<List<String>> database, String email) {
		for (List<String> user : database) {

			if (user.get(3).equals(email)) {
				return true;
			}
		}
		return false;
	}

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


	public static void changePassword(String userID) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter your new password: ");
		String newPassword = scanner.nextLine();

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

	public static boolean checkUserID(String userID) throws IOException {

		List<List<String>> existingUsers = readExistingUsers();
		for (List<String> user : existingUsers) {

			if (user.get(0).equals(userID)) {
				return true;
			}
		}
		return false;
	}

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