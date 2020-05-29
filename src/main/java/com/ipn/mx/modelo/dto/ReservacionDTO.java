
package com.ipn.mx.modelo.dto;

import com.ipn.mx.modelo.entidades.Reservacion;
import java.io.Serializable;

public class ReservacionDTO implements Serializable{
    private Reservacion entidad;

    public ReservacionDTO() {
        entidad = new Reservacion();
    }

    public ReservacionDTO(Reservacion entidad) {
        this.entidad = entidad;
    }

    public Reservacion getEntidad() {
        return entidad;
    }

    public void setEntidad(Reservacion entidad) {
        this.entidad = entidad;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("idReservacion").append(getEntidad().getIdReservacion()).append("\n");
        sb.append("idHuesped").append(getEntidad().getIdHuesped()).append("\n");
        sb.append("idCuarto").append(getEntidad().getIdCuarto()).append("\n");
        sb.append("fechaInicio").append(getEntidad().getFechaInicio()).append("\n");
        sb.append("fechaTermino").append(getEntidad().getFechaTermino()).append("\n");
        sb.append("fechaRegistro").append(getEntidad().getFechaRegistro()).append("\n");
        sb.append("precioTotal").append(getEntidad().getPrecioTotal()).append("\n");
        sb.append("esActiva").append(getEntidad().getEsActiva()).append("\n");
        return sb.toString();
    }
}
