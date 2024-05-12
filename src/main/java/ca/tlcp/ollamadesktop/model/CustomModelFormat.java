package ca.tlcp.ollamadesktop.model;

import io.github.amithkoujalgi.ollama4j.core.models.Model;

public class CustomModelFormat {

    public static String modelFormat(Model model) {
        return model.getName().split(":")[0];
    }
}
