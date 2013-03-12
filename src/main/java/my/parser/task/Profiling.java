/**
 * 
 */
package my.parser.task;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.context.annotation.Scope;

/**
 * @author victor
 *
 */
@Aspect
@Scope("prototype")
public class Profiling {
	private static final Logger LOGGER = Logger.getLogger(Profiling.class);	
	private long start = 0;
	private static ArrayList<Long> arr = new ArrayList<Long>();
	
	@Pointcut("execution(* my.parser.task.XmlProcessor.parseXmlFile(..))")
	private void parseXmlFile() {}
	
	@Before("parseXmlFile()")
	public void startParser() {
		start = System.currentTimeMillis();
	}
	
	@After("parseXmlFile()")
	public void finishParser() {
		long elapsedTime = (System.currentTimeMillis() - start);
		arr.add(Long.valueOf(elapsedTime));
		//LOGGER.info("File parsing time: " + elapsedTime + " ms.\n");
	}
	
	/*@AfterReturning(pointcut="parseXmlFile()", returning="retVal")
	public void afterReturningAdvice(Object retVal) {
		System.out.println("Returning:" + retVal.toString());
	}*/

	@AfterThrowing(pointcut="parseXmlFile()", throwing="ex")
	public void AfterThrowingAdvice(IllegalArgumentException ex) {
		System.out.println("There has been an exception: " + ex.toString());
	}
	
	public static long calcTotal() {
		long total = 0;
		for (Long l : arr) {
			total += l.longValue();
		}
		return total;
	}
	
	public static void printTotal() {
		if (arr.size() > 0)
			LOGGER.info("Elapsed parsing time of " + arr.size()
				+ " files: " + calcTotal() + " ms");
	}
	
	public static void printAverage() {
		if (arr.size() > 0)
			LOGGER.info("Average parsing time: "
				+ (calcTotal() / arr.size()) + " ms");
	}
}
