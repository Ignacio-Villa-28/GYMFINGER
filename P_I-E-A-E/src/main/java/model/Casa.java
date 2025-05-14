package model;

import java.util.ArrayList;

public class Casa {
    private String nombre;

    private ArrayList<Externo> listaExternos;

    public Casa(String nombre) {
        this.nombre = nombre;
        listaExternos = new ArrayList<>();
    }

    public void agregarEstudiante(Externo externo) {
        listaExternos.add(externo);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Externo> getListaEstudiantes() {
        return listaExternos;
    }

    public void setListaEstudiantes(ArrayList<Externo> listaExternos) {
        this.listaExternos = listaExternos;
    }

    public void eliminarEstudiante(Externo externo) {
        listaExternos.remove(externo);
    }

    public void actualizarEstudiante(Externo externo) {
        for (Externo aux: listaExternos) {
            if (aux.getMatricula().equals(externo.getMatricula())) {
                aux.setNombre(externo.getNombre());
                aux.setApellidoPaterno(externo.getApellidoPaterno());
                aux.setApellidoMaterno(externo.getApellidoMaterno());
                aux.setEdad(externo.getEdad());
                aux.setSexo(externo.getSexo());
                aux.setSeguroFacultativo(externo.isSeguroFacultativo());
                aux.setPais(externo.getPais());
                aux.setLicenciatura(externo.getLicenciatura());
            }
        }
    }
}
