# Realtime Publish Kafka Topic via Sever Send Event

This example will show you how easy to extend the dropwizard-kafka-consumer to build a microservice that can show the incoming message of a kafka topic via SSE in realtime.

# Extend the dropwizard-kafka-consumer

To extend the dropwizard-kafka-consumer and build this new micro service, we only need to create two classes.

## MessageSender

The class MessageSender implements the Interface MessageHandler. It contains a list of EventSource instances.
Each time, when new data is produced to the kafka topic, the data will be delegated by the build-in consumer to
MessageSender, which will loop all EvenSource instances and emit the data as a new event to each of them.

## KafkaSSEApp

This is the main class, which will:
1. make an instance MessageSender as the instance of the MessageHandler called by the build-in Kafka consumer.
2. enable the EventSourceServlet.
3. add a new Resource for SSE showcase.

That's all for the extension. The other classes are only used for adding some more features into the service,
 in this case, for the demonstration purpose.

# Adding SSE Feature

The class KafkaSSEEventSourceServlet takes care of creating new EventSource on the serve side. Each time, when
The web client create a new EventSource, i.e. new EventSource("/kafkasse"), a new KafkaEventSource instance will
be created on the server side.

The class KafkaEventSource implements the EventSource interface. After an instance of KafkaEventSource 
is created, it will be put into the list that is held by the MessageSender. Later, when the MessageSender is called 
by the build-in kafka consumer, all these KafkaEventSource instances will be called and the event will be then sent 
to the web clients.

Class SSEResource, SSEView, and the template sseview.ftl are used to visualize the event on a web page.
For the demonstration purpose, only numbers are supported. The numbers sent to the kafka topic will be shown as bar chart 
on the web page via D3.js.

# How to run this demo

1. make sure zookeeper and kafka are running with the standards ports. kafka has a topic "test-sse". 
You can change these in the config.yml or config-test.yml files.
2. start dropwizard-kafka-sse-consumer
3. point your browser to localhost:8080/realtime and your will see a web page. 
4. click the send button on that web page. One click will create a new EventSource. That means, multiple clicks 
on the same web page will create multiple EventSource and you will get multiple events of the same data.
5. you can repeat the steps 3 and 4 to let more than one web clients connect with the server. And you will see 
same events will be sent to all of these clients.
6. start a kafka producer and send numbers to kafka.

As result, you will see the numbers and bar chart. To have a little bit fun, the bar will have different colors 
depends on the reminder of the number, after the number is divided by 10. You will get red bar if the reminder is greater than 5.
