package com.jingge.dw.sse;

import org.eclipse.jetty.servlets.EventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by jing.ge on 27.02.2015.
 */
public class KafkaEventSource implements EventSource {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaEventSource.class);

    private List<KafkaEventSource> listeners;
    private Emitter emitter;

    public KafkaEventSource(List<KafkaEventSource> listeners) {
        this.listeners = listeners;
    }

    @Override
    public void onOpen(Emitter emitter) throws IOException {
        this.emitter = emitter;
        listeners.add(this);
    }

    @Override
    public void onClose() {
        listeners.remove(this);
    }

    public void emitEvent(String dataToSend) throws IOException {
        LOG.info("emit Event");
        this.emitter.data(dataToSend);
    }


}
