package com.test.nodeservice.controller;

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
    public static final String FIRST_NODE_NAME = "First node desc";
    public static final String FIRST_NODE_DESCRIPTION = "First node description";
    public static final String SECOND_NODE_NAME = "Second node desc";
    public static final String SECOND_NODE_DESCRIPTION = "Second node description";
    public static final long FIRST_ID = 1L;
    public static final long SECOND_ID = 2L;
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private NodeService<NodeDesc> service;

    @Test
    public void createNodeDesc(){
        NodeDesc node = new NodeDesc(FIRST_ID, FIRST_NODE_NAME, FIRST_NODE_DESCRIPTION);

        Mockito.when(service.persist(node)).thenReturn(Mono.just(node));
        webTestClient.post()
                .uri(NODE_DESC_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(node), NodeDescDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(FIRST_ID)
                .jsonPath("$.name").isEqualTo(FIRST_NODE_NAME)
                .jsonPath("$.description").isEqualTo(FIRST_NODE_DESCRIPTION);

        Mockito.verify(service, times(ONE)).persist(node);
    }

    @Test
    public void getAllDescNodesIfPresent() {
        NodeDesc firstNode = new NodeDesc(FIRST_ID, FIRST_NODE_NAME, FIRST_NODE_DESCRIPTION);
        NodeDesc secondNode = new NodeDesc(SECOND_ID, SECOND_NODE_NAME, SECOND_NODE_DESCRIPTION);

        Mockito.when(service.getAll()).thenReturn(Flux.just(firstNode, secondNode));

        webTestClient.get().uri(NODE_DESC_URL)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(NodeDescDto.class)
                .hasSize(TWO)
                .consumeWith((response) -> {
                    List<NodeDescDto> nodes =  response.getResponseBody();
                    assert nodes != null;
                    nodes.forEach((node) -> assertNotNull(node.getId()));
                });
        Mockito.verify(service, times(ONE)).getAll();
    }

    @Test
    public void getAllDescNodesIfAbsent() {
        Mockito.when(service.getAll()).thenReturn(Flux.just());

        webTestClient.get().uri(NODE_DESC_URL)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(NodeDescDto.class)
                .hasSize(ZERO);
        Mockito.verify(service, times(ONE)).getAll();
    }
}
