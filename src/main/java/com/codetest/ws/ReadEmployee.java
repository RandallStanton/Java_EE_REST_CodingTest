package com.codetest.ws;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.codetest.Employee;
import com.codetest.EmployeeRepository;

/*
 * c-READ-u-d
 * 
 * returns
 * 
 *  200 OK  -  JSON Employee found
 *  
 *  404 NOT FOUND If no employee exists for the given id 
 * 
 * @author rstanton
 */
@Path("/employees/")
@Produces({ MediaType.APPLICATION_JSON })
public class ReadEmployee {

    @Inject
    Logger log;

    // @Constructor
    private EmployeeRepository employees = null;

    @Inject
    public ReadEmployee(EmployeeRepository _employees) {
	super();
	employees = _employees;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readSingle(@PathParam("id") int id) {
	Response httpResponse;
	Optional<Employee> option = employees.getEmployee(id);
	try {
	    httpResponse = (option.isPresent()) ? Response.ok(option.get(), MediaType.APPLICATION_JSON).build()
		    : Response.status(Response.Status.NOT_FOUND).build();
	} catch (Throwable e) {
	    // terminal point for exceptions, log it and crash and burn message.
	    log.log(Level.SEVERE, "create", e);
	    httpResponse = Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	return httpResponse;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response read() {
	List<Employee> empArry = employees.getEmployees();
	return Response.ok(empArry, MediaType.APPLICATION_JSON).build();
    }

}
