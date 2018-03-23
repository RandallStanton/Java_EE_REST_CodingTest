package com.codetest;

/*
 * To be thrown when attempting to update or delete an employee that doesn't
 * exist in the repository
 * 
 * @author rstanton
 */
@SuppressWarnings("serial")
public class NoSuchEmployeeException extends Exception {
  
  public NoSuchEmployeeException() {
    super();
  }

  public NoSuchEmployeeException(String message) {
    super(message);
  }
}
