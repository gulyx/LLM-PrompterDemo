package it.cnr.iasi.saks.llmPrompterDemo;

import java.io.IOException;
import java.time.Duration;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import it.cnr.iasi.saks.llmPrompter.Prompter;
import it.cnr.iasi.saks.llmPrompter.impl.DescriptionExamplesPrompter;
import it.cnr.iasi.saks.llmPrompter.impl.DescriptionOnlyPrompter;

public class GenerationDriverOllama {

	private static String OLLAMA_BASE_URL = "http://localhost:11434";
    private static String llmName = "llama3.2";
    private static String llmVersion = "latest";

	public static void main (String args[]) {
		
	    ChatModel llm = OllamaChatModel.builder()
                .baseUrl(OLLAMA_BASE_URL)
                .modelName(llmName)
                .temperature(0.8)
                .timeout(Duration.ofSeconds(300))
                .build();
	    String[] items = {"1382", "2375", "1072", "3324", "1641"};
	    for (String id : items) {
//			Prompter p = new DescriptionOnlyPrompter(id, 5, llm);
			Prompter p = new DescriptionExamplesPrompter(id, 5, llm);
			
			String prompt = p.composePrompt();
			System.out.println("Querying LLM for id:" + id + " ...");
			p.queryLLM(prompt);
			System.out.println("... done");
			try {
				p.saveCurrentResponse();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
