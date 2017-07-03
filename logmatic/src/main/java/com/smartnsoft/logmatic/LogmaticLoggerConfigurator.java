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

package com.smartnsoft.logmatic;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.smartnsoft.droid4me.log.LoggerFactory.LoggerConfigurator;
import com.smartnsoft.droid4me.log.LoggerWrapper;

/**
 * @author Ludovic Roland
 * @since 2017.05.17
 */
public abstract class LogmaticLoggerConfigurator
    implements LoggerConfigurator
{

  private static final String IS_LOGMATIC_ENABLED_PREFERENCES_KEY = "isLogmaticEnabledPreferencesKey";

  private static final String LOGMATIC_USAGE_LIMIT_TIMESTAMP_PREFERENCES_KEY = "logmaticUsageLimitTimestampPreferencesKey";

  private static final String LOGMATIC_LAST_USAGE_TIMESTAMP_PREFRENCES_KEY = "logmaticLastUsageTimestampPreferencesKey";

  public static void initialize(@NonNull Context context)
  {
    // We first check whether the Logentries logger
    final boolean useLogentries = LogmaticLoggerConfigurator.isLogmaticEnabled(context);

    LoggerConfigurator loggerConfigurator;
    final String loggerConfiguratorClassFqn = "SmartConfigurator";

    try
    {
      final Class<?> loggerConfiguratorClass = Class.forName(loggerConfiguratorClassFqn);
      loggerConfigurator = (LoggerConfigurator) loggerConfiguratorClass.getDeclaredConstructor(boolean.class).newInstance(useLogentries);
    }
    catch (Exception exception)
    {
      // should not append
      loggerConfigurator = null;
    }

    LoggerWrapper.configure(loggerConfigurator);
  }

  public static void start(@NonNull Context context, long timeInMs)
  {
    final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    final Editor editor = preferences.edit();
    editor.putBoolean(LogmaticLoggerConfigurator.IS_LOGMATIC_ENABLED_PREFERENCES_KEY, true);
    editor.putLong(LogmaticLoggerConfigurator.LOGMATIC_USAGE_LIMIT_TIMESTAMP_PREFERENCES_KEY, timeInMs);
    editor.putLong(LogmaticLoggerConfigurator.LOGMATIC_LAST_USAGE_TIMESTAMP_PREFRENCES_KEY, System.currentTimeMillis());
    editor.apply();

    LogmaticLoggerConfigurator.initialize(context);
  }

  public static void stop(@NonNull Context context)
  {
    final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    final Editor editor = preferences.edit();
    editor.putBoolean(LogmaticLoggerConfigurator.IS_LOGMATIC_ENABLED_PREFERENCES_KEY, false);
    editor.apply();

    LogmaticLoggerConfigurator.initialize(context);
  }

  private static boolean isLogmaticEnabled(@NonNull Context context)
  {
    final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

    if (sharedPreferences.getBoolean(LogmaticLoggerConfigurator.IS_LOGMATIC_ENABLED_PREFERENCES_KEY, false) == false)
    {
      return false;
    }

    final int timestamp = sharedPreferences.getInt(LogmaticLoggerConfigurator.LOGMATIC_USAGE_LIMIT_TIMESTAMP_PREFERENCES_KEY, 0);

    if (timestamp == 0)
    {
      return false;
    }

    final SharedPreferences preferences = sharedPreferences;
    final long logentriesLastUsageTimestamp = preferences.getLong(LogmaticLoggerConfigurator.LOGMATIC_LAST_USAGE_TIMESTAMP_PREFRENCES_KEY, 0);
    return (System.currentTimeMillis() - logentriesLastUsageTimestamp) < timestamp;
  }

}
