import Entity.Extension;
import Entity.Proyecto;
import Entity.Usuario;
import Entity.Usuario_De_Proyecto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GUI extends Application {
    private Scene loginScene;
    private StackPane loginRoot;
    private StackPane mainRoot;
    private StackPane loginPane;
    private Scene mainScene;
    private Stage mainStage;
    private BorderPane contentPane = new BorderPane(); //To change scenes in the main scene
    private VBox dateAndFilterContianer;
    private ScrollPane scrollMainPane;
    private BorderPane filterPane;
    private int counterx = 0;
    private int countery = 0;
    private TextField usernametextField;
    private PasswordField passwordField;
    private Usuario user;
    private Label errorLabel;
    private List<Proyecto> proyectos;
    private List<Usuario_De_Proyecto> proyectosAsociados;
    private GridPane contentMainPane;
    private VBox extensionsMainPane = new VBox(10);
    private Proyecto proyecto;
    private BorderPane proyectoPane;
    private ComboBox<String> proyectType;
    private int extentionID;
    private int actualExtensionID;

    @Override
    public void start(Stage mainStage) {
        configureMainStage(mainStage);  // Configura el escenario principal
    }

    /**
     * Main Scene Configuration (mainStage).
     * @param primaryStage App main scene.
     */
    private void configureMainStage(Stage primaryStage) {
        mainStage = primaryStage;
        mainStage.setTitle("Extensión UP");
        mainStage.setHeight(720);
        mainStage.setWidth(1280);
        mainStage.setMinHeight(480);
        mainStage.setMinWidth(720);
        loginRoot = new StackPane();  // Inicializa el contenedor raíz
        setupLoginScene();
        mainStage.setScene(loginScene);
        mainStage.show();
        String iconPath = getClass().getResource("/logope.png").toExternalForm();
        primaryStage.getIcons().add(new Image(iconPath));
    }

    /**
     * Login Scene Configuration.,
     */
    private void setupLoginScene() {
        //Login Label Configuration
        Label loginLabel = new Label("INICIAR SESIÓN");
        loginLabel.setFont(Font.font("Open Sans", FontWeight.BOLD, 30));
        errorLabel = new Label("Usuario o contraseña incorrectos");
        errorLabel.setFont(Font.font("Open Sans", FontWeight.NORMAL, 12));
        errorLabel.setStyle("-fx-text-fill: red; -fx-padding: 0");
        errorLabel.setManaged(false);
        //Username TextField Config
        usernametextField = new TextField();
        usernametextField.setPromptText("Username");
        usernametextField.setText("CalinnHC");
        usernametextField.setMinHeight(40);
        usernametextField.setStyle("-fx-background-color: FFFAFC; -fx-background-radius: 20;-fx-padding: 0 15 0 15;");

        //Password TextField Config
        passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña");
        passwordField.setText("Carlosbeto1");
        passwordField.setMinHeight(40);
        passwordField.setStyle("-fx-background-color: FFFAFC; -fx-background-radius: 20; -fx-padding: 0 15 0 15;");

        //Login button Config
        Button loginButton = new Button("INICIAR SESIÓN");
        loginButton.setPrefSize(150,40);
        loginButton.getStyleClass().add("hover-button");
        loginButton.setOnAction(event -> {
            loginFunction();
        });

        //Register Container
        HBox registerContainer = new HBox();
        registerContainer.setAlignment(Pos.CENTER);
        Label registerLabel = new Label("¿No estás en el Sistema? ");
        registerLabel.setFont(Font.font("Open Sans", FontWeight.NORMAL, 15));
        Label registerListenerLabel = new Label("Regístrate");
        registerListenerLabel.setFont(Font.font("Open Sans", FontWeight.BOLD, 15));
        registerListenerLabel.setStyle("-fx-font-weight: bold;");
        registerListenerLabel.setTextFill(Color.BLUE);
        registerListenerLabel.setOnMouseEntered(event -> {
            registerListenerLabel.setFont(Font.font("Open Sans", FontWeight.BOLD, 16));
        });
        registerListenerLabel.setOnMouseExited(event -> {
            registerListenerLabel.setFont(Font.font("Open Sans", FontWeight.BOLD, 15));
        });
        registerListenerLabel.setOnMouseClicked(event -> openRegistrationWindow());

        registerContainer.getChildren().addAll(registerLabel, registerListenerLabel);
        // Contenedor para los elementos del login
        VBox loginContainer = new VBox(30);  // Espacio de 10 píxeles entre elementos
        VBox userAnderrorContainer = new VBox(10);
        userAnderrorContainer.getChildren().addAll(errorLabel,usernametextField );
        loginContainer.getChildren().addAll(loginLabel, userAnderrorContainer, passwordField, loginButton, registerContainer);
        loginContainer.setAlignment(Pos.CENTER);
        loginContainer.setPadding(new Insets(50));  // Padding interno dentro de loginContainer
        loginContainer.setStyle("-fx-background-color: C1BBD6; -fx-background-radius: 32");

        // Contenedor para centrar el loginContainer y establecer un tamaño personalizado
        loginPane = new StackPane();
        loginPane.getChildren().add(loginContainer);
        loginPane.setMaxSize(450, 300); // Tamaño personalizado para loginPane

        // Agregar loginPane al root y centrarlo
        loginRoot.getChildren().add(loginPane);
        loginRoot.setStyle("-fx-background-color: #FFFAFC;");
        StackPane.setAlignment(loginPane, Pos.CENTER);  // Alinea loginPane al centro de root

        loginScene = new Scene(loginRoot, 1280, 720);
        loginScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

    }

    private void loginFunction() {
        try {
            // Crear cliente HTTP
            HttpClient client = HttpClient.newHttpClient();
            // Serializar los datos
            String jsonData = "{" +
                    "\"nombreUsuario\": \"" + (usernametextField.getText() != null ? usernametextField.getText() : "") + "\"," +
                    "\"contrasena\": \"" + passwordField.getText() + "\"" +
                    "}";

            // Crear la solicitud HTTP
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8094/autenticar")) // Cambia esta URL
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonData, StandardCharsets.UTF_8))
                    .build();

            // Enviar la solicitud y manejar la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Login Exitoso: " + response.body());
                try {
                    String userJson = response.body();
                    ObjectMapper mapper = new ObjectMapper();
                    user = mapper.readValue(userJson, Usuario.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setupMainScene();
            } else {
                errorLabel.setManaged(true);
                System.err.println("Error al iniciar sesion: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //
    }

    /**
     * Main Scene Configuration.
     */
    private void  setupMainScene() {
        contentMainPane = new GridPane();
        mainRoot = new StackPane();
        mainRoot.setStyle("-fx-background-color: C1BBD6");
        BorderPane mainPane = new BorderPane();
        BorderPane mainPaneCenter = new BorderPane();

        //Formato de la barra de herramientas
        BorderPane toolBarPane = new BorderPane();
        VBox toolBarContainer = new VBox(10);
        toolBarContainer.setMaxWidth(270);
        toolBarContainer.setMinWidth(270);
        int iconSize = 40; // Tamaño de los iconos de los botones

        //Formato del boton de Inicio
        ImageView imageViewHome = new ImageView(new Image("/logope.png"));
        imageViewHome.setFitWidth(iconSize);
        imageViewHome.setFitHeight(iconSize);
        Button homeButton = new Button("Inicio", imageViewHome);
        homeButton.getStyleClass().add("toolBar-button");
        homeButton.setOnAction(event -> {
            showExtensions();
        });

        //Formato del boton de NotificacionesK
        ImageView imageViewNoti = new ImageView(new Image("/bell-filled.png"));
        imageViewNoti.setFitWidth(iconSize);
        imageViewNoti.setFitHeight(iconSize);
        Button notificationsButton = new Button("Notificaciones", imageViewNoti);
        notificationsButton.getStyleClass().add("toolBar-button");
        notificationsButton.setOnAction(event -> {
            notificationsScene();
        });

        //Formato del boton de Tareas
        ImageView taskimageView = new ImageView(new Image("/clock-filled.png"));
        taskimageView.setFitWidth(iconSize);
        taskimageView.setFitHeight(iconSize);
        Button tasksButton = new Button("Tareas", taskimageView);
        tasksButton.setVisible(false);
        tasksButton.setOnAction(event -> {
            taskScene();
        });
        tasksButton.getStyleClass().add("toolBar-button");

        //Formato del boton de Usuario
        ImageView userimageView = new ImageView(new Image("/user-filled.png"));
        userimageView.setFitWidth(iconSize);
        userimageView.setFitHeight(iconSize);
        Button userButton = new Button("Usuario", userimageView);
        userButton.getStyleClass().add("toolBar-button");
        userButton.setOnAction(event -> {
            userScene();
        });
        BorderPane userPane = new BorderPane();
        userPane.setLeft(userButton);
        userPane.setMinHeight(10);
        toolBarPane.setCenter(toolBarContainer);
        toolBarPane.setBottom(userPane);

        //Se termina de configurar la barra de herramientas
        toolBarContainer.getChildren().addAll(homeButton, notificationsButton, tasksButton);
        toolBarPane.setStyle("-fx-background-color: FFFAFC; -fx-padding: 10;-fx-border-width: 0 1px 0 0;-fx-border-color: C1BBD6;");
        mainPane.setLeft(toolBarPane);

        //Formato de la barra de fecha y filtro
        dateAndFilterContianer = new VBox(2);

        //Contenedor de Fecha
        BorderPane dateContainer = new BorderPane();
        Label dateLabel = new Label(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "");
        dateLabel.setFont(Font.font("Open Sans", FontWeight.BOLD, 20));
        ImageView imageViewNoti2 = new ImageView(new Image("/bell-filled.png"));
        imageViewNoti2.setFitWidth(iconSize);
        imageViewNoti2.setFitHeight(iconSize);
        Button notificationButton2 = new Button("",imageViewNoti2);
        notificationButton2.getStyleClass().add("toolBar-button");
        notificationButton2.setOnAction(event -> {
            notificationsScene();
        });
        dateContainer.setMaxHeight(60);
        dateContainer.setMinHeight(60);
        dateContainer.setRight(notificationButton2);
        dateContainer.setLeft(dateLabel);
        dateContainer.setRight(notificationButton2);
        dateContainer.setStyle("-fx-padding: 0 10 0 14;-fx-border-width: 0 0 1px 0;-fx-border-color: C1BBD6;");
        BorderPane.setAlignment(dateLabel, Pos.CENTER);
        BorderPane.setAlignment(notificationButton2, Pos.CENTER);
        dateAndFilterContianer.getChildren().addAll(dateContainer);

        //Contenedor de Filtro y Busqueda
        HBox filterContainer = new HBox(10);
        filterContainer.setAlignment(Pos.CENTER);

        filterPane = new BorderPane();
        ImageView filterImageView = new ImageView(new Image("/filter-filled.png"));
        filterImageView.setFitWidth(30);
        filterImageView.setFitHeight(30);
        Button filterButton = new Button("Filtrar", filterImageView);
        filterButton.getStyleClass().add("toolBar-button");
        ImageView searchImageView = new ImageView(new Image("/search-line.png"));
        searchImageView.setFitWidth(30);
        searchImageView.setFitHeight(30);
        TextField searchTextField = new TextField();
        searchTextField.getStyleClass().add("search-textField");
        searchTextField.setPromptText("buscar...");
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchProyect(newValue);
        });

        Button newProyectButton = new Button("Nuevo");
        newProyectButton.setOnAction(event -> {
            createProyets();
        });
        newProyectButton.getStyleClass().add("hover-button");
        newProyectButton.setMinWidth(150);
        newProyectButton.setMaxHeight(20);
        filterContainer.setMaxHeight(60);
        filterContainer.setMinHeight(60);
        filterContainer.getChildren().addAll( searchImageView, searchTextField);
        filterPane.setLeft(filterContainer);
        filterPane.setRight(newProyectButton);
        filterPane.setStyle("-fx-padding: 0 10 0 10;");
        BorderPane.setAlignment(filterContainer, Pos.CENTER);
        BorderPane.setAlignment(newProyectButton, Pos.CENTER);
        dateAndFilterContianer.getChildren().addAll(filterPane);
        dateAndFilterContianer.setMinHeight(60);
        dateAndFilterContianer.setStyle("-fx-background-color: FFFAFC;");
        mainPaneCenter.setTop(dateAndFilterContianer);

        //Formato del panel de contenido
        contentMainPane.setStyle("-fx-padding: 10;");
        contentMainPane.setHgap(10);
        contentMainPane.setVgap(10);
        scrollMainPane = new ScrollPane(contentMainPane);
        scrollMainPane.getStyleClass().add("scroll-pane");
        mainPane.setStyle("-fx-background-color: C1BBD6;");
        contentPane.setCenter(scrollMainPane);
        mainPaneCenter.setCenter(contentPane);
        mainPane.setCenter(mainPaneCenter);


        mainRoot.getChildren().add(mainPane);
        mainScene = new Scene(mainRoot, 1280, 720);
        mainScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        mainStage.setScene(mainScene); // Cambiar a la nueva escena principal

        showExtensions();
    }

    private void mainScene() {
        if (dateAndFilterContianer.getChildren().size() == 1) {
            contentPane.setCenter(null);
            contentPane.setCenter(scrollMainPane);
            dateAndFilterContianer.getChildren().addAll(filterPane);
        }
    }
    /**
     * Modify the main Scene to show the User Configuration.
     */
    private void userScene(){
        clearContentPane();
        BorderPane userBackPane = new BorderPane();
        BorderPane userPane = new BorderPane();
        VBox userPaneLeft = new VBox(10);
        //Data from the TextFields and Labels
        Label userProfileLabel = new Label("Perfil de Usuario");
        userProfileLabel.setFont(Font.font("Open Sans", FontWeight.BOLD, 20));
        Label usernameLabel = new Label("Nombre de Usuario");
        usernameLabel.setFont(Font.font("Open Sans", FontWeight.NORMAL, 15));
        TextField usernameTextField = new TextField();
        usernameTextField.setText(user.getNombreUsuario());
        usernameTextField.setEditable(false);
        usernameTextField.getStyleClass().add("search-textField");
        usernameTextField.setPromptText("Username");
        Label mailLabel = new Label("Correo Electrónico");
        mailLabel.setFont(Font.font("Open Sans", FontWeight.NORMAL, 15));
        TextField mailTextField = new TextField();
        mailTextField.setText(user.getCorreo());
        mailTextField.setEditable(false);
        mailTextField.getStyleClass().add("search-textField");
        mailTextField.setPromptText("Correo");
        Label phoneNumberLabel = new Label("Número de Teléfono");
        phoneNumberLabel.setFont(Font.font("Open Sans", FontWeight.NORMAL, 15));
        TextField numberTextField = new TextField();
        numberTextField.setText(user.getNumeroTelefonico() + "");
        numberTextField.setEditable(false);
        numberTextField.getStyleClass().add("search-textField");
        numberTextField.setPromptText("Número");
        Button changePasswordButton = new Button("Cambiar Contraseña");
        changePasswordButton.setPrefSize(150,40);
        changePasswordButton.getStyleClass().add("hover-button");
        changePasswordButton.setOnAction(event -> {
            System.out.println("Función para cambiar la contraseña");
        });
        userPaneLeft.getChildren().addAll(usernameLabel, usernameTextField, mailLabel, mailTextField, phoneNumberLabel, numberTextField);
        userPane.setLeft(userPaneLeft);
        userPane.setRight(changePasswordButton);
        userPane.setTop(userProfileLabel);
        userPane.getStyleClass().add("user-panel");
        BorderPane.setAlignment(changePasswordButton, Pos.CENTER_LEFT);//Cambiar
        userBackPane.setStyle("-fx-background-color: C1BBD6; -fx-padding: 10;");
        userBackPane.setCenter(userPane);
        contentPane.setCenter(userBackPane);
    }

    private void taskScene(){
        clearContentPane();
        BorderPane tasksBackPane = new BorderPane();
        VBox taskPane = new VBox(10);
        //Data from the TextFields and Labels
        Label tasksLabel = new Label("Tareas");
        tasksLabel.setFont(Font.font("Open Sans", FontWeight.BOLD, 20));
        Label taskLabel = new Label("Se muestran las Tareas");
        taskLabel.setFont(Font.font("Open Sans", FontWeight.NORMAL, 15));
        taskPane.getChildren().addAll(tasksLabel, taskLabel);
        taskPane.getStyleClass().add("user-panel");
        tasksBackPane.setStyle("-fx-background-color: C1BBD6; -fx-padding: 10;");
        tasksBackPane.setCenter(taskPane);
        contentPane.setCenter(tasksBackPane);
    }

    private void notificationsScene(){
        clearContentPane();
        BorderPane notificationsBackPane = new BorderPane();
        VBox notificationsPane = new VBox(10);
        //Data from the TextFields and Labels
        Label notificationsLabel = new Label("Notificaciones");
        notificationsLabel.setFont(Font.font("Open Sans", FontWeight.BOLD, 20));
        Label notificationLabel = new Label("Se muestran las Notificaciones");
        notificationLabel.setFont(Font.font("Open Sans", FontWeight.NORMAL, 15));
        notificationsPane.getChildren().addAll(notificationsLabel, new NotificationsLine(user.getIdUsuario()));
        notificationsPane.getStyleClass().add("user-panel");
        notificationsBackPane.setStyle("-fx-background-color: C1BBD6; -fx-padding: 10;");
        notificationsBackPane.setCenter(notificationsPane);

        contentPane.setCenter(notificationsBackPane);
    }

    private void openRegistrationWindow(){
        RegistrationWindow registrationWindow = new RegistrationWindow();
        registrationWindow.show();
    }

    private void createProyets(){
        clearContentPane();
        VBox newProyectContainer = new VBox(10);
        newProyectContainer.setStyle("-fx-padding: 10; -fx-background-radius: 32; -fx-background-color: FFFAFC");
        Label newProyectLabel = new Label("Nuevo Proyecto");
        TextField proyectNameTF = new TextField();
        proyectNameTF.setPromptText("Nombre del Proyecto");
        TextField coordinatorTF = new TextField();
        coordinatorTF.setPromptText("Coordinador Asignado");
        coordinatorTF.setEditable(false);
        coordinatorTF.setText(user.getIdUsuario() + "");
        proyectType = new ComboBox<String>();
        proyectType.setPromptText("Tipo de Proyecto");
        proyectType.getItems().addAll("Proyecto de Investigación", "Proyecto Educativo", "Proyecto De Infraestructura", "Proyectos Social");
        proyectType.setOnAction(event -> {
            System.out.println(proyectType.getSelectionModel().getSelectedIndex());
        });
        Label startDateLabel = new Label("Fecha de Inicio");
        DatePicker startDate = new DatePicker();
        Label endDateLabel = new Label("Fecha de Finalización");
        DatePicker endDate = new DatePicker();
        TextField locationTF = new TextField();
        locationTF.setPromptText("Ubicación");
        TextField descriptionTF = new TextField();
        descriptionTF.setPromptText("Descripcion");
        Label BudgetLabel = new Label("Presupuesto Inicial");
        TextField budgetTF = new TextField();
        Button newProyectButton = new Button("Registrar");
        newProyectLabel.getStyleClass().add("title-label");
        proyectNameTF.getStyleClass().add("search-textField");
        coordinatorTF.getStyleClass().add("search-textField");
        startDateLabel.getStyleClass().add("date-label");
        startDate.getStyleClass().add("date-picker");
        endDateLabel.getStyleClass().add("sub-label");
        endDate.getStyleClass().add("date-picker");
        locationTF.getStyleClass().add("search-textField");
        descriptionTF.getStyleClass().add("search-textField");
        BudgetLabel.getStyleClass().add("sub-label");
        budgetTF.getStyleClass().add("search-textField");
        proyectType.getStyleClass().add("combo-box");
        newProyectButton.getStyleClass().add("hover-button");

        newProyectContainer.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        newProyectButton.setOnAction(event -> {
            try {
                // Crear cliente HTTP
                HttpClient client = HttpClient.newHttpClient();

                // Serializar los datos del proyecto
                String jsonData = "{" +
                        "\"ubicacion\": " + 1 + "," +
                        "\"nombre_de_la_extension\": \"" + (proyectNameTF.getText() != null ? proyectNameTF.getText() : "") + "\"," +
                        "\"fecha_De_Inicio\": \"" + (startDate.getValue() != null ? startDate.getValue().toString() : "") + "\"," +
                        "\"fecha_Estimada_De_Finalizacion\": \"" + (startDate.getValue() != null ? startDate.getValue().toString() : "") + "\"," +
                        "\"coordinador\": " + coordinatorTF.getText() + "," +
                        "\"TipoDeProyecto\": " + proyectType.getSelectionModel().getSelectedIndex() + "," +
                        "\"presupuesto\": " + (budgetTF.getText() != null ? budgetTF.getText() : "null") + "," +
                        "\"estado_Proyecto\": " + (1) + "," +
                        "\"extension\": " + actualExtensionID + "," +
                        "\"comentarios\": \"" + (descriptionTF.getText() != null ? descriptionTF.getText() : "") + "\"" +
                        "}";
                System.out.println(jsonData);

                // Crear la solicitud HTTP
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("http://localhost:8094/addProyecto")) // Cambia esta URL
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(jsonData, StandardCharsets.UTF_8))
                        .build();

                // Enviar la solicitud y manejar la respuesta
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200 || response.statusCode() == 201) { // 201 para creación exitosa
                    System.out.println("Proyecto creado exitosamente: " + response.body());
                    try {
                        String projectJson = response.body();
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
                        proyecto = mapper.readValue(projectJson, Proyecto.class);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mainScene();
                } else {
                    System.err.println("Error al crear el proyecto: " + response.statusCode());
                    mainScene();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        newProyectContainer.getChildren().addAll(newProyectLabel, proyectNameTF,coordinatorTF, proyectType,startDateLabel, startDate, endDateLabel,endDate,locationTF,descriptionTF,BudgetLabel, budgetTF, newProyectButton);
        contentPane.setCenter(newProyectContainer);
        contentPane.setStyle("-fx-padding: 10");
    }

    private void clearContentPane(){
        contentPane.setCenter(null);
        if (dateAndFilterContianer.getChildren().size() > 1) {
            dateAndFilterContianer.getChildren().remove(1);
        }
    }

    private void showProyects(int id){
        contentMainPane.getChildren().clear();

        try {
            // Crear cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Crear la solicitud HTTP para obtener los proyectos
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8094/ProyectoByIds/"+user.getIdUsuario() )) // URL de la API
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
                proyectos = mapper.readValue(jsonResponse, new TypeReference<List<Proyecto>>() {});

                // Recorrer la lista de proyectos e imprimir los detalles
                List<Button> proyectButtons = new ArrayList<>();
                for (Proyecto proyecto : proyectos) {
                    if(proyecto.getExtension() == id){
                        ProyectCard projectCard = new ProyectCard(
                                this::handleProyectCard,
                                proyecto.getIdProyecto(),
                                proyecto.getNombre_de_la_extension(), // Nombre del proyecto
                                proyecto.getEstado_Proyecto() + "", // Estado del proyecto
                                proyecto.getIdProyecto(), // Número de participantes
                                proyecto.getTipoDeProyecto(), // Número de facultades
                                proyecto.getIdProyecto(), // Número de carreras
                                (float) proyecto.getPresupuesto() // Presupuesto (convertido a float si es necesario)
                        );
                        contentMainPane.add(projectCard,counterx,countery);
                        counterx++;
                        if (counterx == 3){
                            counterx = 0;
                            countery++;
                        }
                    }
                }
                scrollMainPane.setContent(null);
                scrollMainPane.setContent(contentMainPane);
                counterx = 0;
                countery = 0;
            } else {
                System.err.println("Error al obtener los proyectos: Código de estado " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void showExtensions() {
        clearContentPane();
        scrollMainPane = new ScrollPane();
        try {
            // Crear cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Crear la solicitud HTTP para obtener los proyectos
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8094/extensions")) // URL de la API
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
                List<Extension> extensiones = mapper.readValue(jsonResponse, new TypeReference<List<Extension>>() {});

                // Recorrer la lista de proyectos e imprimir los detalles
                List<Button> proyectButtons = new ArrayList<>();
                extensionsMainPane.getChildren().clear();
                for (Extension extension : extensiones) {
                    ExtensionCard extensionCard = new ExtensionCard(
                            this::handleExtensionCard,
                            extension.getNombre_Extension(),
                            extension.getIdExtension()
                    );
                    extensionsMainPane.getChildren().add(extensionCard);
                }
                scrollMainPane.setContent(extensionsMainPane);
                contentPane.setCenter(scrollMainPane);
            } else {
                System.err.println("Error al obtener los proyectos: Código de estado " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        extensionsMainPane.setStyle("-fx-padding: 10");
        scrollMainPane.setFitToWidth(true);

    }

    private void manageProyect(String id){
        clearContentPane();
        System.out.println("Proyecto: "+id);
        proyectoPane = new BorderPane();
        Proyecto proyectoSeleccionado = new Proyecto();
        for (Proyecto proyecto : proyectos) {
            if(proyecto.getIdProyecto() == Integer.parseInt(id)){
                proyectoSeleccionado = proyecto;
            }
        }
        proyectoPane.setCenter(new ProyectTab(proyectoSeleccionado, user));
        contentPane.setCenter(proyectoPane);
    }

    private void handleProyectCard(String id) {
        manageProyect(id);
    }

    private void handleExtensionCard(String id) {
        showSearchPanel();
        actualExtensionID = Integer.parseInt(id);
        System.out.println("Extensión: " + id);
        extentionID = Integer.parseInt(id);
        showProyects(Integer.parseInt(id));
    }

    private void showSearchPanel(){
        dateAndFilterContianer.getChildren().add(filterPane);
    }

    private void searchProyect(String name) {
        contentMainPane.getChildren().clear();
        for (Proyecto proyecto : proyectos) {
            // Verificar que el nombre comience con la cadena proporcionada (ignorando mayúsculas/minúsculas)
            if (proyecto.getExtension() == extentionID &&
                    proyecto.getNombre_de_la_extension().toLowerCase().startsWith(name.toLowerCase())) {

                ProyectCard projectCard = new ProyectCard(
                        this::handleProyectCard,
                        proyecto.getIdProyecto(),
                        proyecto.getNombre_de_la_extension(), // Nombre del proyecto
                        proyecto.getEstado_Proyecto() + "",  // Estado del proyecto
                        proyecto.getIdProyecto(),           // Número de participantes
                        proyecto.getTipoDeProyecto(),       // Número de facultades
                        proyecto.getIdProyecto(),           // Número de carreras
                        (float) proyecto.getPresupuesto()   // Presupuesto (convertido a float si es necesario)
                );

                contentMainPane.add(projectCard, counterx, countery);
                counterx++;
                if (counterx == 3) {
                    counterx = 0;
                    countery++;
                }
            }
        }

        scrollMainPane.setContent(null);
        scrollMainPane.setContent(contentMainPane);
        counterx = 0;
        countery = 0;

        if (contentMainPane.getChildren().isEmpty()) {
            Label emptyProyect = new Label("No existen proyectos");
            contentMainPane.getChildren().add(emptyProyect);
        }
    }

    public static void main(String[] args) {
        launch(args);  // Inicia la aplicación JavaFX
    }
}
