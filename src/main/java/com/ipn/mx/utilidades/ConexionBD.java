
package com.ipn.mx.utilidades;

import com.ipn.mx.modelo.dao.UsuarioDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConexionBD {
    Connection con;
    
    public ConexionBD(){}
    
    public Connection obtenerConexion() {
        String user = "b618971292afe5";
        String pwd = "2971486e";
        String url = "jdbc:mysql://us-cdbr-east-05.cleardb.net:3306/heroku_305578cf087e9cc?serverTimezone=America/Mexico_City";
        String mySqlDriver = "com.mysql.cj.jdbc.Driver";

        try {
            Class.forName(mySqlDriver);
            con = DriverManager.getConnection(url, user, pwd);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
}
