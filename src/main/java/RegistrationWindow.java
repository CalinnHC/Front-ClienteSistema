import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RegistrationWindow extends Stage {
    TextField usernameField;
    TextField nameTF;
    TextField lastnameTF;
    DatePicker birthdatePicker;
    TextField idTF;
    TextField passwordTF;
    TextField emailTF;
    TextField phoneTF;
    LocalDate fechaHoy;

    public RegistrationWindow() {
        // Crear los controles de la ventana de registro
        Label registrationLabel = new Label("Nuevo Usuario del Sistema");
        usernameField = new TextField();
        usernameField.setPromptText("Ingrese su nombre de usuario");
        nameTF = new TextField();
        nameTF.setPromptText("Nombre de Pila");
        lastnameTF = new TextField();
        lastnameTF.setPromptText("Apellido");
        Label birthdateLabel = new Label("Fecha de Nacimiento");
        birthdatePicker = new DatePicker();
        birthdatePicker.setPromptText("Seleccione su fecha de nacimiento");
        idTF = new TextField();
        idTF.setPromptText("Cédula");
        passwordTF = new TextField();
        passwordTF.setPromptText("Contraseña");
        emailTF = new TextField();
        emailTF.setPromptText("Correo electrónico");
        phoneTF = new TextField();
        phoneTF.setPromptText("Número de Telefono");
        Button registrationButton = new Button("Registrarse");
        registrationButton.setOnAction(e -> {
            enviarDatos();
        });

        // Layout para los controles del formulario de registro
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(registrationLabel,usernameField,nameTF,lastnameTF,birthdateLabel,birthdatePicker,idTF,passwordTF,emailTF,phoneTF, registrationButton);

        // Crear una escena para la ventana de registro
        Scene registrationScene = new Scene(vbox, 600, 550);

        // Configurar la nueva ventana
        this.setTitle("Registro");
        this.setScene(registrationScene);
    }

    private void enviarDatos() {
        try {
            // Crear cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Serializar los datos
            String jsonData = convertirAJSON();
            System.out.println(jsonData);


            // Crear la solicitud HTTP
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8094/addUsuario")) // Cambia esta URL
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonData, StandardCharsets.UTF_8))
                    .build();

            // Enviar la solicitud y manejar la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Registro enviado con éxito: " + response.body());
                this.close();
            } else {
                System.err.println("Error al enviar registro: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String convertirAJSON() {
        // Formatear la fecha actual y la fecha de nacimiento
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        fechaHoy = LocalDate.now();
        String actualDate = fechaHoy.format(dateFormatter);
        // Obtener y formatear la fecha de nacimiento seleccionada
        String birthdate = birthdatePicker.getValue() != null ? birthdatePicker.getValue().format(dateFormatter) : "";

        return "{" +
                "\"idUsuario\": " + idTF.getText() + "," +
                "\"nombreUsuario\": \"" + (usernameField.getText() != null ? usernameField.getText() : "") + "\"," +
                "\"idRol\": " + 2 + "," +
                "\"correo\": \"" + (emailTF.getText() != null ? emailTF.getText() : "") + "\"," +
                "\"nombre\": \"" + nameTF.getText() + "\"," +
                "\"apellido\": \"" + lastnameTF.getText() + "\"," +
                "\"contrasena\": \"" + passwordTF.getText() + "\"," +
                "\"numeroTelefonico\": " + phoneTF.getText() + "," +
                "\"nacimiento\": \"" + birthdate + "\"," +
                "\"ultimoInicioDeSesion\": \"" + actualDate + "\"" +
                "}";
    }
}
