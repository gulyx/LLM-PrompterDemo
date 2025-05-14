package it.cnr.iasi.saks.llmPrompter;

import java.io.IOException;

public class DummyPrompter extends Prompter {

	public static final String SUFFIX = "DummyPrompt"; 
	
	public DummyPrompter(String problemID) {
		super(problemID);
	}

	@Override
	public String composePrompt() {
		this.loadDescription();
		this.loadCPP();
		this.loadJava();
		this.loadPython();
		this.loadExamples();
		
		String prompt = this.description + "\n";
		for (String item : this.examples) {
			prompt = prompt.concat("\n" + item);
		}
		prompt = prompt.concat("\n" + this.implementationCPP);
		prompt = prompt.concat("\n" + this.implementationJava);
		prompt = prompt.concat("\n" + this.implementationPython);
		
		return prompt; 
	}

	@Override
	public String queryLLM(String prompt) {
		this.response = prompt;
		return this.response;
	}
	
	@Override
	public void saveCurrentResponse() throws IOException {
		this.saveCurrentResponse(SUFFIX);
	}

}
