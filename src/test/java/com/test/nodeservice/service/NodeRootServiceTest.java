package com.test.nodeservice.service;

import com.test.nodeservice.model.NodeRoot;
import com.test.nodeservice.repository.NodeRootRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.times;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NodeRootServiceTest {
    public static final String NODE_NAME = "Node root";
    public static final long FIRST_ID = 1L;
    public static final int ONE = 1;
    @Autowired
    private NodeRootServiceImpl service;

    @MockBean
    private NodeRootRepository repository;

    @Test
    public void persist() {
        NodeRoot node = new NodeRoot(FIRST_ID, NODE_NAME);
        Mockito.when(repository.save(node)).thenReturn(Mono.just(node));
        NodeRoot afterPersist = service.persist(node)
                .map(e -> new NodeRoot(e.getId(), e.getName())).block();
        Assert.assertEquals(node, afterPersist);
        Mockito.verify(repository, times(ONE)).save(node);
    }

    @Test
    public void getAll() {
        Mockito.when(repository.findAll()).thenReturn(Flux.empty());
        Assert.assertEquals(Flux.empty(), service.getAll());
        Mockito.verify(repository, times(ONE)).findAll();
    }
}
