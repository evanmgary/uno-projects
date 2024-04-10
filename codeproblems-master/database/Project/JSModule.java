package Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class for part 8b of the project.
 * @author Evan Gary
 */
public class JSModule {
    private Connection conn;
    private int personid;
    private Scanner sc;
    private Queries quer;




    public JSModule(Connection conn) throws SQLException{

        this.conn = conn;
        sc = new Scanner(System.in);
        quer = new Queries(this.conn);

    }

    /**
     * Runs the queries based on user choice. Return format is a bit different from the queries themselves in some cases.
     * @throws SQLException
     */
    public void runModule() throws SQLException{
        System.out.print("Enter your id: ");
        personid = sc.nextInt();
        int choice = 0;
        while(true) {
            System.out.println("What would you like to do?");
            System.out.println("1) Find qualified jobs");
            System.out.println("2) Find qualified job categories");
            System.out.println("3) Find highest paying qualified job");
            System.out.println("4) Exit module");
            try {
                choice = sc.nextInt();
            }
            catch(InputMismatchException e){
                System.out.println("Enter a valid choice.");
                continue;
            }
            if (choice < 1 || choice > 4){
                System.out.println("Enter a valid choice.");
                continue;
            }
            if (choice == 1){
                choice1(personid);
            }
            else if (choice == 2){
                choice2(personid);

            }
            else if (choice == 3){
                choice3(personid);
            }
            else if (choice == 4){
                break;
            }
        }

    }

    /**
     * Basic job search. Implements a modified query 14 (shows all qualified jobs).
     * @param personid
     * @throws SQLException
     */
    public void choice1(int personid) throws SQLException{

        ResultSet rs = quer.query14b(personid);

        ArrayList<ArrayList<Object>> results = JobApp.toArrayList(rs);
        System.out.println("(Position code, Position name, Company, Employee type, Advertised pay)");

        JobApp.printAsCsv(results);

    }

    /**
     * Implements query 13 (job categories)
     * @param personid
     * @throws SQLException
     */
    public void choice2(int personid) throws SQLException{

        ResultSet rs = quer.query13(personid);
        ArrayList<ArrayList<Object>> results = JobApp.toArrayList(rs);
        JobApp.printAsCsv(results);

    }

    /**
     * Implements query 14 as written (highest paying job)
     * @param personid
     * @throws SQLException
     */
    public void choice3(int personid) throws SQLException{

        ResultSet rs = quer.query14(personid);
        ArrayList<ArrayList<Object>> results = JobApp.toArrayList(rs);
        JobApp.printAsCsv(results);

    }

}
