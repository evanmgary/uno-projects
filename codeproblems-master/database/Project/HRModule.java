package Project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

public class HRModule {

    private Connection conn;
    private Queries quer;
    private Scanner sc;

    public HRModule(Connection conn) throws SQLException{

        this.conn = conn;
        quer = new Queries(this.conn);
        sc = new Scanner(System.in);
    }

    public void runModule() throws SQLException{
        System.out.println("Human Resources Module");
        int personid;
        String positionid;
        while(true) {
            System.out.print("Enter the id of the person to add to the system: ");
            String entryMethod = "0";
            try{
                //personid = Integer.parseInt(sc.nextLine());
                personid = 8217024;
                System.out.println("How do you want to enter this person's training information?");
                System.out.println("1) Use a transcript file");
                System.out.println("2) Enter courses manually");
                System.out.println("3) Return to main module");
                System.out.print("Enter your choice: ");
                entryMethod = sc.nextLine();
            }
            catch (InputMismatchException e){
                System.out.println("Enter a valid id.");
                continue;
            }
            dropSimpleTakes();
            simpleTakes(personid);
            System.out.print("Enter the position that this person has been hired for: ");
            //positionid = sc.nextLine();
            positionid = "77799";

            if(entryMethod.equals("1")) {
                System.out.print("Enter the filename of the person's transcript: ");
                String filename = sc.nextLine();
                try {
                    if (transcriptReader(personid, filename) != 0) {
                        continue;
                    }
                    else {
                        addSkills(personid);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(entryMethod.equals("2")){
                getCourses(personid);
                addSkills(personid);
            }
            else if (entryMethod.equals("3")){
                break;
            }
            else{
                System.out.println("Incorrect entry");
                continue;
            }

            if (!checkIfQualified(personid, positionid)){
                trainingOneCourse(personid, positionid);
            }
            dropSimpleTakes();
        }

    }

    /**
     * Makes a temporary takes table for this part of the app to use
     * Because the real takes table uses section, we need one that only uses course codes
     * to simplify the addition of course information (sometimes the section information might not be known)
     * Also adds any courses the person already had in takes to simpletakes
     * @param personid
     * @throws SQLException
     */
    public void simpleTakes(int personid) throws  SQLException{
        String query = "CREATE TABLE simpletakes(\n" +
                "\tperson_id NUMERIC(7,0),\n" +
                "\tc_code VARCHAR(10),\n" +
                "\tPRIMARY KEY (person_id, c_code),\n" +
                "\tFOREIGN KEY (person_id) REFERENCES person,\n" +
                "\tFOREIGN KEY (c_code) REFERENCES course)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.executeUpdate();
        String query2 = "INSERT INTO simpletakes\n" +
                "\tSELECT person_id, c_code \n" +
                "\tFROM takes\n" +
                "\tWHERE person_id = ?";
        PreparedStatement ps2 = conn.prepareStatement(query2);
        ps2.setInt(1, personid);
        ps2.executeUpdate();
    }

    /**
     * Drops the simpletakes table. Because it only applies to one person at a time, it needs to be remade
     * every time a person is added to the system
     */
    public void dropSimpleTakes(){
        try{
        PreparedStatement ps = conn.prepareStatement("DROP TABLE simpletakes");
        ps.executeUpdate();
        }
        catch (SQLException e){
            //do nothing, this will often get called when simpletakes doesn't exist
        }
    }

    /**
     * Gets course codes from the user in place of a transcript
     * @param personid
     * @throws SQLException
     */
    public void getCourses(int personid) throws SQLException{

        String entry = "";
        while(!entry.equalsIgnoreCase("q")){
            System.out.print("Enter a course id, or q to finish: ");
            entry = sc.nextLine();
            if (entry.matches("\\d\\d\\d\\d")){
                try {
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO simpletakes VALUES(?,?)");
                    ps.setInt(1, personid);
                    ps.setString(2, entry);
                    ps.executeUpdate();
                }
                catch (SQLIntegrityConstraintViolationException e){
                    //do nothing
                }
            }

        }

    }

    /**
     * Reads a txt file containing course codes
     * There should be one code per line
     * Adds the courses into the temporary takes table
     * @param person_id
     * @param filename
     * @return
     * @throws IOException
     * @throws SQLException
     */
    public int transcriptReader(int person_id, String filename) throws IOException, SQLException {
        if (!Files.exists(Paths.get(filename))){
            System.out.println("Transcript does not exist.");
            return 1;
        }
        List<String> courseList;
        ArrayList<String> courseList2 = new ArrayList<>();
        courseList= Files.readAllLines(Paths.get(filename));
        for (String s:courseList){
            String[] split = s.split(" ");
            courseList2.add(split[0]);
        }

        for (String t: courseList2){
            try{
                PreparedStatement ps = conn.prepareStatement("INSERT INTO simpletakes VALUES(?,?)");
                ps.setInt(1,person_id);
                ps.setString(2, t);
                ps.executeUpdate();
            }
            catch(SQLIntegrityConstraintViolationException e){
                //sometimes they will already have the course, do nothing
            }
        }
        return 0;
    }

    /**
     * Uses the temporary takes table to update a person's skills
     * @param person_id
     * @throws SQLException
     */
    public void addSkills(int person_id) throws SQLException{
        try {
            //had to look up how to do this, this hint seems to fix the problem of already having certain skills the courses teach
            String query = "INSERT /*+ ignore_row_on_dupkey_index*/ INTO has_skill SELECT person_id, ks_code FROM simpletakes NATURAL JOIN teaches WHERE person_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, person_id);
            ps.executeUpdate();
        }
        catch(SQLIntegrityConstraintViolationException e){}
    }

    /**
     * Uses query 6 to see if a person has a skill gap
     * @param person_id
     * @param position_id
     * @return
     * @throws SQLException
     */
    public boolean checkIfQualified(int person_id, String position_id) throws SQLException{

        ResultSet rs = quer.query6b(position_id, person_id);
        ArrayList<ArrayList<Object>> results = JobApp.toArrayList(rs);

        if (results.size() == 0){
            System.out.println("This person has been entered into the system and is qualified for the given position.");
            completeHire(person_id, position_id);
            return true;
        }
        else{
            System.out.println("Preparing a training plan:");
            return false;
        }

    }

    /**
     * Checks for a skill gap that can be covered by one course with query 9
     * Calls the method for query 12 if not
     * @param person_id
     * @param position_id
     * @throws SQLException
     */
    public void trainingOneCourse(int person_id, String position_id) throws SQLException{
        ResultSet rs = quer.query9(position_id, person_id);
        ArrayList<ArrayList<Object>> results = JobApp.toArrayList(rs);
        if (results.size() == 0){
            trainingCourseSet(person_id, position_id);
        }
        else{
            System.out.println("This person has been added to the system. Recommended course information:");
            System.out.println("(Course code, title)");
            JobApp.printAsCsv(results);
            completeHire(person_id, position_id);
        }

    }

    /**
     * Uses query 12 to get a course set
     * Suggests that the person should not be hired if no course set exists
     * @param person_id
     * @param position_id
     * @return
     * @throws SQLException
     */
    public boolean trainingCourseSet(int person_id, String position_id) throws SQLException{

        ResultSet rs = quer.query12(position_id, person_id);
        ArrayList<ArrayList<Object>> results = JobApp.toArrayList(rs);

        if (results.size() == 0){
            System.out.println("Extensive training required for this person. Review of hiring recommended.");
            return false;
        }
        else{
            System.out.println("This person has been added to the system. Recommended course information:");
            JobApp.printAsCsv(results);
            completeHire(person_id, position_id);
            return true;
        }

    }

    /**
     * Adds information to works
     * @param person_id
     * @param position_id
     * @throws SQLException
     */
    public void completeHire(int person_id, String position_id) throws SQLException{
        PreparedStatement addToWorks = conn.prepareStatement("INSERT INTO works VALUES(?,?,CURRENT_DATE,null,null,null)");
        addToWorks.setInt(1,person_id);
        addToWorks.setString(2,position_id);
        addToWorks.executeUpdate();
    }

}
