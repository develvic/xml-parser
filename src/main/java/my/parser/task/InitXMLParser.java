/**
 * 
 */
package my.parser.task;

import java.util.logging.Logger;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author victor
 *
 */
public class InitXMLParser implements BeanPostProcessor {
	private static final Logger LOGGER = Logger.getLogger(InitXMLParser.class.getName());
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		LOGGER.info("BeforeInitialization: " + beanName);
		return bean;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		LOGGER.info("AfterInitialization: " + beanName);
		return bean;
	}

}
