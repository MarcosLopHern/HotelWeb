
package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.dto.UsuarioDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO {
    private static final String SQL_INSERT = "{call sp_crearUsuario(?,?,?)}";
    private static final String SQL_DELETE="{call sp_eliminarUsuario(?)}";
    private static final String SQL_SELECT="{call sp_login(?,?)}";
    private Connection con;
    
    public Connection obtenerConexion() {
        String user = "root";
        String pwd = "root";
        String url = "jdbc:mysql://localhost:3306/Hotel?serverTimezone=America/Mexico_City&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&useSSL=false";
        String mySqlDriver = "com.mysql.cj.jdbc.Driver";

        try {
            Class.forName(mySqlDriver);
            con = DriverManager.getConnection(url, user, pwd);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return con;
    }
    
    public String crear(UsuarioDTO dto) throws SQLException{
        String res = "";
        con = obtenerConexion();
        CallableStatement cs = null;
        try {
            cs = con.prepareCall(SQL_INSERT);
            cs.setString(1, dto.getEntidad().getNombreUsuario());
            cs.setString(2, dto.getEntidad().getPswrd());
            cs.setString(3, dto.getEntidad().getTipo());
            ResultSet rs = cs.executeQuery();
            rs.next();
            res = rs.getString("msj");
        } finally {
            if (cs != null) {
                cs.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return res;
    }
    
    public String sesion(UsuarioDTO dto) throws SQLException{
        String res = "";
        con = obtenerConexion();
        CallableStatement cs = null;
        try {
            cs = con.prepareCall(SQL_SELECT);
            cs.setString(1, dto.getEntidad().getNombreUsuario());
            cs.setString(2, dto.getEntidad().getPswrd());
            ResultSet rs = cs.executeQuery();
            rs.next();
            res = rs.getString("msj");
        } finally {
            if (cs != null) {
                cs.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return res;
    }
    
     public String eliminar(UsuarioDTO dto) throws SQLException{
        String res = "";
        con = obtenerConexion();
        CallableStatement cs = null;
        try {
            cs = con.prepareCall(SQL_DELETE);
            cs.setString(1, dto.getEntidad().getNombreUsuario());
            ResultSet rs = cs.executeQuery();
            rs.next();
            res = rs.getString("msj");
        } finally {
            if (cs != null) {
                cs.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return res;
    }
    
     public static void main(String[] args) {
        UsuarioDAO dao = new UsuarioDAO();
        UsuarioDTO usr = new UsuarioDTO();
        usr.getEntidad().setNombreUsuario("Cesar");
        usr.getEntidad().setPswrd("asd");
        usr.getEntidad().setTipo("administrador");
        try {
            System.out.println("Resultado: " + dao.sesion(usr));
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
