/**
 * 
 */
package my.parser.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author victor
 *
 */

@Configuration
public class AppConfig {
	@Bean
	@Scope("prototype")
	public ResultContainer resultContainer() {
		return new ResultContainer();
	}
	
	@Bean(initMethod="init", destroyMethod="destroy")
	public DataProcessor dataProcessor() {
		return new DataProcessor(resultContainer()); 
	}
	
	@Bean
	@Scope("prototype")
	public ValueContainer valueContainer() {
		return new ValueContainer();
	}
	
	@Bean
	@Scope("prototype")
	public Meter meter() {
		return new Meter();
	}
	
	@Bean
	@Scope("prototype")
	public XmlProcessor xmlProcessor() {
		return new XmlProcessor();
	}
}
