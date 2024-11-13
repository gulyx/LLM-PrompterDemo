package it.cnr.iasi.saks.llmPrompterDemo;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.testcontainers.containers.GenericContainer;

public class OllamaChatAnotherExample {



  public static void main(String[] args) {
    // The model name to use (e.g., "orca-mini", "mistral", "llama2", "codellama", "phi", or
    // "tinyllama")
    String modelName = "llama";
    // String modelVersion = "latest";
    String modelVersion = "3.1";
    int ollamaPort = 11434;

    // Create and start the Ollama container
    GenericContainer<?> ollama = new GenericContainer<>("langchain4j/ollama-" + modelName + ":" + modelVersion)
            .withExposedPorts(ollamaPort);

    ollama.start();

    // Build the ChatLanguageModel
    ChatLanguageModel model =
        OllamaChatModel.builder().baseUrl(baseUrl(ollama)).modelName(modelName).build();

    // Example usage
    String answer = model.generate("Provide 3 short bullet points explaining why Java is awesome");
    System.out.println(answer);

    // Stop the Ollama container
    ollama.stop();
  }

  private static String baseUrl(GenericContainer<?> ollama) {
    return String.format("http://%s:%d", ollama.getHost(), ollama.getFirstMappedPort());
  }
}
