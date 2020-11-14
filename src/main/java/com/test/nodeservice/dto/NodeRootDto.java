package com.test.nodeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class NodeRootDto {
    private Long id;
    private String name;
}
