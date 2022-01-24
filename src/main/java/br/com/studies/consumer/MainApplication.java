package br.com.studies.consumer;

import br.com.studies.consumer.events.EventConsumer;

public class MainApplication {
    public static void main(String[] args) {
        EventConsumer eventConsumer = new EventConsumer();
        eventConsumer.run();
    }
}
