package logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.Log4jLoggerAdapter;

public class TryDifferentLoggers {
	private static Logger logger = LoggerFactory.getLogger(TryDifferentLoggers.class);

	public static void main(String[] args) {

		String rootLogger = Logger.ROOT_LOGGER_NAME;
		logger.info(rootLogger);
		Logger rLogger = LoggerFactory.getLogger(rootLogger);
		rLogger = (Log4jLoggerAdapter) rLogger ;
		
		
	}

}
