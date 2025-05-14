package model;

import java.util.Date;

public class Estudiante {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private int edad;
    private String sexo;
    private String matricula;
    private String seguroFacultativo;
    private String pais;
    private String licenciatura;
    private Date fechaRegistro;
    private byte[] huella;  // Para almacenar la huella en formato BYTEA

    public Estudiante(String nombre, String apellidoPaterno, String apellidoMaterno, int edad, String sexo, String matricula, String seguroFacultativo, String pais, String licenciatura, Date fechaRegistro, byte[] huella) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.edad = edad;
        this.sexo = sexo;
        this.matricula = matricula;
        this.seguroFacultativo = seguroFacultativo;
        this.pais = pais;
        this.licenciatura = licenciatura;
        this.fechaRegistro = fechaRegistro;
        this.huella = huella;
    }

    public Estudiante() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String isSeguroFacultativo() {
        return seguroFacultativo;
    }

    public void setSeguroFacultativo(String seguroFacultativo) {
        this.seguroFacultativo = seguroFacultativo;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getLicenciatura() {
        return licenciatura;
    }

    public void setLicenciatura(String licenciatura) {
        this.licenciatura = licenciatura;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public byte[] getHuella() {
        return huella;
    }

    public void setHuella(byte[] huella) {
        this.huella = huella;
    }
}
