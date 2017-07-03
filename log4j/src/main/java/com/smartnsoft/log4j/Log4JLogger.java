// The MIT License (MIT)
//
// Copyright (c) 2017 Smart&Soft
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package com.smartnsoft.log4j;

import com.smartnsoft.droid4me.log.Logger;
import com.smartnsoft.droid4me.log.LoggerFactory;

import org.apache.log4j.Level;

/**
 * The logger implementation for Log4J, which works with the "android-logging-log4j" library.
 * <p>
 * <p>
 * When using this implementation (see {@code LoggerFactory.LoggerConfigurator}), the classpath should have {@code log4j.jar} and {@link android
 * -logging-log4j.jar} libraries.
 * </p>
 *
 * @author Édouard Mercier
 * @see LoggerFactory
 * @since 2013.04.18
 */
public class Log4JLogger
    implements Logger
{

  public static Level getLogLevel(int logLevel)
  {
    switch (logLevel)
    {
      case android.util.Log.ASSERT:
      default:
        return Level.TRACE;
      case android.util.Log.DEBUG:
        return Level.DEBUG;
      case android.util.Log.INFO:
        return Level.INFO;
      case android.util.Log.WARN:
        return Level.WARN;
      case android.util.Log.ERROR:
        return Level.ERROR;
    }
  }

  private final org.apache.log4j.Logger log;

  public Log4JLogger(Class<?> theClass)
  {
    this(theClass.getSimpleName());
  }

  public Log4JLogger(String category)
  {
    log = org.apache.log4j.Logger.getLogger(category);
  }

  public void debug(String message)
  {
    log.debug(message);
  }

  public void info(String message)
  {
    log.info(message);
  }

  public void warn(String message)
  {
    log.warn(message);
  }

  public void warn(String message, Throwable throwable)
  {
    log.warn(message, throwable);
  }

  public void warn(StringBuffer message, Throwable throwable)
  {
    log.warn(message.toString(), throwable);
  }

  public void error(String message)
  {
    log.error(message);
  }

  public void error(String message, Throwable throwable)
  {
    log.error(message, throwable);
  }

  public void error(StringBuffer message, Throwable throwable)
  {
    log.error(message.toString(), throwable);
  }

  public void fatal(String message)
  {
    log.fatal(message);
  }

  public void fatal(String message, Throwable throwable)
  {
    log.fatal(message, throwable);
  }

  public boolean isDebugEnabled()
  {
    return LoggerFactory.logLevel <= android.util.Log.DEBUG;
  }

  public boolean isInfoEnabled()
  {
    return LoggerFactory.logLevel <= android.util.Log.INFO;
  }

  public boolean isWarnEnabled()
  {
    return LoggerFactory.logLevel <= android.util.Log.WARN;
  }

  public boolean isErrorEnabled()
  {
    return LoggerFactory.logLevel <= android.util.Log.ERROR;
  }

  public boolean isFatalEnabled()
  {
    return LoggerFactory.logLevel <= android.util.Log.ERROR;
  }

}