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
package it.cnr.iasi.saks.llmPrompter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;


public abstract class Prompter {
	protected String problemID;
	protected int numberOfTests;
		
	protected String description;
	protected String implementationCPP;
	protected String implementationJava;
	protected String implementationPython;
	protected List<String> examples;
	
	private ChatModel llm; 
	protected String response;
	
    private String OLLAMA_BASE_URL = "http://localhost:11434";
    private String llmName = "llama3.2";
    private String llmVersion = "latest";
	
	private static final String PROBLEMS_PATH = "src/main/resources/LeetCodeProblems";
	private static final String PROBLEMS_PATH_HARD = PROBLEMS_PATH + "/Hard";
	private static final String PROBLEMS_PATH_MID = PROBLEMS_PATH + "/Mid";
	
	private static final String PROMPT_LABEL = "PROMPT";

	protected static final String TARGET_PACKAGE ="it.cnr.iasi.saks.llmPrompter.leetCodeDemo.gen";
			
	private static final String DESCRIPTION_FILENAME = "description.txt";
	private static final String CPP_FILENAME = "cpp.txt";
	private static final String JAVA_FILENAME = "java.txt";
	private static final String PYTHON_FILENAME = "python.txt";
	private static final String ITEM_TAG = "_§§_";
	private static final String EXAMPLE_FILENAME = "example"+ITEM_TAG+".txt";

	private static final String UNSET = "THIS STRING HAS NOT BEEN SET";
	
	public Prompter (String problemID) {
		this(problemID,1);
	}

	public Prompter (String problemID, int numberOfTests) {
		this.problemID = problemID;
		
	    // Build the ChatLanguageModel
	    this.llm = OllamaChatModel.builder()
		                       .baseUrl(OLLAMA_BASE_URL)
		                       .modelName(llmName)
		                       .temperature(0.8)
		                       .timeout(Duration.ofSeconds(300))
		                       .build();
	    
	    this.numberOfTests = numberOfTests;
	}
	
	public Prompter (String problemID, int numberOfTests, ChatModel llm) {
		this.problemID = problemID;
	    this.numberOfTests = numberOfTests;
	    this.llm = llm;
	}
	
	public abstract String composePrompt();
	public abstract void savePrompt(String prompt) throws IOException;
	public abstract void saveCurrentResponse() throws IOException;
	
	public String queryLLM(String prompt) {		
		this.response = this.llm.chat(prompt);
		return this.response;
	}

	protected void savePrompt(String prompt, String suffix) throws IOException {
		String tempDirPath = System.getProperty("java.io.tmpdir");
		String responsePath = tempDirPath + "/" + this.problemID + "_" + PROMPT_LABEL + "_" + suffix;
		
		FileWriter fw = new FileWriter(responsePath, false);
//		fw.append(this.response).flush();
		fw.write(prompt);
		fw.close();
	}
	
	protected void saveCurrentResponse(String suffix) throws IOException {
		String tempDirPath = System.getProperty("java.io.tmpdir");
		String responsePath = tempDirPath + "/" + this.problemID + "_" + suffix;
		
		FileWriter fw = new FileWriter(responsePath, false);
//		fw.append(this.response).flush();
		fw.write(this.response);
		fw.close();
	}
	
	protected void loadDescription() {
		this.description = this.loadResource(DESCRIPTION_FILENAME);
	}
	
	protected void loadCPP() {
		this.implementationCPP = this.loadResource(CPP_FILENAME);
	}
	
	protected void loadJava() {
		this.implementationJava = this.loadResource(JAVA_FILENAME);
	}

	protected void loadPython() {
		this.implementationPython = this.loadResource(PYTHON_FILENAME);
	}

	protected void loadExamples() {
		this.examples = new ArrayList<String>();
		
		int counter = 0;
		boolean isResourceUnset = false;
		while (! isResourceUnset){
			counter++;
			
			String resourceName = EXAMPLE_FILENAME.replace(ITEM_TAG, Integer.toString(counter));
			String resource = this.loadResource(resourceName);
			
			isResourceUnset = resource.equals(UNSET);
			if (! isResourceUnset) {
				this.examples.add(resource);
			}
		}
	}

	private String loadResource(String filename) {
		InputStream fis = null;
		String resource = PROBLEMS_PATH_MID + "/" + this.problemID + "/" + filename;;
		try { 
//			ClassLoader classLoader = getClass().getClassLoader();
//			fis = classLoader.getResourceAsStream(resource);
			fis = new FileInputStream(resource);
		} catch (FileNotFoundException e) {
			resource = PROBLEMS_PATH_HARD + "/" + this.problemID + "/" + filename; 
			try {
				fis = new FileInputStream(resource);
			} catch (FileNotFoundException e1) {
				resource = this.problemID + "/" + filename;
				System.err.println("Trying to load as-a-stream the resource: " + resource);
				ClassLoader classLoader = getClass().getClassLoader();
				fis = classLoader.getResourceAsStream(resource);
			}
		}
		
		String data = UNSET;
		try {
			data = IOUtils.toString(fis, "UTF-8");
		} catch (Exception e) {
//			e.printStackTrace();
			System.err.println("Keeping UNSET the contents from the resource: " + resource);
			data = UNSET; 
		}
		
		return data;
	}

}
