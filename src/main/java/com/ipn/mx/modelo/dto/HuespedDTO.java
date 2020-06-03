
package com.ipn.mx.modelo.dto;

import com.ipn.mx.modelo.entidades.Huesped;
import java.io.Serializable;

public class HuespedDTO implements Serializable{
    private Huesped entidad;
    
    public HuespedDTO() {
        entidad = new Huesped();
    }
    
    public HuespedDTO(Huesped entidad) {
        this.entidad = entidad;
    }

    public Huesped getEntidad() {
        return entidad;
    }

    public void setEntidad(Huesped entidad) {
        this.entidad = entidad;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("idHuesped").append(getEntidad().getIdHuesped()).append("\n");
        sb.append("nombreUsuario").append(getEntidad().getNombreUsuario()).append("\n");
        sb.append("nombre").append(getEntidad().getNombre()).append("\n");
        sb.append("apellidoPaterno").append(getEntidad().getApellidoPaterno()).append("\n");
        sb.append("apellidoMaterno").append(getEntidad().getApellidoMaterno()).append("\n");
        sb.append("email").append(getEntidad().getEmail()).append("\n");
        sb.append("numeroTarjeta").append(getEntidad().getNumeroTarjeta()).append("\n");
        sb.append("telefono").append(getEntidad().getTelefono()).append("\n");
        sb.append("foto").append(getEntidad().getFoto()).append("\n");
        sb.append("existe").append(getEntidad().getExiste()).append("\n");
        return sb.toString();
    }
}
