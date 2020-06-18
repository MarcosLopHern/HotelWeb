package com.ipn.mx.web.bean;

import com.ipn.mx.modelo.dao.UsuarioDAO;
import com.ipn.mx.modelo.dto.UsuarioDTO;
import static com.ipn.mx.web.bean.BaseBean.ACC_ACTUALIZAR;
import static com.ipn.mx.web.bean.BaseBean.ACC_CREAR;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

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
        FacesContext fc = FacesContext.getCurrentInstance();
        String username = dto.getEntidad().getNombreUsuario();
        String msj = dao.login(dto);
        dto = dao.read(dto);
        if (username.equalsIgnoreCase(msj)) {
            fc.getExternalContext().getSessionMap().put("nombreUsuario", msj);   
            fc.getCurrentInstance().getExternalContext().getSessionMap().put("tipo", dto.getEntidad().getTipo()); 
            if(dto.getEntidad().getTipo().equals("huesped")){
                fc.getCurrentInstance().getExternalContext().getSessionMap().put("idHuesped", dao.getIdHuesped(dto));
            }
            return "/huespedes/bienvenida?faces-redirect=true";
        } else {
            init();
            fc.addMessage("Login", new FacesMessage("Nombre de Usuario/Contraseña incorrecto"));
            return null;
        }
    }
    
    public String cerrarSesion() {        
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        response.addHeader("Cache-Control", "no-store");
        response.addHeader("Cache-Control", "must-revalidate");
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    }
}

