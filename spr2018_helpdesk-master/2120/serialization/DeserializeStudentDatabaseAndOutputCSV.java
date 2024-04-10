package serialization;

/**
 * Created by emg3 on 3/2/2018.
 */
public class DeserializeStudentDatabaseAndOutputCSV {

    public static void main(String[] args){

        if (args.length != 2) {
            System.err.println("Usage: DeserializeStudentDatabaseAndOutputCSVFile inputfilename outputfilename");
            System.exit(1);
        }

        String infilename = args[0];
        String outfilename = args[1];

        StudentDatabaseSerializedFileReader.readFromFile(infilename);

        StudentDatabase.getInstance().sortByGPA();
        StudentDatabaseCSVFileWriter.openFile(outfilename);
        StudentDatabaseCSVFileWriter.writeData();
        StudentDatabaseCSVFileWriter.closeFile();
    }
}
