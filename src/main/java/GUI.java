import Entity.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

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
    }

    /**
     * Login Scene Configuration.,
     */
    private void setupLoginScene() {
        //Login Label Configuration
        Label loginLabel = new Label("INICIAR SESIÓN");
        loginLabel.setFont(Font.font("Open Sans", FontWeight.BOLD, 30));

        //Username TextField Config
        usernametextField = new TextField();
        usernametextField.setPromptText("Username");
        usernametextField.setMinHeight(40);
        usernametextField.setStyle("-fx-background-color: FFFAFC; -fx-background-radius: 20;-fx-padding: 0 15 0 15;");


        //Password TextField Config
        passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña");
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
        loginContainer.getChildren().addAll(loginLabel, usernametextField, passwordField, loginButton, registerContainer);
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
        usernametextField.getText();
        passwordField.getText();
        try {
            // Crear cliente HTTP
            HttpClient client = HttpClient.newHttpClient();
            // Serializar los datos
            String jsonData = "{" +
                    "\"nombreUsuario\": \"" + (usernametextField.getText() != null ? usernametextField.getText() : "") + "\"," +
                    "\"contrasena\": \"" + passwordField.getText() + "\"" +
                    "}";
            System.out.println(jsonData);


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
                    // Prueba el resultado
                    System.out.println("Nombre: " + user.getNombre());
                    System.out.println("Correo: " + user.getCorreo());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setupMainScene();
            } else {
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
    private void setupMainScene() {
        GridPane contentMainPane = new GridPane(3,2);
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
        ImageView imageViewHome = new ImageView(new Image("/home-filled (1).png"));
        imageViewHome.setFitWidth(iconSize);
        imageViewHome.setFitHeight(iconSize);
        Button homeButton = new Button("Inicio", imageViewHome);
        homeButton.getStyleClass().add("toolBar-button");
        homeButton.setOnAction(event -> {
            mainScene();
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
        Label dateLabel = new Label("13 de octubre de 2024");
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
        Button newActivityButton = new Button("Nuevo");
        newActivityButton.setOnAction(event -> {
            ProyectCard Proyecto1 = new ProyectCard("Extensión Chilibre", "Aprobado", 523,3,10,1000000);
            contentMainPane.add(Proyecto1,counterx,countery);
            counterx++;
            if (counterx == 3){
                counterx = 0;
                countery++;
            }
        });
        newActivityButton.getStyleClass().add("hover-button");
        newActivityButton.setMinWidth(150);
        newActivityButton.setMaxHeight(20);
        filterContainer.setMaxHeight(60);
        filterContainer.setMinHeight(60);
        filterContainer.getChildren().addAll(filterButton, searchImageView, searchTextField);
        filterPane.setLeft(filterContainer);
        filterPane.setRight(newActivityButton);
        filterPane.setStyle("-fx-padding: 0 10 0 0;");
        BorderPane.setAlignment(filterContainer, Pos.CENTER);
        BorderPane.setAlignment(newActivityButton, Pos.CENTER);
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
        contentPane.setCenter(null);
        System.out.println(dateAndFilterContianer.getChildren().size());
        if (dateAndFilterContianer.getChildren().size() > 1) {
            dateAndFilterContianer.getChildren().remove(1);
        }
        BorderPane userBackPane = new BorderPane();
        BorderPane userPane = new BorderPane();
        VBox userPaneLeft = new VBox(10);
        //Data from the TextFields and Labels
        Label userProfileLabel = new Label("Perfil de Usuario");
        userProfileLabel.setFont(Font.font("Open Sans", FontWeight.BOLD, 20));
        Label usernameLabel = new Label("Nombre de Usuario");
        usernameLabel.setFont(Font.font("Open Sans", FontWeight.NORMAL, 15));
        TextField usernameTextField = new TextField();
        usernameTextField.getStyleClass().add("search-textField");
        usernameTextField.setPromptText("Username");
        Label mailLabel = new Label("Correo Electrónico");
        mailLabel.setFont(Font.font("Open Sans", FontWeight.NORMAL, 15));
        TextField mailTextField = new TextField();
        mailTextField.getStyleClass().add("search-textField");
        mailTextField.setPromptText("Correo");
        Label phoneNumberLabel = new Label("Número de Teléfono");
        phoneNumberLabel.setFont(Font.font("Open Sans", FontWeight.NORMAL, 15));
        TextField numberTextField = new TextField();
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
        contentPane.setCenter(null);
        if (dateAndFilterContianer.getChildren().size() > 1) {
            dateAndFilterContianer.getChildren().remove(1);
        }
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
        contentPane.setCenter(null);
        if (dateAndFilterContianer.getChildren().size() > 1) {
            dateAndFilterContianer.getChildren().remove(1);
        }
        BorderPane notificationsBackPane = new BorderPane();
        VBox notificationsPane = new VBox(10);
        //Data from the TextFields and Labels
        Label notificationsLabel = new Label("Notificaciones");
        notificationsLabel.setFont(Font.font("Open Sans", FontWeight.BOLD, 20));
        Label notificationLabel = new Label("Se muestran las Notificaciones");
        notificationLabel.setFont(Font.font("Open Sans", FontWeight.NORMAL, 15));
        notificationsPane.getChildren().addAll(notificationsLabel, notificationLabel);
        notificationsPane.getStyleClass().add("user-panel");
        notificationsBackPane.setStyle("-fx-background-color: C1BBD6; -fx-padding: 10;");
        notificationsBackPane.setCenter(notificationsPane);
        contentPane.setCenter(notificationsBackPane);
    }

    private void openRegistrationWindow(){
        RegistrationWindow registrationWindow = new RegistrationWindow();
        registrationWindow.show();
    }

    public static void main(String[] args) {
        launch(args);  // Inicia la aplicación JavaFX
    }
}
