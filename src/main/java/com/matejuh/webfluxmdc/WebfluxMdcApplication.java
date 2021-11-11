package com.matejuh.webfluxmdc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;
import reactor.core.scheduler.Schedulers;
import reactor.util.Loggers;

@SpringBootApplication
public class WebfluxMdcApplication {

	public static void main(String[] args) {
		Loggers.useSl4jLoggers();
		Hooks.addQueueWrapper("mdcContextAspect", MDCQueue::new);
		Schedulers.addExecutorServiceDecorator("mdcContextAspect", (scheduler, es) -> {
			return new MDCScheduledExecutorService(es);
		});
		SpringApplication.run(WebfluxMdcApplication.class, args);
	}

}
