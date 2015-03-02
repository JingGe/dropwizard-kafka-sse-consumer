package com.jingge.dw.sse;

import org.eclipse.jetty.servlets.EventSource;
import org.eclipse.jetty.servlets.EventSourceServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jing.ge on 27.02.2015.
 */
public class KafkaSSEEventSourceServlet extends EventSourceServlet{

    private static final Logger LOG = LoggerFactory.getLogger(KafkaSSEEventSourceServlet.class);

    private MessageSender messageSender;

    public KafkaSSEEventSourceServlet(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    protected EventSource newEventSource(HttpServletRequest httpServletRequest) {
        LOG.info("create a new EventSource for {}", httpServletRequest.getRemoteAddr());
        return new KafkaEventSource(messageSender.getListeners());
    }
}
