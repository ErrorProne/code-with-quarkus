package org.acme;

import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.eventbus.EventBus;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class GreetingResource {

    @Inject
    EventBus eventBus;

    @GET
    @Path("/blocking")
    @Produces(MediaType.TEXT_PLAIN)
    public void blocking()  {
        eventBus.send("blocking", "eventbus call");
    }

    @GET
    @Path("/nonblocking")
    @Produces(MediaType.TEXT_PLAIN)
    public void nonblocking()  {
        eventBus.send("nonBlocking", "eventbus call");
    }

    @ConsumeEvent(value = "blocking", blocking = true)
    public void blockingException(String testMessage) {
        if (1==1) {
            throw new RuntimeException("I SHOULD BE IN THE LOG");
        }
    }

    @ConsumeEvent(value = "nonBlocking")
    public void nonblockingException(String testMessage) {
        if (1==1) {
            throw new RuntimeException("I SHOULD BE IN THE LOG");
        }
    }
}
