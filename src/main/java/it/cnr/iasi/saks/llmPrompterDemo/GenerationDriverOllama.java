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
package it.cnr.iasi.saks.llmPrompterDemo;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import it.cnr.iasi.saks.llmPrompter.Prompter;
import it.cnr.iasi.saks.llmPrompter.impl.DescriptionExamplesPrompter;
import it.cnr.iasi.saks.llmPrompter.impl.DescriptionOnlyPrompter;

public class GenerationDriverOllama {

	private static String OLLAMA_BASE_URL = "http://localhost:11434";
	private static String llmName = "llama3.2";
	private static String llmVersion = "latest";

	private static String OPTION_PATH_LONG = "p";
	private static String OPTION_PATH_SHORT = "path";
	private static String OPTION_PATH_DESCRIPTION = "path to the directory to process";

	private static Options declareOptions() {
		Options options = new Options();

		Option input = new Option(OPTION_PATH_SHORT, OPTION_PATH_LONG, true, OPTION_PATH_DESCRIPTION);
		input.setRequired(true);
		options.addOption(input);

//        Option output = new Option("o", "output", true, "output file");
//        output.setRequired(true);
//        options.addOption(output);

		return options;
	}

	private static CommandLine processOptions(String[] args, Options options) throws ParseException {
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);
		return cmd;
	}

	private static List<String> readDirsName(String dirPath) throws IOException {
		File f = new File(dirPath);
		if (!f.exists() || !f.isDirectory()) {
			String msg = f.getAbsolutePath() +" does not exist or it is not a directory";
			IOException e = new IOException(msg);
			throw e;
		}

		List<String> listOfDirs = Stream.of(f.listFiles())
	      .filter(file -> file.isDirectory())
	      .map(File::getName)
	      .collect(Collectors.toList());
		
		return listOfDirs;
	}

	private static void processDirs(List<String> items) {
		ChatModel llm = OllamaChatModel.builder().baseUrl(OLLAMA_BASE_URL).modelName(llmName).temperature(0.8)
				.timeout(Duration.ofSeconds(300)).build();

		for (String id : items) {
			Prompter p = new DescriptionOnlyPrompter(id, 5, llm);
//			Prompter p = new DescriptionExamplesPrompter(id, 5, llm);
		
			String prompt = p.composePrompt();
			System.out.println("Querying LLM for id:" + id + " ...");
			p.queryLLM(prompt);
			System.out.println("... done");
			try {
				p.savePrompt(prompt);
				p.saveCurrentResponse();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public static void main(String args[]) {
		Options options = declareOptions();
		List<String> listOfDirsName = null; 
		try {
			CommandLine cmd = processOptions(args, options);
			
			String dirPath = cmd.getOptionValue(OPTION_PATH_LONG);
			listOfDirsName = readDirsName(dirPath);
			
		} catch (ParseException | IOException e) {
			System.err.println(e.getMessage());

			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("utility-name", options);

			System.exit(1);
		}
		processDirs(listOfDirsName);

	}

}
