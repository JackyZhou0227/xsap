package com.kclm.xsap.web.controller;

import com.kclm.xsap.model.dto.CourseDTO;
import com.kclm.xsap.model.entity.CourseEntity;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/course")
public class CourseController {
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);
    @Resource
    private CourseService courseService;
    @Resource
    private MemberCardService memberCardService;

    @GetMapping("/x_course_list.do")
    public String toCourseList() {
        log.info("前往courseList页面");
        return "course/x_course_list";
    }

    @PostMapping("/courseList.do")
    public ResponseEntity<Map<String, Object>> courseList() {
        Map<String, Object> returnData = new HashMap<>();
        returnData.put("courseList", courseService.list());
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    @GetMapping("/x_course_list_edit.do")
    public String toCourseListEdit(@RequestParam("id") Long id, Model model) {
        log.info("前往courseListEdit页面");
        CourseEntity courseEntity = courseService.getById(id);
        model.addAttribute("courseInfo", courseEntity);
        model.addAttribute("cardCarry", memberCardService.getBindCardIdsByCourseId(id));
        return "course/x_course_list_edit";
    }

    @GetMapping("x_course_list_add.do")
    public String toCourseListAdd() {
        log.info("前往courseListAdd页面");
        return "course/x_course_list_add";
    }

    @PostMapping("/courseAdd.do")
    public ResponseEntity<Map<String, Object>> courseAdd(@Valid CourseDTO courseDTO, BindingResult bindingResult) {
        log.info("添加课程，课程名" + courseDTO.getName());
        Map<String, Object> returnData = new HashMap<>();
        Map<String, String> errorMap = BeanError.getErrorDataMap(bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Bean验证错误");
            returnData.put("code", 400);
            returnData.put("errorMap", errorMap);
            return ResponseEntity.ok(returnData);
        }

        if (courseService.isCourseNameExist(courseDTO.getName())) {
            log.info("课程名已存在");
            returnData.put("code", 406);
            returnData.put("msg", "课程名已存在");
            return ResponseEntity.ok(returnData);
        }

        List<Long> cardIdList = courseDTO.getCardListStr();
        if (cardIdList != null && !cardIdList.isEmpty()){
            cardIdList = removeInvalidCardId(cardIdList);
            if (cardIdList == null || cardIdList.isEmpty()) {
                log.info("未选择会员卡1");
                returnData.put("code", 406);
                returnData.put("msg", "请至少选择一张会员卡");
                return ResponseEntity.ok(returnData);
            }
            courseDTO.setCardListStr(cardIdList);
            if (courseService.saveCourseDTO(courseDTO)){
                log.info("添加成功");
                returnData.put("code",0);
                return ResponseEntity.ok(returnData);
            }else {
                log.error("添加失败");
                returnData.put("msg", "未知错误");
                return ResponseEntity.ok(returnData);
            }
        }else {
            log.info("未选择会员卡2");
            returnData.put("code", 406);
            returnData.put("msg", "请至少选择一张会员卡");
            return ResponseEntity.ok(returnData);
        }

    }

    //移除无效的卡号
    public List<Long> removeInvalidCardId(List<Long> cardCarry) {
        return cardCarry.stream()
                .filter(cardId -> cardId != -1)
                .collect(Collectors.toList());
    }
}