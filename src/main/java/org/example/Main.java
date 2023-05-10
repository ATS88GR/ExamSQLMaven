package org.example;

import Close.CLoseBD;
import Connection.ConnectionDB;
import Create.CreateDB;
import Read.ReadDB;
import Write.WriteDB;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args){
        try {
            ConnectionDB.connectDB();
            CreateDB.createDB();
            WriteDB.writeDB();
            ReadDB.readDB();
            CLoseBD.closeDB();
        } catch (Exception e){
            System.out.println();
        }

    }
}