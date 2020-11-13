package com.test.nodeservice.service.mapper;

import com.test.nodeservice.dto.NodeDescDto;
import com.test.nodeservice.model.NodeDesc;
import org.springframework.stereotype.Component;

@Component
public class NodeDescMapper {
    public NodeDesc mapFromDto(NodeDescDto nodeDescDto) {
        return NodeDesc.builder()
                .id(nodeDescDto.getId())
                .name(nodeDescDto.getName())
                .description(nodeDescDto.getDescription())
                .build();
    }

    public NodeDescDto mapToDto(NodeDesc nodeDesc) {
        return NodeDescDto.builder()
                .id(nodeDesc.getId())
                .name(nodeDesc.getName())
                .description(nodeDesc.getDescription())
                .build();
    }
}
