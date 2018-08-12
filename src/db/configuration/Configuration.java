package db.configuration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Configuration implements ServletContextListener{

	private static Map<String,String> configurations;
	
	public static String getConfigurations(String configurationName) {
		return configurations.get(configurationName);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		this.loadConfiguration();
	}
	
	private void loadConfiguration() {
		configurations=new HashMap<>();
		
		SAXReader reader = new SAXReader();
		
		URL url = Configuration.class.getClassLoader().getResource("configuration.xml");
		
		try {
			Document document = reader.read(url);
			
			Element root = document.getRootElement();
			
			for (@SuppressWarnings("unchecked")
			Iterator<Element> it = root.elementIterator(); it.hasNext();) {
		        Element element = it.next();
		        configurations.put(element.getName(), element.getData().toString());
		    }
		}catch (Exception e) {
			String paht=Configuration.class.getClassLoader().getResource("").toString();
			File cofgfile=new File(paht+"configuration.xml");
			if(!cofgfile.exists())
				try {
					cofgfile.createNewFile();
				} catch (IOException e1) {
					System.out.println("configuration.xml不存在且创建失败");
				}
		}
	}
	
}
