package it.cnr.iasi.saks.llmPrompterDemo;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

import java.time.Duration;
    
public class OllamaChatExample {

 /**
     * If you have Ollama running locally,
     * please set the OLLAMA_BASE_URL environment variable (e.g., http://localhost:11434).
     * If you do not set the OLLAMA_BASE_URL environment variable,
     * Testcontainers will download and start Ollama Docker container.
     * It might take a few minutes.
     */

   public static void main(String[] args) {
   
    System.setProperty("OLLAMA_BASE_URL", "http://localhost:11434");
       
     // The model name to use (e.g., "orca-mini", "mistral", "llama2", "codellama", "phi", or
     // "tinyllama")
     String modelName = "llama3.1";
     String modelVersion = "latest";
     // String modelVersion = "3.1";

     // Build the ChatLanguageModel
     OllamaChatModel model = OllamaChatModel.builder()
	                       .baseUrl("ollama")
	                       .modelName(modelName)
	                       .temperature(0.8)
	                       .timeout(Duration.ofSeconds(300))
	                       .build();
     
 
     // Example usage
//     String answer = model.generate("Provide 3 short bullet points explaining why Java is awesome");
     String answer = model.generate("What is your Name?");
     System.out.println(answer);
   }

}
