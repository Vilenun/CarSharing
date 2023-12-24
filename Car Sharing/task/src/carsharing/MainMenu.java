package carsharing;

import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {
    static void menu(Scanner scanner,CompanyDB companyDB,CarDB carDB, CustomerDB customerDB) throws SQLException {

        int end =-1;
        while (end!=0) {
            System.out.println("""
                1. Log in as a manager
                2. Log in as a customer
                3. Create a customer
                0. Exit""");
            end = scanner.nextInt();
            scanner.nextLine();
            switch (end) {
                case 1:
                    ManagerMenu.menu(scanner, companyDB, carDB);
                    break;
                case 2:
                    int customer=customerDB.show(scanner);
                    if (customer>0) {
                        CustomerMenu.menu(scanner, companyDB, carDB,customerDB,customer);
                    }
                    break;
                case 3:
                    System.out.println("Enter the customer name:");
                    customerDB.add(scanner.nextLine());
                    break;
                case 0:
                    break;

            }
        }
    }

}
