package com.hubosm.turingsimulator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class BlockingQueueConfig {

    @Bean
    public BlockingQueue<UUID> jobQueue(){
        return new LinkedBlockingQueue<>();
    }
}
