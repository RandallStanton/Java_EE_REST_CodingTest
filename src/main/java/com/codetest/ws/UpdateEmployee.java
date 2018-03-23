package com.codetest.ws;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.codetest.Employee;
import com.codetest.EmployeeRepository;
import com.codetest.NoSuchEmployeeException;

/*
 * Implements the REST interface for updating domain objects in the repository
 *  via HTTP PUT  and a 'Employee JSON' pay load. 
 * 
 * Returned HTTP Response code
 * 
 * 200 OK   -      On update succeeded Employee JSON pay load  & location header
 *                 
 * 404 NOT FOUND  - Trying to "update" an employee not already in the repository
 *                  or otherwise UTL.
 *                           
 *  
 * @author rstanton
 */
@Path("/employees/")
public class UpdateEmployee {

    @Inject
    Logger log;

    // @Constructor Injected
    private EmployeeRepository employees = null;

    @Inject
    public UpdateEmployee(EmployeeRepository employees) {
	super();
	this.employees = employees;
    }

    @Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, Employee employee) {
	log.entering(this.getClass().getName(), "updateEmployee");
	Response httpResponse;
	try { 
	    employees.updateEmployee(employee);
	    httpResponse = Response.status(Status.OK).entity(employee).type(MediaType.APPLICATION_JSON).build();
	} catch (NoSuchEmployeeException e) {
	    // Consensus says this is '404'
	    httpResponse = Response.status(Status.NOT_FOUND).build();
	} catch (Throwable t) {
	    // end of the line for the exceptio n- log it and report internal server error
	    log.log(Level.SEVERE, "update", t);
	    httpResponse = Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
	log.exiting(this.getClass().getName(), "updateEmployee");
	return httpResponse;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Employee employee) {
	return Response.status(Status.METHOD_NOT_ALLOWED).build();
    }

}
