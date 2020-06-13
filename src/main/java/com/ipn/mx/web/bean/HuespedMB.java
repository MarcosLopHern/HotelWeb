package com.ipn.mx.web.bean;

import com.ipn.mx.modelo.dao.EstadoDAO;
import com.ipn.mx.modelo.dao.HuespedDAO;
import com.ipn.mx.modelo.dto.EstadoDTO;
import com.ipn.mx.modelo.dto.HuespedDTO;
import static com.ipn.mx.web.bean.BaseBean.ACC_ACTUALIZAR;
import static com.ipn.mx.web.bean.BaseBean.ACC_CREAR;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;


@ManagedBean(name = "huespedMB")
@SessionScoped
public class HuespedMB extends BaseBean implements Serializable {
    private HuespedDAO dao = new HuespedDAO();
    private HuespedDTO dto;
    private List<HuespedDTO> listaDeHuespedes;
    private int idEstado;
    public HuespedMB(){}

    public HuespedDAO getDao() {
        return dao;
    }

    public void setDao(HuespedDAO dao) {
        this.dao = dao;
    }

    public HuespedDTO getDto() {
        return dto;
    }

    public void setDto(HuespedDTO dto) {
        this.dto = dto;
    }

    public List<HuespedDTO> getListaDeHuespedes() {
        return listaDeHuespedes;
    }

    public void setListaDeHuespedes(List<HuespedDTO> listaDeHuespedes) {
        this.listaDeHuespedes = listaDeHuespedes;
    }
    
    @PostConstruct
    public void init(){
        listaDeHuespedes = new ArrayList<>();
        listaDeHuespedes = dao.readAll();
    }
    
    public String prepareAdd(){
        dto = new HuespedDTO();
        setAccion(ACC_CREAR);
        return "/huespedes/huespedForm?faces-redirect=true";
    }
    
    public String prepareUpdate(){
        setAccion(ACC_ACTUALIZAR);
        return "/huespedes/huespedForm?faces-redirect=true";
    }
    
    public String prepareIndex(){
        init();
        return "/usuarios/listadoHuespedes?faces-redirect=true";
    }
    
    public String back(){
        return prepareIndex();
    }
    
    public Boolean validate(){
        boolean valido = true;
        //validar campos del formulario
        return valido;
    }
    
    public String add(){
        Boolean valido = validate();
        if(valido){
            dao.create(dto);
            if(valido){
                return prepareIndex();
            }else{
                prepareAdd();
            }
        }
        return prepareAdd();
    }
    
    public String update(){
        Boolean valido = validate();
        if(valido){
            dao.update(dto);
            if(valido){
                return prepareIndex();
            }else{
                prepareUpdate();
            }
        }
        return prepareUpdate();
    }
    
    public String delete(){
        dao.delete(dto);
        return prepareIndex();
    }
    
    public void seleccionarHuesped(ActionEvent event){
        String claveSel = (String) FacesContext.getCurrentInstance()
                        .getExternalContext().getRequestParameterMap()
                        .get("claveSel");
        dto = new HuespedDTO();
        dto.getEntidad().setIdHuesped(Integer.parseInt(claveSel));
        try{
           dto = dao.read(dto);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public Map<Integer, String> listaEstados(){
        Map<Integer,String> listaEstados = new LinkedHashMap<>();
        EstadoDAO dao = new EstadoDAO();
        for(EstadoDTO estado : dao.readAll()){
            listaEstados.put(estado.getEntidad().getIdEstado(),estado.getEntidad().getNombre());
        }
        return listaEstados;
    }
}