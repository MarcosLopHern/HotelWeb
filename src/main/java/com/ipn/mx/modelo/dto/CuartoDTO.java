
package com.ipn.mx.modelo.dto;

import com.ipn.mx.modelo.entidades.Cuarto;
import java.io.Serializable;

public class CuartoDTO implements Serializable{
    private Cuarto entidad;

    public CuartoDTO() {
        entidad = new Cuarto();
    }

    public CuartoDTO(Cuarto entidad) {
        this.entidad = entidad;
    }

    public Cuarto getEntidad() {
        return entidad;
    }

    public void setEntidad(Cuarto entidad) {
        this.entidad = entidad;
    }
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("idCuarto").append(getEntidad().getIdCuarto()).append("\n");
        sb.append("precioDiario").append(getEntidad().getPrecioDiario()).append("\n");
        sb.append("esReservable").append(getEntidad().getEsReservable()).append("\n");
        
        return sb.toString();
    }
}
