package com.algaworks.algasensors.ems_temperature_monitoring.infrastructure.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    //aula 12.14
  //  public static final String QUEUE = "temperature-monitoring.process-temperature.v1.q"; //aula 12.14 substituido pelos abaixo na 12.17
    //aula 12.17
    /*substituidos pelo abaixo na 13.04
    public static final String QUEUE_PROCESS_TEMPERATURE = "temperature-monitoring.process-temperature.v1.q";
    public static final String QUEUE_ALERTING = "temperature-monitoring.alerting.v1.q";*/
    //fim aula 12.17
    //aula 13.04
    private static final String PROCESS_TEMPERATURE = "temperature-monitoring.process-temperature.v1";
    public static final String QUEUE_PROCESS_TEMPERATURE =  PROCESS_TEMPERATURE + ".q";
    public static final String DEAD_LETTER_QUEUE_PROCESS_TEMPERATURE = PROCESS_TEMPERATURE + ".dlq";
    public static final String QUEUE_ALERTING = "temperature-monitoring.alerting.v1.q";
    //fim aula 13.04

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
//aula 12.14

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

   /* Substituido pelos abaixo na 12.17
   @Bean
    public Queue queue() {
        return QueueBuilder.durable(QUEUE).build();
    }*/

    //12.17
   /* substituido na 13.04
   @Bean
    public Queue queueProcessTemperature() {
        return QueueBuilder.durable(QUEUE_PROCESS_TEMPERATURE).build();
    }*/

    @Bean
    public Queue queueAlerting() {
        return QueueBuilder.durable(QUEUE_ALERTING).build();
    }
    //fim 12.17

    //aula 13.04
    @Bean
    public Queue queueProcessTemperature() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", "");
        args.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUE_PROCESS_TEMPERATURE);
        return QueueBuilder.durable(QUEUE_PROCESS_TEMPERATURE).withArguments(args).build();
    }

    @Bean
    public Queue deadLetterQueueProcessTemperature() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE_PROCESS_TEMPERATURE).build();
    }
    //fim aula 13.04

    public FanoutExchange exchange() {
        return ExchangeBuilder.fanoutExchange("temperature-processing.temperature-received.v1.e").build();
    }

    /* Substituido pelos abaixo na 12.17
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange());
    }*/

    //12.17
    @Bean
    public Binding bindingProcessTemperature() {
        return BindingBuilder.bind(queueProcessTemperature()).to(exchange());
    }

    @Bean
    public Binding bindingAlerting() {
        return BindingBuilder.bind(queueAlerting()).to(exchange());
    }
    //Fim 12.17
}
