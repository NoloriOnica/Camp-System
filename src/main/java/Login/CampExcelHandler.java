// package Login;

// import org.apache.poi.ss.usermodel.*;
// import org.apache.poi.xssf.usermodel.XSSFWorkbook;
// import java.io.*;
// import java.util.ArrayList;

// import Camp.Camp;
// import Camp.CampInfo;
// import Camp.CampUserGroup;
// import Camp.CampCommittee;
// import Feedback.Enquiries;
// import Feedback.Suggestion;
// import Student.Student;



// public class CampExcelHandler {
	
	
// 	/*
	 
// 	 CAMP EXCEL FILE
// 	 Camp Class:
// 	 CampInfo:
// 		 "Camp Name", "Start Date", "End Date", "Registration Closing Date",
// 	     "User Group", "Visibility", "Location", "Total Slots", "Committee Slots",
// 	     "Description", "Staff In Charge ID", 
     
//      "Remaining Attendee Slots"
     
//      Students: List of Student Names
     
//      Camp Committee: List of Camp Committee members
     
//      ENQUIRY FILE
//      	"CampName", "enquiry", "isProcessed", "senderName"
     	
//      SUGGESTION FILE
//      	"CampName", "suggestion", "isProcessed", "senderName"
     
     
//      Student:
//      	"isCampCommittee", "committeeForCamps", "campUserGroup", "registerdCamps" (just store campNames),  "bannedCamps (just store campNames)"
     
//      CampCommittee:
//      	"
	  
// 	*/
	
	
	
	
//     private static final String FILE_NAME = "camp.xlsx";

//     public static void writeCampsToExcel(ArrayList<Camp> allCamps) {
//         try (Workbook workbook = new XSSFWorkbook()) {
//             Sheet sheet = workbook.createSheet("Camps");

//             // Create header row
//             Row headerRow = sheet.createRow(0);
//             String[] headers = {
//                     "Camp Name", "Start Date", "End Date", "Registration Closing Date",
//                     "User Group", "Visibility", "Location", "Total Slots", "Committee Slots",
//                     "Description", "Staff In Charge ID", "Remaining Attendee Slots", "Students", "Camp Committee Members" 
//             };

//             for (int i = 0; i < headers.length; i++) {
//                 Cell cell = headerRow.createCell(i);
//                 cell.setCellValue(headers[i]);
//             }

//             // Populate data
//             int rowNum = 1;
//             for (Camp camp : allCamps) {
//                 Row row = sheet.createRow(rowNum++);
//                 CampInfo campInfo = camp.getCampInfo();

//                 // Populate camp info
//                 row.createCell(0).setCellValue(campInfo.getCampName());
//                 row.createCell(1).setCellValue(campInfo.getStartDate());
//                 row.createCell(2).setCellValue(campInfo.getEndDate());
//                 row.createCell(3).setCellValue(campInfo.getRegClosingDate());
//                 row.createCell(4).setCellValue(campInfo.getCampUserGroup());
//                 row.createCell(5).setCellValue(campInfo.getCampVisibility());
//                 row.createCell(6).setCellValue(campInfo.getLocation());
//                 row.createCell(7).setCellValue(campInfo.getTotalSlots());
//                 row.createCell(8).setCellValue(campInfo.getCampCommitteeSlot());
//                 row.createCell(9).setCellValue(campInfo.getDescription());
//                 row.createCell(10).setCellValue(campInfo.getStaffInChargeID());

//                 // Remaining attendee slots
//                 row.createCell(11).setCellValue(camp.getRemainingAttendeeSlot());

//                 // Registered Students
//                 ArrayList<Student> registeredStudents = camp.getRegisteredStudents();
//                 if (registeredStudents != null && !registeredStudents.isEmpty()) {
//                     String studentsString = "";
//                     for (Student student : registeredStudents) {
//                         studentsString += student.getName() + ", ";
//                     }
//                     row.createCell(12).setCellValue(studentsString);
//                 }
                
