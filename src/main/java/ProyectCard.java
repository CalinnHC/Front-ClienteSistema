import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.function.Consumer;

public class ProyectCard extends BorderPane {
    private int proyectID;
    private String proyectName;
    private String state;
    private int participantsCount;
    private int facultiesCount;
    private int careersCount;
    private float budget;
    public ProyectCard(Consumer<String> onButtonClicked, int proyectID, String proyectName, String state, int participantsCount, int facultiesCount, int careersCount, float budget) {
        this.setPrefSize(305,370);
        this.proyectID = proyectID;
        this.proyectName = proyectName;
        this.state = state;
        this.participantsCount = participantsCount;
        this.facultiesCount = facultiesCount;
        this.careersCount = careersCount;
        this.budget = budget;

        Label proyectNameLabel = new Label(this.proyectName);
        proyectNameLabel.getStyleClass().add("card-proyect-name");
        proyectNameLabel.setFont(Font.font("Open Sans", FontWeight.BOLD, 20));
        BorderPane.setAlignment(proyectNameLabel, Pos.CENTER);
        this.setTop(proyectNameLabel);
        BorderPane cardData = new BorderPane();
        VBox cardDataL = new VBox();
        VBox cardDataR = new VBox();
        Label stateLabel = new Label("Estado");
        Label participantsLabel = new Label("Participantes");
        Label facultiesLabel = new Label("Facultades");
        Label careersLabel = new Label("Carreras");
        Label budgetLabel = new Label("Presupuesto");
        Label stateLabelData = new Label(this.state);
        Label participantsLabelData = new Label(this.participantsCount + "");
        Label facultiesLabelData = new Label(this.facultiesCount + "");
        Label careersLabelData = new Label(this.careersCount + "");
        Label budgetLabelData = new Label(this.budget + "");
        cardDataL.getChildren().addAll(stateLabel,participantsLabel, facultiesLabel, careersLabel, budgetLabel);
        cardDataL.setSpacing(25);
        cardDataR.setSpacing(25);
        cardDataR.setAlignment(Pos.CENTER_RIGHT);
        cardData.getStyleClass().add("cardData-panel");
        cardDataR.getChildren().addAll(stateLabelData, participantsLabelData, facultiesLabelData, careersLabelData, budgetLabelData);
        cardData.setLeft(cardDataL);
        cardData.setRight(cardDataR);
        this.setCenter(cardData);
        Button manageButton = new Button("Gestionar");
        manageButton.setOnAction(event -> {
            if (onButtonClicked != null) {
                onButtonClicked.accept(this.proyectID + ""); // Notifica el evento al callback
            }
        });
        BorderPane.setAlignment(manageButton, Pos.CENTER);
        manageButton.setPrefSize(150,40);
        manageButton.getStyleClass().add("hover-button");
        this.getStyleClass().add("card-panel");
        this.setBottom(manageButton);
        this.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
    }
}
