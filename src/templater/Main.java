package templater;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import com.esotericsoftware.yamlbeans.YamlReader;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Main {

	/**
	 * This is a project to generate template based file from input data
	 * The library that are used
	 *  - Freemarker : A template library - http://freemarker.sourceforge.net/
	 *  - yamlbeans  : A library to read/write yaml files - http://yamlbeans.sourceforge.net/
	 * @param args
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void main(String[] args) throws IOException, TemplateException {
		if (args.length != 3) {
			System.err.println("Wrong number of arguments");
			System.err.println("Try: java -jar templater.jar {templateLocation} {dataFileLocation} {outputFileLocation}");
			return;
		}
		String templateLocation = args[0];
		String dataFileLocation = args[1];
		String outputFileLocation = args[2];

		File templteFile = new File(templateLocation);
		File outputFile = new File(outputFileLocation);

		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(templteFile.getParentFile());
		cfg.setObjectWrapper(new DefaultObjectWrapper());

		Template temp = cfg.getTemplate(templteFile.getName());

		YamlReader reader = new YamlReader(new FileReader(dataFileLocation));
		Map dataMap = (Map) reader.read();

		Writer out = new OutputStreamWriter(new FileOutputStream(outputFile));
		temp.process(dataMap, out);
		out.flush();
		out.close();

	}

}
