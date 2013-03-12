package my.parser.task;

import java.util.Arrays;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author victor
 *
 */
public class App {
	@SuppressWarnings("unused")
	private final static Logger LOGGER = Logger.getLogger(App.class);
	public static AnnotationConfigApplicationContext context =
			new AnnotationConfigApplicationContext();
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BeanDefinition beanDefinition = BeanDefinitionBuilder
				.rootBeanDefinition(Arrays.class, "asList")
                .addConstructorArgValue(args).getBeanDefinition();
		context.getBeanFactory().addBeanPostProcessor(
				new AutowiredAnnotationBeanPostProcessor());
		context.registerBeanDefinition("args", beanDefinition);
		context.scan("my.parser.task");
		context.refresh();
		
		Settings config = context.getBean(Settings.class);
		DataProcessor dp = context.getBean(DataProcessor.class);
		dp.process(config.getPath());
		dp.print();
		
		context.close();
	}
}
