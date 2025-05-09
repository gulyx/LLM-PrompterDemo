package it.cnr.iasi.saks.llmPrompterDemo;

// This interaction model is not supported anymore.
// In order to use it, enable older versions of langchain4j, like ver. 0.35
// import dev.langchain4j.model.chat.ChatLanguageModel;

import dev.langchain4j.model.ollama.OllamaChatModel;

import java.time.Duration;

// import it.cnr.iasi.saks.langchain4j.ollama-examples.utils;

public class OllamaChatExample {


   public static void main(String[] args) {
   
    String OLLAMA_BASE_URL = "http://localhost:11434";
       
     // The model name to use (e.g., "orca-mini", "mistral", "llama2", "codellama", "phi", or
     // "tinyllama")
//     String modelName = "llama3.1";
     String modelName = "llama3.2";
     String modelVersion = "latest";
     // String modelVersion = "3.1";

     // Build the ChatLanguageModel
     OllamaChatModel model = OllamaChatModel.builder()
	                       .baseUrl(OLLAMA_BASE_URL)
	                       .modelName(modelName)
	                       .temperature(0.8)
	                       .timeout(Duration.ofSeconds(300))
	                       .build();
     

     // Example usage -- This interaction model is not supported anymore. In order to use it, enable older versions of langchain4j, like ver. 0.35
//     String answer = model.generate("What is your Name?");
//     System.out.println(answer);
//     answer = model.generate("Provide 3 short bullet points explaining why Java is awesome");
//     System.out.println(answer);

// Possibly the current way to interact with Ollama
     String answer2 = model.chat("Provide a bullet list with 3 items on forunes");
     System.out.println(answer2);
   }

}
