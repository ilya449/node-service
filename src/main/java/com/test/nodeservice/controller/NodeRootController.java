package com.test.nodeservice.controller;

import com.test.nodeservice.dto.NodeRootDto;
import com.test.nodeservice.model.NodeRoot;
import com.test.nodeservice.service.NodeService;
import com.test.nodeservice.service.mapper.NodeRootMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/node-root")
public class NodeRootController {
    private final NodeService<NodeRoot> service;
    private final NodeRootMapper mapper;

    public NodeRootController(NodeRootMapper mapper, NodeService<NodeRoot> service) {
        this.mapper = mapper;
        this.service = service;
    }

    @GetMapping
    public Flux<NodeRootDto> getAllNodeRoot() {
        return service.getAll().map(mapper::mapToDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<NodeRootDto> addNewNodeRoot(@RequestBody NodeRootDto dto) {
        return service.persist(mapper.mapFromDto(dto)).map(mapper::mapToDto);
    }
}
