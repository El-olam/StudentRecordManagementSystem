import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class StudentRecordManager {
    private String textFile = "data/students.txt";
    private String binaryFile = "data/students.dat";
    private String serialFile = "data/students.ser";
    private String backupFile = "data/students_backup.bak";

    public StudentRecordManager() {
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public void saveToTextFile(ArrayList<Student> list) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(textFile));
            for (int i = 0; i < list.size(); i++) {
                Student s = list.get(i);
                writer.println(s.getId() + "," + s.getName() + "," + s.getDepartment() + "," + s.getGpa());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing text file.");
        }
    }

    public ArrayList<Student> loadFromTextFile() {
        ArrayList<Student> list = new ArrayList<>();
        File file = new File(textFile);
        if (!file.exists()) {
            return list;
        }

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(",");
                if (tokens.length == 4) {
                    String id = tokens[0];
                    String name = tokens[1];
                    String dept = tokens[2];
                    double gpa = Double.parseDouble(tokens[3]);
                    list.add(new Student(id, name, dept, gpa));
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error reading text file.");
        }
        return list;
    }

    public void saveToBinaryFile(ArrayList<Student> list) {
        try {
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(binaryFile));
            dos.writeInt(list.size());
            for (int i = 0; i < list.size(); i++) {
                Student s = list.get(i);
                dos.writeUTF(s.getId());
                dos.writeUTF(s.getName());
                dos.writeUTF(s.getDepartment());
                dos.writeDouble(s.getGpa());
            }
            dos.close();
        } catch (IOException e) {
            System.out.println("Error writing binary file.");
        }
    }

    public ArrayList<Student> loadFromBinaryFile() {
        ArrayList<Student> list = new ArrayList<>();
        File file = new File(binaryFile);
        if (!file.exists()) {
            return list;
        }

        try {
            DataInputStream dis = new DataInputStream(new FileInputStream(binaryFile));
            int count = dis.readInt();
            for (int i = 0; i < count; i++) {
                String id = dis.readUTF();
                String name = dis.readUTF();
                String dept = dis.readUTF();
                double gpa = dis.readDouble();
                list.add(new Student(id, name, dept, gpa));
            }
            dis.close();
        } catch (IOException e) {
            System.out.println("Error reading binary file.");
        }
        return list;
    }

    public void saveToSerialFile(ArrayList<Student> list) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(serialFile));
            oos.writeObject(list);
            oos.close();
        } catch (IOException e) {
            System.out.println("Serialization error occurred.");
        }
    }

    public ArrayList<Student> loadFromSerialFile() {
        File file = new File(serialFile);
        if (!file.exists()) {
            return new ArrayList<Student>();
        }

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(serialFile));
            ArrayList<Student> list = (ArrayList<Student>) ois.readObject();
            ois.close();
            return list;
        } catch (Exception e) {
            System.out.println("Deserialization error occurred.");
            return new ArrayList<Student>();
        }
    }

    public void backupRecords() {
        File src = new File(textFile);
        if (!src.exists()) {
            System.out.println("No original file to backup.");
            return;
        }

        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(textFile));
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(backupFile));

            byte[] buffer = new byte[1024];
            int length;
            while ((length = bis.read(buffer)) > 0) {
                bos.write(buffer, 0, length);
            }

            bis.close();
            bos.close();
            System.out.println("Backup created.");
        } catch (IOException e) {
            System.out.println("Backup process failed.");
        }
    }

    public void displayFileProperties() {
        String[] paths = {textFile, binaryFile, serialFile, backupFile};
        System.out.println("\n--- File Info ---");
        for (int i = 0; i < paths.length; i++) {
            File f = new File(paths[i]);
            if (f.exists()) {
                System.out.println("File Name: " + f.getName());
                System.out.println("Path: " + f.getAbsolutePath());
                System.out.println("Size: " + f.length() + " bytes");
                System.out.println("Modified: " + new Date(f.lastModified()));
                System.out.println("-------------------------");
            } else {
                System.out.println(f.getName() + " does not exist yet.");
                System.out.println("-------------------------");
            }
        }
    }

    public void generateReport(ArrayList<Student> list) {
        if (list.isEmpty()) {
            System.out.println("No students found to analyze.");
            return;
        }

        int total = list.size();
        double highest = list.get(0).getGpa();
        double lowest = list.get(0).getGpa();
        double totalSum = 0;

        for (int i = 0; i < list.size(); i++) {
            double currentGpa = list.get(i).getGpa();
            totalSum += currentGpa;

            if (currentGpa > highest) {
                highest = currentGpa;
            }
            if (currentGpa < lowest) {
                lowest = currentGpa;
            }
        }

        double average = totalSum / total;

        System.out.println("\n--- Statistical Report ---");
        System.out.println("Total Students: " + total);
        System.out.println("Highest GPA: " + highest);
        System.out.println("Lowest GPA: " + lowest);
        System.out.println("Average GPA: " + average);
    }
}