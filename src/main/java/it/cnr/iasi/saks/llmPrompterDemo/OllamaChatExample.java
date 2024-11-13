package it.cnr.iasi.saks.llmPrompterDemo;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
// import org.testcontainers.ollama.OllamaContainer;
// import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
    
public class OllamaChatExample {

   public static void main(String[] args) {
     // The model name to use (e.g., "orca-mini", "mistral", "llama2", "codellama", "phi", or
     // "tinyllama")
     String modelName = "llama3.1";
     String modelVersion = "latest";
     // String modelVersion = "3.1";
// 
//     // Create and start the Ollama container
//     OllamaContainer ollama =
//         new OllamaContainer(DockerImageName.parse("langchain4j/ollama-" + modelName + ":" + modelVersion)
//             .asCompatibleSubstituteFor("ollama/ollama"));

//     ollama.start();
// 
//     // Build the ChatLanguageModel
//     ChatLanguageModel model =
//         OllamaChatModel.builder().baseUrl(baseUrl(ollama)).modelName(modelName).build();

OllamaChatModel model = OllamaChatModel.builder()
    .baseUrl("http://localhost:11434")
    .modelName(modelName)
    .temperature(0.8)
    .timeout(Duration.ofSeconds(300))
    .build();
     

// 
//     // Example usage
//     String answer = model.generate("Provide 3 short bullet points explaining why Java is awesome");
     String answer = model.generate("What is your Name?");
     System.out.println(answer);
// 
//     // Stop the Ollama container
//     ollama.stop();
   }

//   private static String baseUrl(GenericContainer<?> ollama) {
//     return String.format("http://%s:%d", ollama.getHost(), ollama.getFirstMappedPort());
//   }
}
