package com.jingge.dw.sse;

import com.jingge.dw.configuration.BaseKafkaConsumerConfiguration;
import com.jingge.dw.handler.MessageHandler;
import com.jingge.dw.kafka.BaseKafkaConsumerApp;
import com.jingge.dw.sse.resource.SSEResource;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

/**
 * Created by jing.ge on 27.02.2015.
 */
public class KafkaSSEApp extends BaseKafkaConsumerApp {

    public static final String KAFKA_SSE_MAPPING = "/kafkasse";

    public static void main(String[] args) throws Exception {
        new KafkaSSEApp().run(args);
    }

    private MessageSender messageSender;

    public KafkaSSEApp() {
        messageSender = new MessageSender();
    }

    @Override
    public void initialize(Bootstrap<BaseKafkaConsumerConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addBundle(new ViewBundle());
    }

    @Override
    protected void registerResources(Environment environment) {
        super.registerResources(environment);
        environment.servlets().addServlet("kafkaSSE", new KafkaSSEEventSourceServlet(messageSender)).addMapping(KAFKA_SSE_MAPPING);
        environment.jersey().register(new SSEResource(KAFKA_SSE_MAPPING));
    }

    @Override
    protected MessageHandler createMessageHandler() {
        return messageSender;
    }
}
