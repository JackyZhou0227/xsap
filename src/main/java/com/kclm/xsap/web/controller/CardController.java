package com.kclm.xsap.web.controller;

import com.kclm.xsap.model.dto.MemberCardDTO;
import com.kclm.xsap.model.entity.MemberCardEntity;
import com.kclm.xsap.service.CourseCardService;
import com.kclm.xsap.service.CourseService;
import com.kclm.xsap.service.MemberCardService;
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

    @PostMapping("/cardAdd.do")
    public ResponseEntity<Map<String, Object>> cardAdd(@Valid MemberCardDTO memberCardDTO, BindingResult bindingResult) {
        log.info("添加卡信息，卡名:" + memberCardDTO.getName());
        Map<String, Object> returnData = new HashMap<>();

        //Bean Validation 错误回显
        if (bindingResult.hasErrors()) {
            log.info("bean验证错误，cardAdd提交失败");
            log.info(BeanError.getErrorData(bindingResult));
            String errorMap = BeanError.getErrorData(bindingResult);
            returnData.put("code", 400);
            returnData.put("errorMap", errorMap);
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }

        //重复卡名
        if (memberCardService.isCardNameExist(memberCardDTO.getName())) {
            log.info("卡名已存在，cardAdd提交失败");
            returnData.put("msg", "卡名已存在");
            returnData.put("code", 406);
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
        List<Long> courseIdList = memberCardDTO.getCourseListStr();
        if (courseIdList != null && !courseIdList.isEmpty()) {
            courseIdList = keepValidCourseIds(courseIdList);
            if (courseIdList.isEmpty()) {
                log.info("提交的courseId不合法");
                returnData.put("msg", "请提交合法的课程id");
                returnData.put("code", 406);
                return new ResponseEntity<>(returnData, HttpStatus.OK);
            }
            memberCardDTO.setCourseListStr(courseIdList);
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

    @GetMapping("/x_member_card_edit.do")
    public String toMemberCardEdit(@RequestParam("id") Long id, Model model) {
        log.info("跳转至card编辑页面,id=" + id);
        MemberCardEntity cardMsg = memberCardService.getById(id);
        model.addAttribute("cardMsg", cardMsg);
        model.addAttribute("courseCarry", courseCardService.selectCourseIdsByCardId(id));
        return "member/x_member_card_edit";
    }

    @PostMapping("/cardEdit.do")
    public ResponseEntity<Map<String, Object>> cardEdit(@Valid MemberCardDTO cardDTO, BindingResult bindingResult) {
        log.info("编辑卡信息,id=" + cardDTO.getId() + "，卡名:" + cardDTO.getName());
        Map<String, Object> returnData = new HashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("bean验证错误，cardEdit提交失败");
            String errorData = BeanError.getErrorData(bindingResult);
            returnData.put("msg", errorData);
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
        if (memberCardService.isCardNameExist(cardDTO.getName()) && !cardDTO.getName().equals(memberCardService.getById(cardDTO.getId()).getName())) {
            log.info("用户提交的卡名已存在");
            returnData.put("msg", "卡名已存在");
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }

        cardDTO.setLastModifyTime(LocalDateTime.now());
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
    public ResponseEntity<Map<String, Object>> getOperateRecord(@RequestParam("memberId") Long memberId, @RequestParam("cardId") String cardId) {
        Map<String, Object> returnData = new HashMap<>();
        return null;
    }

}