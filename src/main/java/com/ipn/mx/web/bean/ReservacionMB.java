package com.ipn.mx.web.bean;

import com.ipn.mx.modelo.dao.CuartoDAO;
import com.ipn.mx.modelo.dao.HuespedDAO;
import com.ipn.mx.modelo.dao.ReservacionDAO;
import com.ipn.mx.modelo.dto.ReservacionDTO;
import com.ipn.mx.modelo.entidades.Huesped;
import static com.ipn.mx.web.bean.BaseBean.ACC_ACTUALIZAR;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.SelectEvent;


@ManagedBean(name = "reservacionMB")
@SessionScoped
public class ReservacionMB extends BaseBean implements Serializable {
    private ReservacionDAO dao = new ReservacionDAO();
    private ReservacionDTO dto;
    private List<ReservacionDTO> listaDeReservaciones;
    private Date fechaInicio;
    private Date fechaFin;
    private int idCuarto;
    
    public ReservacionMB() {}
    
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
    
    public Date getFechaInicio(){
        return java.sql.Date.valueOf(LocalDate.now());  
    }
    
      public void setFechaInicio(Date fechaInicio){
       this.fechaInicio = fechaInicio;  
          System.out.println("fechaFin: " + fechaInicio);
          System.out.println("fechaFin2: " + this.fechaInicio);
    }
      
    public Date getFechaFin(){
        return fechaFin;
    }
        
    public void setFechaFin(Date fechaFin){
        this.fechaFin = fechaFin; 
        System.out.println("fechaFin: " + fechaFin);
          System.out.println("fechaFin2: " + this.fechaFin);
    }
    
    @PostConstruct
    public void init(){
        fechaInicio = java.sql.Date.valueOf(LocalDate.now());
        fechaFin = java.sql.Date.valueOf(LocalDate.now());
        listaDeReservaciones = new ArrayList<>();
        listaDeReservaciones = dao.readAll();    
    }
    
    public String prepareUpdate(){
        setAccion(ACC_ACTUALIZAR);
        return "/reservaciones/reservacionForm?faces-redirect=true";
    }
    
     public String prepareNew(){
        setAccion(ACC_CREAR);
        return "/reservaciones/reservacionForm?faces-redirect=true";
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
    
    public String update(){
        Boolean valido = validate();
        if(valido){
            dao.update(dto);
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
    
    public int costoCuarto(){
        CuartoDAO cdao = new CuartoDAO();
        System.out.println("FI: " + fechaInicio);
        System.out.println("FF: " + fechaFin);
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        //LocalDate date1 = LocalDate.parse(fechaInicio.toString(), formatter);
        //LocalDate date2 = LocalDate.parse(fechaFin.toString(), formatter);
        //long days = ChronoUnit.DAYS.between(date1, date2);
        return 0;
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
    
}