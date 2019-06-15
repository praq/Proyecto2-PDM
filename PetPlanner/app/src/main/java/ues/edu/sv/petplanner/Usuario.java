package ues.edu.sv.petplanner;

public class Usuario {
    private String nombreUsuario;
    private String apellidoUsuario;
    private int edadUsuario;
    private String sexoUsuario;
    private String correo;
    private String contraseña;

    public Usuario(String nombreUsuario, String apellidoUsuario, int edadUsuario, String sexoUsuario, String correo, String contraseña) {
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.edadUsuario = edadUsuario;
        this.sexoUsuario = sexoUsuario;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public Usuario() {
    }

    public String getSexoUsuario() {
        return sexoUsuario;
    }

    public void setSexoUsuario(String sexoUsuario) {
        this.sexoUsuario = sexoUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public int getEdadUsuario() {
        return edadUsuario;
    }

    public void setEdadUsuario(int edadUsuario) {
        this.edadUsuario = edadUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
