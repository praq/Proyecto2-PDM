package ues.edu.sv.petplanner;

public class Enfermedad {
    private String nombreEnfermedad;
    private String descripcionEnfermedad;

    public Enfermedad() {

    }

    public Enfermedad(String nombreEnfermedad,String descripcionEnfermedad) {

        this.nombreEnfermedad=nombreEnfermedad;
        this.descripcionEnfermedad=descripcionEnfermedad;
    }

    public String getNombreEnfermedad() {
        return nombreEnfermedad;
    }

    public void setNombreEnfermedad(String nombreEnfermedad) {
        this.nombreEnfermedad = nombreEnfermedad;
    }

    public String getDescripcionEnfermedad() {
        return descripcionEnfermedad;
    }

    public void setDescripcionEnfermedad(String descripcionEnfermedad) {
        this.descripcionEnfermedad = descripcionEnfermedad;
    }
}
