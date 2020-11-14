package com.test.nodeservice.repository;

import com.test.nodeservice.model.NodeRoot;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeRootRepository extends ReactiveMongoRepository<NodeRoot, Long> {
}
