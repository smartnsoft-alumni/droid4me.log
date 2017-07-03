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

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.smartnsoft.droid4me.log.LoggerFactory.LoggerConfigurator;
import com.smartnsoft.droid4me.log.LoggerWrapper;

/**
 * @author Ludovic Roland
 * @since 2014.03.14
 */
public abstract class LogentriesLoggerConfigurator
    implements LoggerConfigurator
{

  private static final String IS_LOGENTRIES_ENABLED_PREFERENCES_KEY = "isLogentriesEnabledPreferencesKey";

  private static final String LOGENTRIES_USAGE_LIMIT_TIMESTAMP_PREFERENCES_KEY = "logentriesUsageLimitTimestampPreferencesKey";

  private static final String LOGENTRIES_LAST_USAGE_TIMESTAMP_PREFRENCES_KEY = "logentriesLastUsageTimestampPreferencesKey";

  public static void initialize(@NonNull Context context)
  {
    // We first check whether the Logentries logger
    final boolean useLogentries = LogentriesLoggerConfigurator.isLogentriesEnabled(context);

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
    editor.putBoolean(LogentriesLoggerConfigurator.IS_LOGENTRIES_ENABLED_PREFERENCES_KEY, true);
    editor.putLong(LogentriesLoggerConfigurator.LOGENTRIES_USAGE_LIMIT_TIMESTAMP_PREFERENCES_KEY, timeInMs);
    editor.putLong(LogentriesLoggerConfigurator.LOGENTRIES_LAST_USAGE_TIMESTAMP_PREFRENCES_KEY, System.currentTimeMillis());
    editor.apply();

    LogentriesLoggerConfigurator.initialize(context);
  }

  public static void stop(@NonNull Context context)
  {
    final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    final Editor editor = preferences.edit();
    editor.putBoolean(LogentriesLoggerConfigurator.IS_LOGENTRIES_ENABLED_PREFERENCES_KEY, false);
    editor.apply();

    LogentriesLoggerConfigurator.initialize(context);
  }

  private static boolean isLogentriesEnabled(@NonNull Context context)
  {
    final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

    if (sharedPreferences.getBoolean(LogentriesLoggerConfigurator.IS_LOGENTRIES_ENABLED_PREFERENCES_KEY, false) == false)
    {
      return false;
    }

    final int timestamp = sharedPreferences.getInt(LogentriesLoggerConfigurator.LOGENTRIES_USAGE_LIMIT_TIMESTAMP_PREFERENCES_KEY, 0);

    if (timestamp == 0)
    {
      return false;
    }

    final SharedPreferences preferences = sharedPreferences;
    final long logentriesLastUsageTimestamp = preferences.getLong(LogentriesLoggerConfigurator.LOGENTRIES_LAST_USAGE_TIMESTAMP_PREFRENCES_KEY, 0);
    return (System.currentTimeMillis() - logentriesLastUsageTimestamp) < timestamp;
  }

}
