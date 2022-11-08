import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.Path;

public class JAXBWorker {

    public void objToXml(Student student, Path path) throws JAXBException {
        File file = path.toFile();
        JAXBContext jaxbContext = JAXBContext.newInstance(Student.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.marshal(student, file);
    }

    public Student xmlToObj(Path path) throws JAXBException {
        File file = path.toFile();
        Student unmarshal;
        JAXBContext jaxbContext = JAXBContext.newInstance(Student.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        unmarshal = (Student) jaxbUnmarshaller.unmarshal(file);
        return unmarshal;
    }

}
