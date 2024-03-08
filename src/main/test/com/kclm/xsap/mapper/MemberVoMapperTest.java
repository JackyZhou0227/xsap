package com.kclm.xsap.mapper;

import com.kclm.xsap.model.vo.MemberVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberVoMapperTest {

    @Autowired
    private MemberVoMapper memberVoMapper;
    @Test
    void listAllMemberVo() {
        List<MemberVo> memberVos = memberVoMapper.listAllMemberVo();
        //
        memberVos.forEach(System.out::println);

    }
}