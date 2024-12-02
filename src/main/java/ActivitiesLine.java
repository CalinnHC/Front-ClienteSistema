import Entity.Actividades;
import Entity.Usuario;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class ActivitiesLine extends HBox {
    Actividades actividad;
    Label userLabel = new Label();
    public ActivitiesLine(Actividades actividad, boolean colorx) {
        this.actividad = actividad;
        getUsuario();
        this.setStyle("-fx-background-color: #ffffff");
        Label descriptionLabel = new Label(actividad.getDescripcion());
        HBox.setHgrow(descriptionLabel, Priority.ALWAYS);
        this.setAlignment(Pos.CENTER);
        descriptionLabel.setMaxWidth(Double.MAX_VALUE);
        Label stateLabel = new Label(setStateLabel(actividad.getEstado()));
        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefSize(120,40);
        stateLabel.setPrefSize(120,40);
        userLabel.setPrefSize(120,40);
        progressBar.setProgress(actividad.getProgreso());
        descriptionLabel.setPrefHeight(40);
        if (colorx) {
            descriptionLabel.getStyleClass().add("activitiesDLabel");
            userLabel.getStyleClass().add("activitiesULabel");
            stateLabel.getStyleClass().add("activitiesSLabel");
            progressBar.getStyleClass().add("activitiesPLabel");
        }
        else {
            descriptionLabel.getStyleClass().add("activitiesDLabel2");
            userLabel.getStyleClass().add("activitiesULabel2");
            stateLabel.getStyleClass().add("activitiesSLabel2");
            progressBar.getStyleClass().add("activitiesPLabel2");
        }
        progressBar.setStyle("-fx-padding: 10");

        this.getChildren().addAll(descriptionLabel, userLabel, stateLabel, progressBar);

    }
    public ActivitiesLine() {
        Label descriptionLabel = new Label("Descripcion");
        descriptionLabel.getStyleClass().add("title-label2");
        HBox.setHgrow(descriptionLabel, Priority.ALWAYS);
        descriptionLabel.setMaxWidth(Double.MAX_VALUE);
        descriptionLabel.setStyle("-fx-alignment: CENTER_LEFT");
        Label userLabel = new Label("Encargado");
        userLabel.getStyleClass().add("title-label2");
        Label stateLabel = new Label("Estado");
        stateLabel.getStyleClass().add("title-label2");
        Label progressLabel = new Label("Progreso");
        progressLabel.getStyleClass().add("title-label2");
        this.setAlignment(Pos.CENTER);
        progressLabel.setPrefSize(120,40);
        stateLabel.setPrefSize(120,40);
        userLabel.setPrefSize(120,40);
        this.getChildren().addAll(descriptionLabel, userLabel, stateLabel, progressLabel);
        this.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
    }
    private String setStateLabel(int idEstado){
        switch (idEstado){
            case 1:
                return "Sin Iniciar";
                case 2:
                    return "En progreso";
                    case 3:
                        return "Finalizada";
                        default:
                            return "";
        }
    }

    private void getUsuario(){
        try {
            // Crear cliente HTTP
            HttpClient client2 = HttpClient.newHttpClient();

            // Crear la solicitud HTTP para obtener los proyectos
            HttpRequest request2 = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8094/usuarioById/"+ actividad.getUsuario_Asignado())) // URL de la API
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
            // Enviar la solicitud y manejar la respuesta
            HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());
            if (response2.statusCode() == 200) {
                // Procesar la respuesta JSON
                String userJson = response2.body();
                // Mapear la respuesta a una lista de objetos Proyecto
                ObjectMapper mapper2 = new ObjectMapper();
                mapper2.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
                Usuario user = mapper2.readValue(userJson, Usuario.class);
                userLabel.setText(user.getNombre() + " " + user.getApellido());
            } else {
                System.err.println("Error al obtener usuario: Código de estado " + response2.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
