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
    private HBox proyectButtons = new HBox();
    private VBox buttonsVBox = new VBox();
    private VBox activitiesVBox = new VBox();
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
        Label activitiesLabel = new Label("No hay actividades pendientes...");
        activitiesLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        activitiesVBox.getChildren().addAll(activitiesLabel);
        this.setTop(buttonsVBox);
        this.setCenter(activitiesVBox);
    }



}
