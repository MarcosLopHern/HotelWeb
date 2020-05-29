package com.ipn.mx.modelo.entidades;

public class Reservacion {
    private int idReservacion;
    private int idHuesped;
    private int idCuarto;
    private String fechaInicio;
    private String fechaTermino;
    private String fechaRegistro;
    private double precioTotal;
    private boolean esActiva;

    public Reservacion() {
    }

    public int getIdReservacion() {
        return idReservacion;
    }

    public void setIdReservacion(int idReservacion) {
        this.idReservacion = idReservacion;
    }

    public int getIdHuesped() {
        return idHuesped;
    }

    public void setIdHuesped(int idHuesped) {
        this.idHuesped = idHuesped;
    }

    public int getIdCuarto() {
        return idCuarto;
    }

    public void setIdCuarto(int idCuarto) {
        this.idCuarto = idCuarto;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(String fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public boolean getEsActiva() {
        return esActiva;
    }

    public void setEsActiva(boolean esActiva) {
        this.esActiva = esActiva;
    }
    
}
