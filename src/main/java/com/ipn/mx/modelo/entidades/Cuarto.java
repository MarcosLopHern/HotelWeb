package com.ipn.mx.modelo.entidades;

public class Cuarto {
    private int idCuarto;
    private double precioDiario;
    private boolean esReservable;
    private boolean estaEnUso;

    public Cuarto() {
    }

    public int getIdCuarto() {
        return idCuarto;
    }

    public void setIdCuarto(int idCuarto) {
        this.idCuarto = idCuarto;
    }

    public double getPrecioDiario() {
        return precioDiario;
    }

    public void setPrecioDiario(double precioDiario) {
        this.precioDiario = precioDiario;
    }

    public boolean getEsReservable() {
        return esReservable;
    }

    public void setEsReservable(boolean esReservable) {
        this.esReservable = esReservable;
    }

    public boolean getEstaEnUso() {
        return estaEnUso;
    }

    public void setEstaEnUso(boolean estaEnUso) {
        this.estaEnUso = estaEnUso;
    }
    
}
