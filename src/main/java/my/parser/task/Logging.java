/**
 * 
 */
package my.parser.task;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;

/**
 * @author victor
 * 
 */
@Aspect
@Scope("prototype")
public class Logging {
	private static final Logger LOGGER = Logger.getLogger(Logging.class);

	@Pointcut("execution(* my.parser.task.*.*(..))")
	private void selectAll() {
	}

	/*
	 * @Before("selectAll()") public void beforeAdvice() {
	 * LOGGER.debug("Entering method ..."); }
	 * 
	 * @After("selectAll()") public void afterAdvice() {
	 * LOGGER.debug("Leaving method ..."); }
	 */

	@Around("execution(* my.parser.task.*.*(..))")
	public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
		String packageName = pjp.getSignature().getDeclaringTypeName();
		String methodName = pjp.getSignature().getName();

		long start = System.currentTimeMillis();
		if (!pjp.getSignature().getName().equals("initBinder")) {
			LOGGER.debug("Entering method [" + packageName + "." + methodName
					+ "]");
		}
		
		Object output = pjp.proceed();
		
		long elapsedTime = System.currentTimeMillis() - start;
		if (!methodName.equals("initBinder")) {
			LOGGER.debug("Exiting method [" + packageName + "." + methodName
					+ "]; exec time (ms): " + elapsedTime);
		}
		return output;
	}
}
