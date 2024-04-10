package serialization;

/**
 * Created by emg3 on 3/2/2018.
 */
public class ReadCSVAndSerializeStudentDatabase {
    public static void main(String[] args){

        if (args.length != 2) {
            System.err.println("Usage: ReadCSVAndSerializeStudentDatabase inputfilename outputfilename");
            System.exit(1);
        }

        String infilename = args[0];
        String outfilename = args[1];

        StudentDatabaseCSVFileReader.openFile(infilename);
        StudentDatabaseCSVFileReader.readData();
        StudentDatabaseCSVFileReader.closeFile();

        StudentDatabase.getInstance().sortByGPA();
        StudentDatabaseSerializedFileWriter.writeToFile(outfilename);

    }
}
