package com.codetest.ws;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.codetest.Employee;
import com.codetest.EmployeeRepository;
import com.codetest.NotAllowedException;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/*
 * Implements the REST interface for creating a new employee domain object
 *  via HTTP POST  and a 'Employee JSON' pay load. 
 * 
 * Returned HTTP Response code (not in error conditions):
 * 
 * 201 CREATED   - When new employee successfully added to repository. Message Body includes
 *                 updated (with ID) Employee JSON  & location header
 *                 
 * 405 METHOD NOT ALLOWED  - Clients are not allowed specify employee ID or Version.
 *                           This prevents 409 CONFLICT. (No body)
 *  
 * @author rstanton
 */
@Path("/employees/")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class CreateEmployee {

    @Inject
    Logger log;

    // @Constructor Injected
    private EmployeeRepository employees = null;

    @Inject
    public CreateEmployee(EmployeeRepository employees) {
	super();
	this.employees = employees;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Employee newEmployee, @Context UriInfo uriInfo) {

	Response httpResponse;
	try {
	    Employee _newEmployee = employees.addEmployee(newEmployee);
	    UriBuilder builder = uriInfo.getAbsolutePathBuilder();
	    builder.path(_newEmployee.getId().toString());
	    httpResponse = Response.created(builder.build()).entity(_newEmployee).type(MediaType.APPLICATION_JSON)
		    .build();
	} catch (NotAllowedException e) {
	    httpResponse = Response.status(Status.METHOD_NOT_ALLOWED).build();
	} catch (Throwable t) {
	    // we are at a terminal point here a broad catch is acceptable
	    // here for internal server error and logging
	    log.log(Level.SEVERE, "create", t);
	    httpResponse = Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	return httpResponse;
    }

    @Path("{id}")
    @POST
    public Response notAllowed() {
	return Response.status(Status.METHOD_NOT_ALLOWED).build();
    }

}
