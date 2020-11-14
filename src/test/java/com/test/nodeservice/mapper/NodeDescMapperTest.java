package com.test.nodeservice.mapper;

import com.test.nodeservice.dto.NodeDescDto;
import com.test.nodeservice.model.NodeDesc;
import com.test.nodeservice.service.mapper.NodeDescMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NodeDescMapperTest {
    @Autowired
    private NodeDescMapper mapper;

    @Test
    public void mapToDto() {
        NodeDescDto expected = new NodeDescDto(1L, "Root node", "Description");
        NodeDescDto actual = mapper.mapToDto(new NodeDesc(1L, "Root node", "Description"));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void mapFromDto() {
        NodeDesc expected = new NodeDesc(1L, "Root node", "Description");
        NodeDesc actual = mapper.mapFromDto(new NodeDescDto(1L, "Root node", "Description"));
        Assert.assertEquals(expected, actual);
    }
}
