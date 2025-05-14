package model;

import java.util.ArrayList;

public class Escuela {
    private String nombre;

    private ArrayList<Estudiante> listaEstudiantes;

    public Escuela(String nombre) {
        this.nombre = nombre;
        listaEstudiantes = new ArrayList<>();
    }

    public void agregarEstudiante(Estudiante estudiante) {
        listaEstudiantes.add(estudiante);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Estudiante> getListaEstudiantes() {
        return listaEstudiantes;
    }

    public void setListaEstudiantes(ArrayList<Estudiante> listaEstudiantes) {
        this.listaEstudiantes = listaEstudiantes;
    }

    public void eliminarEstudiante(Estudiante estudiante) {
        listaEstudiantes.remove(estudiante);
    }

    public void actualizarEstudiante(Estudiante estudiante) {
        for (Estudiante aux: listaEstudiantes) {
            if (aux.getMatricula().equals(estudiante.getMatricula())) {
                aux.setNombre(estudiante.getNombre());
                aux.setApellidoPaterno(estudiante.getApellidoPaterno());
                aux.setApellidoMaterno(estudiante.getApellidoMaterno());
                aux.setEdad(estudiante.getEdad());
                aux.setSexo(estudiante.getSexo());
                aux.setSeguroFacultativo(estudiante.isSeguroFacultativo());
                aux.setPais(estudiante.getPais());
                aux.setLicenciatura(estudiante.getLicenciatura());
            }
        }
    }
}
