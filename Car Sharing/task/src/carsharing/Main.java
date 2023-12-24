package carsharing;


import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        DB db = new DB();
        CompanyDB companyDB = new CompanyDB();
        CarDB carDB = new CarDB();
        CustomerDB customerDB = new CustomerDB();
        String name = args[1];
        Scanner scanner = new Scanner(System.in);

        try {
            Statement stmt = db.initialize(name);
            companyDB.initialize(stmt);
            carDB.initialize(stmt);
            customerDB.initialize(stmt);
            MainMenu.menu(scanner,companyDB,carDB,customerDB);
            db.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("error: "+e.getMessage());;
        }
    }
}