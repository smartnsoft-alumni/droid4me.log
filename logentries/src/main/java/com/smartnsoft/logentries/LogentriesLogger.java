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

package com.smartnsoft.logentries;

import java.io.IOException;

import android.content.Context;

import com.smartnsoft.droid4me.log.AndroidLogger;

/**
 * An implementation of logger which is able to log to <a href="http://logentries.com">Logentries</a>.
 *
 * @author Ludovic Roland
 * @since 2014.03.14
 */
public class LogentriesLogger
    extends AndroidLogger
{

  private static String logPrefix;

  /**
   * Enables to set up a prefix for all Logentries logs. By default, no log prefix is set up. The prefix starts to be working as soon as this method
   * returns.
   *
   * @param prefix if {@code null}, no prefix will be used ; otherwise, each log will be prefixed with this provided string
   */
  public static void setLogPrefix(String prefix)
  {
    LogentriesLogger.logPrefix = prefix;
  }

  private final com.logentries.logger.AndroidLogger log;

  /**
   * Exceptions are generate where mutually exclusive settings collide - these are: "useHttpPost" and "useSsl" cannot be both true - HTTP is not available
   * with TLS/SSL "useHttpPost" and "isUsingDataHub" cannot be both true - use one or the other only
   *
   * @param theClass
   * @param context        for example, if in an Activity class, use getApplicationContext(), or if in an Application class, use getBaseContext().
   * @param useHttpPost    if set true, use HTTP (cannot be used with TLS/SSL or the Datahub)
   * @param useSsl         if set true, the data sent using the default TCP Token, will be done over an SSL Socket.
   *                       The library itself does not validate or manage TLS/SSL certificates - it will use the default TrustManager and KeyManager used by the application or host.
   * @param isUsingDataHub if set true, library will forward log events to a Datahub (requires Datahub IP Address and Port)
   * @param dataHubAddr    is a String of the IP Address of your DataHub machine.
   * @param dataHubPort    is an int of the port number of your incoming connection on your DataHub machine.
   *                       The default is port 10000, but this can be changed to any port by altering the /etc/leproxy/leproxyLocal.config file on your DataHub
   *                       machine and restarting the leproxy daemon using "sudo service leproxy restart".
   * @param token          the Token UUID, this is unique to the log to which the log events are sent This can be copied from the log in the the Logentries Account
   * @param logHostName    if set true will return host name in log event
   * @throws IOException
   */
  public LogentriesLogger(Class<?> theClass, Context context, boolean useHttpPost, boolean useSsl,
      boolean isUsingDataHub, String dataHubAddr, int dataHubPort, String token, boolean logHostName)
      throws IOException
  {
    this(theClass.getSimpleName(), context, useHttpPost, useSsl, isUsingDataHub, dataHubAddr, dataHubPort, token, logHostName);
  }

  /**
   * Exceptions are generate where mutually exclusive settings collide - these are: "useHttpPost" and "useSsl" cannot be both true - HTTP is not available
   * with TLS/SSL "useHttpPost" and "isUsingDataHub" cannot be both true - use one or the other only
   *
   * @param category
   * @param context        for example, if in an Activity class, use getApplicationContext(), or if in an Application class, use getBaseContext().
   * @param useHttpPost    if set true, use HTTP (cannot be used with TLS/SSL or the Datahub)
   * @param useSsl         if set true, the data sent using the default TCP Token, will be done over an SSL Socket.
   *                       The library itself does not validate or manage TLS/SSL certificates - it will use the default TrustManager and KeyManager used by the application or host.
   * @param isUsingDataHub if set true, library will forward log events to a Datahub (requires Datahub IP Address and Port)
   * @param dataHubAddr    is a String of the IP Address of your DataHub machine.
   * @param dataHubPort    is an int of the port number of your incoming connection on your DataHub machine.
   *                       The default is port 10000, but this can be changed to any port by altering the /etc/leproxy/leproxyLocal.config file on your DataHub
   *                       machine and restarting the leproxy daemon using "sudo service leproxy restart".
   * @param token          the Token UUID, this is unique to the log to which the log events are sent This can be copied from the log in the the Logentries Account
   * @param logHostName    if set true will return host name in log event
   * @throws IOException
   */
  public LogentriesLogger(String category, Context context, boolean useHttpPost, boolean useSsl, boolean isUsingDataHub,
      String dataHubAddr, int dataHubPort, String token, boolean logHostName)
      throws IOException
  {
    super(category);
    log = com.logentries.logger.AndroidLogger.createInstance(context, useHttpPost, useSsl, isUsingDataHub, dataHubAddr, dataHubPort, token, logHostName);
  }

  @Override
  public void debug(String message)
  {
    super.debug(message);
    log.log(computeMessage(message, null));
  }

  @Override
  public void error(String message, Throwable throwable)
  {
    super.error(message, throwable);
    log.log(computeMessage(message, throwable));
  }

  @Override
  public void error(String message)
  {
    super.error(message);
    log.log(computeMessage(message, null));
  }

  @Override
  public void error(StringBuffer message, Throwable throwable)
  {
    super.error(message, throwable);
    log.log(computeMessage(message, throwable));

  }

  @Override
  public void fatal(String message, Throwable throwable)
  {
    super.fatal(message, throwable);
    log.log(computeMessage(message, throwable));
  }

  @Override
  public void fatal(String message)
  {
    super.fatal(message);
    log.log(computeMessage(message, null));
  }

  @Override
  public void info(String message)
  {
    super.info(message);
    log.log(computeMessage(message, null));
  }

  @Override
  public void warn(String message, Throwable throwable)
  {
    super.warn(message, throwable);
    log.log(computeMessage(message, throwable));
  }

  @Override
  public void warn(String message)
  {
    super.warn(message);
    log.log(computeMessage(message, null));
  }

  @Override
  public void warn(StringBuffer message, Throwable throwable)
  {
    super.warn(message, throwable);
    log.log(computeMessage(message, throwable));
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

  protected String computeMessage(String message, Throwable throwable)
  {
    return (LogentriesLogger.logPrefix != null ? LogentriesLogger.logPrefix : "") + message + (throwable == null ? ""
        : (": reason is '" + throwable.getMessage() + "'"));
  }

  protected final String computeMessage(StringBuffer message, Throwable throwable)
  {
    return computeMessage(message.toString(), throwable);
  }

}
