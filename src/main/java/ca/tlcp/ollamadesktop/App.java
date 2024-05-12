package ca.tlcp.ollamadesktop;

import io.github.amithkoujalgi.ollama4j.core.OllamaAPI;
import io.github.amithkoujalgi.ollama4j.core.exceptions.OllamaBaseException;
import io.github.amithkoujalgi.ollama4j.core.types.OllamaModelType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URISyntaxException;

public class App extends Application {
    public static OllamaAPI AI_API = new OllamaAPI("http://localhost:11434/");;
    @Override
    public void start(Stage stage) throws IOException, OllamaBaseException, URISyntaxException, InterruptedException {
        AI_API.setVerbose(true);
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("AI Desktop");
        stage.setScene(scene);
//        stage.setMaximized(true);
        stage.show();
    }

    public static void run() {
        launch();
    }

}