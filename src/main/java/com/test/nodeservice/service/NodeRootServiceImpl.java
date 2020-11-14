package com.test.nodeservice.service;

import com.test.nodeservice.model.NodeRoot;
import com.test.nodeservice.repository.NodeRootRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NodeRootServiceImpl implements NodeService<NodeRoot> {
    private final NodeRootRepository nodeRootRepository;

    public NodeRootServiceImpl(NodeRootRepository nodeRootRepository) {
        this.nodeRootRepository = nodeRootRepository;
    }

    @Override
    public Mono<NodeRoot> persist(NodeRoot nodeRoot) {
        return nodeRootRepository.save(nodeRoot);
    }

    @Override
    public Flux<NodeRoot> getAll() {
        return nodeRootRepository.findAll();
    }
}
