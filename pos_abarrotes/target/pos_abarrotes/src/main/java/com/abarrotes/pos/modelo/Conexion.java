package com.abarrotes.pos.modelo;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    Connection con;

    public Connection getConnection() {

        try {
            String url = "jdbc:mysql://localhost:3306/pos_abarrotes";
            String user = "root";
            String pass = "0101";

            con = DriverManager.getConnection(url, user, pass);
            System.out.println("Conexión exitosa");

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return con;
    }
}