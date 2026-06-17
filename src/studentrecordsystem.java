import java.util.ArrayList;
import java.util.Scanner;

public class studentrecordsystem {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        StudentRecordManager manager = new StudentRecordManager();

        ArrayList<Student> studentList = manager.loadFromSerialFile();

        boolean running = true;
        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Student");
            System.out.println("2. Search Student by ID");
            System.out.println("3. Update Student Info");
            System.out.println("4. Delete Student");
            System.out.println("5. Display All Students");
            System.out.println("6. Save All Formats");
            System.out.println("7. View Summary Report");
            System.out.println("8. Run Backup");
            System.out.println("9. View File Attributes");
            System.out.println("10. Exit");
            System.out.print("Enter choice: ");

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("ID: ");
                    String id = input.nextLine();
                    System.out.print("Name: ");
                    String name = input.nextLine();
                    System.out.print("Department: ");
                    String dept = input.nextLine();
                    System.out.print("GPA: ");
                    double gpa = input.nextDouble();

                    studentList.add(new Student(id, name, dept, gpa));
                    System.out.println("Added to working list.");
                    break;

                case 2:
                    System.out.print("Enter ID to find: ");
                    String searchId = input.nextLine();
                    boolean found = false;
                    for (int i = 0; i < studentList.size(); i++) {
                        if (studentList.get(i).getId().equalsIgnoreCase(searchId)) {
                            System.out.println("Found: " + studentList.get(i));
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Student not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter ID to change: ");
                    String updateId = input.nextLine();
                    boolean updated = false;
                    for (int i = 0; i < studentList.size(); i++) {
                        Student s = studentList.get(i);
                        if (s.getId().equalsIgnoreCase(updateId)) {
                            System.out.print("New Name: ");
                            s.setName(input.nextLine());
                            System.out.print("New Dept: ");
                            s.setDepartment(input.nextLine());
                            System.out.print("New GPA: ");
                            s.setGpa(input.nextDouble());
                            System.out.println("Updated.");
                            updated = true;
                            break;
                        }
                    }
                    if (!updated) {
                        System.out.println("Student not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter ID to drop: ");
                    String deleteId = input.nextLine();
                    boolean deleted = false;
                    for (int i = 0; i < studentList.size(); i++) {
                        if (studentList.get(i).getId().equalsIgnoreCase(deleteId)) {
                            studentList.remove(i);
                            System.out.println("Removed from memory.");
                            deleted = true;
                            break;
                        }
                    }
                    if (!deleted) {
                        System.out.println("Student not found.");
                    }
                    break;

                case 5:
                    System.out.println("\nAll Students:");
                    if (studentList.isEmpty()) {
                        System.out.println("No records loaded.");
                    }
                    for (int i = 0; i < studentList.size(); i++) {
                        System.out.println(studentList.get(i));
                    }
                    break;

                case 6:
                    manager.saveToTextFile(studentList);
                    manager.saveToBinaryFile(studentList);
                    manager.saveToSerialFile(studentList);
                    System.out.println("Saved to files successfully.");
                    break;

                case 7:
                    manager.generateReport(studentList);
                    break;

                case 8:
                    manager.backupRecords();
                    break;

                case 9:
                    manager.displayFileProperties();
                    break;

                case 10:
                    System.out.println("Exiting application.");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option selected.");
            }
        }
        input.close();
    }
}