// SPDX-License-Identifier: Apache-2.0
package io.github.stavshamir.springwolf.asyncapi.v3.model.channel;

import io.github.stavshamir.springwolf.asyncapi.v3.ClasspathUtil;
import io.github.stavshamir.springwolf.asyncapi.v3.bindings.amqp.AMQPChannelBinding;
import io.github.stavshamir.springwolf.asyncapi.v3.bindings.amqp.AMQPChannelQueueProperties;
import io.github.stavshamir.springwolf.asyncapi.v3.bindings.amqp.AMQPChannelType;
import io.github.stavshamir.springwolf.asyncapi.v3.jackson.DefaultAsyncApiSerializer;
import io.github.stavshamir.springwolf.asyncapi.v3.model.ExternalDocumentation;
import io.github.stavshamir.springwolf.asyncapi.v3.model.Tag;
import io.github.stavshamir.springwolf.asyncapi.v3.model.channel.message.Message;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class ChannelTest {
    private static final DefaultAsyncApiSerializer serializer = new DefaultAsyncApiSerializer();

    @Test
    void shouldSerializeChannelObject() throws IOException {
        Channel channel = Channel.builder()
                .address("users.{userId}")
                .title("Users channel")
                .description("This channel is used to exchange messages about user events.")
                .messages(Map.of(
                        "userSignedUp",
                        Message.builder()
                                .ref("#/components/messages/userSignedUp")
                                .build(),
                        "userCompletedOrder",
                        Message.builder()
                                .ref("#/components/messages/userCompletedOrder")
                                .build()))
                .parameters(Map.of(
                        "userId",
                        ChannelParameter.builder()
                                .ref("#/components/parameters/userId")
                                .build()))
                .servers(List.of(
                        ServerReference.builder()
                                .ref("#/servers/rabbitmqInProd")
                                .build(),
                        ServerReference.builder()
                                .ref("#/servers/rabbitmqInStaging")
                                .build()))
                .bindings(Map.of(
                        "amqp",
                        AMQPChannelBinding.builder()
                                .is(AMQPChannelType.QUEUE)
                                .queue(AMQPChannelQueueProperties.builder()
                                        .exclusive(true)
                                        .build())
                                .build()))
                .tags(List.of(Tag.builder()
                        .name("user")
                        .description("User-related messages")
                        .build()))
                .externalDocs(ExternalDocumentation.builder()
                        .description("Find more info here")
                        .url("https://example.com")
                        .build())
                .build();

        String example = ClasspathUtil.readAsString("/v3/model/channel/channel.json");
        assertThatJson(serializer.toJsonString(channel))
                // These values are autogenerated, so we can ignore them from the test
                .whenIgnoringPaths("bindings.amqp.bindingVersion", "bindings.amqp.queue.vhost")
                .isEqualTo(example);
    }
}