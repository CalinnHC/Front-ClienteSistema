package Entity;

import java.time.LocalDate;

public class Usuario {
    private int idUsuario;
    private String nombreUsuario;
    private int idRol;
    private String correo;
    private String nombre;
    private String apellido;
    private String contrasena;
    private long numeroTelefonico;
    private String nacimiento;
    private String ultimoInicioDeSesion;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public long getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public void setNumeroTelefonico(long numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getUltimoInicioDeSesion() {
        return ultimoInicioDeSesion;
    }

    public void setUltimoInicioDeSesion(String ultimoInicioDeSesion) {
        this.ultimoInicioDeSesion = ultimoInicioDeSesion;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", idRol=" + idRol +
                ", correo='" + correo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", numeroTelefonico=" + numeroTelefonico +
                ", nacimiento=" + nacimiento +
                ", ultimoInicioDeSesion=" + ultimoInicioDeSesion +
                '}';
    }
}
