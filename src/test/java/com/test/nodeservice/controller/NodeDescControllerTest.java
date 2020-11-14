package com.test.nodeservice.controller;

import com.test.nodeservice.controller.NodeDescController;
import com.test.nodeservice.dto.NodeDescDto;
import com.test.nodeservice.model.NodeDesc;
import com.test.nodeservice.service.NodeService;
import com.test.nodeservice.service.mapper.NodeDescMapper;
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
@WebFluxTest(controllers = NodeDescController.class)
@Import({NodeDescMapper.class})
public class NodeDescControllerTest {
    public static final String NODE_DESC_URL = "/node-desc";
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    NodeService<NodeDesc> service;

    @Test
    public void createNodeDesc(){
        NodeDesc node = new NodeDesc(1L, "First node desc", "First node description");

        Mockito.when(service.persist(node)).thenReturn(Mono.just(node));
        webTestClient.post()
                .uri(NODE_DESC_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(node), NodeDescDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1L)
                .jsonPath("$.name").isEqualTo("First node desc")
                .jsonPath("$.description").isEqualTo("First node description");

        Mockito.verify(service, times(1)).persist(node);
    }

    @Test
    public void getAllDescNodesIfPresent() {
        NodeDesc firstNode = new NodeDesc(1L, "First node desc", "First node description");
        NodeDesc secondNode = new NodeDesc(2L, "Second node desc", "Second node description");

        Mockito.when(service.getAll()).thenReturn(Flux.just(firstNode, secondNode));

        webTestClient.get().uri(NODE_DESC_URL)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(NodeDescDto.class)
                .hasSize(2)
                .consumeWith((response) -> {
                    List<NodeDescDto> nodes =  response.getResponseBody();
                    assert nodes != null;
                    nodes.forEach((node) -> assertNotNull(node.getId()));
                });
        Mockito.verify(service, times(1)).getAll();
    }

    @Test
    public void getAllDescNodesIfAbsent() {
        Mockito.when(service.getAll()).thenReturn(Flux.just());

        webTestClient.get().uri(NODE_DESC_URL)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(NodeDescDto.class)
                .hasSize(0);
        Mockito.verify(service, times(1)).getAll();
    }
}
