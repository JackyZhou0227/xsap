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

    /**
     * 保存会员卡信息及其关联的课程信息。
     * @param memberCardDTO 会员卡数据传输对象，包含会员卡的基本信息和关联的课程列表。
     * @return 返回一个布尔值，表示保存操作是否成功。
     * @throws RuntimeException 如果保存过程中发生异常，则抛出运行时异常。
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveMemberCardDTO(MemberCardDTO memberCardDTO) {
        // 将DTO转换为实体对象，并设置创建时间和最后修改时间
        MemberCardEntity memberCardEntity = new MemberCardEntity(memberCardDTO);
        memberCardEntity.setCreateTime(LocalDateTime.now());
        memberCardEntity.setLastModifyTime(LocalDateTime.now());

        // 提取课程ID列表，准备插入到课程卡关系表
        List<Long> courseIdList = memberCardDTO.getCourseListStr();
        try {
            // 插入会员卡实体到数据库
            memberCardMapper.insert(memberCardEntity);
            // 插入会员卡和课程之间的关系到数据库，并检查是否成功插入至少一条关系
            return courseCardMapper.insertCardAndCourses(memberCardEntity.getId(), courseIdList) > 0;
        }catch (Exception e){
            // 记录异常信息，并抛出运行时异常提示保存会员卡失败
            log.error(e.getMessage());
            throw new RuntimeException("保存会员卡失败",e);
        }
    }

    /**
     * 更新会员卡信息及其关联的课程信息。
     *
     * @param memberCardDTO 会员卡数据传输对象，包含会员卡的详细信息和课程列表。
     * @return 返回更新操作是否成功。若成功插入课程信息，则返回true；否则返回false。
     * @throws RuntimeException 当更新过程中发生异常时，抛出运行时异常。
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateMemberCardDTO(MemberCardDTO memberCardDTO) {
        log.info("memberCardDTO的id为"+memberCardDTO.getId());
        // 将DTO转换为实体对象，并设置ID
        MemberCardEntity memberCardEntity = new MemberCardEntity(memberCardDTO);
        memberCardEntity.setId(memberCardDTO.getId());
        log.info("memberCardEntity id = "+memberCardEntity.getId());
        // 设置最后修改时间为当前时间
        memberCardEntity.setLastModifyTime(LocalDateTime.now());

        // 提取课程ID列表
        List<Long> courseIdList = memberCardDTO.getCourseListStr();
        try {
            // 更新会员卡实体信息
            memberCardMapper.updateById(memberCardEntity);
            // 删除该会员卡当前关联的所有课程
            courseCardMapper.deleteAllCoursesByCardId(memberCardEntity.getId());
            // 插入新的课程关联信息，并返回操作结果
            return courseCardMapper.insertCardAndCourses(memberCardEntity.getId(), courseIdList) > 0;
        }catch (Exception e){
            log.error(e.getMessage());
            // 抛出更新失败的异常
            throw new RuntimeException("更新会员卡失败",e);
        }
    }
}
