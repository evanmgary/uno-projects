package Project;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main app for database project.
 * @author Evan Gary
 */
public class JobApp {

    private static Connection conn;
    private static Scanner sc = new Scanner(System.in);
    private static Queries quer;

    public static void main(String[] args) throws SQLException{

        //Uses Dr. Tu's file to get the connection
       DBConnection tc = new DBConnection("dbsvcs.cs.uno.edu", 1521, "orcl");
       conn = tc.getDBConnection(args[0], args[1]);
       //Queries is an automatically generated java class using our GenerateCode class
       quer = new Queries(conn);
       int mode = 0;

       while(true) {
           System.out.println();
           System.out.println("Welcome to the Gary-Tosh Work Solutions App.");
           System.out.println("1) Human Resources Mode"); //8a
           System.out.println("2) Job Seeker Mode"); //8b
           System.out.println("3) Employer Mode"); //8c
           System.out.println("4) Designed Query Mode"); //7
           System.out.println("5) Custom Query Mode");
           System.out.println("6) Database Modification");
           System.out.println("7) Exit");
           System.out.print("Please enter your chosen mode: ");
           try {
               mode = Integer.parseInt(sc.nextLine());
           } catch (NumberFormatException e) {
               System.out.println("Try again.");
               mode = 0;
           }
           switch (mode) {
               case 1:
                   HRModule hr = new HRModule(conn);
                   hr.runModule();
                   break;
               case 2:
                   JSModule js = new JSModule(conn);
                   js.runModule();
                   break;
               case 3:
                   EmpModule emp = new EmpModule(conn);
                   emp.runModule();
                   break;
               case 4:
                   System.out.print("Enter the query to run: ");

                   try {
                       int choice = Integer.parseInt(sc.nextLine());
                       ArrayList<ArrayList<Object>> result= runQuery(choice);
                       printAsCsv(result);
                   }
                   catch (SQLException e){
                       e.printStackTrace();
                       System.out.println("SQL error");
                   }
                   catch(NumberFormatException e){
                       System.out.println("Incorrect input type.");
                   }
                   break;
               case 5:
                   freeQuery();
                   break;
               case 6:
                   ModifyDB mod = new ModifyDB(conn);
                   mod.runModule();
                   break;
               case 7:
                   System.exit(0);
                   break;
               default:
                   System.out.print("Enter a valid mode.");
                   break;

           }
       }

    }

    /**
     * Runs the project queries based on the number in the assignment paper
     * @param toRun number of query to run
     * @return 2d array list with data
     * @throws SQLException
     */
    public static ArrayList<ArrayList<Object>> runQuery(int toRun) throws  SQLException{
        ArrayList<ArrayList<Object>> result= new ArrayList<>();
        String input;
        String input2;
        ResultSet resultSet = null;
        Scanner sc = new Scanner(System.in);
        switch(toRun){
            case 1:
                System.out.print("Enter the company id:");
                input = sc.nextLine();
                resultSet = quer.query1(input);
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 2:
                System.out.print("Enter the company id:");
                input = sc.nextLine();
                resultSet = quer.query2(input);
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 3:
                resultSet = quer.query3();
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 4:
                System.out.print("Enter the person id:");
                input = sc.nextLine();
                System.out.println(Integer.parseInt(input));
                resultSet = quer.query4(Integer.parseInt(input));
                result = toArrayList(resultSet);
                ////result.add(0, getColumnName(resultSet));
                break;
            case 5:
                System.out.print("Enter the person id:");
                input = sc.nextLine();
                resultSet = quer.query5(Integer.parseInt(input));
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 6:
                System.out.print("Enter the person id:");
                input = sc.nextLine();
                resultSet = quer.query6(Integer.parseInt(input), Integer.parseInt(input));
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 7:
                System.out.print("Enter the position id:");
                input = sc.nextLine();
                resultSet = quer.query7a(input);
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 8:
                System.out.print("Enter the position id:");
                input = sc.nextLine();
                System.out.print("Enter the person id:");
                input2 = sc.nextLine();
                resultSet = quer.query8(input, Integer.parseInt(input2));
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 9:
                System.out.print("Enter the position id:");
                input = sc.nextLine();
                System.out.print("Enter the person id:");
                input2 = sc.nextLine();
                resultSet = quer.query9(input, Integer.parseInt(input2));
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 10:
                System.out.print("Enter the position id:");
                input = sc.nextLine();
                System.out.print("Enter the person id:");
                input2 = sc.nextLine();
                resultSet = quer.query10(input, Integer.parseInt(input2));
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 11:
                System.out.print("Enter the position id:");
                input = sc.nextLine();
                System.out.print("Enter the person id:");
                input2 = sc.nextLine();
                resultSet = quer.query11(input, Integer.parseInt(input2));
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 12:
                System.out.print("Enter the person id:");
                input = sc.nextLine();
                System.out.print("Enter the position id:");
                input2 = sc.nextLine();
                resultSet = quer.query12(input2,Integer.parseInt(input));
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 13:
                System.out.print("Enter the person id:");
                input = sc.nextLine();
                resultSet = quer.query13(Integer.parseInt(input));
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 14:
                System.out.print("Enter the person id:");
                input = sc.nextLine();
                resultSet = quer.query14(Integer.parseInt(input));
                result = toArrayList(resultSet);               //result.add(0, getColumnName(resultSet));
                break;
            case 15:
                System.out.print("Enter the position id:");
                input = sc.nextLine();
                resultSet = quer.query15(input);
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 16:
                System.out.print("Enter the position id:");
                input = sc.nextLine();
                resultSet = quer.query16(input, input);
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 17:
                System.out.print("Enter the position id:");
                input = sc.nextLine();
                resultSet = quer.query17(input, input, input);
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 18:
                System.out.print("Enter the position id:");
                input = sc.nextLine();
                resultSet = quer.query18(input);
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 19:
                System.out.print("Enter the person id:");
                input = sc.nextLine();
                System.out.print("Enter the missing-k value:");
                input2 = sc.nextLine();
                resultSet = quer.query19(input, input, Integer.parseInt(input2));
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;

            case 20:
                System.out.print("Enter the position id:");
                input = sc.nextLine();
                resultSet = quer.query20(input, input, input);
                result = toArrayList(resultSet);               //result.add(0, getColumnName(resultSet));
                break;

            case 21:
                System.out.print("Enter the job category code:");
                input = sc.nextLine();
                resultSet = quer.query21(input);
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 22:
                System.out.print("Enter the position id:");
                input = sc.nextLine();
                resultSet = quer.query22(input);
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 23:
                resultSet = quer.query23a();
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 24:
                resultSet = quer.query24a();
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 26:
                resultSet = quer.query26();
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 27:
                resultSet = quer.query27();
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 61:
                System.out.print("Enter the position id:");
                input = sc.nextLine();
                System.out.print("Enter the person id:");
                input2 = sc.nextLine();
                resultSet = quer.query6b(input, Integer.parseInt(input2));
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;

            case 71:
                System.out.print("Enter the position id:");
                input = sc.nextLine();
                resultSet = quer.query7b(input);
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;

            case 181:
                System.out.print("Enter the position id:");
                input = sc.nextLine();
                resultSet = quer.query18(input);
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 231:
                resultSet = quer.query23b();
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 25:
                resultSet = quer.query25a();
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 251:
                resultSet = quer.query25b();
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 252:
                resultSet = quer.query25c();
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;
            case 253:
                resultSet = quer.query25d();
                result = toArrayList(resultSet);
                //result.add(0, getColumnName(resultSet));
                break;

        }
        JobApp.printColumnsCsv(resultSet);
        return result;
    }

