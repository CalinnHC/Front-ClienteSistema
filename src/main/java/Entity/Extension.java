package Entity;

public class Extension {
    private int idExtension;
    private String nombre_de_la_extension;

    public int getIdExtension() {
        return idExtension;
    }

    public void setIdExtension(int idExtension) {
        this.idExtension = idExtension;
    }

    public String getNombre_de_la_extension() {
        return nombre_de_la_extension;
    }

    public void setNombre_de_la_extension(String nombre_de_la_extension) {
        this.nombre_de_la_extension = nombre_de_la_extension;
    }

    @Override
    public String toString() {
        return "Extension{" +
                "idExtension=" + idExtension +
                ", nombre_de_la_extension='" + nombre_de_la_extension + '\'' +
                '}';
    }
}
