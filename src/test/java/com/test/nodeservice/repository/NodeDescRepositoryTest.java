package com.test.nodeservice.repository;

import com.test.nodeservice.model.NodeDesc;
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
public class NodeDescRepositoryTest {
    public static final String NODE_DESC_NAME = "Node desc name";
    public static final String NODE_DESC_DESCRIPTION = "Node description";

    @Autowired
    private NodeDescRepository repository;

    @Test
    public void saveNodeRoot() {
        NodeDesc node = new NodeDesc(1L, NODE_DESC_NAME, NODE_DESC_DESCRIPTION);
        Mono<NodeDesc> saved = repository.save(node);
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
                        && e.getName().equals(NODE_DESC_NAME))
                .verifyComplete();
    }
}
