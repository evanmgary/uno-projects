package Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;

public class ModifyDB {

    private Connection conn;
    private Scanner sc;

    public ModifyDB(Connection conn){
        this.conn = conn;
        sc = new Scanner(System.in);
    }

    public void runModule() throws SQLException{
        while(true) {
            System.out.println();
            System.out.println("1) Add a person");
            System.out.println("2) Delete a person");
            System.out.println("3) Add a company");
            System.out.println("4) Delete a company");
            System.out.println("5) Add a position");
            System.out.println("6) Delete a position");
            System.out.println("7) Add a course");
            System.out.println("8) Delete a course");
            System.out.print("Choice: ");
            String choice = sc.nextLine();
            if (choice.equals("1")){
                addPerson();
            }
            else if (choice.equals("2")){
                deletePerson();
            }
            else if (choice.equals("3")){
                addCompany();
            }
            else if (choice.equals("4")){
                deleteCompany();
            }
            else if (choice.equals("5")){
                addPosition();
            }
            else if (choice.equals("6")){
                deletePosition();
            }
            else if (choice.equals("7")){
                addCourse();
            }
            else if (choice.equals("8")){
                deleteCourse();
            }
            else if (choice.equals("9")){
                break;
            }
            else{
                System.out.println("Enter a valid choice.");
            }
        }
    }


    public void addPerson() throws SQLException{
        try {
            String entry;
            PreparedStatement ps = conn.prepareStatement("INSERT INTO person VALUES(?,?,?,?,?,?,?,?,?)");
            System.out.print("Enter the person id: ");
            entry = sc.nextLine();
            ps.setInt(1, Integer.parseInt(entry));
            System.out.print("Enter the person's name: ");
            entry = sc.nextLine();
            ps.setString(2, entry);
            System.out.print("Enter the person's gender: ");
            entry = sc.nextLine();
            ps.setString(3, entry);
            System.out.print("Enter the street: ");
            entry = sc.nextLine();
            ps.setString(4, entry);
            System.out.print("Enter the city: ");
            entry = sc.nextLine();
            ps.setString(5, entry);
            System.out.print("Enter the state: ");
            entry = sc.nextLine();
            ps.setString(6, entry);
            System.out.print("Enter the country: ");
            entry = sc.nextLine();
            ps.setString(7, entry);
            System.out.print("Enter the email: ");
            entry = sc.nextLine();
            ps.setString(8, entry);
            System.out.print("Enter the phone number: ");
            entry = sc.nextLine();
            ps.setString(9, entry);
            ps.executeUpdate();
        }

        catch(SQLIntegrityConstraintViolationException e){
            System.out.println("This person is already in the system.");
        }
    }

    public void addCompany() throws SQLException{
        try {
            String entry;
            PreparedStatement ps = conn.prepareStatement("INSERT INTO company VALUES(?,?,?,?,?,?,?,?)");
            System.out.print("Enter the company id: ");
            entry = sc.nextLine();
            ps.setString(1, entry);
            System.out.print("Enter the company name: ");
            entry = sc.nextLine();
            ps.setString(2, entry);
            System.out.print("Enter the street: ");
            entry = sc.nextLine();
            ps.setString(3, entry);
            System.out.print("Enter the city: ");
            entry = sc.nextLine();
            ps.setString(4, entry);
            System.out.print("Enter the state: ");
            entry = sc.nextLine();
            ps.setString(5, entry);
            System.out.print("Enter the country: ");
            entry = sc.nextLine();
            ps.setString(6, entry);
            System.out.print("Enter the primary sector: ");
            entry = sc.nextLine();
            ps.setString(7, entry);
            System.out.print("Enter the website: ");
            entry = sc.nextLine();
            ps.setString(8, entry);

            ps.executeUpdate();
        }

        catch(SQLIntegrityConstraintViolationException e){
            System.out.println("This company is already in the system.");
        }
    }

    public void addPosition() throws SQLException{
        String positionid = "";
        try {
            String entry;
            PreparedStatement ps = conn.prepareStatement("INSERT INTO position VALUES(?,?,?,?,?,?)");
            System.out.print("Enter the position id: ");
            entry = sc.nextLine();
            positionid = entry;
            ps.setString(1, entry);
            System.out.print("Enter the company id: ");
            entry = sc.nextLine();
            ps.setString(2, entry);
            System.out.print("Enter the category id: ");
            entry = sc.nextLine();
            ps.setString(3, entry);
            System.out.print("Enter the position name: ");
            entry = sc.nextLine();
            ps.setString(4, entry);
            System.out.print("Enter the employee mode: ");
            entry = sc.nextLine();
            ps.setString(5, entry);
            System.out.print("Enter the standard pay: ");
            entry = sc.nextLine();
            ps.setInt(6, Integer.parseInt(entry));
            ps.executeUpdate();
        }

        catch(SQLIntegrityConstraintViolationException e){
            System.out.println("This company is already in the system.");
        }

        String entry = "";
        while(!entry.equalsIgnoreCase("q") && !positionid.equals("")){
            System.out.print("Enter a skill code, or q to finish: ");
            entry = sc.nextLine();
            if (entry.matches("\\d\\d\\d\\d")){
                try {
                    PreparedStatement ps2 = conn.prepareStatement("INSERT INTO requires VALUES(?,?)");
                    ps2.setString(1, positionid);
                    ps2.setInt(2, Integer.parseInt(entry));
                    ps2.executeUpdate();
                }
                catch (SQLIntegrityConstraintViolationException e){
                    System.out.println("This position already requires that skill");
                }
            }

        }




    }

