// SPDX-License-Identifier: Apache-2.0
package io.github.stavshamir.springwolf.asyncapi.sns;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sns.core.SnsTemplate;
import io.github.stavshamir.springwolf.asyncapi.controller.SpringwolfSnsController;
import io.github.stavshamir.springwolf.configuration.AsyncApiDocketService;
import io.github.stavshamir.springwolf.producer.SpringwolfSnsProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static io.github.stavshamir.springwolf.configuration.properties.SpringwolfSnsConfigConstants.SPRINGWOLF_SNS_CONFIG_PREFIX;
import static io.github.stavshamir.springwolf.configuration.properties.SpringwolfSnsConfigConstants.SPRINGWOLF_SNS_PLUGIN_PUBLISHING_ENABLED;

@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(
        prefix = SPRINGWOLF_SNS_CONFIG_PREFIX,
        name = SPRINGWOLF_SNS_PLUGIN_PUBLISHING_ENABLED,
        havingValue = "true")
public class SpringwolfSnsProducerConfiguration {

    @Bean
    public SpringwolfSnsController springwolfSnsController(
            AsyncApiDocketService asyncApiDocketService,
            SpringwolfSnsProducer springwolfSnsProducer,
            ObjectMapper objectMapper) {
        return new SpringwolfSnsController(asyncApiDocketService, springwolfSnsProducer, objectMapper);
    }

    @Bean
    public SpringwolfSnsProducer springwolfSnsProducer(List<SnsTemplate> snsTemplate) {
        return new SpringwolfSnsProducer(snsTemplate);
    }
}