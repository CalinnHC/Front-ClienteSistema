import Entity.Actividades;
import Entity.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ActivityCard extends VBox {
    List<Actividades> activities;
    ComboBox<String> activityState = new ComboBox<>();
    public ActivityCard(List<Actividades> actividades) {
        this.activities = actividades;
        for (Actividades actividad : actividades) {
            HBox hbox = new HBox();
            hbox.setSpacing(20);
            Label descriptionLabel = new Label(actividad.getDescripcion());
            Button editButton = new Button("Editar");
            editButton.setOnAction(e -> {
                this.getChildren().clear();
                editActivity(actividad);
            });
            descriptionLabel.getStyleClass().add("sub-label");
            editButton.getStyleClass().add("hover-button");
            hbox.setStyle("-fx-background-color: rgba(184, 228, 229, 0.5); -fx-padding: 10; -fx-background-radius: 15");
            hbox.getChildren().addAll(descriptionLabel, editButton);
            this.getChildren().add(hbox);
        }
        this.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        this.setSpacing(10);
        this.setStyle("-fx-padding: 10");

    }

    private void editActivity(Actividades activity) {
        Label descriptionLabel = new Label(activity.getDescripcion());
        Label estadoLabel = new Label("Estado: ");
        activityState.getItems().addAll("En Espera", "Iniciada", "Finalizada");
        Label progressLabel = new Label("Progreso: ");
        Slider progressSlider = new Slider();
        progressSlider.setMin(0);  // Valor mínimo
        progressSlider.setMax(100);  // Valor máximo
        progressSlider.setValue(50);  // Valor inicial
        progressSlider.setShowTickLabels(true);  // Mostrar etiquetas
        progressSlider.setShowTickMarks(true);  // Mostrar marcas
        progressSlider.setMajorTickUnit(10);  // Espaciado de las marcas mayores
        progressSlider.setBlockIncrement(5);
        Button SaveButton = new Button("Guardar");
        descriptionLabel.getStyleClass().add("title-label");
        estadoLabel.getStyleClass().add("sub-label");
        progressLabel.getStyleClass().add("sub-label");
        SaveButton.getStyleClass().add("hover-button");

        SaveButton.setOnAction(e -> {
            activity.setEstado(activityState.getSelectionModel().getSelectedIndex() + 1);
            activity.setProgreso((int) progressSlider.getValue());
            updateProyect(activity);
        });
        this.getChildren().addAll(descriptionLabel,estadoLabel, activityState, progressLabel,progressSlider, SaveButton);
        this.setStyle("-fx-padding: 10");
        this.setSpacing(10);


    }

    private void updateProyect(Actividades activity) {
        try {
            // Crear cliente HTTP
            HttpClient client = HttpClient.newHttpClient();
            // Serializar los datos
            String jsonData = "{" +
                    "\"idActividad\": " + activity.getIdActividad() + "," +
                    "\"proyecto_Asociado\": " + activity.getProyecto_Asociado() + "," +
                    "\"nombre\": \"" + activity.getNombre() + "\"," +
                    "\"descripcion\": \"" + activity.getDescripcion() + "\"," +
                    "\"usuario_Asignado\": " + activity.getUsuario_Asignado() + "," +
                    "\"progreso\": " + activity.getProgreso() + "," +
                    "\"estado\": " + activity.getEstado() + "," +
                    "\"fecha_de_inicio\": \"" + activity.getFecha_de_inicio() + "\"," +
                    "\"fecha_de_finalizacion\": \"" + activity.getFecha_de_finalizacion() + "\"" +
                    "}";
            System.out.println(jsonData);
            ObjectMapper mapper = new ObjectMapper();
            // Crear la solicitud HTTP
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8094/updateActividad")) // Cambia esta URL si es necesario
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonData, StandardCharsets.UTF_8)) // Cambia a PUT
                    .build();
            // Enviar la solicitud y manejar la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                try {
                    String userJson = response.body();
                    System.out.println("Exito -> "+userJson);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Error al actualizar la actividad: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
