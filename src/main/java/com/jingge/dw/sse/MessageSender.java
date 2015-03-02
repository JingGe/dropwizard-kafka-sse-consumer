package com.jingge.dw.sse;

import com.jingge.dw.handler.MessageHandler;
import org.eclipse.jetty.servlets.EventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jing.ge on 27.02.2015.
 */
public class MessageSender implements MessageHandler{

    private static final Logger LOG = LoggerFactory.getLogger(MessageSender.class);

    private List<KafkaEventSource> listeners = Collections.synchronizedList(new ArrayList<KafkaEventSource>());

    public List<KafkaEventSource> getListeners() {
        return listeners;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void dispose(byte[] message) {
        LOG.info("new incoming message: " + new String(message));
        synchronized (listeners) {
            for (KafkaEventSource eventSource : listeners) {
                try {
                    eventSource.emitEvent(new String(message));
                } catch (IOException e) {
                    LOG.error(e.getMessage());
                }
            }
        }

    }
}
