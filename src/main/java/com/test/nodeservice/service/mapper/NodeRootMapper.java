package com.test.nodeservice.service.mapper;

import com.test.nodeservice.dto.NodeRootDto;
import com.test.nodeservice.model.NodeRoot;
import org.springframework.stereotype.Component;

@Component
public class NodeRootMapper {
    public NodeRoot mapFromDto(NodeRootDto nodeRequestDto) {
        return NodeRoot.builder()
                .id(nodeRequestDto.getId())
                .name(nodeRequestDto.getName())
                .build();
    }

    public NodeRootDto mapToDto(NodeRoot node) {
        return NodeRootDto.builder()
                .id(node.getId())
                .name(node.getName())
                .build();
    }
}
