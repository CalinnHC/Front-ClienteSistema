import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
    }
    public void showParticipants(){
        proyectContent.setCenter(null);
        proyectContent.setCenter(participantsVBox);
    }
    public void showActivities(){
        proyectContent.setCenter(null);
        proyectContent.setCenter(activitiesVBox);
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
