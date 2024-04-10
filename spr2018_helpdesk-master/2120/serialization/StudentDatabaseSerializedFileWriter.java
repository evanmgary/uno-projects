package serialization;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by emg3 on 3/2/2018.
 */
public class StudentDatabaseSerializedFileWriter {

        public static void writeToFile(String filename){

            Path path = Paths.get("./"+filename);
            try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path))){
                out.writeObject(StudentDatabase.getInstance());
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }

}
