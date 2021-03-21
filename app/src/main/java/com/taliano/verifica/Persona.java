package com.taliano.verifica;

import java.io.Serializable;

public class Persona implements Serializable,Comparable<Persona>  {
    private String nome;
    private String gender;
    private  String telefono;
    private String imgI;
    private  Integer img;
private Integer p;

    public Integer getP() {
        return p;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public Persona() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getImgI() {
        return imgI;
    }

    public void setImgI(String imgI) {
        this.imgI = imgI;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    @Override
    public int compareTo(Persona persona) {
        String cmp= persona.getNome();
        return this.getNome().compareToIgnoreCase(cmp);
    }
}
