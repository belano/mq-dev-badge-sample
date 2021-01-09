package com.belano.mq.events;

import java.time.LocalDate;
import java.time.LocalTime;

public class Venue {

  private String location = null;
  private LocalDate date = null;
  private LocalTime time = null;

  public Venue(String location, LocalDate date, LocalTime time) {
    this.location = location;
    this.date = date;
    this.time = time;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public LocalTime getTime() {
    return time;
  }

  public void setTime(LocalTime time) {
    this.time = time;
  }

  /**
   * Determines whether the current instance of {@link Venue} has all it's properties set correctly.
   *
   * @return True or false depending if the {@link Venue} is set up correctly.
   */
  public boolean isComplete() {
    return location != null && date != null && time != null;
  }
}
