Student Record System
A Java-based console application designed to manage student data. The project satisfies all assignment requirements using standard Object-Oriented Programming (OOP), explicit exception handling, and various Java File I/O streams.

🏗️ System Design
The application is split into three core classes to separate data modeling, business logic, and the user interface:

1. Student.java (Data Layer)
Purpose: Represents a single student entity.

Fields: Private id, name, department, and gpa (strict encapsulation via getters and setters).

Key Implementation: Implements Serializable so the entire object can be saved directly to a file without manual parsing.

2. StudentRecordManager.java (Service Layer)
This class contains all the logic for file manipulation and covers the three required storage formats:

Text Files: Uses PrintWriter to write data separated by commas and a Scanner to read and parse the text line-by-line.

Binary Files: Uses DataOutputStream and DataInputStream to read and write raw Java primitive types directly, making the storage compact and fast.

Object Serialization: Uses ObjectOutputStream and ObjectInputStream to save and restore the entire ArrayList<Student> collection in its native Java state.

3. studentrecordsystem.java (Presentation Layer)
Purpose: The main entry point of the app.

Features: Implements a text-based menu loop (switch-case) that lets users interact with the system to perform CRUD actions, view reports, or manage file states.

🛠️ Requirements Fulfillment
Automated File Setup: The StudentRecordManager constructor checks for the data/ directory using the File class and automatically creates it via dir.mkdir() if it is missing.

File Properties: Uses the java.io.File API (length(), getAbsolutePath(), lastModified()) to fetch and print live file traits to the console.

Buffered Backup Engine: Uses BufferedInputStream and BufferedOutputStream with a 1024-byte buffer array to create an efficient, low-overhead copy of your active records file.

Reporting Logic: Loops through the active ArrayList to dynamically calculate total students, highest GPA, lowest GPA, and the exact average GPA.
