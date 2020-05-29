
package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.dto.CuartoDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CuartoDAO {
    private final String SQL_UPDATE = "{call sp_actualizarCuarto(?,?, ?)}";
    private final String SQL_SELECT_ONE = "{call sp_ocuparCuarto(?,?)}";
    private final String SQL_SELECT_ALL = "{call sp_consultarCuartos()}";
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
    
    public String update(CuartoDTO dto) throws SQLException{
        con = obtenerConexion();
        CallableStatement cs = null;
        ResultSet rs = null;
        String res = "";
        try {
            cs = con.prepareCall(SQL_UPDATE);
            cs.setInt(1, dto.getEntidad().getIdCuarto());
            cs.setDouble(2, dto.getEntidad().getPrecioDiario());
            cs.setBoolean(3, dto.getEntidad().getEsReservable());
            rs = cs.executeQuery();
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
    
    public String ocupar(CuartoDTO dto) throws SQLException{
        con = obtenerConexion();
        CallableStatement cs = null;
        ResultSet rs = null;
        String res = "";
        try {
            cs = con.prepareCall(SQL_SELECT_ONE);
            cs.setBoolean(1, dto.getEntidad().getEstaEnUso());
            cs.setInt(2, dto.getEntidad().getIdCuarto());
            rs = cs.executeQuery();
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
    
    public List readAll() throws SQLException {
        obtenerConexion();
        CallableStatement cs = null;
        ResultSet rs = null;
        try {
            cs = con.prepareCall(SQL_SELECT_ALL);
            rs = cs.executeQuery();
            List resultados = obtenerResultados(rs);
            if (resultados.size() > 0) {
                return resultados;
            } else {
                return null;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (cs != null) {
                cs.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    private List obtenerResultados(ResultSet rs)
            throws SQLException {
        List resultados = new ArrayList();
        while (rs.next()) {
            CuartoDTO dto = new CuartoDTO();
            dto.getEntidad().setIdCuarto(rs.getInt("idCuarto"));
            dto.getEntidad().setPrecioDiario(rs.getDouble("precioDiario"));
            dto.getEntidad().setEsReservable(rs.getBoolean("esReservable"));
            dto.getEntidad().setEstaEnUso(rs.getBoolean("estaEnUso"));
            resultados.add(dto);
        }
        return resultados;
    }
    
     public static void main(String[] args) {
        CuartoDAO dao = new CuartoDAO();
        CuartoDTO usr = new CuartoDTO();
        usr.getEntidad().setIdCuarto(3);
        usr.getEntidad().setPrecioDiario(1500);
        usr.getEntidad().setEsReservable(true);
        usr.getEntidad().setEstaEnUso(true);
        try {
            System.out.println("Resultado: " + dao.readAll());
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
