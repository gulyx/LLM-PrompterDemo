package it.cnr.iasi.saks.llmPrompter.impl;

import java.io.IOException;

import dev.langchain4j.model.chat.ChatModel;
import it.cnr.iasi.saks.llmPrompter.Prompter;

public class DescriptionOnlyPrompter extends Prompter {

	public static final String SUFFIX = "DescriptionOnly";

	public DescriptionOnlyPrompter(String problemID) {
		super(problemID);
	}

	public DescriptionOnlyPrompter(String problemID, int numberOfTests) {
		super(problemID, numberOfTests);
	}

	public DescriptionOnlyPrompter(String problemID, int numberOfTests, ChatModel llm) {
		super(problemID, numberOfTests, llm);
	}

	@Override
	public String composePrompt() {
		this.loadDescription();

		// You may improve this by looking at this example:
		// https://github.com/langchain4j/langchain4j-examples/blob/8c6870202e7c9be333ec50e04397042bd65d5d69/tutorials/src/main/java/_03_PromptTemplate.java#L28
		String testFileName = this.problemID + "_" + SUFFIX;
		String prompt = "As a professional Software Tester, generate a complete Junit class test file (named "
				+ testFileName + " in the package " + TARGET_PACKAGE + ") with " + this.numberOfTests
				+ " different JUnit tests for the following description in natural language. Give back only the Junit test class, no other informations. Here is the description: ";

		prompt = prompt.concat("\n" + this.description);

		return prompt;
	}

	@Override
	public void saveCurrentResponse() throws IOException {
		this.saveCurrentResponse(SUFFIX);
	}

}