//                 // Camp Committee Members
//                 ArrayList<CampCommittee> committeeMembers = camp.getRegisteredCampCommittee();
//                 if (committeeMembers != null && !committeeMembers.isEmpty()) {
//                     String commMemberString = "";
//                     for (CampCommittee campCommMember : committeeMembers) {
//                         commMemberString += campCommMember.getName() + ", ";
//                     }
//                     row.createCell(13).setCellValue(commMemberString);
//                 }
                
//             }

//             // Write the output to a file
//             try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME)) {
//                 workbook.write(fileOut);
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     public static ArrayList<Camp> readCampsFromExcel() {
//         ArrayList<Camp> allCamps = new ArrayList<>();

//         try (InputStream inp = new FileInputStream(FILE_NAME)) {
//             Workbook workbook = WorkbookFactory.create(inp);
//             Sheet sheet = workbook.getSheetAt(0);

//             // Iterate over rows skipping the header
//             for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
//                 Row row = sheet.getRow(rowNum);
//                 if (row != null) {
//                     Camp camp = new Camp();

//                     // Set CampInfo
//                     CampInfo campInfo = new CampInfo();
//                     campInfo.setCampName(getStringValue(row, 0));
//                     campInfo.setStartDate(getStringValue(row, 1));
//                     campInfo.setEndDate(getStringValue(row, 2));
//                     campInfo.setRegClosingDate(getStringValue(row, 3));
//                     campInfo.setCampUserGroup(getStringValue(row, 4));
//                     campInfo.setCampVisibility(getStringValue(row, 5));
//                     campInfo.setLocation(getStringValue(row, 6));
//                     campInfo.setTotalSlots(getNumericValue(row, 7));
//                     campInfo.setCampCommitteeSlot(getNumericValue(row, 8));
//                     campInfo.setDescription(getStringValue(row, 9));
//                     campInfo.setStaffInChargeID(getStringValue(row, 10));
//                     camp.setCampInfo(campInfo);

//                     // Set remaining attendee slots
//                     camp.setRemainingAttendeeSlot(getNumericValue(row, 11));

//                     // Set Enquiries
//                     ArrayList<Enquiry> enquiriesList = new ArrayList<>();
//                     String enquiriesString = getStringValue(row, 12);
//                     if (enquiriesString != null && !enquiriesString.isEmpty()) {
//                         String[] enquiriesArray = enquiriesString.split(", ");
//                         for (String enquiry : enquiriesArray) {
//                             Enquiry e = new Enquiry();
//                             e.setEnquiry(enquiry);
//                             enquiriesList.add(e);
//                         }
//                     }
//                     camp.setEnquiriesList(enquiriesList);

//                     // Set Suggestions
//                     ArrayList<Suggestion> suggestionsList = new ArrayList<>();
//                     String suggestionsString = getStringValue(row, 13);
//                     if (suggestionsString != null && !suggestionsString.isEmpty()) {
//                         String[] suggestionsArray = suggestionsString.split(", ");
//                         for (String suggestion : suggestionsArray) {
//                             Suggestion s = new Suggestion();
//                             s.setSuggestionString(suggestion);
//                             suggestionsList.add(s);
//                         }
//                     }
//                     camp.setSuggestionsList(suggestionsList);

//                     // Set Registered Students
//                     ArrayList<Student> registeredStudents = new ArrayList<>();
//                     String studentsString = getStringValue(row, 14);
//                     if (studentsString != null && !studentsString.isEmpty()) {
//                         String[] studentsArray = studentsString.split(", ");
//                         for (String studentName : studentsArray) {
//                             Student student = new Student();
//                             student.setName(studentName);
//                             registeredStudents.add(student);
//                         }
//                     }
//                     camp.setRegisteredStudents(registeredStudents);

//                     allCamps.add(camp);
//                 }
//             }
//         } catch (IOException | org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
//             e.printStackTrace();
//         }

//         return allCamps;
//     }

//     private static String getStringValue(Row row, int cellIndex) {
//         Cell cell = row.getCell(cellIndex);
//         return cell == null ? null : cell.getStringCellValue();
//     }

//     private static int getNumericValue(Row row, int cellIndex) {
//         Cell cell = row.getCell(cellIndex);
//         return cell == null ? 0 : (int) cell.getNumericCellValue();
//     }
// }
