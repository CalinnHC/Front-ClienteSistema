import Entity.Actividades;
import Entity.Usuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.javac.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.InputStream;
import java.util.List;

public class ProyectTab extends BorderPane {
    private BorderPane proyectContent = new BorderPane();
    private HBox proyectButtons = new HBox();
    private VBox buttonsVBox = new VBox();
    private VBox activitiesVBox = new VBox();
    private VBox participantsVBox = new VBox();
    private Button activitiesButton = new Button("Activities");
    private Button HRButton = new Button("Recursos Humanos");
    private Button participantsButton = new Button("Participantes");
    private Button financingButton = new Button("Financiación");
    ProyectTab() {
        proyectButtons.getChildren().addAll(activitiesButton, HRButton, participantsButton, financingButton);
        proyectButtons.setAlignment(Pos.CENTER);
        proyectButtons.setSpacing(10);
        Label proyectTitle = new Label("Proyect Name");
        buttonsVBox.getChildren().addAll(proyectTitle, proyectButtons);
        setupTabs();
        activitiesButton.setOnAction(actionEvent -> {
            showActivities();
        });
        participantsButton.setOnAction(actionEvent -> {
           showParticipants();
        });
        proyectContent.setTop(buttonsVBox);
        proyectContent.setCenter(activitiesVBox);
        proyectContent.getStyleClass().add("cardProyect-panel");
        this.setCenter(proyectContent);
        this.setStyle("-fx-padding: 20");
        this.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        showActivities();
    }
    public void showParticipants(){
        proyectContent.setCenter(null);
        proyectContent.setCenter(participantsVBox);
        {
            participantsVBox.getChildren().clear();
        /*
        try {
            // Crear cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Crear la solicitud HTTP para obtener los proyectos
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8094/http://localhost:8094/UsuarioDeProyectoByIdProyecto/"+ idProyecto)) // URL de la API
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
            // Enviar la solicitud y manejar la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Procesar la respuesta JSON
                String jsonResponse = response.body();
                System.out.println("Respuesta obtenida: " + jsonResponse);

                // Mapear la respuesta a una lista de objetos Proyecto
                ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
                    List<Usuario> usuarios = mapper.readValue(inputStream, new TypeReference<List<Usuario>>() {});
                    for (Usuario usuario : usuarios) {
                        System.out.println(usuario.toString());
                        Label participantsLabel = new Label(usuario.getNombreUsuario());
                        participantsVBox.getChildren().add(participantsLabel);
                    }
            } else {
                System.err.println("Error al obtener los proyectos: Código de estado " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
            try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("users.json")) {
                if (inputStream == null) {
                    throw new RuntimeException("Archivo JSON no encontrado");
                }
                else{
                    System.out.println(inputStream);
                    // Mapear la respuesta a una lista de objetos Proyecto
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
                    List<Usuario> usuarios = mapper.readValue(inputStream, new TypeReference<List<Usuario>>() {});


                    TableView<Usuario> participantsTable = new TableView<>();

                    TableColumn<Usuario, String> idColumn = new TableColumn<>("ID");
                    idColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdUsuario() + ""));

                    TableColumn<Usuario, String> nameColumn = new TableColumn<>("Nombre");
                    nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));

                    TableColumn<Usuario, String> lastNameColumn = new TableColumn<>("Cargo");
                    lastNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getApellido()));

                    TableColumn<Usuario, String> mailColumn = new TableColumn<>("Correo");
                    mailColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCorreo()));

                    TableColumn<Usuario, String> rolColumn = new TableColumn<>("Rol");
                    rolColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdRol() + ""));


                    participantsTable.getColumns().addAll(idColumn, nameColumn, lastNameColumn, mailColumn, rolColumn);


                    for (Usuario usuario : usuarios) {
                        participantsTable.getItems().add(usuario);
                    }
                    participantsVBox.getChildren().add(participantsTable);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showActivities(){
        proyectContent.setCenter(null);
        proyectContent.setCenter(activitiesVBox);
        {
            activitiesVBox.getChildren().clear();
        /*
        try {
            // Crear cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Crear la solicitud HTTP para obtener los proyectos
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8094/idActividadesById/" + idProyecto)) // URL de la API
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
                    List<Actividades> actividades = mapper.readValue(inputStream, new TypeReference<List<Actividades>>() {});
                    for (Actividades actividad : actividades) {
                        System.out.println(actividad.toString());
                        Label activitiesLabel = new Label(actividad.getDescripcion());
                        activitiesVBox.getChildren().add(activitiesLabel);
                    }
            } else {
                System.err.println("Error al obtener los proyectos: Código de estado " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
            try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("activities.json")) {
                if (inputStream == null) {
                    throw new RuntimeException("Archivo JSON no encontrado");
                }
                else{
                    System.out.println(inputStream);
                    // Mapear la respuesta a una lista de objetos Proyecto
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
                    List<Actividades> actividades = mapper.readValue(inputStream, new TypeReference<List<Actividades>>() {});



                    TableView<Actividades> activityTable = new TableView<>();
                    TableColumn<Actividades, String> descriptionColumn = new TableColumn<>("Descripción");
                    descriptionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescripcion()));

                    TableColumn<Actividades, String> managerColumn = new TableColumn<>("Usuario Asignado");
                    managerColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUsuario_Asignado() + ""));

                    TableColumn<Actividades, String> stateColumn = new TableColumn<>("Estado");
                    stateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstado() + ""));

                    TableColumn<Actividades, String> progressColumn = new TableColumn<>("Progreso");
                    progressColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProgreso() + ""));

                    activityTable.getColumns().addAll(descriptionColumn, managerColumn, stateColumn, progressColumn);


                    for (Actividades actividad : actividades) {
                        activityTable.getItems().add(actividad);
                    }
                    activitiesVBox.getChildren().add(activityTable);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void setupTabs(){
        Label activitiesLabel = new Label("No hay actividades pendientes...");
        activitiesLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        Label participantsLabel = new Label("No hay participantes...");
        participantsLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        participantsVBox.getChildren().addAll(participantsLabel);
        activitiesVBox.getChildren().addAll(activitiesLabel);
    }
}
