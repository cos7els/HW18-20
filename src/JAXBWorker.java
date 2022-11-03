import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class JAXBWorker {

    public void objToXml(Student student, File file) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Student.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.marshal(student, file);
    }

    public Student xmlToObj(File file) throws JAXBException {
        Student unmarshal;
        JAXBContext jaxbContext = JAXBContext.newInstance(Student.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        unmarshal = (Student) jaxbUnmarshaller.unmarshal(file);
        return unmarshal;
    }
}
