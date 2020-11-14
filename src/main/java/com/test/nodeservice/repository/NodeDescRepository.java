package com.test.nodeservice.repository;

import com.test.nodeservice.model.NodeDesc;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeDescRepository extends ReactiveMongoRepository<NodeDesc, Long> {
}
