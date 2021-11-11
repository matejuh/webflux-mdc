package com.matejuh.webfluxmdc;

import org.slf4j.MDC;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MDCScheduledExecutorService  implements ScheduledExecutorService {

    private final ScheduledExecutorService es;

    public MDCScheduledExecutorService(ScheduledExecutorService es) {
        this.es = es;
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        final Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
        return es.schedule(() -> {
            try {
                MDC.setContextMap(copyOfContextMap);
                command.run();
            } finally {
                MDC.clear();
            }
        }, delay, unit);
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        final Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
        return es.schedule(() -> {
            try {
                MDC.setContextMap(copyOfContextMap);
                return callable.call();
            } finally {
                MDC.clear();
            }
        }, delay, unit);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay,
                                                  long period, TimeUnit unit) {
        final Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
        return es.scheduleAtFixedRate(() -> {
            try {
                MDC.setContextMap(copyOfContextMap);
                command.run();
            } finally {
                MDC.clear();
            }
        }, initialDelay, period, unit);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay,
                                                     long delay, TimeUnit unit) {
        final Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
        return es.scheduleWithFixedDelay(() -> {
            try {
                MDC.setContextMap(copyOfContextMap);
                command.run();
            } finally {
                MDC.clear();
            }
        }, initialDelay, delay, unit);
    }

    @Override
    public void shutdown() {
        es.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return es.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return es.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return es.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return es.awaitTermination(timeout, unit);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        final Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
        return es.submit(() -> {
            try {
                MDC.setContextMap(copyOfContextMap);
                return task.call();
            } finally {
                MDC.clear();
            }
        });
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        final Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
        return es.submit(() -> {
            try {
                MDC.setContextMap(copyOfContextMap);
                task.run();
            } finally {
                MDC.clear();
            }
        }, result);
    }

    @Override
    public Future<?> submit(Runnable task) {
        final Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
        return es.submit(() -> {
            try {
                MDC.setContextMap(copyOfContextMap);
                task.run();
            } finally {
                MDC.clear();
            }
        });
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
            throws InterruptedException {
        return null;
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout,
                                         TimeUnit unit) throws InterruptedException {
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks)
            throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    @Override
    public void execute(Runnable command) {
        final Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
        es.execute(() -> {
            try {
                MDC.setContextMap(copyOfContextMap);
                command.run();
            } finally {
                MDC.clear();
            }
        });
    }
}
