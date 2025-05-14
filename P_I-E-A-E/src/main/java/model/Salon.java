package model;

import java.util.ArrayList;

public class Salon {
    private String nombre;

    private ArrayList<Admin> listaAdmins;

    public Salon(String nombre) {
        this.nombre = nombre;
        listaAdmins = new ArrayList<>();
    }

    public void agregarEstudiante(Admin admin) {
        listaAdmins.add(admin);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Admin> getListaEstudiantes() {
        return listaAdmins;
    }

    public void setListaEstudiantes(ArrayList<Admin> listaAdmins) {
        this.listaAdmins = listaAdmins;
    }

    public void eliminarEstudiante(Admin admin) {
        listaAdmins.remove(admin);
    }

    public void actualizarEstudiante(Admin admin) {
        for (Admin aux: listaAdmins) {
            if (aux.getMatricula().equals(admin.getMatricula())) {
                aux.setNombre(admin.getNombre());
                aux.setApellidoPaterno(admin.getApellidoPaterno());
                aux.setApellidoMaterno(admin.getApellidoMaterno());
                aux.setEdad(admin.getEdad());
                aux.setSexo(admin.getSexo());
                aux.setSeguroFacultativo(admin.isSeguroFacultativo());
                aux.setPais(admin.getPais());
                aux.setLicenciatura(admin.getLicenciatura());
            }
        }
    }
}
