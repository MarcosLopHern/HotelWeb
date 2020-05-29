
package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.dto.ReservacionDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservacionDAO {
    private static final String SQL_CREATE = "{call sp_crearReservacion(?,?,?,?)}";
    private static final String SQL_DELETE = "{call sp_cancelarReservacion(?)}";
    private static final String SQL_SELECT_ALL = "{call sp_consultarReservaciones()}";
    private static final String SQL_SELECT_ONE = "{call sp_consultarReservacionesHuesped(?)}";
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
   
   public String crear(ReservacionDTO dto) throws SQLException{
        String res = "";
        con = obtenerConexion();
        CallableStatement cs = null;
        try {
            cs = con.prepareCall(SQL_CREATE);
            cs.setInt(1, dto.getEntidad().getIdHuesped());
            cs.setInt(2, dto.getEntidad().getIdCuarto());
            cs.setString(3, dto.getEntidad().getFechaInicio());
            cs.setString(4, dto.getEntidad().getFechaTermino());
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
   
   public String eliminar(ReservacionDTO dto) throws SQLException{
        String res = "";
        con = obtenerConexion();
        CallableStatement cs = null;
        try {
            cs = con.prepareCall(SQL_DELETE);
            cs.setInt(1, dto.getEntidad().getIdReservacion());
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
   
    public List readAllHuesped(ReservacionDTO dto) throws SQLException {
        obtenerConexion();
        CallableStatement cs = null;
        ResultSet rs = null;
        try {
            cs = con.prepareCall(SQL_SELECT_ONE);
                        cs.setInt(1, dto.getEntidad().getIdHuesped());
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
            ReservacionDTO dto = new ReservacionDTO();
            dto.getEntidad().setIdReservacion(rs.getInt("idReservacion"));
            dto.getEntidad().setIdHuesped(rs.getInt("idHuesped"));
            dto.getEntidad().setIdCuarto(rs.getInt("idCuarto"));
            dto.getEntidad().setFechaInicio(rs.getString("fechaInicio"));
            dto.getEntidad().setFechaTermino(rs.getString("fechaTermino"));
            dto.getEntidad().setFechaRegistro(rs.getString("fechaRegistro"));
            dto.getEntidad().setPrecioTotal(rs.getDouble("precioTotal"));
            dto.getEntidad().setEsActiva(rs.getBoolean("esActiva"));
            resultados.add(dto);
        }
        return resultados;
    }
   
   public static void main(String[] args) {
        ReservacionDAO dao = new ReservacionDAO();
        ReservacionDTO usr = new ReservacionDTO();
        usr.getEntidad().setIdHuesped(1);
        usr.getEntidad().setIdReservacion(1);
        usr.getEntidad().setIdCuarto(3);
        usr.getEntidad().setFechaInicio("2015-12-5");
        usr.getEntidad().setFechaTermino("2015-12-12");
        try {
            System.out.println("Resultado: " + dao.readAllHuesped(usr));
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
