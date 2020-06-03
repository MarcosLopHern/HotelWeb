package com.ipn.mx.web.bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class BaseBean {
    protected static final String ACC_CREAR = "CREAR";
    protected static final String ACC_ACTUALIZAR = "ACTUALIZAR";
    protected String accion;
    
    protected void error(String idMensaje, String mensaje) {
        FacesContext.getCurrentInstance().addMessage(idMensaje,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, idMensaje));
    }
    public boolean isModoCrear() {
        if (accion != null) {
            return accion.equals(ACC_CREAR);
        } else {
            return false;
        }
    }
    public boolean isModoActualizar() {
        if (accion != null) {
            return accion.equals(ACC_ACTUALIZAR);
        } else {
            return false;
        }
    }
}