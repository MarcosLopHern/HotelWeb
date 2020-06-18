package com.ipn.mx.modelo.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
public class Grafica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Cuarto")
    private int cuarto;
    @Column(name = "Usos")
    private int usos;

    public Grafica() {}

    public int getCuarto() {
        return cuarto;
    }

    public void setCuarto(int cuarto) {
        this.cuarto = cuarto;
    }

    public int getUsos() {
        return usos;
    }

    public void setUsos(int usos) {
        this.usos = usos;
    }

    

    @Override
    public String toString() {
        return "Grafica{" + "Cuarto=" + cuarto + ", Usos=" + usos + '}';
    }

}
