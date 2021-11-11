package com.matejuh.webfluxmdc;

import org.slf4j.MDC;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;

public class MDCQueue implements Queue<Object> {

    private final Queue<?> q;

    public MDCQueue(Queue<?> q) {
        this.q = q;
    }

    @Override
    public boolean add(Object o) {
        return ((Queue) q).add(Tuples.of(MDC.getCopyOfContextMap(), o));
    }

    @Override
    public boolean offer(Object o) {
        final Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
        return ((Queue) q).offer(
                Tuples.of(copyOfContextMap == null ? Collections.emptyMap() : copyOfContextMap, o));
    }

    @Override
    public Object remove() {
        final Tuple2<Map, Object> envelop = (Tuple2<Map, Object>) ((Queue) q).remove();
        MDC.setContextMap(envelop.getT1());
        return envelop.getT2();
    }

    @Override
    public Object poll() {
        final Tuple2<Map, Object> envelop = (Tuple2<Map, Object>) ((Queue) q).poll();
        if (envelop != null) {
            MDC.setContextMap(envelop.getT1());
            return envelop.getT2();
        }
        return envelop;
    }

    @Override
    public Object element() {
        final Tuple2<Map, Object> envelop = (Tuple2<Map, Object>) ((Queue) q).element();
        if (envelop != null) {
            MDC.setContextMap(envelop.getT1());
            return envelop.getT2();
        }
        return envelop;
    }

    @Override
    public Object peek() {
        final Tuple2<Map, Object> envelop = (Tuple2<Map, Object>) ((Queue) q).peek();
        if (envelop != null) {
            MDC.setContextMap(envelop.getT1());
            return envelop.getT2();
        }
        return envelop;
    }

    @Override
    public int size() {
        return q.size();
    }

    @Override
    public boolean isEmpty() {
        return q.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return q.contains(o);
    }

    @Override
    public Iterator<Object> iterator() {
        return (Iterator<Object>) q.iterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.stream(q.toArray()).map(e -> ((Tuple2) e).getT2()).toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) Arrays.stream(q.toArray(a)).map(e -> ((Tuple2) e).getT2()).toArray();
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        q.clear();
    }
}
