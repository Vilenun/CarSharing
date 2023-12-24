package carsharing;

import java.sql.SQLException;
import java.util.Scanner;

public class CarMenu {

    static void menu(Scanner scanner, CompanyDB companyDB, CarDB carDB) throws SQLException {
    int choiceCompany = scanner.nextInt();
    scanner.nextLine();
    if (choiceCompany == 0){return;}
    String company = companyDB.companies.get(choiceCompany);
    int choiceCar = -1;
    while (choiceCar!=0){
        System.out.printf("""
                %s company
                1. Car list
                2. Create a car
                0. Back
                """,company);
        choiceCar = scanner.nextInt();
        scanner.nextLine();
        switch (choiceCar){
            case 1:
                carDB.show(company,companyDB.stmt);
                break;
            case 2:
                System.out.println("Enter the car name:");
                carDB.add(companyDB.stmt,scanner.nextLine(),choiceCompany);
                break;
            case 0:
                // managerMenu(scanner,companyDB,carDB);
                break;

        }
    }

}
}
