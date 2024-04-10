package serialization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by emg3 on 3/2/2018.
 */
public class StudentDatabaseSerializedFileReader {

    public static void readFromFile(String filename){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))){
            Object obj = in.readObject();
            if (obj instanceof StudentDatabase){
                StudentDatabase.instance = (StudentDatabase) obj;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }


    }
}
