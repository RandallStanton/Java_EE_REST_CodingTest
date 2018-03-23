package com.codetest.ws;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.codetest.Employee;
import com.codetest.EmployeeRepository;

/*
 * Provides the REST interface implementation for an Employee DELETE operation.                    
 * This implementation does the delete immediately.
 * 
 * Success:   
 *     204 NO CONTENT
 * 
 * Employee not found (by id):
 *     404 NOT FOUND
 *     
 * Cannot delete more than one employee or any anonymous employee per request
 *     405 NOT ALLOWED
 * 
 * 
 * @author rstanton
 *
 */
@Path("/employees/")
public class DeleteEmployee {

    @Inject
    Logger log;

    @Inject
    EmployeeRepository employees;

    @Path("{id}")
    @DELETE
    public Response deleteEmployee(@PathParam("id") int id) {
	Response httpResponse;
	Optional<Employee> option = employees.getEmployee(id);
	try {
	    if (option.isPresent()) {
		employees.removeEmployee(option.get());
		httpResponse = Response.status(Status.NO_CONTENT).build();
	    } else {
		httpResponse = Response.status(Status.NOT_FOUND).build();
	    }
	} catch (Throwable t) {
	    // we are at a terminal point here a broad catch is acceptable
	    // here for internal server error and logging
	    log.log(Level.SEVERE, "delete", t);
	    httpResponse = Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
	return httpResponse;
    }

    @DELETE
    public Response deleteEmployees(Employee employee) {
	return Response.status(Status.METHOD_NOT_ALLOWED).build();
    }
}
