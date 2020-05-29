
package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.dto.HuespedDTO;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HuespedDAO {
    
    private static final String SQL_CREATE = "{call sp_crearHuesped(?,?,?,?,?,?,?,?)}";
    private static final String SQL_UPDATE = "{call sp_actualizarHuesped(?,?,?,?,?,?,?,?,?)}";
    private static final String SQL_DELETE = "{call sp_eliminarHuesped(?)}";
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
    public String crear(HuespedDTO dto) throws SQLException{
        String res = "";
        con = obtenerConexion();
        CallableStatement cs = null;
        try {
            cs = con.prepareCall(SQL_CREATE);
            cs.setString(1, dto.getEntidad().getNombreUsuario());
            cs.setString(2, dto.getEntidad().getNombre());
            cs.setString(3, dto.getEntidad().getApellidoPaterno());
            cs.setString(4, dto.getEntidad().getApellidoMaterno());
            cs.setString(5, dto.getEntidad().getEmail());
            cs.setString(6, dto.getEntidad().getNumeroTarjeta());
            cs.setString(7, dto.getEntidad().getTelefono());
            if(dto.getEntidad().getFoto() != null)cs.setBlob(8, dto.getEntidad().getFoto());
            else cs.setBlob(8, (Blob)null);
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
    
    public String actualizar(HuespedDTO dto) throws SQLException{
        String res = "";
        con = obtenerConexion();
        CallableStatement cs = null;
        try {
            cs = con.prepareCall(SQL_UPDATE);
            cs.setInt(1, dto.getEntidad().getIdHuesped());
            cs.setString(2, dto.getEntidad().getNombreUsuario());
            cs.setString(3, dto.getEntidad().getNombre());
            cs.setString(4, dto.getEntidad().getApellidoPaterno());
            cs.setString(5, dto.getEntidad().getApellidoMaterno());
            cs.setString(6, dto.getEntidad().getEmail());
            cs.setString(7, dto.getEntidad().getNumeroTarjeta());
            cs.setString(8, dto.getEntidad().getTelefono());
            if(dto.getEntidad().getFoto() != null)cs.setBlob(9, dto.getEntidad().getFoto());
            else cs.setBlob(9, (Blob)null);
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
    
     public String borrrar(HuespedDTO dto) throws SQLException{
        String res = "";
        con = obtenerConexion();
        CallableStatement cs = null;
        try {
            cs = con.prepareCall(SQL_DELETE);
            cs.setInt(1, dto.getEntidad().getIdHuesped());
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
        HuespedDAO dao = new HuespedDAO();
        HuespedDTO usr = new HuespedDTO();
        usr.getEntidad().setIdHuesped(1);
        usr.getEntidad().setNombreUsuario("CesarA");
        usr.getEntidad().setNombre("asd");
        usr.getEntidad().setApellidoMaterno("Diaz");
        usr.getEntidad().setApellidoPaterno("Gonzalez");
        usr.getEntidad().setApellidoMaterno("administrador");
        usr.getEntidad().setEmail("cesar@cesar.cesar");
        usr.getEntidad().setNumeroTarjeta("123213123");
        usr.getEntidad().setTelefono("82828282");
        usr.getEntidad().setFoto(null);
        try {
            System.out.println("Resultado: " + dao.borrrar(usr));
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
