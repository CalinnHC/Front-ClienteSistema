package Entity;

public class Notificaciones {
    private int idNotificaciones;

    private String descripcion;

    private int idUsuario;

    private String estado_Visualizacion;

    public int getIdNotificaciones() {
        return idNotificaciones;
    }

    public void setIdNotificaciones(int idNotificaciones) {
        this.idNotificaciones = idNotificaciones;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEstado_Visualizacion() {
        return estado_Visualizacion;
    }

    public void setEstado_Visualizacion(String estado_Visualizacion) {
        this.estado_Visualizacion = estado_Visualizacion;
    }

    @Override
    public String toString() {
        return "Notificaciones{" +
                "idNotificaciones=" + idNotificaciones +
                ", descripcion='" + descripcion + '\'' +
                ", idUsuario=" + idUsuario +
                ", estado_Visualizacion='" + estado_Visualizacion + '\'' +
                '}';
    }
}
