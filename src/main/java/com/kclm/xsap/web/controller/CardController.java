package com.kclm.xsap.web.controller;

import com.kclm.xsap.model.dto.MemberCardDTO;
import com.kclm.xsap.model.entity.MemberCardEntity;
import com.kclm.xsap.model.vo.CardInfoVo;
import com.kclm.xsap.model.vo.CardTipVo;
import com.kclm.xsap.service.*;
import com.kclm.xsap.utils.BeanError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/card")
public class CardController {

    private final static Logger log = LoggerFactory.getLogger(CardController.class);
    @Resource
    private MemberCardService memberCardService;

    @Resource
    private CourseService courseService;

    @Resource
    private CourseCardService courseCardService;

    @Resource
    private MemberBindRecordService memberBindRecordService;

    @Resource
    private MemberLogService memberLogService;

    @GetMapping("/x_member_card.do")
    public String toMemberCard() {
        log.info("跳转至card页面");
        return "member/x_member_card";
    }

    @PostMapping("/cardList.do")
    public ResponseEntity<Map<String, Object>> cardList() {
        Map<String, Object> returnData = new HashMap<>();
        returnData.put("data", memberCardService.list());
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    @GetMapping("x_member_add_card.do")
    public String toMemberCardAdd(Model model) {
        log.info("跳转至card添加页面");
        return "member/x_member_add_card";
    }

    /**
     * 添加会员卡信息
     *
     * @param memberCardDTO 包含会员卡信息的数据传输对象，其中必须包含卡名和可选的课程列表
     * @param bindingResult 对memberCardDTO进行数据绑定和验证的结果
     * @return ResponseEntity 包含操作结果的状态码和数据。成功添加返回状态码200和相关数据，失败返回相应错误信息和状态码。
     */
    @PostMapping("/cardAdd.do")
    public ResponseEntity<Map<String, Object>> cardAdd(@Valid MemberCardDTO memberCardDTO, BindingResult bindingResult) {
        log.info("添加卡信息，卡名:" + memberCardDTO.getName());
        Map<String, Object> returnData = new HashMap<>();

        // 校验数据提交中的Bean Validation错误
        if (bindingResult.hasErrors()) {
            log.info("bean验证错误，cardAdd提交失败");
            log.info(BeanError.getErrorData(bindingResult));
            String errorMap = BeanError.getErrorData(bindingResult);
            returnData.put("code", 400);
            returnData.put("errorMap", errorMap);
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }

        // 检查是否存在重复的卡名
        if (memberCardService.isCardNameExist(memberCardDTO.getName())) {
            log.info("卡名已存在，cardAdd提交失败");
            returnData.put("msg", "卡名已存在");
            returnData.put("code", 406);
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }

        List<Long> courseIdList = memberCardDTO.getCourseListStr();
        // 验证并处理课程ID列表
        if (courseIdList != null && !courseIdList.isEmpty()) {
            courseIdList = keepValidCourseIds(courseIdList);
            if (courseIdList.isEmpty()) {
                log.info("提交的courseId不合法");
                returnData.put("msg", "请提交合法的课程id");
                returnData.put("code", 406);
                return new ResponseEntity<>(returnData, HttpStatus.OK);
            }
            memberCardDTO.setCourseListStr(courseIdList);
            // 保存会员卡信息
            if (memberCardService.saveMemberCardDTO(memberCardDTO)) {
                log.info("添加成功");
                returnData.put("msg", "添加成功");
                returnData.put("code", 0);
                return new ResponseEntity<>(returnData, HttpStatus.OK);
            } else {
                log.error("添加失败,未知错误");
                returnData.put("msg", "添加失败,未知错误！");
                return new ResponseEntity<>(returnData, HttpStatus.OK);
            }
        } else {
            log.info("提交的courseId为空");
            returnData.put("msg", "至少选择一个课程！");
            returnData.put("code", 406);
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
    }

    /**
     * 跳转到会员卡编辑页面。
     *
     * @param id    会员卡的ID，用于根据ID获取会员卡信息和相关的课程信息。
     * @param model Model对象，用于在跳转页面时传递数据。
     * @return 返回编辑页面的路径。
     */
    @GetMapping("/x_member_card_edit.do")
    public String toMemberCardEdit(@RequestParam("id") Long id, Model model) {
        log.info("跳转至card编辑页面,id=" + id);
        // 根据ID从数据库中获取会员卡信息
        MemberCardEntity cardMsg = memberCardService.getById(id);
        model.addAttribute("cardMsg", cardMsg);
        // 获取该会员卡关联的课程ID列表
        model.addAttribute("courseCarry", courseCardService.selectCourseIdsByCardId(id));
        return "member/x_member_card_edit";
    }


    /**
     * 编辑会员卡信息
     *
     * @param cardDTO       包含会员卡信息的数据传输对象，必须经过验证
     * @param bindingResult 验证结果，用于存放数据校验时产生的错误信息
     * @return 返回一个响应实体，包含操作结果信息（成功或失败）和相应的状态码
     */
    @PostMapping("/cardEdit.do")
    public ResponseEntity<Map<String, Object>> cardEdit(@Valid MemberCardDTO cardDTO, BindingResult bindingResult) {
        log.info("编辑卡信息,id=" + cardDTO.getId() + "，卡名:" + cardDTO.getName());
        Map<String, Object> returnData = new HashMap<>();

        // 验证传入的MemberCardDTO对象是否有错误
        if (bindingResult.hasErrors()) {
            log.info("bean验证错误，cardEdit提交失败");
            String errorData = BeanError.getErrorData(bindingResult);
            returnData.put("msg", errorData);
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }

        // 检查卡名是否已存在（不包括当前卡）
        if (memberCardService.isCardNameExist(cardDTO.getName()) && !cardDTO.getName().equals(memberCardService.getById(cardDTO.getId()).getName())) {
            log.info("用户提交的卡名已存在");
            returnData.put("msg", "卡名已存在");
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }

        // 更新会员卡的最后修改时间
        cardDTO.setLastModifyTime(LocalDateTime.now());

        // 尝试更新会员卡信息，成功或失败均会返回响应数据
        if (memberCardService.updateMemberCardDTO(cardDTO)) {
            returnData.put("code", 0);
            returnData.put("msg", "修改成功");
        } else {
            log.error("未知错误，cardEdit失败");
            returnData.put("msg", "修改失败");
        }

        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    public List<Long> keepValidCourseIds(List<Long> courseCarry) {
        Set<Long> allCourseIdsSet = new HashSet<>(courseService.getAllCourseIds());
        return courseCarry.stream().filter(allCourseIdsSet::contains).collect(Collectors.toList());
    }

    @PostMapping("/deleteOne.do")
    public ResponseEntity<Map<String, Object>> deleteOne(@RequestParam("id") Long id) {
        Map<String, Object> returnData = new HashMap<>();
        //todo
        if (memberCardService.removeById(id)) {
            returnData.put("status", "success");
            returnData.put("message", "删除成功");
        } else {
            returnData.put("status", "error");
            returnData.put("message", "删除失败");
        }
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }


    @PostMapping("/operateRecord.do")
    public ResponseEntity<Map<String, Object>> getOperateRecord(@RequestParam("memberId") Long memberId, @RequestParam("cardId") Long cardId) {
        log.info("获取操作记录，memberId= " + memberId + "；cardId= " + cardId);
        if (memberId == null || cardId == null) {
            log.warn("参数为空");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> returnData = new HashMap<>();
        returnData.put("data", memberLogService.getOperateRecordVoByMemberIdAndCardId(memberId, cardId));
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    /**
     * 激活操作接口。
     * 通过传入成员ID、绑定ID和状态，更新绑定状态，并返回操作结果。
     *
     * @param memberId 成员ID，用于标识成员。
     * @param bindId   绑定ID，用于标识成员和设备等的绑定关系。
     * @param status   状态，用于更新绑定关系的状态。
     * @return ResponseEntity<Map < String, Object>> 包含操作结果的数据实体。
     * 如果操作成功，返回状态码200和激活状态；
     * 如果操作失败，返回状态码500和错误消息。
     */
    @PostMapping("activeOpt.do")
    public ResponseEntity<Map<String, Object>> activeOpt(@RequestParam("memberId") Long memberId,
                                                         @RequestParam("bindId") Long bindId,
                                                         @RequestParam("status") Integer status) {
        // 记录激活操作的开始日志
        log.info("激活操作，memberId=" + memberId + ",bindId=" + bindId + ",status=" + status);
        Map<String, Object> returnData = new HashMap<>();

        // 更新绑定状态，并根据结果返回不同的数据
        if (memberBindRecordService.updateBindStatus(memberId, bindId, status)) {
            log.info("操作成功");
            // 操作成功，返回激活状态
            returnData.put("data", memberBindRecordService.getById(bindId).getActiveStatus());
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        } else {
            log.error("操作失败!");
            // 操作失败，返回错误消息
            returnData.put("msg", "操作失败");
            return new ResponseEntity<>(returnData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据会员ID查询会员卡信息
     *
     * @param memberId 会员ID，用于查询该会员的卡信息
     * @return ResponseEntity<Map < String, Object>> 返回一个响应实体，包含查询结果和HTTP状态码。
     * 其中，Map的"value"键对应的值为查询到的卡信息列表。
     */
    @PostMapping("/toSearchByMemberId.do")
    public ResponseEntity<Map<String, Object>> toSearchByMemberId(@RequestParam("memberId") Long memberId) {
        // 初始化返回数据容器
        Map<String, Object> returnData = new HashMap<>();
        // 根据会员ID查询卡信息
        List<CardInfoVo> cardInfoVoList = memberCardService.getCardsInfoByMemberId(memberId);
        // 将查询结果放入返回数据容器
        returnData.put("value", cardInfoVoList);
        // 返回响应实体，包含查询结果和HTTP状态码OK
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    @PostMapping("/cardTip.do")
    public ResponseEntity<Map<String, Object>> cardTip(@RequestParam("bindId") Long bindId, @RequestParam("scheduleId") Long scheduleId) {
        log.info("cardTip.do,cardId = " + bindId + ",scheduleId = " + scheduleId);
        Map<String, Object> returnData = new HashMap<>();
        CardTipVo cardTipVo = memberBindRecordService.getCardTip(bindId, scheduleId);
        log.info("可使用次数：" + cardTipVo.getCardTotalCount() + "课程消耗：" + cardTipVo.getCourseTimesCost());
        returnData.put("data", cardTipVo);
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }


    //todo 充值会员卡
    @PostMapping("/rechargeOpt.do")
    public ResponseEntity<Map<String, Object>> rechargeOpt(){
        log.info("rechargeOpt.do");
        Map<String, Object> returnData = new HashMap<>();

        return null;
    }

}