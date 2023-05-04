package org.example;

import Close.CLoseBD;
import Connection.ConnectionDB;
import Create.CreateDB;
import Write.WriteDB;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args){
        try {
            ConnectionDB.connectDB();
            CreateDB.createDB();
            WriteDB.writeDB();
            CLoseBD.closeDB();
        } catch (Exception e){
            System.out.println();
        }

    }
}