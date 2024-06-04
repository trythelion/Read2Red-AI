package ca.tlcp.ollamadesktop.model;

import ca.tlcp.ollamadesktop.App;
import io.github.amithkoujalgi.ollama4j.core.OllamaAPI;
import io.github.amithkoujalgi.ollama4j.core.exceptions.OllamaBaseException;
import io.github.amithkoujalgi.ollama4j.core.models.OllamaResult;
import io.github.amithkoujalgi.ollama4j.core.utils.OptionsBuilder;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AI {

    static OllamaAPI API = new OllamaAPI("http://localhost:11434/");

    public void showOutput(String prompt) {
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

    static OllamaResult runAI(String input, List<File> files) throws OllamaBaseException, IOException, InterruptedException {
        String prompt = "Do your job with the following passage.You are restricted to making the modifications only to the word \"read\".\n" +  input;
        return App.AI_API.generateWithImageFiles("read2red", prompt, files, new OptionsBuilder().build());
    }

    static OllamaResult runAI(String input) throws OllamaBaseException, IOException, InterruptedException {
        String prompt = "Do your job with the following passage.You are restricted to making the modifications only to the word \"read\".\n" +  input;
        return App.AI_API.generate("read2red", prompt , new OptionsBuilder().build());
    }

}
