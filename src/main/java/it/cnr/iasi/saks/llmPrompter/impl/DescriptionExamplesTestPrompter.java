/* 
 * This file is part of the LLM-PrompterDemo project.
 * 
 * LLM-PrompterDemo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * LLM-PrompterDemo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with LLM-PrompterDemo.  If not, see <https://www.gnu.org/licenses/>
 *
 */
package it.cnr.iasi.saks.llmPrompter.impl;

import java.io.IOException;

import dev.langchain4j.model.chat.ChatModel;
import it.cnr.iasi.saks.llmPrompter.Prompter;

public class DescriptionExamplesTestPrompter extends Prompter {

	public static final String SUFFIX = "DescriptionExample_tests";
	public static final String TAG_EXAMPLE = "[--Example XX--]";

	public DescriptionExamplesTestPrompter(String problemID) {
		super(problemID);

		this.loadDescription();
		this.loadExamples();
	}

	public DescriptionExamplesTestPrompter(String problemID, int numberOfTests) {
		super(problemID, numberOfTests);

		this.loadDescription();
		this.loadExamples();
	}

	public DescriptionExamplesTestPrompter(String problemID, int numberOfTests, ChatModel llm) {
		super(problemID, numberOfTests, llm);

		this.loadDescription();
		this.loadExamples();
	}

	@Override
	public String composePrompt() {
		// You may improve this by looking at this example:
		// https://github.com/langchain4j/langchain4j-examples/blob/8c6870202e7c9be333ec50e04397042bd65d5d69/tutorials/src/main/java/_03_PromptTemplate.java#L28
		String testFileName = this.problemID + "_" + SUFFIX;
		String prompt = "As a professional Software Tester, generate a complete Junit class test file (name the class as :'"
				+ testFileName + "Test', and use as first line the declaration: 'package " + TARGET_PACKAGE + "') with " + this.numberOfTests
				+ " different JUnit tests for the following description in natural language with examples. Specifically below the description, each example is introduced by the keyword "
				+ TAG_EXAMPLE
				+ " (where XX is a number), and it is composed by an input tuple and an expected output. Emit only the Java code, without any other tag or text.  Here is the description: ";

		prompt = prompt.concat("\n" + this.description);

		String examplesList = "";
		int count = 0;
		for (String example : this.examples) {
			count++;
			examplesList = examplesList.concat("\n" + TAG_EXAMPLE.replace("XX", Integer.toString(count)));
			examplesList = examplesList.concat("\n" + example);
		}
		prompt = prompt.concat(examplesList);

		return prompt;
	}

	@Override
	public void savePrompt(String prompt) throws IOException {
		this.savePrompt(prompt, SUFFIX);
	}

	@Override
	public void saveCurrentResponse() throws IOException {
		this.saveCurrentResponse(SUFFIX);
	}

}
