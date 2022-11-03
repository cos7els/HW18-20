import java.io.*;
import java.util.Scanner;

public class Console {
    private Scanner in = new Scanner(System.in);

    private Console() {
        menu();
    }

    public static void start() {
        new Console();
    }

    private void menu() {
        String read;
        try {
            do {
                System.out.print("""
                        -------------------------------------------------
                        Enter 1 for serialize student object to json by gson
                        Enter 2 for deserialize student object from json by gson
                        Enter 3 for serialize student object to xml by jaxb
                        Enter 4 for deserialize student object from xml by jaxb
                        Enter 5 for serialize student object to file
                        Enter 6 for deserialize student object from file
                        Enter 0 for exit
                        --------------------------------------------------
                        """);
                System.out.print("Your choice: ");
                read = in.nextLine().trim();
                switch (read) {
                    case "1" -> {
                        createStudent();
                    }
                    case "2" -> System.out.println(2);
                    case "3" -> {
                        Student student = createStudent();
                        JAXBWorker jaxbWorker = new JAXBWorker();
                        jaxbWorker.objToXml(student, new File(in.nextLine()));
                    }
                    case "4" -> {
                        JAXBWorker jaxbWorker = new JAXBWorker();
                        Student student = jaxbWorker.xmlToObj(new File(in.nextLine()));
                        System.out.println(student);
                    }
                    case "5" -> {
                        Student student = createStudent();
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(in.nextLine()));
                        objectOutputStream.writeObject(student);
                    }
                    case "6" -> {
                        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(in.nextLine()));
                        Student student = (Student) objectInputStream.readObject();
                        objectInputStream.close();
                        System.out.println(student);
                    }
                    case "0" -> {
                        System.out.print("Are you sure want to exit? Y/N: ");
                        if (in.nextLine().equalsIgnoreCase("n")) {
                            read = "continue";
                        }
                    }
                    default -> System.out.printf("'%s' is incorrect value, try again\n", read);
                }
            } while (!read.equals("0"));
        } catch (Exception e) {

        }
    }

    private Student createStudent() {
        System.out.print("Enter the first name of the student: ");
        String firstName = in.nextLine();
        System.out.print("Enter the last name of the student: ");
        String lastName = in.nextLine();
        System.out.print("Enter the city of the student: ");
        String city = in.nextLine();
        Student student = new Student(firstName, lastName, city);
        System.out.println(student);
        return student;
    }

}
