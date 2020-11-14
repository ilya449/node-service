package com.test.nodeservice.service;

import com.test.nodeservice.model.NodeDesc;
import com.test.nodeservice.repository.NodeDescRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NodeDescServiceImpl implements NodeService<NodeDesc> {
    private final NodeDescRepository nodeDescRepository;

    public NodeDescServiceImpl(NodeDescRepository nodeDescRepository) {
        this.nodeDescRepository = nodeDescRepository;
    }

    @Override
    public Mono<NodeDesc> persist(NodeDesc nodeDesc) {
        return nodeDescRepository.save(nodeDesc);
    }

    @Override
    public Flux<NodeDesc> getAll() {
        return nodeDescRepository.findAll();
    }
}
