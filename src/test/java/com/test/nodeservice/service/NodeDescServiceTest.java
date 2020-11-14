package com.test.nodeservice.service;

import com.test.nodeservice.model.NodeDesc;
import com.test.nodeservice.repository.NodeDescRepository;
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
public class NodeDescServiceTest {
    public static final String NODE_NAME = "Node desc";
    public static final String NODE_DESCRIPTION = "Node desc description";
    public static final long ID = 1L;
    @Autowired
    private NodeDescServiceImpl service;

    @MockBean
    private NodeDescRepository repository;

    @Test
    public void persist() {
        NodeDesc node = new NodeDesc(ID, NODE_NAME, NODE_DESCRIPTION);
        Mockito.when(repository.save(node)).thenReturn(Mono.just(node));
        NodeDesc persisted = service.persist(node)
                .map(e -> new NodeDesc(e.getId(), e.getName(), e.getDescription())).block();
        Assert.assertEquals(node, persisted);
        Mockito.verify(repository, times(1)).save(node);
    }

    @Test
    public void getAll() {
        Mockito.when(repository.findAll()).thenReturn(Flux.empty());
        Assert.assertEquals(Flux.empty(), service.getAll());
        Mockito.verify(repository, times(1)).findAll();
    }
}
