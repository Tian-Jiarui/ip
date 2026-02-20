package valencia.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.animation.PauseTransition;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Platform;
import valencia.Valencia;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Valencia valencia;

    private final Image userImage = new Image(
            getClass().getResourceAsStream("/images/speed.png")
    );

    private final Image valenciaImage = new Image(
            getClass().getResourceAsStream("/images/galgadot.png")
    );

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setValencia(Valencia v) {
        valencia = v;
        showWelcome();
    }

    private void showWelcome() {
        dialogContainer.getChildren().add(
                DialogBox.getValenciaDialog(
                        "Hello! I'm Valencia.\nWhat can I do for you?",
                        valenciaImage,
                        "Other"
                )
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Valencia's reply,
     * and then appends them to the dialog container.
     * Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = valencia.getResponse(input);
        String commandType = valencia.getCommandType();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getValenciaDialog(response, valenciaImage, commandType)
        );
        userInput.clear();

        if (input.equalsIgnoreCase("bye")) {
            userInput.setDisable(true);
            sendButton.setDisable(true);

            PauseTransition delay = new PauseTransition(Duration.seconds(0.6));
            delay.setOnFinished(e -> {
                Stage stage = (Stage) dialogContainer.getScene().getWindow();
                stage.close();
                Platform.exit();
            });
            delay.play();
        }
    }
}
