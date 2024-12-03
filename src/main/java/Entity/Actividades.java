package Entity;

import java.sql.Date;
import java.time.LocalDate;

public class Actividades {
    private int idActividad;
    private int proyecto_Asociado;
    private String nombre;
    private String descripcion;
    private int usuario_Asignado;
    private double progreso;
    private int estado;
    private Date  fecha_de_inicio;
    private Date fecha_de_finalizacion;

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public int getProyecto_Asociado() {
        return proyecto_Asociado;
    }

    public void setProyecto_Asociado(int proyecto_Asociado) {
        this.proyecto_Asociado = proyecto_Asociado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getUsuario_Asignado() {
        return usuario_Asignado;
    }

    public void setUsuario_Asignado(int usuario_Asignado) {
        this.usuario_Asignado = usuario_Asignado;
    }

    public double getProgreso() {
        return progreso;
    }

    public void setProgreso(double progreso) {
        this.progreso = progreso;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Date getFecha_de_inicio() {
        return fecha_de_inicio;
    }

    public void setFecha_de_inicio(Date  fecha_de_inicio) {
        this.fecha_de_inicio = fecha_de_inicio;
    }

    public Date  getFecha_de_finalizacion() {
        return fecha_de_finalizacion;
    }

    public void setFecha_de_finalizacion(Date  fecha_de_finalizacion) {
        this.fecha_de_finalizacion = fecha_de_finalizacion;
    }

    @Override
    public String toString() {
        return "{" +
                "idActividad=" + idActividad +
                ", proyecto_Asociado=" + proyecto_Asociado +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", usuario_Asignado=" + usuario_Asignado +
                ", progreso=" + progreso +
                ", estado=" + estado +
                ", fecha_de_inicio=" + fecha_de_inicio +
                ", fecha_de_finalizacion=" + fecha_de_finalizacion +
                '}';
    }
}
