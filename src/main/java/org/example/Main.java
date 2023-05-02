package org.example;

import Close.CLoseBD;
import Connection.ConnectionDB;
import Create.CreateDB;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        ConnectionDB.conn();
        CreateDB.createDB();
        CLoseBD.closeDB();
    }
}