package com.belano.mq.badge;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

public class SevereHandler extends StreamHandler {

  /**
   * Print the stack trace everytime the application runs into a severe log message.
   *
   * @param record The message to be logged.
   */
  @Override
  public void publish(LogRecord record) {
    // Print the message with the current logger config.
    super.publish(record);
    if (record.getLevel().equals(Level.SEVERE)) {
      System.exit(1);
    }
  }
}
