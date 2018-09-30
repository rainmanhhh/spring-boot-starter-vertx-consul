package cn.ez.vertx.consul;

import io.vertx.core.Vertx;
import io.vertx.ext.consul.CheckOptions;
import io.vertx.ext.consul.ConsulClient;
import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.ext.consul.ServiceOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsulConfiguration {
    @ConditionalOnMissingBean(ConsulClient.class)
    @Bean
    public ConsulClient consulClient(Vertx vertx, ConsulClientOptions options) {
        return ConsulClient.create(vertx, options);
    }

    @ConditionalOnMissingBean(ConsulClientOptions.class)
    @ConfigurationProperties("vertx.consul.client-options")
    @Bean
    public ConsulClientOptions consulClientOptions() {
        return new ConsulClientOptions();
    }


    @ConditionalOnMissingBean(ServiceOptions.class)
    @ConfigurationProperties("vertx.consul.service-options")
    @Bean
    public ServiceOptions serviceOptions(@Autowired(required = false) CheckOptions checkOptions) {
        return new ServiceOptions().setCheckOptions(checkOptions);
        //todo extend class to read spring application name, generate random id(if conflict, retry), add property to switch off service register
    }

    @ConditionalOnMissingBean(CheckOptions.class)
    @ConfigurationProperties("vertx.consul.service-options.check-options")
    @Bean
    public CheckOptions checkOptions() {
        return new CheckOptions();
        //todo extend class to set default check options
    }
}
