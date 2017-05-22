package com.smartnsoft.logmatic;

import java.util.Map;

import com.smartnsoft.droid4me.log.AndroidLogger;

import io.logmatic.android.Logger;
import io.logmatic.android.LoggerBuilder;

/**
 * Redirect the logs traces to Logmatic
 *
 * @author Antoine Gerard
 * @since 2016.05.23
 */
public class LogmaticLogger
    extends AndroidLogger
{

  private static String logPrefix;

  private static Logger logger;

  public static void setLogPrefix(String prefix)
  {
    LogmaticLogger.logPrefix = prefix;
  }

  public static Logger getLogger()
  {
    return logger;
  }

  public LogmaticLogger(Class<?> theClass, String apiKey)
  {
    this(theClass.getSimpleName(), apiKey);
  }

  public LogmaticLogger(String category, String apiKey)
  {
    super(category);
    this.logger = new LoggerBuilder().init(apiKey).withName(category).build();
  }

  @Override
  public void debug(String message)
  {
    super.debug(message);
    logger.d(logPrefix + " " + message);
  }

  @Override
  public void info(String message)
  {
    super.info(message);
    logger.i(logPrefix + " " + message);
  }


  @Override
  public void warn(String message)
  {
    super.warn(message);
    logger.w(logPrefix + " " + message);
  }

  @Override
  public void warn(String message, Throwable throwable)
  {
    super.warn(message, throwable);
    logger.w(logPrefix + " " + message, throwable);
  }

  @Override
  public void error(String message)
  {
    super.error(message);
    logger.e(logPrefix + " " + message);
  }

  @Override
  public void error(String message, Throwable throwable)
  {
    super.error(message, throwable);
    logger.e(logPrefix + " " + message, throwable);
  }

  @Override
  public void fatal(String message)
  {
    super.fatal(message);
    logger.wtf(logPrefix + " " + message);
  }

  @Override
  public void fatal(String message, Throwable throwable)
  {
    super.fatal(message, throwable);
    logger.wtf(logPrefix + " " + message, throwable);
  }

  @Override
  public boolean isDebugEnabled()
  {
    return true;
  }

  @Override
  public boolean isInfoEnabled()
  {
    return true;
  }

  @Override
  public boolean isWarnEnabled()
  {
    return true;
  }

  @Override
  public boolean isErrorEnabled()
  {
    return true;
  }

  @Override
  public boolean isFatalEnabled()
  {
    return true;
  }

  /**
   * Those methods can send more data type to logmatic
   * For instance, Date, objects, system data
   * The map will be converted to a JSON object
   *
   * @param contextName Name given to the map that will be show in logmatic dashboard
   * @param contextMap  The map containing all the data
   */
  public void debugCustomData(String contextName, Map<String, Object> contextMap)
  {
    logger.d(contextName, contextMap);
  }

  public void infoCustomData(String contextName, Map<String, Object> contextMap)
  {
    logger.i(contextName, contextMap);
  }

  public void warnCustomData(String contextName, Map<String, Object> contextMap)
  {
    logger.w(contextName, contextMap);
  }

  public void errorCustomData(String contextName, Map<String, Object> contextMap)
  {
    logger.e(contextName, contextMap);
  }

  public void fatalCustomData(String contextName, Map<String, Object> contextMap)
  {
    logger.wtf(contextName, contextMap);
  }
}
