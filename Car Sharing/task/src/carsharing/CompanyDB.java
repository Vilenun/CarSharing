package carsharing;

import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

public class CompanyDB {
    CompanyDB() {}
//    static final String JDBC_DRIVER = "org.h2.Driver";
//    Connection conn = null;
    Statement stmt = null;

    HashMap<Integer,String> companies;

    public void initialize(Statement stmt) throws ClassNotFoundException, SQLException {
//        String DB_URL = String.format( "jdbc:h2:./src/carsharing/db/%s",name);
//        Class.forName(JDBC_DRIVER);
//        this.conn = DriverManager.getConnection(DB_URL);
//        conn.setAutoCommit(true);
//        this.stmt = conn.createStatement();
        this.stmt = stmt;
        stmt.executeUpdate("create table if not exists COMPANY (" +
                "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                "NAME VARCHAR(50) UNIQUE NOT NULL)");

    }

    public void add(String name) throws SQLException {
        stmt.executeUpdate(String.format("insert into company(name) values('%s')",name));
        System.out.println("The company was created!");
    }

    public boolean show() throws SQLException{
        boolean isEmpty = true;
        this.companies = new HashMap<>();
        ResultSet rs = stmt.executeQuery("select name from company where id=1");
        if(!rs.isBeforeFirst()){
            System.out.println("The company list is empty!"); //data not exist
        }
        else{
            isEmpty = false;
        }
        rs.close();
        ResultSet rsAll = stmt.executeQuery("select * from company");

        if (!isEmpty) {
            System.out.println("Choose the company:");
            while (rsAll.next()) {
                companies.put(rsAll.getInt("id"),rsAll.getString("name"));
                System.out.println(rsAll.getInt("id") + ". " + rsAll.getString("name"));
            }
            System.out.println("0. Back");
            return true;
        } return false;
    }

    public void showCompanies(Scanner scanner, CarDB carDB, int customer) throws SQLException {
        int choiceCompany=-1;
        while(choiceCompany!=0) {
            ResultSet rsAll = stmt.executeQuery("select * from company order by id");
            System.out.println("Choose the company:");
            while (rsAll.next()) {
                System.out.println(rsAll.getInt("id") + ". " + rsAll.getString("name"));
            }
            System.out.println("0. Back");
            choiceCompany = scanner.nextInt();
            scanner.nextLine();
            if (choiceCompany!=0) {
                if (carDB.showCustomer(choiceCompany, scanner,customer)){
                    choiceCompany =0;
                };
            }
        }
    }
}
