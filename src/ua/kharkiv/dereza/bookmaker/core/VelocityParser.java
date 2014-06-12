package ua.kharkiv.dereza.bookmaker.core;

import java.io.File;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

/**
 * Class for generating email messages for clients and admin.
 * 
 * @author dereza
 */
public class VelocityParser {

	private static final Logger log = Logger.getLogger(VelocityParser.class);

	private VelocityEngine velocityEngine;
	private Template template;
	private VelocityContext velocityContext;

	/**
	 * creates message according template
	 * 
	 * @param templateName
	 * @param parameters for template
	 * @return null if cannot find template String if template was found
	 */
	public String getMessage(String templateName, Map<String, String> parameters) {

		// gets absolute path of project
		String absolutePath = new File(Thread.currentThread()
				.getContextClassLoader().getResource("").getFile())
				.getParentFile().getParentFile().getPath();

		// setups properties for initialization velocityEngine
		Properties props = new Properties();
		props.setProperty("resource.loader", "jar");
		props.setProperty("jar.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.JarResourceLoader");
		props.setProperty("jar.resource.loader.path", "jar:file:"
				+ absolutePath + Constants.VELOCITY_TEMPLATES_PATH);

		// gets and initializes an engine
		velocityEngine = new VelocityEngine(props);
		velocityEngine.init();

		try {
			// gets the Template
			template = velocityEngine.getTemplate(templateName, "UTF-8");
		} catch (ResourceNotFoundException ex) {
			log.error("Cannot find template", ex);
			throw new Error("Cannot find template", ex);
		} catch (ParseErrorException ex) {
			log.error("Cannot parse template", ex);
			throw new Error("Cannot parse template", ex);
		}
		log.debug("Template will be created --> " + templateName);

		// creates a context and add data
		velocityContext = new VelocityContext();
		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			velocityContext.put(entry.getKey(), entry.getValue());
		}
		log.debug("Established next values into template --> "
				+ parameters.toString());

		// renders the template into a StringWriter
		StringWriter writer = new StringWriter();
		template.merge(velocityContext, writer);
		log.trace("Ready template --> " + writer.toString());

		return writer.toString();
	}
}