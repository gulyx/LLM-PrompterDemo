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
