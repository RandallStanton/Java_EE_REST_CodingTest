package com.codetest.jax.rs.core;

import org.glassfish.jersey.server.ResourceConfig;

/*
 * javax.ws.rs.core.Application instance for the app server
 * 
 * @author rstanton
 */
public class JEECoreApp extends ResourceConfig {

    public JEECoreApp() {
	//hard coded for demo/time reasons
	packages(true, "com.codetest.ws");
    }

}
