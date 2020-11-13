package com.test.nodeservice.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NodeService<T> {
    Mono<T> persist(T t);

    Flux<T> getAll();
}
