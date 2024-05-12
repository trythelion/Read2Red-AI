package ca.tlcp.ollamadesktop;

import ca.tlcp.ollamadesktop.model.CustomModelFormat;
import io.github.amithkoujalgi.ollama4j.core.OllamaStreamHandler;
import io.github.amithkoujalgi.ollama4j.core.exceptions.OllamaBaseException;
import io.github.amithkoujalgi.ollama4j.core.models.Model;
import io.github.amithkoujalgi.ollama4j.core.models.OllamaAsyncResultCallback;
import io.github.amithkoujalgi.ollama4j.core.models.OllamaResult;
import io.github.amithkoujalgi.ollama4j.core.types.OllamaModelType;
import io.github.amithkoujalgi.ollama4j.core.utils.OptionsBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {
    @FXML
    private TextArea input;
    @FXML
    private Text output;
    @FXML
    private ListView<String> models;
    @FXML
    private ButtonBar bbar;
    private List<File> files = new ArrayList<>();
    private OllamaResult result;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        result = null;
        try {
            for (Model model : App.AI_API.listModels()) {
                models.getItems().add(CustomModelFormat.modelFormat(model));
            }
        } catch (IOException | InterruptedException | URISyntaxException | OllamaBaseException e) {
        }
        output.setWrappingWidth(1000);
        App.AI_API.setRequestTimeoutSeconds(6000);
    }
    public void clearFiles() {
        files.clear();
    }

    public void addFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        for (File file : fileChooser.showOpenMultipleDialog(models.getScene().getWindow())) {
            files.add(file);
        }
    }
    public void showOutput() {
        String prompt = input.getText();
        if (models.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error: No model Selected");
            alert.setContentText("Please select a model to run the AI.");
            alert.show();
            return;
        }
        input.clear();
        bbar.setDisable(true);
        output.setText("Running " + models.getSelectionModel().getSelectedItem());
        TimerTask task = new TimerTask() {
            public void run() {
                try {
                    runAI(prompt);
                } catch (OllamaBaseException | IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 5000);
    }

    private void runAI(String input) throws OllamaBaseException, IOException, InterruptedException {
        String prompt = "Do your job with the following passage. output what is expected of you. \n" +  input;
        if (files != null) {
            result = App.AI_API.generateWithImageFiles(models.getSelectionModel().getSelectedItem(), prompt, files, new OptionsBuilder().build());
        } else {
            result = App.AI_API.generate(models.getSelectionModel().getSelectedItem(), prompt , new OptionsBuilder().build());
        }
        output.setText(result.getResponse());

        bbar.setDisable(false);
    }

}