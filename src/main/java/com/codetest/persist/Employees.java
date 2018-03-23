package com.codetest.persist;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.codetest.Employee;
import com.codetest.EmployeeRepository;
import com.codetest.NoSuchEmployeeException;
import com.codetest.NotAllowedException;

/*
 * Basic JPA implementation of the EmployeeRepository interface
 * 
 * @author rstanton
 */
@ApplicationScoped
public class Employees implements EmployeeRepository {

    @Inject
    Logger log;

    // @Constructor Injected
    EntityManager entityManager;

    @Inject
    public Employees(EntityManager _entityManager) {
	super();
	entityManager = _entityManager;
    }

    /*
     * @see com.codetest.EmployeeRepository#addEmployee(com.codetest .Employee)
     */
    @Override
    public Employee addEmployee(Employee newEmployee) throws NotAllowedException {

	if (newEmployee.getId() != null && newEmployee.getId() != 0) {
	    throw new NotAllowedException("Specifying an Id Parameter value prohibited when creating employee");
	}

	// REMEMBER, we've decided to let exceptions propagate BUT we want to at least
	// TRY and 'rollback' on a persistence or transactional exceptions/errors
	// without actually handling them here but letting it propagate without
	// re-throwing. It's probably overkill here, but the pattern is sound.

	boolean try_rollback = true;
	
	try {
	    entityManager.getTransaction().begin();
	    entityManager.persist(newEmployee);
	    entityManager.getTransaction().commit();
	    try_rollback = false;
	} finally {
	    tryRollbackIfTrue(try_rollback);
	}

	return newEmployee;
    }

    /*
     * @see com.codetest.EmployeeRepository#updateEmployee(com.codetest
     * .domain.Employee)
     */
    @Override
    public void updateEmployee(Employee employee) throws NoSuchEmployeeException {

	// Same exception/roll-back pattern used in addEmployee()
	boolean try_rollback = true;

	try {
	    if (!containsEmployeeById(employee.getId())) {
		try_rollback = false; // no transaction, no need for roll back
		throw new NoSuchEmployeeException();
	    }
	    entityManager.getTransaction().begin();
	    entityManager.merge(employee);
	    entityManager.getTransaction().commit();
	    try_rollback = false; // everything is fine - no roll back
	} finally {
	    tryRollbackIfTrue(try_rollback);
	}
    }

    /*
     * Remove employee
     *
     * Returns true is the employee actually existed in the repository before
     * remove. Does not throw NoSuchEmployeeException();
     *
     * @see com.codetest.EmployeeRepository#removeEmployeeExplicit(com.codetest
     * .domain.Employee)
     */
    @Override
    public boolean removeEmployee(Employee employee) {

	// THOUGHTS ON WHY OPTING FOR THE EXPLICIT REMOVE?
	//
	// While using a simple JPQL delete query might 'currently' be suitable here,
	// using Entitymanager's explicit remove is more robust. It will better support
	// cascades,
	// orphans, and etc. . . Using it will make this method less like to change as
	// employee
	// changes.

	//FYI: forcing the 'lambda expression' and 'optional' use here a bit 'just for show'
	
	Optional<Employee> optional;
	boolean try_rollback = true;
	
	try {
	    // Make sure employee is attached. If not get an attached instance.
	    optional = (!entityManager.contains(employee)) ? getEmployee(employee.getId())
		    : Optional.ofNullable(employee);
	    optional.ifPresent((Consumer<Employee>) ((Employee e) -> {
		entityManager.getTransaction().begin();
		entityManager.remove(e);
		entityManager.getTransaction().commit();
	    }));
	    try_rollback = false;
	} finally {
	    tryRollbackIfTrue(try_rollback);
	}

	return optional.isPresent();
    }

    /*
     * Returns ALL Employees in the repository
     * 
     * NO SEARCH, SORT OR PAGINATION OPTIONS ARE IMPLEMENTED AS THE PROGRAMMING
     * TEST/QUIZ DOES NOT SPECIFIY OR REQUIRE THEM (IT SPECIFICLYSPECIFYS ALL
     * EMPLOYEES). IN A PRODUCTION PRODUCTION THESE OPTIONS WOULD LIKELY BE NEEDED.
     * 
     * @see com.codetest.EmployeeRepository#getEmployees()
     */
    @Override
    public List<Employee> getEmployees() {
	List<Employee> employeeList = null;
	TypedQuery<Employee> q = entityManager.createNamedQuery("Employee.list", Employee.class);
	employeeList = q.getResultList();
	return employeeList;
    }

    /*
     * Return the employee for the given id (if it exists) in Optional wrapper.
     * 
     * @see com.codetest.EmployeeRepository#getEmployee(java.lang.Integer)
     */
    @Override
    public Optional<Employee> getEmployee(Integer id) {
	Optional<Employee> result = Optional.ofNullable(entityManager.find(Employee.class, id));
	return result;
    }

    /*
     * Quick,dirty and simple does row exist check counts instances of the pk and
     * returns true if there are more than zero.
     * 
     * @param id
     * 
     * @return
     */
    private boolean containsEmployeeById(Integer id) {
	TypedQuery<Long> countQuery = entityManager.createNamedQuery("Employee.countId", Long.class);
	countQuery.setParameter("id", id);
	long count = countQuery.getSingleResult();
	return (count > 0);
    }

    /*
     * Convenience - roll back if true and transaction is active
     */
    private void tryRollbackIfTrue(boolean rollback) {
	if (entityManager.getTransaction().isActive() && rollback) {
	    try {
		entityManager.getTransaction().rollback();
	    } catch (Exception e) {
		log.log(Level.INFO, "jpa transaction attempt failed", e);
	    }
	}
    }
}
