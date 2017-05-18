import com.sun.org.apache.xpath.internal.SourceTree;

import javax.xml.transform.Result;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;

/**
 * Created by Claps on 18/05/2017.
 */
public class Main {

    public static void main(String[] args) {

        int choice;
        Scanner scnnr = new Scanner(System.in);


        do {
            menuDisplay();

            choice = scnnr.nextInt();

            switch (choice) {

                case 1:

                    System.out.print("Creating a new Player. Please enter the id of the player you want to insert into the database: ");
                    Integer id = scnnr.nextInt();
                    System.out.print("Enter the name of the player you want to insert into the database: ");
                    String name = scnnr.next();
                    System.out.print("Enter the first surname of the player you want to insert into the database: ");
                    String surname1 = scnnr.next();
                    System.out.print("Enter the seco1nd surname of the player you want to insert into the database: ");
                    String surname2 = scnnr.next();
                    System.out.print("Enter the password of the player you want to insert into the database: ");
                    String newpassword = scnnr.next();
                    System.out.print("Enter the email of the player you want to insert into the database: ");
                    String newemail = scnnr.next();
                    addPlayer(id, name, surname1, surname2, newpassword, newemail);
                    break;

                case 2:

                    System.out.println("What table do you want?");
                    String tabla = scnnr.next();
                    showTable(tabla);
                    break;

                case 3:

                    System.out.println("Enter the id of the player you want to search");
                    Integer playerId = scnnr.nextInt();
                    findById(playerId);
                    break;

                case 4:

                    System.out.println("Enter the id of the player you want to delete");
                    Integer deleteId = scnnr.nextInt();
                    deletePlayerDatabase(deleteId);
                    System.out.println("Player deleted");
                    break;

                case 5:
                    System.out.println("Goodbye");
                    break;

                default:
                    System.out.println("Please enter a valid command");
                    break;

            }
        }
            while(choice != 5);
    }

    public static void showTable(String table) {
        try {
            //Getting a connection to the database
            Connection myConn = Singleton.getConn();

            //Creating a statement
            PreparedStatement myStat;

            String query = String.format("select * from %s", table);

            myStat = myConn.prepareStatement(query);

            //No idea what this does
            ResultSet rSet = myStat.executeQuery();

            ResultSetMetaData rsmd = rSet.getMetaData();

            int columnsNumber = rsmd.getColumnCount();

            while (rSet.next()) {
                //Print one row
                for(int i = 1; i <= columnsNumber; i++) {
                    System.out.println(rSet.getString(i) + " ");//Print an element of the row
                }
                System.out.println();//Move to the next line to print the next row
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /*
    THIS WONT WORK

    private static void addPlayer(int id, String nombre, String apellido1, String apellido2, String password, String email) {
        try {
            Connection myConn = Singleton.getConn();

            Statement myStat = myConn.createStatement();

            String sql = "insert into players " +  "(id, nombre, apellido1, apellido2, password, email)" + "values ("+ "'" + id + "'" + ", " + "'" + nombre + "'" + ", " + "'" + apellido1 + "'" + ", " + "'" + apellido2 + "'" + ", " + "'" + password + "'" + ", " + "'" + email + "'" + ")";

            myStat.executeUpdate(sql);

            System.out.println("Insert completed");
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }*/

    private static void addPlayer(int id, String name, String lastName1, String lastName2, String psw, String email) {

        try {
            //Getting a connection to the database
            Connection conn = Singleton.getConn();

            //Creating a statement
            PreparedStatement stmnt = conn.prepareStatement("insert into players"
                    + "(id, nombre, apellido1, apellido2, password, email)"
                    + "values (?,?,?,?,?,?);");

            stmnt.setInt(1, id);
            stmnt.setString(2, name);
            stmnt.setString(3, lastName1);
            stmnt.setString(4, lastName2);
            stmnt.setString(5, psw);
            stmnt.setString(6, email);

            //Executing SQL query
            stmnt.executeUpdate();
            System.out.println("Insert complete.");
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }


    private static void deletePlayerDatabase(int id) {

        try {
            //Getting a connection to the database
            Connection myConn = Singleton.getConn();

            //Creating a statement
            PreparedStatement myStat;

            //Executing SQL query
            myStat = myConn.prepareStatement("delete from players where id= ?");

            myStat.setInt(1, id);

            myStat.executeUpdate();

        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private static void findById(int id) {

        try {
            //Getting a connection to the database
            Connection myConn = Singleton.getConn();

            //Creating a statement
            PreparedStatement myStat;

            //Executing SQL query
            myStat = myConn.prepareStatement("select * from players where id = ?");

            myStat.setInt(1,id);

            ResultSet res = myStat.executeQuery();

            if(res.next()) {

                String str = res.getString("id") + " " + res.getString("nombre") + " " + res.getString("apellido1") + " " + res.getString("apellido2") + " " + res.getString("password") + " " + res.getString("email") + " ";

                System.out.println(str);
            }

        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public static void menuDisplay() {
        System.out.println("DATABASE OPERATIONS");
        System.out.println(" --------------------- ");
        System.out.println(" 1 - Add a player");
        System.out.println(" 2 - Show table");
        System.out.println(" 3 - Search for a player by Id");
        System.out.println(" 4 - Delete a player by Id");
        System.out.println(" 5 - Exit");

        System.out.print("\n");

        System.out.print("Please choose how you want to interact with the database: \n");
    }
}


