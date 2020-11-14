package com.test.nodeservice.mapper;

import com.test.nodeservice.dto.NodeRootDto;
import com.test.nodeservice.model.NodeRoot;
import com.test.nodeservice.service.mapper.NodeRootMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NodeRootMapperTest {
    @Autowired
    private NodeRootMapper mapper;

    @Test
    public void mapToDto() {
        NodeRootDto expected = new NodeRootDto(1L, "Root node");
        NodeRootDto actual = mapper.mapToDto(new NodeRoot(1L, "Root node"));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void mapFromDto() {
        NodeRoot expected = new NodeRoot(1L, "Root node");
        NodeRoot actual = mapper.mapFromDto(new NodeRootDto(1L, "Root node"));
        Assert.assertEquals(expected, actual);
    }
}
