import Entity.Actividades;
import Entity.Proyecto;
import Entity.Usuario;
import Entity.Usuario_De_Proyecto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.javac.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ProyectTab extends BorderPane {
    private BorderPane proyectContent = new BorderPane();
    private HBox proyectButtons = new HBox();
    private VBox buttonsVBox = new VBox();
    private VBox activitiesVBox = new VBox();
    private VBox participantsVBox = new VBox();
    private Button activitiesButton = new Button("Actividades");
    private Button HRButton = new Button("Recursos Humanos");
    private Button participantsButton = new Button("Participantes");
    private Button activityManagerButton = new Button("Gestionar Actividades");
    private Button financingButton = new Button("Financiación");
    private Button newParticipantButton= new Button("Nuevo Participante");
    private Button newActivityButton = new Button("Nueva Actividad");
    private Usuario user;
    private int proyectID;
    Actividades actividad;
    ComboBox<String> rolCB;
    int comboValue = 2;
    ComboBox<String> asignedUserCB;
    List<Usuario> proyectUsers = new ArrayList<Usuario>();
    List<Actividades> userActivities = new ArrayList<>();
    List<Usuario_De_Proyecto> userRol = new ArrayList<>();
    private Proyecto proyecto;


    public ProyectTab(Proyecto selectedProyect, Usuario user) {
        this.user = user;
        this.proyectID = selectedProyect.getIdProyecto();
        this.proyecto = selectedProyect;
        proyectButtons.getChildren().addAll(activitiesButton, participantsButton, activityManagerButton);
        proyectButtons.setAlignment(Pos.CENTER);
        proyectButtons.setSpacing(10);
        Label proyectTitle = new Label(proyecto.getNombre_de_la_extension());
        buttonsVBox.getChildren().addAll(proyectTitle, proyectButtons);
        setupTabs();
        activitiesButton.setOnAction(actionEvent -> {
            showActivities();
        });
        participantsButton.setOnAction(actionEvent -> {
           showParticipants();
        });
        activityManagerButton.setOnAction(actionEvent -> {
           editActivities();
        });
        proyectContent.setTop(buttonsVBox);
        proyectContent.setCenter(activitiesVBox);
        proyectContent.getStyleClass().add("cardProyect-panel");
        //Styles
        proyectTitle.getStyleClass().add("title-label");
        activitiesButton.getStyleClass().add("toolBar-button");
        participantsButton.getStyleClass().add("toolBar-button");
        activityManagerButton.getStyleClass().add("toolBar-button");
        newParticipantButton.getStyleClass().add("hover-button");
        newActivityButton.getStyleClass().add("hover-button");
        proyectButtons.setStyle("-fx-font-weight: bold; -fx-border-width: 1 0 1 0; -fx-border-color: C1BBD6;");
        activitiesVBox.setStyle("-fx-padding: 10");
        participantsVBox.setStyle("-fx-padding: 10");

        this.setCenter(proyectContent);
        this.setStyle("-fx-padding: 20");
        this.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        saveProyectUsers();
        showActivities();
    }

    public void showParticipants(){
        proyectContent.setCenter(null);
        proyectContent.setCenter(participantsVBox);
        participantsVBox.getChildren().clear();
        newParticipantButton.setOnAction(actionEvent -> {
            newParticipant();
        });
        participantsVBox.getChildren().add(newParticipantButton);
        VBox participantsTable = new VBox();
        participantsVBox.setSpacing(10);
        participantsTable.getChildren().add(new ParticipantsLine());
        for (Usuario usuario : proyectUsers) {
            for(Usuario_De_Proyecto rol: userRol){
                if (rol.getIdUsuario() == usuario.getIdUsuario()){
                    participantsTable.getChildren().add(new ParticipantsLine(usuario, rol.getIdRol()));
                }
            }
        }
        participantsVBox.getChildren().add(participantsTable);
    }

    public void showActivities(){
        proyectContent.setCenter(null);
        proyectContent.setCenter(activitiesVBox);
        activitiesVBox.getChildren().clear();
        newActivityButton.setOnAction(actionEvent -> {
            newActivity();
        });
        activitiesVBox.getChildren().add(newActivityButton);

        try {
            // Crear cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Crear la solicitud HTTP para obtener los proyectos
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8094/idActividadesById/" + proyectID)) // URL de la API
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
                List<Actividades> actividades = mapper.readValue(jsonResponse, new TypeReference<List<Actividades>>() {});

                ActivitiesLine activityHolders = new ActivitiesLine();
                activitiesVBox.getChildren().add(activityHolders);
                boolean color = true;
                for (Actividades actividad : actividades) {
                    ActivitiesLine activity = new ActivitiesLine(actividad, color);
                    activitiesVBox.getChildren().add(activity);
                    color = !color;
                }


            } else {
                System.err.println("Error al obtener los proyectos: Código de estado " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editActivities(){
        proyectContent.setCenter(null);
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8094/idActividadesByUsuarioAsignado/" + user.getIdUsuario() )) // URL de la API
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
                userActivities = mapper.readValue(jsonResponse, new TypeReference<List<Actividades>>() {});

            } else {
                System.err.println("Error al obtener los proyectos: Código de estado " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        proyectContent.setCenter(new ActivityCard(userActivities));
        activitiesVBox.getChildren().clear();
    }

    public void setupTabs(){
        Label activitiesLabel = new Label("No hay actividades pendientes...");
        activitiesLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        Label participantsLabel = new Label("No hay participantes...");
        participantsLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        participantsVBox.getChildren().addAll(participantsLabel);
        activitiesVBox.getChildren().addAll(activitiesLabel);
    }

    public void newParticipant(){
        proyectContent.setCenter(null);
        proyectContent.setCenter(participantsVBox);
        participantsVBox.getChildren().clear();
        Label newParticipantLabel = new Label("Nuevo Participante");
        TextField IDTextField = new TextField();
        IDTextField.setPromptText("Cédula del Participante");
        rolCB = new ComboBox<>();
        rolCB.getItems().addAll("Coordinador", "Usuario de Actividad");
        rolCB.setValue("Usuario de Actividad");
        Button addParticipantButton = new Button("Agregar");
        newParticipantLabel.getStyleClass().add("title-label");
        IDTextField.getStyleClass().add("search-textField");
        rolCB.getStyleClass().add("combo-box");
        addParticipantButton.getStyleClass().add("hover-button");

        addParticipantButton.setOnAction(actionEvent -> {
            rolCombo();
            try {
                HttpClient client = HttpClient.newHttpClient();
                String jsonData = "{" +
                        "\"idProyecto\": " + proyectID + "," +
                        "\"idUsuario\": " + (IDTextField.getText() != null ? IDTextField.getText() : "null") + "," +
                        "\"idRol\": " + comboValue +
                        "}";
                System.out.println(jsonData);
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("http://localhost:8094/addUsuarioDeProyecto"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(jsonData, StandardCharsets.UTF_8))
                        .build();
                // Enviar la solicitud y manejar la respuesta
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200 || response.statusCode() == 201) { // 201 para creación exitosa
                    System.out.println("Usuario agregado al proyecto correctamente " + response.body());
                    newParticipantLabel.setText("Participante agregado correctamente");
                }
            } catch (InterruptedException e) {
                newParticipantLabel.setText("Error al agregar participante");
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                newParticipantLabel.setText("Error al agregar participante");
                throw new RuntimeException(e);
            } catch (IOException e) {
                newParticipantLabel.setText("Error al agregar participante");
                throw new RuntimeException(e);
            }
            participantsVBox.getChildren().clear();
            Button backButton = new Button("Ver Participantes");
            backButton.setOnAction(actionEvent1 -> {
                showParticipants();
            });

            participantsVBox.getChildren().addAll(newParticipantLabel,backButton);
        });
        participantsVBox.getChildren().addAll(newParticipantLabel, IDTextField, rolCB, addParticipantButton);
        saveProyectUsers();


    }

    public void newActivity(){
        proyectContent.setCenter(null);
        VBox newActivityVBox = new VBox();
        proyectContent.setCenter(newActivityVBox);
        activitiesVBox.getChildren().clear();
        Label newActivityLabel = new Label("Nuevo Actividad");
        TextField activityNameTF = new TextField();
        activityNameTF.setPromptText("Nombre de la actividad");
        asignedUserCB = new ComboBox<String>();
        asignedUserCB.setPromptText("Usuario Asignado");
        for (Usuario usuario : proyectUsers) {
            asignedUserCB.getItems().add(usuario.getIdUsuario()+"");
        }
        DatePicker StartActivityDate = new DatePicker();
        DatePicker EndActivityDate = new DatePicker();
        TextField descriptionTF = new TextField();
        descriptionTF.setPromptText("Descripción");
        Button addActivityButton = new Button("Agregar");
        newActivityLabel.getStyleClass().add("title-label");
        activityNameTF.getStyleClass().add("search-textField");
        asignedUserCB.getStyleClass().add("combo-box");
        StartActivityDate.getStyleClass().add("date-picker");
        EndActivityDate.getStyleClass().add("date-picker");
        descriptionTF.getStyleClass().add("search-textField");
        addActivityButton.getStyleClass().add("hover-button");
        addActivityButton.setOnAction(actionEvent -> {
            try {
                HttpClient client = HttpClient.newHttpClient();
                String jsonData = "{" +
                        "\"proyecto_Asociado\": " + proyectID + "," +
                        "\"nombre\": \"" + (activityNameTF.getText() != null ? activityNameTF.getText() : "") + "\"," +
                        "\"descripcion\": \"" + (descriptionTF.getText() != null ? descriptionTF.getText() : "") + "\"," +
                        "\"usuario_Asignado\": " + (asignedUserCB.getValue() != null ? asignedUserCB.getValue() : "null") + "," +
                        "\"progreso\": " + 0 + "," +
                        "\"estado\": " + 1 + "," +
                        "\"fecha_de_inicio\": \"" + (StartActivityDate.getValue() != null ? StartActivityDate.getValue().toString() : "") + "\"," +
                        "\"fecha_de_finalizacion\": \"" + (EndActivityDate.getValue() != null ? EndActivityDate.getValue().toString() : "") + "\"" +
                        "}";
                System.out.println(jsonData);
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("http://localhost:8094/addActividad"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(jsonData, StandardCharsets.UTF_8))
                        .build();
                // Enviar la solicitud y manejar la respuesta
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200 || response.statusCode() == 201) { // 201 para creación exitosa
                    System.out.println("Actividad creada exitosamente: " + response.body());
                    try {
                        String projectJson = response.body();
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
                        actividad = mapper.readValue(projectJson, Actividades.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    newActivityLabel.setText("Actividad ( " + actividad.getNombre() +") agregada correctamente");
                }
            } catch (InterruptedException e) {
                newActivityLabel.setText("Error al agregar actividad");
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                newActivityLabel.setText("Error al agregar actividad");
                throw new RuntimeException(e);
            } catch (IOException e) {
                newActivityLabel.setText("Error al agregar actividad");
                throw new RuntimeException(e);
            }
            activitiesVBox.getChildren().clear();
            Button backButton = new Button("Ver Actividades");
            backButton.setOnAction(actionEvent1 -> {
                showActivities();
            });
            activitiesVBox.getChildren().addAll(newActivityLabel,backButton);

        });
        newActivityVBox.setSpacing(10);
        newActivityVBox.setStyle("-fx-padding: 10");
        newActivityVBox.getChildren().addAll(activityNameTF, asignedUserCB, StartActivityDate, EndActivityDate, descriptionTF, addActivityButton);

    }

    public void rolCombo(){
        if (rolCB.getValue() == "Usuario de Actividad"){comboValue = 2;}
        else{comboValue = 3;}
    }

    public void saveProyectUsers(){
        proyectUsers.clear();
        try {
            // Crear cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Crear la solicitud HTTP para obtener los proyectos
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8094/UsuarioDeProyectoByIdProyecto/"+ proyectID)) // URL de la API
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
                userRol = mapper.readValue(jsonResponse, new TypeReference<List<Usuario_De_Proyecto>>() {});
                for (Usuario_De_Proyecto usuario : userRol) {

                    try {
                        // Crear cliente HTTP
                        HttpClient client2 = HttpClient.newHttpClient();

                        // Crear la solicitud HTTP para obtener los proyectos
                        HttpRequest request2 = HttpRequest.newBuilder()
                                .uri(new URI("http://localhost:8094/usuarioById/"+ usuario.getIdUsuario())) // URL de la API
                                .header("Content-Type", "application/json")
                                .GET()
                                .build();
                        // Enviar la solicitud y manejar la respuesta
                        HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());
                        if (response2.statusCode() == 200) {
                            // Procesar la respuesta JSON
                            String userJson = response2.body();
                            System.out.println("Respuesta obtenida: " + userJson);
                            // Mapear la respuesta a una lista de objetos Proyecto
                            ObjectMapper mapper2 = new ObjectMapper();
                            mapper2.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
                            proyectUsers.add(mapper2.readValue(userJson, Usuario.class));
                        } else {
                            System.err.println("Error al obtener usuarios: Código de estado " + response2.statusCode());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.err.println("Error al obtener los proyectos: Código de estado " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
