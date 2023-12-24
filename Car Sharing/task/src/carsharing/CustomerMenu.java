package carsharing;

import java.sql.SQLException;
import java.util.Scanner;

public class CustomerMenu {
    static void menu(Scanner scanner, CompanyDB companyDB, CarDB carDB,CustomerDB customerDB, int customer) throws SQLException {
        int choice=-1;
        while(choice!=0){

            System.out.println("""
                1. Rent a car
                2. Return a rented car
                3. My rented car
                0. Back""");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 1:
                    customerDB.rent(carDB,companyDB,customer,scanner);
                    break;
                case 2:
                    customerDB.returnCar(customer);
                    break;
                case 3:
                    customerDB.checkRent(customer);
                    break;
                case 0:
                    break;
            }
        }
    }
}
