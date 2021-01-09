package com.belano.mq.exceptions;

public class DataDidNotSaveException extends Exception {


  public DataDidNotSaveException(String errorMessage, Exception e) {
    super(errorMessage, e);
  }

  public DataDidNotSaveException(String errorMessage) {
    super(errorMessage);
  }
}
