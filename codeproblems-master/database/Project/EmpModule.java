package Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class for part 8c
 * @author Evan Gary
 */
public class EmpModule {

    private Connection conn;
    private Scanner sc;
    private Queries quer;


    public EmpModule(Connection conn) throws SQLException{

        this.conn = conn;
        sc = new Scanner(System.in);

        quer = new Queries(this.conn);
    }

    public void runModule() throws SQLException{
        System.out.print("Enter your company id: ");
        String companyId = sc.nextLine();
        int choice = 0;
        try {
            while (true) {
                System.out.println("What would you like to do?");
                System.out.println("1) Find qualified employees for a position");
                System.out.println("2) Find nearly qualified persons for a position");
                System.out.println("3) Exit module");
                sc = new Scanner(System.in);
                choice = Integer.parseInt(sc.nextLine());
                if (choice < 1 || choice > 3) {
                    System.out.println("Enter a valid choice.");
                    continue;
                }
                //List of qualified people
                if (choice == 1) {
                    System.out.print("Enter a position id: ");
                    String userInput = sc.nextLine();
                    ResultSet rs = quer.query15(userInput);
                    ArrayList<ArrayList<Object>> results = JobApp.toArrayList(rs);
                    System.out.println("Employee id, Employee name, Email");
                    JobApp.printAsCsv(results);
                //Missing one list if no one is qualified
                } else if (choice == 2) {
                    System.out.print("Enter a position id: ");
                    String userInput = sc.nextLine();
                    ResultSet rs = quer.query15(userInput);
                    ArrayList<ArrayList<Object>> results = JobApp.toArrayList(rs);

                    if (results.size() > 0) {
                        System.out.println("Employee id, Employee name, Email");
                        JobApp.printAsCsv(results);
                    }
                    else {
                        rs = quer.query18b(userInput);
                        results = JobApp.toArrayList(rs);
                        System.out.println("Employee id, Employee name, Email, number of skills missing");
                        JobApp.printAsCsv(results);
                    }
                //Inserts

                } else if (choice == 3) {
                    break;
                }

            }
        }
        catch (SQLException s){
            s.printStackTrace();
            System.out.println("Error, returning to main module.");
        }
    }


}
