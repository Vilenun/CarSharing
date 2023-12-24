package carsharing;

import java.sql.*;
import java.util.Scanner;

public class CarDB {

    Statement stmt = null;
    public void initialize(Statement stmt) throws ClassNotFoundException, SQLException {
        this.stmt = stmt;
        stmt.executeUpdate("create table if not exists CAR (" +
                "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                "NAME VARCHAR(50) UNIQUE NOT NULL, " +
                "COMPANY_ID INT NOT NULL, " +
                "FOREIGN KEY (COMPANY_ID) REFERENCES company(id) " +
                "on delete cascade" +
                ")");
        stmt.executeUpdate("""
                ALTER TABLE COMPANY ALTER COLUMN ID RESTART WITH 1""");
    }
    public void show(String company,Statement stmt) throws SQLException {
        boolean isEmpty = true;
        ResultSet rs = stmt.executeQuery(String.format("select * from CAR " +
                "where company_id = select (id) from company where name = '%s'",company));
        if(!rs.isBeforeFirst()){
            System.out.println("The car list is empty!"); //data not exist
        }
        else{
            isEmpty = false;
        }
        rs.close();
        ResultSet rsAll = stmt.executeQuery(String.format("select * from CAR " +
                "where company_id = select (id) from company where name = '%s'",company));
        if (!isEmpty) {
            System.out.println("Car list");
            int i=0;
            while (rsAll.next()) {
                i++;
                System.out.println(i + ". " + rsAll.getString("name"));
            }
        }

    }
    public void add(Statement stmt,String name,int companyID) throws SQLException {
        stmt.executeUpdate(String.format("insert into Car(name,company_id) values('%s' , '%d')",name, companyID));
        System.out.println("The car was added!");
    }
    public boolean showCustomer(int chosenCompany, Scanner scanner, int customer) throws SQLException {
        ResultSet rs = stmt.executeQuery(String.format("""
                SELECT
                         ID,NAME,COMPANY_ID
                FROM
                         CAR
                WHERE
                         COMPANY_ID =  %d
                         AND
                         ID NOT IN (
                                             SELECT RENTED_CAR_ID
                                             FROM CUSTOMER
                                             WHERE RENTED_CAR_ID IS NOT NULL)""",chosenCompany));
        if(!rs.isBeforeFirst()){
            rs.close();
            ResultSet rs1 = stmt.executeQuery(String.format("select name from company where id = %d",chosenCompany));
            System.out.printf("No available cars in the %s company\n",rs1.getString("name"));
            rs1.close();
            return false;
        }
        rs.close();
        int i = 0;
        ResultSet rs2 = stmt.executeQuery(String.format("""
                SELECT
                         ID,NAME,COMPANY_ID
                FROM
                         CAR
                WHERE
                         COMPANY_ID =  %d
                         AND
                         ID NOT IN (
                                             SELECT RENTED_CAR_ID
                                             FROM CUSTOMER
                                             WHERE RENTED_CAR_ID IS NOT NULL)""",chosenCompany));
        while (rs2.next()) {
            i++;
            System.out.println(i + ". " + rs2.getString("name"));
        }
        System.out.println("0. Back");
        int chosenCar = scanner.nextInt();
        scanner.nextLine();
        if (chosenCar==0){
            return false;
        }
        stmt.executeUpdate(String.format("""
                         update customer
                         set rented_car_id = %d
                         where id = %d""",chosenCar,customer));
            ResultSet rented = stmt.executeQuery("""
                    SELECT CAR.NAME FROM CAR
                    LEFT JOIN CUSTOMER
                    ON CAR.ID = CUSTOMER.RENTED_CAR_ID
                    """);
            rented.next();
            System.out.printf("You rented '%s'\n" ,rented.getString("name"));
        return true;
    }
}

