package com.test.nodeservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NodeRootDto {
    private Long id;
    private String name;
}
