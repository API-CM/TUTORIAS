package com.tutorius.tutorius;

/**
 * Created by tecsuno on 11/04/17.
 */

public class Departamentos {

    private int id;
    private String nombre;
    private String siglas;

    public Departamentos(int id, String nombre, String siglas) {
        super();
        this.id=id;
        this.nombre=nombre;
        this.siglas=siglas;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public String getNombre(){
        return nombre;
    }

    public String getSiglas(){
        return siglas;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return this.nombre+" siglas: "+this.siglas;
    }
}
