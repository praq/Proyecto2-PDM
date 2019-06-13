package ues.edu.sv.petplanner;

public class Rutina {

    String codigoRutina;
    int codigoRegistro;
    String fechaRutina;
    String duracionRutina;

    public Rutina(){

    }

    public Rutina(String codigoRutina, int codigoRegistro, String fechaRutina, String duracionRutina) {
        this.codigoRutina = codigoRutina;
        this.codigoRegistro = codigoRegistro;
        this.fechaRutina = fechaRutina;
        this.duracionRutina = duracionRutina;
    }

    public String getCodigoRutina() {
        return codigoRutina;
    }

    public void setCodigoRutina(String codigoRutina) {
        this.codigoRutina = codigoRutina;
    }

    public int getCodigoRegistro() {
        return codigoRegistro;
    }

    public void setCodigoRegistro(int codigoRegistro) {
        this.codigoRegistro = codigoRegistro;
    }

    public String getFechaRutina() {
        return fechaRutina;
    }

    public void setFechaRutina(String fechaRutina) {
        this.fechaRutina = fechaRutina;
    }

    public String getDuracionRutina() {
        return duracionRutina;
    }

    public void setDuracionRutina(String duracionRutina) {
        this.duracionRutina = duracionRutina;
    }
}
