package com.kclm.xsap.web.controller;

import com.kclm.xsap.model.dto.CourseDTO;
import com.kclm.xsap.model.entity.CourseEntity;
import com.kclm.xsap.service.CourseCardService;
import com.kclm.xsap.service.CourseService;
import com.kclm.xsap.service.MemberCardService;
import com.kclm.xsap.utils.BeanError;
import lombok.extern.slf4j.Slf4j;
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
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/course")
public class CourseController {

    @Resource
    private CourseService courseService;
    @Resource
    private MemberCardService memberCardService;
    @Resource
    private CourseCardService courseCardService;

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
        model.addAttribute("cardCarry", courseCardService.selectCardIdsByCourseId(id));
        return "course/x_course_list_edit";
    }

    @GetMapping("x_course_list_add.do")
    public String toCourseListAdd() {
        log.info("前往courseListAdd页面");
        return "course/x_course_list_add";
    }

    /**
     * 添加课程信息
     *
     * @param courseDTO     包含课程信息的数据传输对象，必须是有效的课程信息
     * @param bindingResult 对courseDTO进行验证后的结果，包含验证错误信息
     * @return 返回一个实体响应，包含操作结果代码、错误信息等
     */
    @PostMapping("/courseAdd.do")
    public ResponseEntity<Map<String, Object>> courseAdd(@Valid CourseDTO courseDTO, BindingResult bindingResult) {
        log.info("添加课程，课程名" + courseDTO.getName());
        Map<String, Object> returnData = new HashMap<>();
        // 验证课程信息输入是否有错误
        if (bindingResult.hasErrors()) {
            log.info("Bean验证错误");
            log.info(BeanError.getErrorData(bindingResult));
            Map<String, String> errorMap = BeanError.getErrorDataMap(bindingResult);
            returnData.put("code", 400);
            returnData.put("errorMap", errorMap);
            return ResponseEntity.ok(returnData);
        }

        // 检查课程名称是否已存在
        if (courseService.isCourseNameExist(courseDTO.getName())) {
            log.info("课程名已存在");
            returnData.put("code", 406);
            returnData.put("msg", "课程名已存在");
            return ResponseEntity.ok(returnData);
        }

        // 处理课程限制年龄和人数的默认值
        if (courseDTO.getLimitAgeRadio() == 0) {
            courseDTO.setLimitAge(0);
        }

        if (courseDTO.getLimitCountsRadio() == 0) {
            courseDTO.setLimitCounts(0);
        }

        // 处理选择的会员卡列表
        List<Long> cardIdList = courseDTO.getCardListStr();
        if (cardIdList != null && !cardIdList.isEmpty()) {
            cardIdList = keepValidCardIds(cardIdList);
            if (cardIdList == null || cardIdList.isEmpty()) {
                log.info("未选择会员卡1");
                returnData.put("code", 406);
                returnData.put("msg", "请至少选择一张会员卡");
                return ResponseEntity.ok(returnData);
            }
            courseDTO.setCardListStr(cardIdList);
            // 保存课程信息
            if (courseService.saveCourseDTO(courseDTO)) {
                log.info("添加成功");
                returnData.put("code", 0);
                returnData.put("msg", "添加成功");
                return ResponseEntity.ok(returnData);
            } else {
                log.error("添加失败");
                returnData.put("msg", "未知错误");
                return ResponseEntity.ok(returnData);
            }
        } else {
            log.info("未选择会员卡2");
            returnData.put("code", 406);
            returnData.put("msg", "请至少选择一张会员卡");
            return ResponseEntity.ok(returnData);
        }

    }

    /**
     * 编辑课程信息
     *
     * @param courseDTO     包含课程编辑信息的数据传输对象，包括课程ID、名称等
     * @param bindingResult 对courseDTO进行验证后的结果，包含验证错误信息
     * @return ResponseEntity 包含编辑结果的响应实体，包括成功与否的消息和状态码
     */
    @PostMapping("/courseEdit.do")
    public ResponseEntity<Map<String, Object>> courseEdit(@Valid CourseDTO courseDTO, BindingResult bindingResult) {
        log.info("编辑课程，课程id=" + courseDTO.getId());

        Map<String, Object> returnData = new HashMap<>();
        // 验证courseDTO中的数据是否符合要求
        if (bindingResult.hasErrors()) {
            log.info("Bean验证错误");
            log.info(BeanError.getErrorData(bindingResult));
            returnData.put("msg", BeanError.getErrorData(bindingResult));
            return ResponseEntity.ok(returnData);
        }
        // 根据课程ID获取课程实体
        CourseEntity courseEntity = courseService.getById(courseDTO.getId());
        // 检查课程名称是否已存在
        if (courseService.isCourseNameExist(courseDTO.getName()) && !courseDTO.getName().equals(courseEntity.getName())) {
            log.info("课程名已存在");
            returnData.put("msg", "课程名已存在");
            return ResponseEntity.ok(returnData);
        }
        List<Long> cardIdList = courseDTO.getCardListStr();

        // 判断并处理传入的会员卡ID列表，过滤无效卡号
        if (cardIdList != null && !cardIdList.isEmpty()) {
            cardIdList = keepValidCardIds(cardIdList);
            // 过滤后判断列表是否为空，为空则表示没有有效卡号
            if (cardIdList == null || cardIdList.isEmpty()) {
                log.info("用户传入了无效的会员卡号");
                returnData.put("msg", "请选择有效的会员卡");
                return ResponseEntity.ok(returnData);
            }

            courseDTO.setCardListStr(cardIdList);

            // 处理课程的限制年龄和限制人数选项，无限制时设置为0
            if (courseDTO.getLimitAgeRadio() == 0) {
                courseDTO.setLimitAge(0);
            }
            if (courseDTO.getLimitCountsRadio() == 0) {
                courseDTO.setLimitCounts(0);
            }

            // 更新课程信息
            if (courseService.updateCourseDTO(courseDTO)) {
                log.info("编辑成功");
                returnData.put("code", 0);
                returnData.put("msg", "编辑成功");
            } else {
                log.error("编辑失败");
                returnData.put("msg", "未知错误");
            }
            return ResponseEntity.ok(returnData);
        } else {
            // 用户未选择任何会员卡
            log.info("用户未选择任何会员卡");
            returnData.put("msg", "请至少选择一张会员卡");
            return ResponseEntity.ok(returnData);
        }
    }


    /**
     * 移除不在所有有效卡号列表中的卡号
     *
     * @param cardCarry 包含待处理卡号的列表。
     * @return 返回一个仅包含存在于有效卡号列表中的新列表。
     */
    public List<Long> keepValidCardIds(List<Long> cardCarry) {
        Set<Long> allCardIdsSet = new HashSet<>(memberCardService.getAllCardIds());
        return cardCarry.stream().filter(allCardIdsSet::contains).collect(Collectors.toList());
    }

    @GetMapping("/toSearch.do")
    public ResponseEntity<Map<String, Object>> toSearchCourses(@RequestParam("keyword") String keyword) {
        log.info("排课操作查找课程，keyword =" + keyword);
        Map<String, Object> returnData = new HashMap<>();
        List<CourseEntity> courseEntityList = courseService.selectCoursesByKeyword(keyword);
        returnData.put("value", courseEntityList);
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    @PostMapping("/getOneCourse.do")
    public ResponseEntity<Map<String, Object>> getOneCourse(@RequestParam("id") Long id) {
        log.info("获取单个课程信息，课程id=" + id);
        Map<String, Object> returnData = new HashMap<>();
        CourseEntity courseEntity = courseService.getById(id);
        returnData.put("data", courseEntity);
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }
}