import java.io.*;
import java.nio.file.Path;
import java.util.Scanner;

public class Console {
    private final Scanner in;

    private Console() {
        in = new Scanner(System.in);
    }

    public static void start() {
        new Console().menu();
    }

    private void menu() {
        String read;
        try {
            do {
                System.out.print("""
                        -------------------------------------------------
                        Enter 1 for serialize student object to JSON by Gson
                        Enter 2 for deserialize student object from JSON by Gson
                        Enter 3 for serialize student object to XML by JAXB
                        Enter 4 for deserialize student object from XML by JAXB
                        Enter 5 for serialize student object to TXT file
                        Enter 6 for deserialize student object from TXT file
                        Enter 7 for add student object to MySQL DB
                        Enter 8 for remove student object to MySQL DB
                        Enter 9 for view all students from MySQL DB
                        Enter 0 for exit
                        --------------------------------------------------
                        """);
                System.out.print("Your choice: ");
                read = in.nextLine().trim();
                switch (read) {
                    case "1" -> {
                        Student student = createStudent();
                        String stringPath = getPath();
                        Path path = checkWriteFile(stringPath, ".json");
                        GsonWorker gsonWorker = new GsonWorker();
                        gsonWorker.objToJson(student, path);
                    }
                    case "2" -> {
                        String stringPath = getPath();
                        Path path = checkReadFile(stringPath, ".json");
                        GsonWorker gsonWorker = new GsonWorker();
                        Student student = gsonWorker.jsonToObj(path);
                        System.out.println(student);
                    }
                    case "3" -> {
                        Student student = createStudent();
                        String stringPath = getPath();
                        Path path = checkWriteFile(stringPath, ".xml");
                        JAXBWorker jaxbWorker = new JAXBWorker();
                        jaxbWorker.objToXml(student, path);
                    }
                    case "4" -> {
                        String stringPath = getPath();
                        Path path = checkReadFile(stringPath, ".xml");
                        JAXBWorker jaxbWorker = new JAXBWorker();
                        Student student = jaxbWorker.xmlToObj(path);
                        System.out.println(student);
                    }
                    case "5" -> {
                        Student student = createStudent();
                        String stringPath = getPath();
                        Path path = checkWriteFile(stringPath, ".txt");
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path.toFile()));
                        objectOutputStream.writeObject(student);
                        objectOutputStream.close();
                    }
                    case "6" -> {
                        String stringPath = getPath();
                        Path path = checkReadFile(stringPath, ".txt");
                        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path.toFile()));
                        Student student = (Student) objectInputStream.readObject();
                        objectInputStream.close();
                        System.out.println(student);
                    }
                    case "7" -> {
                        DBWorker dbWorker = DBWorker.getInstance();
                        Student student = createStudent();
                        dbWorker.addStudent(student.getFirstName(), student.getLastName(), student.getCity());
                    }
                    case "8" -> {
                        DBWorker dbWorker = DBWorker.getInstance();
                        Student student = createStudent();
                        dbWorker.removeStudent(student.getFirstName(), student.getLastName(), student.getCity());
                    }
                    case "9" -> {
                        DBWorker dbWorker = DBWorker.getInstance();
                        System.out.println(dbWorker.getAllStudents());
                    }
                    case "0" -> {
                        System.out.print("Are you sure want to exit? Y/N: ");
                        if (in.nextLine().equalsIgnoreCase("n")) {
                            read = "continue";
                        }
                    }
                    default -> System.out.printf("'%s' is incorrect value, try again\n", read);
                }
            } while (!read.equalsIgnoreCase("0"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Student createStudent() {
        System.out.print("Enter the first name of the student: ");
        String firstName = in.nextLine();
        System.out.print("Enter the last name of the student: ");
        String lastName = in.nextLine();
        System.out.print("Enter the city of the student: ");
        String city = in.nextLine();
        return new Student(firstName, lastName, city);
    }

    private String getPath() {
        System.out.print("Enter the path: ");
        return in.nextLine();
    }

    private Path checkWriteFile(String path, String extension) throws IOException {
        String newPath = checkExtension(path, extension);
        File file = new File(newPath);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file.toPath();
    }

    private Path checkReadFile(String path, String extension) {
        String newPath = checkExtension(path, extension);
        File file = new File(newPath);
        while (!file.exists()) {
            System.out.printf("File '%s' is not exist, try again\n", file.getAbsolutePath());
            file = new File(checkExtension(getPath(), extension));
        }
        return file.toPath();
    }

    private String checkExtension(String path, String extension) {
        return path.endsWith(extension) ? path : path + extension;
    }

}
