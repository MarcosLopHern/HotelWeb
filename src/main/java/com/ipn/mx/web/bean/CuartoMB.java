package com.ipn.mx.web.bean;

import com.ipn.mx.modelo.dao.CuartoDAO;
import com.ipn.mx.modelo.dto.CuartoDTO;
import com.ipn.mx.modelo.entidades.Cuarto;
import static com.ipn.mx.web.bean.BaseBean.ACC_ACTUALIZAR;
import static com.ipn.mx.web.bean.BaseBean.ACC_CREAR;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.Part;


@ManagedBean(name = "cuartoMB")
@SessionScoped
public class CuartoMB extends BaseBean implements Serializable {
    private CuartoDAO dao = new CuartoDAO();
    private CuartoDTO dto;
    private List<CuartoDTO> listaDeCuartos;
    
    public CuartoMB(){}

    public CuartoDAO getDao() {
        return dao;
    }

    public void setDao(CuartoDAO dao) {
        this.dao = dao;
    }

    public CuartoDTO getDto() {
        return dto;
    }

    public void setDto(CuartoDTO dto) {
        this.dto = dto;
    }

    public List<CuartoDTO> getListaDeCuartos() {
        return listaDeCuartos;
    }

    public void setListaDeCuartos(List<CuartoDTO> listaDeCuartos) {
        this.listaDeCuartos = listaDeCuartos;
    }
    
    @PostConstruct
    public void init(){
        listaDeCuartos = new ArrayList<>();
        listaDeCuartos = dao.readAll();    
    }
    
    public String prepareUpdate(){
        setAccion(ACC_ACTUALIZAR);
        return "/cuartos/cuartoForm?faces-redirect=true";
    }
    
    public String prepareListaCuartos(){
        init();
        return "/cuartos/listaCuartos?faces-redirect=true";
    }
    
    public String prepareIndex(){
        init();
        return "/index?faces-redirect=true";
    }
    
    public String back(){
        return prepareListaCuartos();
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
                return prepareListaCuartos();
            }else{
                return prepareUpdate();
            }
        }
        return prepareUpdate();
    }
    
    
    public void seleccionarCuarto(ActionEvent event){
        String claveCuartoSeleccionado =(String)
                FacesContext.getCurrentInstance().
                        getExternalContext().getRequestParameterMap().get("claveSel");
        dto = new CuartoDTO();
        dto.getEntidad().setIdCuarto(Integer.parseInt(claveCuartoSeleccionado));
        try{
            dto = dao.read(dto);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public List<CuartoDTO> listaCuartos(){
        CuartoDAO dao = new CuartoDAO();
        try{
           return dao.readAll();
        }catch(Exception e){
            error("errorListaCuartos", "Error al obtener lista de cuartos");
            return null;
        }
    }

    
    
    
    
}