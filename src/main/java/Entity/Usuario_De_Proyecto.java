package Entity;

public class Usuario_De_Proyecto {

    private int idUsuarioDeProyecto;

    private int idProyecto;

    private int idUsuario;

    private int idRol;



    public int getIdUsuarioDeProyecto() {
        return idUsuarioDeProyecto;
    }

    public void setIdUsuarioDeProyecto(int idUsuarioDeProyecto) {
        this.idUsuarioDeProyecto = idUsuarioDeProyecto;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    @Override
    public String toString() {
        return "Usuario_De_Proyecto{" +
                "idUsuarioDeProyecto=" + idUsuarioDeProyecto +
                ", idProyecto=" + idProyecto +
                ", idUsuario=" + idUsuario +
                ", idRol=" + idRol +
                '}';
    }
}
