package com.test.nodeservice.controller;

import com.test.nodeservice.dto.NodeRootDto;
import com.test.nodeservice.model.NodeRoot;
import com.test.nodeservice.service.NodeService;
import com.test.nodeservice.service.mapper.NodeRootMapper;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = NodeRootController.class)
@Import({NodeRootMapper.class})
public class NodeRootControllerTest {
    public static final String NODE_ROOT_URL = "/node-root";
    public static final String FIRST_NODE_NAME = "First node root";
    public static final String SECOND_NODE_NAME = "Second node root";
    public static final long FIRST_ID = 1L;
    public static final long SECOND_ID = 2L;
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private NodeService<NodeRoot> service;

    @Test
    public void createNodeRoot(){
        NodeRoot node = new NodeRoot(FIRST_ID, FIRST_NODE_NAME);

        Mockito.when(service.persist(node)).thenReturn(Mono.just(node));
        webTestClient.post()
                .uri(NODE_ROOT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(node), NodeRootDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(FIRST_ID)
                .jsonPath("$.name").isEqualTo(FIRST_NODE_NAME);

        Mockito.verify(service, times(ONE)).persist(node);
    }

    @Test
    public void getAllRootNodesIfPresent() {
        NodeRoot firstNode = new NodeRoot(FIRST_ID, FIRST_NODE_NAME);
        NodeRoot secondNode = new NodeRoot(SECOND_ID, SECOND_NODE_NAME);

        Mockito.when(service.getAll()).thenReturn(Flux.just(firstNode, secondNode));

        webTestClient.get().uri(NODE_ROOT_URL)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(NodeRootDto.class)
                .hasSize(TWO)
                .consumeWith((response) -> {
                    List<NodeRootDto> nodes =  response.getResponseBody();
                    assert nodes != null;
                    nodes.forEach((node) -> assertNotNull(node.getId()));
                });
        Mockito.verify(service, times(ONE)).getAll();
    }

    @Test
    public void getAllRootNodesIfAbsent() {
        Mockito.when(service.getAll()).thenReturn(Flux.just());

        webTestClient.get().uri(NODE_ROOT_URL)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(NodeRootDto.class)
                .hasSize(ZERO);
        Mockito.verify(service, times(ONE)).getAll();
    }
}
