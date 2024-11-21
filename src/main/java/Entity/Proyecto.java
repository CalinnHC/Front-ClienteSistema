package Entity;

public class Proyecto {
    private int idProyecto;
    private int ubicacion;
    private String nombre_de_la_extension;
    private String fecha_De_Inicio; // Usamos String para compatibilidad con JSON, puede cambiarse a LocalDate
    private String fecha_Estimada_De_Finalizacion;
    private int coordinador;
    private double presupuesto;
    private int estado_Proyecto;
    private String comentarios;

    // Constructor vacío (necesario para la deserialización)
    public Proyecto() {
    }

    public Proyecto(int idProyecto, int ubicacion, String nombre_de_la_extension, String fecha_De_Inicio, String fecha_Estimada_de_Finalizacion, int coordinador, double presupuesto, int estado_Proyecto, String comentarios) {
        this.idProyecto = idProyecto;
        this.ubicacion = ubicacion;
        this.nombre_de_la_extension = nombre_de_la_extension;
        this.fecha_De_Inicio = fecha_De_Inicio;
        this.fecha_Estimada_De_Finalizacion = fecha_Estimada_de_Finalizacion;
        this.coordinador = coordinador;
        this.presupuesto = presupuesto;
        this.estado_Proyecto = estado_Proyecto;
        this.comentarios = comentarios;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public int getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(int ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getNombre_de_la_extension() {
        return nombre_de_la_extension;
    }

    public void setNombre_de_la_extension(String nombre_de_la_extension) {
        this.nombre_de_la_extension = nombre_de_la_extension;
    }

    public String getFecha_De_Inicio() {
        return fecha_De_Inicio;
    }

    public void setFecha_De_Inicio(String fecha_De_Inicio) {
        this.fecha_De_Inicio = fecha_De_Inicio;
    }

    public String getFecha_Estimada_de_Finalizacion() {
        return fecha_Estimada_De_Finalizacion;
    }

    public void setFecha_Estimada_de_Finalizacion(String fecha_Estimada_de_Finalizacion) {
        this.fecha_Estimada_De_Finalizacion = fecha_Estimada_de_Finalizacion;
    }

    public int getCoordinador() {
        return coordinador;
    }

    public void setCoordinador(int coordinador) {
        this.coordinador = coordinador;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public int getEstado_Proyecto() {
        return estado_Proyecto;
    }

    public void setEstado_Proyecto(int estado_Proyecto) {
        this.estado_Proyecto = estado_Proyecto;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public String toString() {
        return "Proyecto{" +
                "idProyecto=" + idProyecto +
                ", ubicacion=" + ubicacion +
                ", nombre_de_la_extension='" + nombre_de_la_extension + '\'' +
                ", fecha_De_Inicio='" + fecha_De_Inicio + '\'' +
                ", fecha_Estimada_de_Finalizacion='" + fecha_Estimada_De_Finalizacion + '\'' +
                ", coordinador=" + coordinador +
                ", presupuesto=" + presupuesto +
                ", estado_Proyecto=" + estado_Proyecto +
                ", comentarios='" + comentarios + '\'' +
                '}';
    }
}