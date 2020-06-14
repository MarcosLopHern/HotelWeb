package com.ipn.mx.web.bean;

import com.ipn.mx.modelo.dao.HuespedDAO;
import com.ipn.mx.modelo.entidades.Huesped;
import com.ipn.mx.modelo.dao.ReservacionDAO;
import com.ipn.mx.modelo.dto.ReservacionDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@Named(value = "reservacionMB")
@Dependent
public class ReservacionMB extends BaseBean implements Serializable {

    private ReservacionDAO dao = new ReservacionDAO();
    private ReservacionDTO dto;
    private List<ReservacionDTO> listaDeReservaciones;
    
    public ReservacionMB() {}
    
    public ReservacionDTO getDto(){
        return dto;
    }
    
    public List getListaDeReservaciones(){
        return listaDeReservaciones;
    }
    
    @PostConstruct
    public void init(){
        listaDeReservaciones = new ArrayList<>();
        listaDeReservaciones = dao.readAll();    
    }
    
    public String prepareUpdate(){
        setAccion(ACC_ACTUALIZAR);
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
    
    public List<ReservacionDTO> listaCuartos(){
        ReservacionDAO dao = new ReservacionDAO();
        try{
           return dao.readAll();
        }catch(Exception e){
            error("errorListaCuartos", "Error al obtener lista de cuartos");
            return null;
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
    
}
