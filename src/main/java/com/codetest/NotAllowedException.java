package com.codetest;

/*
 * To be thrown when attempting to create/add a new employee in the repository
 * that already exist in to repository or otherwise includes an id (treat id
 * as read only)
 * 
 * @author rstanton
 */
@SuppressWarnings("serial")
public class NotAllowedException extends Exception {
  
  public NotAllowedException(String message) {
    super(message);
  }
}
