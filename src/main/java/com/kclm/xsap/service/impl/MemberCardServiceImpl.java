package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kclm.xsap.mapper.CourseCardMapper;
import com.kclm.xsap.mapper.MemberCardMapper;
import com.kclm.xsap.model.dto.MemberCardDTO;
import com.kclm.xsap.model.entity.CourseEntity;
import com.kclm.xsap.model.entity.MemberCardEntity;
import com.kclm.xsap.model.vo.CardInfoVo;
import com.kclm.xsap.service.MemberCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MemberCardServiceImpl extends ServiceImpl<MemberCardMapper, MemberCardEntity> implements MemberCardService {

    private final static Logger log = LoggerFactory.getLogger(MemberCardServiceImpl.class);

    @Resource
    private MemberCardMapper memberCardMapper;

    @Resource
    private CourseCardMapper courseCardMapper;



    @Override
    public boolean isCardNameExist(String name) {
        return baseMapper.selectCount(new QueryWrapper<MemberCardEntity>().eq("name", name)) > 0;
    }

    @Override
    public List<CardInfoVo> getCardsInfoByMemberId(Long memberId) {
        return memberCardMapper.getCardsInfoByMemberId(memberId);
    }

    @Override
    public List<Long> getAllCardIds() {
        return memberCardMapper.getAllCardIds();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveMemberCardDTO(MemberCardDTO memberCardDTO) {
        MemberCardEntity memberCardEntity = new MemberCardEntity(memberCardDTO);
        memberCardEntity.setCreateTime(LocalDateTime.now());
        memberCardEntity.setLastModifyTime(LocalDateTime.now());
        List<Long> courseIdList = memberCardDTO.getCourseListStr();
        try {
            memberCardMapper.insert(memberCardEntity);
            return courseCardMapper.insertCardAndCourses(memberCardEntity.getId(), courseIdList) > 0;
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("保存会员卡失败",e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateMemberCardDTO(MemberCardDTO memberCardDTO) {
        log.info("memberCardDTO的id为"+memberCardDTO.getId());
        MemberCardEntity memberCardEntity = new MemberCardEntity(memberCardDTO);
        memberCardEntity.setId(memberCardDTO.getId());
        log.info("memberCardEntity id = "+memberCardEntity.getId());
        memberCardEntity.setLastModifyTime(LocalDateTime.now());
        List<Long> courseIdList = memberCardDTO.getCourseListStr();
        try {
            memberCardMapper.updateById(memberCardEntity);
            courseCardMapper.deleteAllCoursesByCardId(memberCardEntity.getId());
            return courseCardMapper.insertCardAndCourses(memberCardEntity.getId(), courseIdList) > 0;
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("更新会员卡失败",e);
        }
    }

}
