package ues.edu.sv.petplanner;

public class Vacuna {
    private String nombreVacuna;
    private int codRegistro;
    private String fecha;

    public Vacuna() {
    }

    public Vacuna(String nombreVacuna, int codRegistro, String Fecha) {
        this.nombreVacuna= nombreVacuna;
        this.codRegistro=codRegistro;
        this.fecha= Fecha;
    }


    public String getNombreVacuna() {
        return nombreVacuna;
    }

    public void setNombreVacuna(String nombreVacuna) {
        this.nombreVacuna = nombreVacuna;
    }

    public int getCodRegistro() {
        return codRegistro;
    }

    public void setCodRegistro(int codRegistro) {
        this.codRegistro = codRegistro;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
