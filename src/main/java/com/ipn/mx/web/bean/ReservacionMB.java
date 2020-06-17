package com.ipn.mx.web.bean;

import com.ipn.mx.modelo.dao.CuartoDAO;
import com.ipn.mx.modelo.dao.HuespedDAO;
import com.ipn.mx.modelo.dao.ReservacionDAO;
import com.ipn.mx.modelo.dto.CuartoDTO;
import com.ipn.mx.modelo.dto.HuespedDTO;
import com.ipn.mx.modelo.dto.ReservacionDTO;
import com.ipn.mx.modelo.entidades.Huesped;
import com.ipn.mx.utilidades.ConexionBD;
import static com.ipn.mx.web.bean.BaseBean.ACC_ACTUALIZAR;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.primefaces.event.SelectEvent;


@ManagedBean(name = "reservacionMB")
@SessionScoped
public class ReservacionMB extends BaseBean implements Serializable {
    private ReservacionDAO dao = new ReservacionDAO();
    private ReservacionDTO dto;
    private List<ReservacionDTO> listaDeReservaciones;
    private int idCuarto;
    String fI;
    String ff;
    
    public ReservacionMB() {}
    
    public void onDateSelect(SelectEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fI = sdf.format(event.getObject());
    }
    
    public void onDateSelect2(SelectEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ff = sdf.format(event.getObject());
    } 
    
    public ReservacionDTO getDto(){
        return dto;
    }
    
    public List getListaDeReservaciones(){
        return listaDeReservaciones;
    }
    
    public int getIdCuarto(){
        return idCuarto;
    }
    
    public void setIdCuarto(int idCuarto){
        this.idCuarto = idCuarto;
    }
    
    @PostConstruct
    public void init(){
        idCuarto = -1;
        listaDeReservaciones = new ArrayList<>();
        listaDeReservaciones = dao.readAll();  
    }
    
    public String prepareUpdate(){
        setAccion(ACC_ACTUALIZAR);
        return "/reservaciones/reservacionForm?faces-redirect=true";
    }
    
     public String prepareNew(){
        setAccion(ACC_CREAR);
        idCuarto = -1;
        dto = new ReservacionDTO();
        dto.getEntidad().setFechaInicio(java.sql.Date.valueOf(LocalDate.now()));
        dto.getEntidad().setFechaTermino(java.sql.Date.valueOf(LocalDate.now()));
        dto.getEntidad().setFechaRegistro(java.sql.Date.valueOf(LocalDate.now()));
        dto.getEntidad().setEsActiva(true);
        dto.getEntidad().setIdCuarto(-1);
        return "/reservaciones/reservacionForm?faces-redirect=true";
    }
    
     public Date getHoy(){
         return java.sql.Date.valueOf(LocalDate.now());
     }
     
     public String prepareListaReservaciones(){
        init();
        return "/reservaciones/listaDeReservaciones?faces-redirect=true";
    }
    
    public String prepareIndex(){
        init();
        return "/index?faces-redirect=true";
    }
    
    public String back(){
        return prepareListaReservaciones();
    }
    
    public Boolean validate(){
        boolean valido = true;
        //validar campos del formulario
        return valido;
    }
    
    public String add(){
        Boolean valido = validate();
        System.out.println("DTO: " + dto);
        if(valido){
            dao.create(dto);
            if(valido){
                return prepareListaReservaciones();
            }else{
                return prepareUpdate();
            }
        }
        return prepareUpdate();
    }
    
    public String delete(){
        Boolean valido = validate();
        if(valido){
            dao.delete(dto);
            if(valido){
                return prepareListaReservaciones();
            }else{
                return prepareUpdate();
            }
        }
        return prepareUpdate();
    }
    
    public void seleccionarReservacion(ActionEvent event){
        String claveResSeleccionado =(String)
                FacesContext.getCurrentInstance().
                        getExternalContext().getRequestParameterMap().get("claveSel");
        dto = new ReservacionDTO();
        dto.getEntidad().setIdReservacion(Integer.parseInt(claveResSeleccionado));
        try{
            dto = dao.read(dto);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public String nombreUsuario(int id){
        HuespedDAO daoc = new HuespedDAO();
        List l = daoc.readAll();
        for(int x = 0; x < l.size();x++){
            Huesped h = (Huesped)l.get(x);
            if(h.getIdHuesped() == id)
                return h.getNombre();
        }
        return "";
    }
    
    public double sacaCosto() throws ParseException{
        if(dto.getEntidad().getIdCuarto() != -1){
            long diffInMillies = Math.abs(dto.getEntidad().getFechaInicio().getTime() - dto.getEntidad().getFechaTermino().getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS); 
            CuartoDTO cdto = new CuartoDTO();
            cdto.getEntidad().setIdCuarto(dto.getEntidad().getIdCuarto());
            CuartoDAO cdao = new CuartoDAO();
            cdto = cdao.read(cdto);
            dto.getEntidad().setPrecioTotal(cdto.getEntidad().getPrecioDiario() * diff);
            return cdto.getEntidad().getPrecioDiario() * diff;
        }
        return 0;
    }
    
    public void seleccionarHuesped(ActionEvent event){
        String claveSel = (String) FacesContext.getCurrentInstance()
                        .getExternalContext().getRequestParameterMap()
                        .get("claveSel");
        dto.getEntidad().setIdHuesped(Integer.parseInt(claveSel));
    }
    
    public void generarReporteGeneral() {
        File reporte = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/Reservaciones.jasper"));
        ConexionBD con = new ConexionBD();
        byte[] bytes;
        try {
            bytes = JasperRunManager.runReportToPdf(reporte.getPath(), null, con.obtenerConexion());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            ServletOutputStream outStream = response.getOutputStream();
            outStream.write(bytes, 0, bytes.length);
            outStream.flush();
            outStream.close();
        } catch (JRException | IOException ex) {
            Logger.getLogger(ReservacionMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public void generarReporteIndividual(){
        Map parametros = new HashMap();
        int idReservacion = dto.getEntidad().getIdReservacion();
        parametros.put("idReservacion", idReservacion);
        File reporte = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/Reservacion.jasper"));
        ConexionBD con = new ConexionBD();
        byte[] bytes;
        try {
            bytes = JasperRunManager.runReportToPdf(reporte.getPath(), parametros, con.obtenerConexion());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            ServletOutputStream outStream = response.getOutputStream();
            outStream.write(bytes, 0, bytes.length);
            outStream.flush();
            outStream.close();
        } catch (JRException | IOException ex) {
            Logger.getLogger(ReservacionMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().responseComplete();
    }
}