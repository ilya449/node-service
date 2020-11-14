package com.test.nodeservice.controller;

import com.test.nodeservice.controller.NodeRootController;
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
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    NodeService<NodeRoot> service;

    @Test
    public void createNodeRoot(){
        NodeRoot node = new NodeRoot(1L, "First node root");

        Mockito.when(service.persist(node)).thenReturn(Mono.just(node));
        webTestClient.post()
                .uri(NODE_ROOT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(node), NodeRootDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1L)
                .jsonPath("$.name").isEqualTo("First node root");

        Mockito.verify(service, times(1)).persist(node);
    }

    @Test
    public void getAllRootNodesIfPresent() {
        NodeRoot firstNode = new NodeRoot(1L, "First node root");
        NodeRoot secondNode = new NodeRoot(2L, "Second node root");

        Mockito.when(service.getAll()).thenReturn(Flux.just(firstNode, secondNode));

        webTestClient.get().uri(NODE_ROOT_URL)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(NodeRootDto.class)
                .hasSize(2)
                .consumeWith((response) -> {
                    List<NodeRootDto> nodes =  response.getResponseBody();
                    assert nodes != null;
                    nodes.forEach((node) -> assertNotNull(node.getId()));
                });
        Mockito.verify(service, times(1)).getAll();
    }

    @Test
    public void getAllRootNodesIfAbsent() {
        Mockito.when(service.getAll()).thenReturn(Flux.just());

        webTestClient.get().uri(NODE_ROOT_URL)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(NodeRootDto.class)
                .hasSize(0);
        Mockito.verify(service, times(1)).getAll();
    }
}
