package com.codetest.producer;

import java.util.logging.Logger;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * 
 * Serves as JSR-299 @Producer for the java.util.logging.Loggers injected in the
 * application. Makes use of InjectionPoint to produce class specific loggers
 * 
 * @author rstanton
 */
public class LogProvider {

    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
	return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

}