    /**
     * Allows entry of any sql (sort of a debug mode)
     * @throws SQLException
     */
    public static void freeQuery() throws SQLException{
        TableInfo2 ti = new TableInfo2(conn);
        Scanner sc2 = new Scanner(System.in);
        sc2.useDelimiter(";");
        while(true) {
            System.out.print("Enter your query:");
            String query = sc2.next();
            if (query.contains("quit")){
                break;
            }
            try{
                PreparedStatement ps = conn.prepareStatement(query);
                if (query.contains("insert") || query.contains("delete") || query.contains("update")){
                    ps.executeUpdate();
                }
                else{
                    ResultSet rs = ps.executeQuery();
                    ArrayList<ArrayList<Object>> result= toArrayList(rs);
                    //result.add(0, TableInfo2.getColumnName(rs));
                    printTable(result);
                }


            }
            catch(SQLSyntaxErrorException e){
                System.out.println("Bad SQL. Try again.");
            }

        }

    }

    /**
     * Prints table using the format in Dr. Tu's TableInfo2
     * @param toPrint
     */
    public static void printTable(ArrayList<ArrayList<Object>> toPrint){
        for (ArrayList<Object> row : toPrint) {
            for (Object value : row) {
                System.out.print(value + "\t\t|");
            }
            System.out.println();
        }
    }

    /**
     * Prints the column names as in csv format
     * @param rs a resultset
     * @throws SQLException
     */
    public static void printColumnsCsv(ResultSet rs) throws SQLException{
        ArrayList<Object> list = getColumnName(rs);
        System.out.print("(");
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1){
                System.out.print(list.get(i));
            }
            else{
                System.out.print(list.get(i) + ", ");
            }
        }
        System.out.println(")");
    }

    /**
     * Prints the result set from a 2d arraylist in csv format
     * Algorithm is based on Dr. Tu's print table algorithm in TableInfo2
     * @param toPrint
     */
    public static void printAsCsv(ArrayList<ArrayList<Object>> toPrint){
        for (ArrayList<Object> row : toPrint) {
            System.out.print("(");
            for (int i = 0; i < row.size(); i++) {
                if (i == row.size() - 1){
                    System.out.print(row.get(i));
                }
                else {
                    System.out.print(row.get(i) + ", ");
                }
            }
            System.out.println(")");
        }
    }

    /**
     * Returns the column names for a given resultset
     * @param rs a resultset
     * @return
     * @throws SQLException
     */
    public static ArrayList<Object> getColumnName(ResultSet rs) throws SQLException {
        
        ResultSetMetaData rmd = rs.getMetaData();
        int colNum = rmd.getColumnCount();
        ArrayList<Object> col = new ArrayList<>();
        for (int i = 0; i < colNum; i++) {
            col.add(rmd.getColumnName(i+1));
        }
        return col;
    }

    /**
     * Turns the resultset into a 2d arraylist. Based on a similar method in TableInfo2
     * @param rs
     * @return
     * @throws SQLException
     */
    public static ArrayList<ArrayList<Object>> toArrayList(ResultSet rs) throws SQLException{

        ResultSetMetaData rsmd = rs.getMetaData();
        int col = rsmd.getColumnCount();

        ArrayList<ArrayList<Object>> arr = new ArrayList<>();

        int counter = 0;

        while(rs.next()){
            arr.add(new ArrayList<>());
            for (int i = 0; i < col ; i++) {
                //System.out.println(rs.getObject(i+1));
                arr.get(counter).add(rs.getObject(i+1));
            }
            counter++;

        }


        return arr;
    }
}
