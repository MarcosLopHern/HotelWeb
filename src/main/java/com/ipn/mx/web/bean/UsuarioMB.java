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
    
    private String iniciarSesion() {
        String username = dto.getEntidad().getNombreUsuario();
        String msj = dao.validate(dto);
        if (username.equalsIgnoreCase(msj)) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("nombreUsuario", msj);       
            return "/huespedes/bienvenida?faces-redirect=true";
        } else {
            return null;
        }
    }
    
//    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response) {
//        try {
//            HttpSession session = request.getSession(true);
//            session.removeAttribute("nombreUsuario");
//            if (session.getAttribute("nombreUsuario") == null) {
//                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
//                session.invalidate();
//                response.setHeader("Pragma", "no-cache");
//                response.setHeader("Cache-Control", "no-store");
//                response.setHeader("Expires", "0");
//                response.setDateHeader("Expires", -1);   
//                response.sendRedirect("index.jsp");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

