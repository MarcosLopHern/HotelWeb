package com.ipn.mx.modelo.dto;

import com.ipn.mx.modelo.entidades.Estado;

public class EstadoDTO {
    private Estado entidad;
    
    public EstadoDTO(Estado entidad) {
        this.entidad = entidad;
    }

    public Estado getEntidad() {
        return entidad;
    }

    public void setEntidad(Estado entidad) {
        this.entidad = entidad;
    }
    
    @Override
    public String toString() {       
        StringBuilder sb = new StringBuilder();
        sb.append("idEstado").append(getEntidad().getIdEstado()).append("\n");
        sb.append("clave").append(getEntidad().getClave()).append("\n");
        sb.append("nombre").append(getEntidad().getNombre()).append("\n");
        sb.append("abreviacion").append(getEntidad().getAbreviacion()).append("\n");
        sb.append("activo").append(getEntidad().getActivo()).append("\n");
        return sb.toString();
    }
}
