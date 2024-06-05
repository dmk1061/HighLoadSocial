package otus.social.config;

import org.otus.social.rabbitmq.PostsPersist;
import org.otus.social.rabbitmq.PostsReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;


@Configuration
public class RabbitConfig {
    public static final String EXCHANGE_NAME = "social-exchange";
    public static final String ROUTING_KEY = "post.feed.#";

    static final String QUEUE_NAME_1 = "post-feed-1";
    static final String QUEUE_NAME_2 = "post-feed-2";

    @Bean
    Queue queue1() {
        return new Queue(QUEUE_NAME_1, false);
    }

    @Bean
    Queue queue2() {
        return new Queue(QUEUE_NAME_2, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding binding1(final Queue queue1, final TopicExchange exchange) {
        return BindingBuilder.bind(queue1).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    Binding binding2(final Queue queue2, final TopicExchange exchange) {
        return BindingBuilder.bind(queue2).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    SimpleMessageListenerContainer container1(final ConnectionFactory connectionFactory,
                                              final MessageListenerAdapter listenerAdapter) {
        final SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME_1);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    SimpleMessageListenerContainer container2(final ConnectionFactory connectionFactory,
                                              final MessageListenerAdapter listenerAdapter2) {
        final SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME_2);
        container.setMessageListener(listenerAdapter2);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(final PostsReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    MessageListenerAdapter listenerAdapter2(final PostsPersist receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}
