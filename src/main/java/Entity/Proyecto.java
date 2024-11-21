package Entity;

public class Proyecto {
    private int ID_Proyecto;
    private int Ubicacion;
    private String Nombre_de_la_Extension;
    private String Fecha_de_Inicio; // Usamos String para compatibilidad con JSON, puede cambiarse a LocalDate
    private String Fecha_Estimada_de_Finalizacion;
    private int Coordinador;
    private double Presupuesto;
    private int Estado_Proyecto;
    private String Comentarios;

    // Constructor vacío (necesario para la deserialización)
    public Proyecto() {
    }

    // Constructor completo
    public Proyecto(int ID_Proyecto, int Ubicacion, String Nombre_de_la_Extension,
                    String Fecha_de_Inicio, String Fecha_Estimada_de_Finalizacion,
                    int Coordinador, double Presupuesto, int Estado_Proyecto, String Comentarios) {
        this.ID_Proyecto = ID_Proyecto;
        this.Ubicacion = Ubicacion;
        this.Nombre_de_la_Extension = Nombre_de_la_Extension;
        this.Fecha_de_Inicio = Fecha_de_Inicio;
        this.Fecha_Estimada_de_Finalizacion = Fecha_Estimada_de_Finalizacion;
        this.Coordinador = Coordinador;
        this.Presupuesto = Presupuesto;
        this.Estado_Proyecto = Estado_Proyecto;
        this.Comentarios = Comentarios;
    }

    // Getters y Setters
    public int getID_Proyecto() {
        return ID_Proyecto;
    }

    public void setID_Proyecto(int ID_Proyecto) {
        this.ID_Proyecto = ID_Proyecto;
    }

    public int getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(int Ubicacion) {
        this.Ubicacion = Ubicacion;
    }

    public String getNombreDeLaExtension() {
        return Nombre_de_la_Extension;
    }

    public void setNombreDeLaExtension(String Nombre_de_la_Extension) {
        this.Nombre_de_la_Extension = Nombre_de_la_Extension;
    }

    public String getFechaDeInicio() {
        return Fecha_de_Inicio;
    }

    public void setFechaDeInicio(String Fecha_de_Inicio) {
        this.Fecha_de_Inicio = Fecha_de_Inicio;
    }

    public String getFechaEstimadaDeFinalizacion() {
        return Fecha_Estimada_de_Finalizacion;
    }

    public void setFechaEstimadaDeFinalizacion(String Fecha_Estimada_de_Finalizacion) {
        this.Fecha_Estimada_de_Finalizacion = Fecha_Estimada_de_Finalizacion;
    }

    public int getCoordinador() {
        return Coordinador;
    }

    public void setCoordinador(int Coordinador) {
        this.Coordinador = Coordinador;
    }

    public double getPresupuesto() {
        return Presupuesto;
    }

    public void setPresupuesto(double Presupuesto) {
        this.Presupuesto = Presupuesto;
    }

    public int getEstadoProyecto() {
        return Estado_Proyecto;
    }

    public void setEstadoProyecto(int Estado_Proyecto) {
        this.Estado_Proyecto = Estado_Proyecto;
    }

    public String getComentarios() {
        return Comentarios;
    }

    public void setComentarios(String Comentarios) {
        this.Comentarios = Comentarios;
    }

    @Override
    public String toString() {
        return "Proyecto{" +
                "ID_Proyecto=" + ID_Proyecto +
                ", Ubicacion=" + Ubicacion +
                ", Nombre_de_la_Extension='" + Nombre_de_la_Extension + '\'' +
                ", Fecha_de_Inicio='" + Fecha_de_Inicio + '\'' +
                ", Fecha_Estimada_de_Finalizacion='" + Fecha_Estimada_de_Finalizacion + '\'' +
                ", Coordinador=" + Coordinador +
                ", Presupuesto=" + Presupuesto +
                ", Estado_Proyecto=" + Estado_Proyecto +
                ", Comentarios='" + Comentarios + '\'' +
                '}';
    }
}
