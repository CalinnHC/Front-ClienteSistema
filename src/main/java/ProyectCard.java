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
    private String proyectType;
    private int careersCount;
    private float budget;
    public ProyectCard(Consumer<String> onButtonClicked, int proyectID, String proyectName, String state, int participantsCount, int proyectType, int careersCount, float budget) {
        this.setPrefSize(305,370);
        this.proyectID = proyectID;
        this.proyectName = proyectName;
        this.state = state;
        this.participantsCount = participantsCount;
        this.proyectType = proyectTypeToString(proyectType);
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
        Label proyectTypeLabel = new Label("Tipo de Proyecto");
        Label activitiesLabel = new Label("Actividades");
        Label budgetLabel = new Label("Presupuesto");
        Label stateLabelData = new Label(estadoToString(Integer.parseInt(this.state)));
        Label participantsLabelData = new Label("???");
        Label projectTypeLabelData = new Label(this.proyectType);
        Label activitiesLabelData = new Label("???");
        Label budgetLabelData = new Label(this.budget + "");
        cardDataL.getChildren().addAll(stateLabel,participantsLabel, proyectTypeLabel, activitiesLabel, budgetLabel);
        cardDataL.setSpacing(25);
        cardDataR.setSpacing(25);
        cardDataR.setAlignment(Pos.CENTER_RIGHT);
        cardDataL.setAlignment(Pos.CENTER_LEFT);
        cardData.getStyleClass().add("cardData-panel");
        cardDataR.getChildren().addAll(stateLabelData, participantsLabelData, projectTypeLabelData, activitiesLabelData, budgetLabelData);
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

    private String proyectTypeToString(int proyectType) {
        switch (proyectType) {
            case 0:
                return "De Investigación";
                case 1:
                    return "Educativo";
                    case 2:
                        return "De Infraestructura";
                        case 3:
                            return "Social";
                            default:
                                return "";
        }
    }

    private String estadoToString(int estado) {
        switch (estado) {
            case 0:
                return "En espera";
                case 1:
                    return "Iniciado";
                    case 2:
                        return "Finalizado";
            default:
                return "";
        }
    }
}
