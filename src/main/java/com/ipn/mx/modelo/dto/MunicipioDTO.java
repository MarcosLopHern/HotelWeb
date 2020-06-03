package com.ipn.mx.modelo.dto;

import com.ipn.mx.modelo.entidades.Municipio;

public class MunicipioDTO {
    private Municipio entidad;
    
    public MunicipioDTO(Municipio entidad) {
        this.entidad = entidad;
    }

    public Municipio getEntidad() {
        return entidad;
    }

    public void setEntidad(Municipio entidad) {
        this.entidad = entidad;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("idMunicipio").append(getEntidad().getIdMunicipio()).append("\n");
        sb.append("idEstado").append(getEntidad().getIdEstado()).append("\n");
        sb.append("clave").append(getEntidad().getClave()).append("\n");
        sb.append("nombre").append(getEntidad().getNombre()).append("\n");
        sb.append("activo").append(getEntidad().getActivo()).append("\n");
        return sb.toString();
    }
}
