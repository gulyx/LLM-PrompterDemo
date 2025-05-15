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
