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
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Assertions;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

public class PrompterSimpleTest {
		
	@ParameterizedTest
	@ValueSource(strings = {"52", "1823", "Foo", "", " "})
	public void loadAndSaveTest (String input) {
		Prompter p = new DummyPrompter(input);
		
		String prompt = p.composePrompt();
		p.queryLLM(prompt);
		try {
			p.saveCurrentResponse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String tempDirPath = System.getProperty("java.io.tmpdir");
		String responsePath = tempDirPath + "/" + input + "_" + DummyPrompter.SUFFIX;
		InputStream fis;
		String data="";
		try {
			fis = new FileInputStream(responsePath);
			data = IOUtils.toString(fis, "UTF-8");
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
			Assertions.fail(e.getMessage());
		} catch (IOException e) {
//			e.printStackTrace();
			Assertions.fail(e.getMessage());
		}
		Assertions.assertEquals(prompt, data);
	}
}
