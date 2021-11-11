package com.matejuh.webfluxmdc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.context.Context;

import java.time.Duration;

@RestController
public class WeatherController {

    private static final Logger log = LoggerFactory.getLogger(WeatherController.class);
    private final WebClient webClient;

    public WeatherController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping(value = "/weather")
    public ResponseEntity<String> getWeather() {
        log.info("action=getWeather");
        return webClient
                .get()
                .uri("https://wttr.in?AT")
                .exchangeToMono(clientResponse -> clientResponse.toEntity(String.class))
//                .contextWrite(Context.of(MDC.getCopyOfContextMap()))
                .block(Duration.ofSeconds(10));
    }
}
