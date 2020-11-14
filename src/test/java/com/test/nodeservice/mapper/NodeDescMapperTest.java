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
    public static final String NODE_NAME = "Root node";
    public static final String NODE_DESCRIPTION = "Description";
    public static final long FIRS_ID = 1L;
    @Autowired
    private NodeDescMapper mapper;

    @Test
    public void mapToDto() {
        NodeDescDto expected = new NodeDescDto(FIRS_ID, NODE_NAME, NODE_DESCRIPTION);
        NodeDescDto actual = mapper.mapToDto(new NodeDesc(FIRS_ID, NODE_NAME, NODE_DESCRIPTION));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void mapFromDto() {
        NodeDesc expected = new NodeDesc(FIRS_ID, NODE_NAME, NODE_DESCRIPTION);
        NodeDesc actual = mapper.mapFromDto(new NodeDescDto(FIRS_ID, NODE_NAME, NODE_DESCRIPTION));
        Assert.assertEquals(expected, actual);
    }
}
