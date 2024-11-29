import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.function.Consumer;

public class ExtensionCard extends BorderPane{
    private String extensionName;
    private int extensionID;
    public ExtensionCard(Consumer<String> onButtonClicked, String extensionName, int extensionID) {
        this.extensionName = extensionName;
        this.extensionID = extensionID;
        System.out.println("Contruyendo");
        Label extensionNameLabel = new Label(this.extensionName);
        extensionNameLabel.getStyleClass().add("card-proyect-name");
        extensionNameLabel.setFont(Font.font("Open Sans", FontWeight.BOLD, 20));
        BorderPane.setAlignment(extensionNameLabel, Pos.CENTER_LEFT);
        this.setLeft(extensionNameLabel);
        Button manageButton = new Button("Gestionar");
        manageButton.setOnAction(event -> {
            if (onButtonClicked != null) {
                onButtonClicked.accept(this.extensionID + ""); // Notifica el evento al callback
            }
        });
        BorderPane.setAlignment(manageButton, Pos.CENTER_RIGHT);
        manageButton.setPrefSize(150,40);
        manageButton.getStyleClass().add("hover-button");
        this.getStyleClass().add("extensionCard-panel");
        this.setRight(manageButton);
        this.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
    }

}
