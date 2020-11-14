package com.test.nodeservice.controller;

import com.test.nodeservice.dto.NodeDescDto;
import com.test.nodeservice.model.NodeDesc;
import com.test.nodeservice.service.NodeService;
import com.test.nodeservice.service.mapper.NodeDescMapper;
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
@RequestMapping("/node-desc")
public class NodeDescController {
    private final NodeService<NodeDesc> service;
    private final NodeDescMapper mapper;

    public NodeDescController(NodeService<NodeDesc> service, NodeDescMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public Flux<NodeDescDto> getAllNodeDesc() {
        return service.getAll().map(mapper::mapToDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<NodeDescDto> addNewNodeDesc(@RequestBody NodeDescDto dto) {
        return service.persist(mapper.mapFromDto(dto)).map(mapper::mapToDto);
    }
}