    public void addCourse() throws SQLException{
        String courseid = "";
        try {
            String entry;
            PreparedStatement ps = conn.prepareStatement("INSERT INTO course VALUES(?,?,?,?,?,?)");
            System.out.print("Enter the course code: ");
            entry = sc.nextLine();
            courseid = entry;
            ps.setString(1, entry);
            System.out.print("Enter the title: ");
            entry = sc.nextLine();
            ps.setString(2, entry);
            System.out.print("Enter the course level: ");
            entry = sc.nextLine();
            ps.setString(3, entry);
            System.out.print("Enter the description: ");
            entry = sc.nextLine();
            ps.setString(4, entry);
            System.out.print("Enter the status (active or inactive): ");
            entry = sc.nextLine();
            ps.setString(5, entry);
            System.out.print("Enter the price: ");
            entry = sc.nextLine();
            ps.setInt(6, Integer.parseInt(entry));
            ps.executeUpdate();

            entry = "";

        }
        catch(SQLIntegrityConstraintViolationException e){
            System.out.println("This company is already in the system.");
        }

        try{
            String entry = "";
            while(!entry.equalsIgnoreCase("q") && !courseid.equals("")){
                System.out.print("Enter a skill code, or q to finish: ");
                entry = sc.nextLine();
                if (entry.matches("\\d\\d\\d\\d")){
                    try {
                        PreparedStatement ps2 = conn.prepareStatement("INSERT INTO teaches VALUES(?,?)");
                        ps2.setString(1, courseid);
                        ps2.setInt(2, Integer.parseInt(entry));
                        ps2.executeUpdate();
                    }
                    catch (SQLIntegrityConstraintViolationException e){
                        System.out.println("This course already teaches that skill");
                    }
                }

            }

        }
        catch(SQLException e){
            System.out.println("Error adding course information.");
        }


    }

    public void deletePerson(){
        System.out.print("Enter the person id to delete: ");
        try{
            int personid = Integer.parseInt(sc.nextLine());
            PreparedStatement ps = conn.prepareStatement("DELETE FROM person WHERE person_id = ?");
            ps.setInt(1, personid);
            ps.executeUpdate();
            System.out.println("Person entry deleted.");
        }
        catch (SQLException e){
            System.out.println("Error deleting person entry. This person's id may be in another part of the database.");
        }
        catch (NumberFormatException e){
            System.out.println("Invalid person id");
        }

    }

    public void deleteCompany(){
        System.out.print("Enter the company id to delete: ");
        try{
            String company = sc.nextLine();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM company WHERE comp_id = ?");
            ps.setString(1, company);
            ps.executeUpdate();
            System.out.println("Person entry deleted.");
        }
        catch (SQLException e){
            System.out.println("Error deleting company entry. This company's id may be in another part of the database.");
        }


    }

    public void deletePosition(){
        System.out.print("Enter the position id to delete: ");
        try{
            String position = sc.nextLine();
            PreparedStatement prep = conn.prepareStatement("DELETE FROM requires WHERE pos_code = ?");
            prep.setString(1, position);
            prep.executeUpdate();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM position WHERE pos_code = ?");
            ps.setString(1, position);
            ps.executeUpdate();
            System.out.println("Position entry deleted.");
        }
        catch (SQLException e){
            System.out.println("Error deleting company entry. This position's id may be in another part of the database.");
        }


    }

    public void deleteCourse(){
        System.out.print("Enter the course id to delete: ");
        try{
            String course = sc.nextLine();
            PreparedStatement prep = conn.prepareStatement("DELETE FROM teaches WHERE c_code = ?");
            prep.setString(1, course);
            prep.executeUpdate();
            PreparedStatement prep2 = conn.prepareStatement("DELETE FROM takes WHERE c_code = ?");
            prep2.setString(1, course);
            prep2.executeUpdate();
            PreparedStatement prep3 = conn.prepareStatement("DELETE FROM section WHERE c_code = ?");
            prep3.setString(1, course);
            prep3.executeUpdate();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM course WHERE c_code = ?");
            ps.setString(1, course);
            ps.executeUpdate();
            System.out.println("Course entry deleted.");
        }
        catch (SQLException e){
            System.out.println("Error deleting course entry. This position's id may be in another part of the database.");
        }


    }


}
