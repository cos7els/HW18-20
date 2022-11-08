import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GsonWorker {

    public void objToJson(Student student, Path path) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(student);
        Files.write(path, json.getBytes());
    }

    public Student jsonToObj(Path path) throws IOException {
        Gson gson = new Gson();
        String json = new String(Files.readAllBytes(path));
        return gson.fromJson(json, Student.class);
    }

}
