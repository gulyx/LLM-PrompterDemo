package it.cnr.iasi.saks.llmPrompterDemo;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

import java.time.Duration;
    
public class OllamaChatExample {

   public static void main(String[] args) {
     // The model name to use (e.g., "orca-mini", "mistral", "llama2", "codellama", "phi", or
     // "tinyllama")
     String modelName = "llama3.1";
     String modelVersion = "latest";
     // String modelVersion = "3.1";

     // Build the ChatLanguageModel
     OllamaChatModel model = OllamaChatModel.builder()
	                       .baseUrl("http://localhost:11434")
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
