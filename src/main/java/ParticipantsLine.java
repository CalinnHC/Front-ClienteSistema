import Entity.Actividades;
import Entity.Usuario;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class ParticipantsLine extends HBox {
    Usuario usuario;
    public ParticipantsLine(Usuario usuario1) {
        usuario = usuario1;
        Label idLabel = new Label(""+usuario.getIdUsuario());
        idLabel.getStyleClass().add("table-label");

        idLabel.setStyle("-fx-alignment: CENTER_LEFT");
        Label nameLabel = new Label(usuario.getNombre());
        nameLabel.getStyleClass().add("table-label");
        Label lastnameLabel = new Label(usuario.getApellido());
        lastnameLabel.getStyleClass().add("table-label");
        Label mailLabel = new Label(usuario.getCorreo());
        mailLabel.getStyleClass().add("table-label");
        Label rolLabel = new Label(setRolLabel(usuario.getIdRol()));
        rolLabel.getStyleClass().add("table-label");
        this.setAlignment(Pos.CENTER);
        idLabel.setPrefSize(120,40);
        nameLabel.setPrefSize(120,40);
        lastnameLabel.setPrefSize(120,40);
        rolLabel.setPrefSize(200,40);
        mailLabel.setPrefHeight(40);
        HBox.setHgrow(mailLabel, Priority.ALWAYS);
        HBox.setHgrow(rolLabel, Priority.ALWAYS);
        mailLabel.setMaxWidth(Double.MAX_VALUE);
        this.getChildren().addAll(idLabel, nameLabel, lastnameLabel, mailLabel, rolLabel);
        this.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

    }
    public ParticipantsLine() {
        Label idLabel = new Label("Cédula");
        idLabel.getStyleClass().add("table-label2");
        idLabel.setStyle("-fx-alignment: CENTER_LEFT");
        Label nameLabel = new Label("Nombre");
        nameLabel.getStyleClass().add("table-label2");
        Label lastnameLabel = new Label("Apellido");
        lastnameLabel.getStyleClass().add("table-label2");
        Label mailLabel = new Label("Correo electrónico");
        mailLabel.getStyleClass().add("table-label2");
        mailLabel.setMaxWidth(Double.MAX_VALUE);
        Label rolLabel = new Label("Rol");
        rolLabel.getStyleClass().add("table-label2");
        this.setAlignment(Pos.CENTER);
        idLabel.setPrefSize(120,40);
        nameLabel.setPrefSize(120,40);
        lastnameLabel.setPrefSize(120,40);
        rolLabel.setPrefSize(200,40);
        mailLabel.setPrefHeight(40);
        HBox.setHgrow(mailLabel, Priority.ALWAYS);
        mailLabel.setMaxWidth(Double.MAX_VALUE);
        this.getChildren().addAll(idLabel, nameLabel, lastnameLabel, mailLabel, rolLabel);
        this.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
    }
    private String setRolLabel(int idRol){
        switch (idRol){
            case 1:
                return "Administrador";
            case 2:
                return "Usuario de Actividad";
            case 3:
                return "Coordinador del Proyecto";
            default:
                return "";
        }
    }
}
