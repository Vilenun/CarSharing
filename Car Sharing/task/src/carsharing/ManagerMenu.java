package carsharing;

import java.sql.SQLException;
import java.util.Scanner;

public class ManagerMenu {

    static void menu(Scanner scanner, CompanyDB companyDB, CarDB carDB) throws SQLException {


    int choice = -1;
    while (choice!=0){
        System.out.println("""
                
                1. Company list
                2. Create a company
                0. Back""");
        choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 1:
                if (companyDB.show()){
                    CarMenu.menu(scanner,companyDB,carDB);
                }
                break;
            case 2:
                System.out.println("Enter the company name:");
                companyDB.add(scanner.nextLine());
                break;
            case 0:
                break;

        }
    }
}
}
