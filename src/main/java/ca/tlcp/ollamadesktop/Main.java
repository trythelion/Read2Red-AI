package ca.tlcp.ollamadesktop;

public class Main {

    public static void main(String[] args) {
        if (App.AI_API.ping()) {
            App.run();
        } else {
            throw new RuntimeException("Failed to connect to the Ollama API");
        }
    }

}
