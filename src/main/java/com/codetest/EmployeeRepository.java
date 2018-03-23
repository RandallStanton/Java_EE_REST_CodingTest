package com.codetest;

import java.util.List;
import java.util.Optional;

/*
 * 
 * Describes a simple persistent repository for employee domain objects;
 * 
 * The intent here is have something that looks and interacts like a simple collection
 * for domain objects rather than a DAO or service (but really, it's impl is DAO and SVC) 
 * 
 * 
 * @author rstanton
 */
public interface EmployeeRepository {

  /*
   * Add a NEW employee to the repository collection;
   * 
   * "It gets grumpy intentionally if an actor attempts to add an employee
   * that is already in the repository (id by PK)"
   * 
   * 
   * @param newEmployee
   * 
   * @return
   * 
   * @throws NotAllowedException
   */
  public Employee addEmployee(Employee newEmployee) throws NotAllowedException;

  /*
   * Persist the (new) state of an existing Employee in the repo
   * 
   * "Yep, it should get grumpy and throws an exception if given employee isn't 
   * already in the repo"
   * 
   * @param employee
   */
  public void updateEmployee(Employee employee) throws NoSuchEmployeeException;

  /*
   * Removes the given Employee
   * 
   * Returns true the employee was in the repository and removed otherwise
   * false.
   * 
   * This method is to operate as an 'explicit remove.' If a persistence context
   * exists and the employee is detached, the employee is reloaded, re-attached
   * then explicitly removed.
   * 
   * @param employee
   * 
   * @return
   */
  public boolean removeEmployee(Employee employee);

  /*
   * Return a single employee from the repo by id if that Employye exists
   * 
   * @param id
   * 
   * @return
   */
  public Optional<Employee> getEmployee(Integer id);

  /*
   * Returns all employees
   * 
   * Pagination is TBD (not requested and test time considerations)
   * 
   * @return
   * 
   * @throws NotAllowedException
   */
  public List<Employee> getEmployees();

  /*
   * Search and Sort
   * 
   * This is TBD 
   * Not requested in programming test instructions and time considerations
   * 
   * public List<Employee> findEmployees(EmpCriteria criteria);
   */

}
