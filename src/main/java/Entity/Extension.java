package Entity;

public class Extension {
    private int idExtension;
    private String nombre_Extension;

    public int getIdExtension() {
        return idExtension;
    }

    public void setIdExtension(int idExtension) {
        this.idExtension = idExtension;
    }

    public String getNombre_Extension() {
        return nombre_Extension;
    }

    public void setNombre_Extension(String nombre_Extension) {
        this.nombre_Extension = nombre_Extension;
    }

    @Override
    public String toString() {
        return "Extension{" +
                "idExtension=" + idExtension +
                ", nombre_Extension='" + nombre_Extension + '\'' +
                '}';
    }
}
