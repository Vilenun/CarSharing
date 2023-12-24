package carsharing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CustomerDB {
    Statement stmt = null;


    public void initialize(Statement stmt) throws SQLException {
        this.stmt = stmt;
        stmt.executeUpdate("create table if not exists CUSTOMER (" +
                "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                "NAME VARCHAR(50) UNIQUE NOT NULL, " +
                "RENTED_CAR_ID INT default NULL, " +
                "FOREIGN KEY (RENTED_CAR_ID) REFERENCES car(id) " +
                "on delete cascade" +
                ")");

    }

    public void add(String name) throws SQLException {
        stmt.executeUpdate(String.format("insert into CUSTOMER(name) values('%s')",name));
        System.out.println("The customer was added!");
    }

    public int show(Scanner scanner) throws SQLException{
        ResultSet rs = stmt.executeQuery("select name from CUSTOMER where id=1");
        if(!rs.isBeforeFirst()){
            System.out.println("The customer list is empty!"); //data not exist
             rs.close();
            return  0;
        }
        rs.close();

        ResultSet rsAll = stmt.executeQuery("select * from customer");


        System.out.println("Customer list:");
        while (rsAll.next()) {
            System.out.println(rsAll.getInt("id") + ". " + rsAll.getString("name"));
        }
        System.out.println("0. Back");

        return scanner.nextInt();


    }
    public void rent(CarDB carDB, CompanyDB companyDB, int customer, Scanner scanner) throws SQLException {
        ResultSet rsRent = stmt.executeQuery(String.format("""
                    SELECT
                    *
                    FROM
                    customer
                    WHERE id = %d AND rented_car_id is null""", customer));
        if (!rsRent.isBeforeFirst()) {
            System.out.println("You've already rented a car!");
            rsRent.close();
            return;
        }
        rsRent.close();
        ResultSet rs = stmt.executeQuery("select * from company");
        if (!rs.isBeforeFirst()) {
            System.out.println("The company list is empty!");
            rs.close();
        return;
        }
        rs.close();
        companyDB.showCompanies(scanner,carDB,customer);



    }
    public void returnCar(int customer) throws SQLException {
        ResultSet rs2 = stmt.executeQuery(String.format("""
                SELECT
                *
                FROM
                customer
                WHERE id = %d AND rented_car_id is not null""",customer));
        if (!rs2.isBeforeFirst()) {
            System.out.println("You didn't rent a car!");
        } else {stmt.executeUpdate(String.format("""
                update customer
                set rented_car_id = NULL
                where id = %d""",customer));

            System.out.println("You've returned a rented car!");
        }
        rs2.close();
    }
    public void checkRent(int customer) throws SQLException {
        ResultSet rs2 = stmt.executeQuery(String.format("""
                SELECT
                *
                FROM
                customer
                WHERE id = %d AND rented_car_id is not null""",customer));
        if (!rs2.isBeforeFirst()) {
            System.out.println("You didn't rent a car!");
            rs2.close();
            return;
        }
        rs2.close();
        ResultSet rsCheck = stmt.executeQuery(String.format("""
                SELECT
                company.name as Company_name,
                car.name as Car_name
                FROM
                company
                join car
                on company.id = car.company_id
                join customer
                on car.id = customer.rented_car_id
                WHERE customer.id = %d""",customer));
        while(rsCheck.next()){
            System.out.println("Your rented car:\n"+
                    rsCheck.getString("Car_name")+
                    "\nCompany:\n"+
                    rsCheck.getString("Company_name"));
        }

    }

}

