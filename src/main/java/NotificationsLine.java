import Entity.Actividades;
import Entity.Notificaciones;
import Entity.Usuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class NotificationsLine extends VBox {
    List<Notificaciones> userNotifications = new ArrayList<Notificaciones>();
    int userID;
    public NotificationsLine(int userID) {
        this.userID = userID;
        this.setSpacing(10);
        getNotifications();
        this.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

    }
    public void getNotifications(){
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8094/NotificacionesByIdUsuario/" + userID)) // URL de la API
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
            // Enviar la solicitud y manejar la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Procesar la respuesta JSON
                String jsonResponse = response.body();
                System.out.println("Respuesta obtenida: " + jsonResponse);
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
                List<Notificaciones> notificaciones = mapper.readValue(jsonResponse, new TypeReference<List<Notificaciones>>() {});
                if (notificaciones.size() > 0) {
                    for (int i = notificaciones.size() - 1; i >= 0; i--) {
                        Notificaciones notificacion = notificaciones.get(i);
                        HBox hbox = new HBox();
                        Label notificationLabel = new Label(notificacion.getDescripcion());
                        notificationLabel.getStyleClass().add("not-label");
                        hbox.setStyle("-fx-background-color: rgba(193, 187, 214, 0.5); -fx-background-radius: 15; -fx-padding: 10");
                        hbox.getChildren().add(notificationLabel);
                        this.getChildren().add(hbox);
                    }
                }
                else{
                    Label notificationLabel = new Label("No tienes notificaciones pendientes...");
                    this.getChildren().add(notificationLabel);
                }
            } else {
                System.err.println("Error al obtener los proyectos: Código de estado " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
