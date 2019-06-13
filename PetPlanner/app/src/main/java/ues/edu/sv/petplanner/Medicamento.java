package ues.edu.sv.petplanner;

public class Medicamento {
    private String nombreMedicamento;
    private int codRegistro;
    private String nombreEnfermedad;
    private String descripcionMedicamento;
    private float dosis;
    private String fecha;

    public Medicamento() {
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public int getCodRegistro() {
        return codRegistro;
    }

    public void setCodRegistro(int codRegistro) {
        this.codRegistro = codRegistro;
    }

    public String getNombreEnfermedad() {
        return nombreEnfermedad;
    }

    public void setNombreEnfermedad(String nombreEnfermedad) {
        this.nombreEnfermedad = nombreEnfermedad;
    }

    public String getDescripcionMedicamento() {
        return descripcionMedicamento;
    }

    public void setDescripcionMedicamento(String descripcionMedicamento) {
        this.descripcionMedicamento = descripcionMedicamento;
    }

    public float getDosis() {
        return dosis;
    }

    public void setDosis(float dosis) {
        this.dosis = dosis;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
