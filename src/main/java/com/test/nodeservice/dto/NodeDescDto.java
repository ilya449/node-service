package com.test.nodeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class NodeDescDto {
    private Long id;
    private String name;
    private String description;
}
