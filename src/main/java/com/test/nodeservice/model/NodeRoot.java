package com.test.nodeservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@Document(collection = "nodes")
public class NodeRoot {
    @Id
    private Long id;
    private String name;
}
