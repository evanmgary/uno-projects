package serialization;
import java.util.ArrayList;

public class BuildAndSortStudentDataFromCSVFile {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("Usage: BuildAndSortStudentDataFromCSVFile inputfilename outputfilename");
            System.exit(1);
        }

        String infilename = args[0];
        String outfilename = args[1];

        StudentDatabaseCSVFileReader.openFile(infilename);
        StudentDatabaseCSVFileReader.readData();
        StudentDatabaseCSVFileReader.closeFile();

        StudentDatabase db = StudentDatabase.getInstance();
        db.sortByGPA();

        StudentDatabaseCSVFileWriter.openFile(outfilename);
        StudentDatabaseCSVFileWriter.writeData();
        StudentDatabaseCSVFileWriter.closeFile();
         
    }
}
