package com.test.nodeservice.repository;

import com.test.nodeservice.model.NodeRoot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
@RunWith(SpringRunner.class)
@DirtiesContext
public class NodeRootRepositoryTest {
    public static final String NODE_ROOT_NAME = "Node root name";
    @Autowired
    private NodeRootRepository repository;

    @Test
    public void saveNodeRoot() {
        NodeRoot node = new NodeRoot(1L, NODE_ROOT_NAME);
        Mono<NodeRoot> saved = repository.save(node);
        StepVerifier.create(saved)
                .expectSubscription()
                .expectNextMatches(e -> (e.getId() != null
                        && e.getName().equals(node.getName())))
                .verifyComplete();
    }

    @Test
    public void getAllRootNodes() {
        StepVerifier.create(repository.findAll())
                .expectSubscription()
                .expectNextMatches(e -> e.getId().equals(1L)
                        && e.getName().equals(NODE_ROOT_NAME))
                .verifyComplete();
    }
}
