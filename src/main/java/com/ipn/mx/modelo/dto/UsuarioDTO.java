
package com.ipn.mx.modelo.dto;

import com.ipn.mx.modelo.entidades.Usuario;

public class UsuarioDTO {
    private Usuario entidad;

    public UsuarioDTO() {
        entidad = new Usuario();
    }

    public UsuarioDTO(Usuario entidad) {
        this.entidad = entidad;
    }

    public Usuario getEntidad() {
        return entidad;
    }

    public void setEntidad(Usuario entidad) {
        this.entidad = entidad;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("nombreUsuario").append(getEntidad().getNombreUsuario()).append("\n");
        sb.append("pswrd").append(getEntidad().getPswrd()).append("\n");
        sb.append("tipo").append(getEntidad().getTipo()).append("\n");
        sb.append("existe").append(getEntidad().getExiste()).append("\n");
        
        return sb.toString();
    }
}
