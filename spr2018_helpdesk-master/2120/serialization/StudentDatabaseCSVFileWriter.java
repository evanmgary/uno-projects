package serialization;
import java.io.IOException;
import java.lang.IllegalStateException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Formatter;


public class StudentDatabaseCSVFileWriter {

    private static Formatter output;

    public static void openFile (String filename) {

        try {
            output = new Formatter(filename);
        }   
        catch (IOException e) {
            System.err.println("Error opening file.  Terminating");
            System.exit(1);
        }        
    }    

    public static void writeData() {
        StudentDatabase db = StudentDatabase.getInstance();
        int numStudents = db.getNumStudents();
        for (int i=0; i<numStudents; i++) {
            Student current = db.getStudent(i);
            String currentName = current.getName();
            String[] nameSplit = currentName.split(" ");
            String newName = nameSplit[3] + ", " + nameSplit[1] + " " + nameSplit[2];

            output.format("%d,%s,%s",current.getStudentID(),current.getSex(),current.getEthnicGroup());
            output.format(",\"%s\",%s,%s",newName,current.getProgram(),current.getAcademicPlan());
            output.format(",%s,%d,%f",current.getSubPlan(),current.getStrtLevel(),current.getTotal());
            output.format(",%f,%s,%f",current.getGPA(),current.getFAFSA(),current.getTakePrgrs());
            output.format(",%f",current.getFinancialNeed());

            // YOUR CODE HERE
            /* here, you're going to output the rest of the student information to the file
               see the format of how the "parsing" method works in StudentDatabaseCSVFileReader
            */


            // end the line that represents all the data for one student
            output.format("%n");
        }
    }

    public static void closeFile() {
        if (output != null)
            output.close();
        output = null;
    }

}
