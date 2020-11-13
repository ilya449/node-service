package com.test.nodeservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NodeDescDto {
    private Long id;
    private String name;
    private String description;
}
