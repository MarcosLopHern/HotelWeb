package com.ipn.mx.web.bean;

import com.ipn.mx.modelo.dao.UsuarioDAO;
import com.ipn.mx.modelo.dto.UsuarioDTO;
import static com.ipn.mx.web.bean.BaseBean.ACC_ACTUALIZAR;
import static com.ipn.mx.web.bean.BaseBean.ACC_CREAR;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean(name = "usuarioMB")
@SessionScoped
public class UsuarioMB extends BaseBean implements Serializable {
    private UsuarioDAO dao = new UsuarioDAO();
    private UsuarioDTO dto;
    private List<UsuarioDTO> listaDeUsuarios;
    
    public UsuarioMB(){}

    public UsuarioDAO getDao() {
        return dao;
    }

    public void setDao(UsuarioDAO dao) {
        this.dao = dao;
    }

    public UsuarioDTO getDto() {
        return dto;
    }

    public void setDto(UsuarioDTO dto) {
        this.dto = dto;
    }

    public List<UsuarioDTO> getListaDeUsuarios() {
        return listaDeUsuarios;
    }

    public void setListaDeUsuarios(List<UsuarioDTO> listaDeUsuarios) {
        this.listaDeUsuarios = listaDeUsuarios;
    }
    
    @PostConstruct
    public void init(){
        dto = new UsuarioDTO();
        listaDeUsuarios = new ArrayList<>();
        listaDeUsuarios = dao.readAll();
    }
    
    public String prepareAdd(){
        dto = new UsuarioDTO();
        setAccion(ACC_CREAR);
        return "/huespedes/huespedForm?faces-redirect=true";
    }
    
    public String prepareUpdate(){
        setAccion(ACC_ACTUALIZAR);
        return "/huespedes/huespedForm?faces-redirect=true";
    }
    
    public String prepareIndex(){
        init();
        return "/usuarios/listadoUsuarios?faces-redirect=true";
    }
    
    public String back(){
        return prepareIndex();
    }
    
    public void seleccionarUsuario(ActionEvent event){
        String claveSel = (String) FacesContext.getCurrentInstance()
                        .getExternalContext().getRequestParameterMap()
                        .get("claveSel");
        dto = new UsuarioDTO();
        dto.getEntidad().setNombreUsuario(claveSel);
        try{
           dto = dao.read(dto);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public String iniciarSesion() {
        String username = dto.getEntidad().getNombreUsuario();
        String msj = dao.validate(dto);
        if (username.equalsIgnoreCase(msj)) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("nombreUsuario", msj);       
            return "/huespedes/bienvenida?faces-redirect=true";
        } else {
            return null;
        }
    }
    
    public String cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }
}

