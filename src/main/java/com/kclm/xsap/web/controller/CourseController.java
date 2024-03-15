package com.kclm.xsap.web.controller;

import com.kclm.xsap.model.dto.CourseDTO;
import com.kclm.xsap.model.entity.CourseEntity;
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
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/course")
public class CourseController {
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);
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

    @PostMapping("/courseAdd.do")
    public ResponseEntity<Map<String, Object>> courseAdd(@Valid CourseDTO courseDTO, BindingResult bindingResult) {
        log.info("添加课程，课程名" + courseDTO.getName());
        Map<String, Object> returnData = new HashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Bean验证错误");
            log.info(BeanError.getErrorData(bindingResult));
            Map<String, String> errorMap = BeanError.getErrorDataMap(bindingResult);
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

        if (courseDTO.getLimitAgeRadio()==0){
            courseDTO.setLimitAge(0);
        }
        if (courseDTO.getLimitCountsRadio()==0){
            courseDTO.setLimitCounts(0);
        }

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

    @PostMapping("/courseEdit.do")
    public ResponseEntity<Map<String, Object>> courseEdit(@Valid CourseDTO courseDTO, BindingResult bindingResult) {
        log.info("编辑课程，课程id=" + courseDTO.getId());

        Map<String, Object> returnData = new HashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Bean验证错误");
            log.info(BeanError.getErrorData(bindingResult));
            returnData.put("msg", BeanError.getErrorData(bindingResult));
            return ResponseEntity.ok(returnData);
        }
        CourseEntity courseEntity = courseService.getById(courseDTO.getId());
        if (courseService.isCourseNameExist(courseDTO.getName()) && !courseDTO.getName().equals(courseEntity.getName())) {
            log.info("课程名已存在");
            returnData.put("msg", "课程名已存在");
            return ResponseEntity.ok(returnData);
        }
        List<Long> cardIdList = courseDTO.getCardListStr();

        //传入的cardListStr可能为空，如果不做判断，在进行keepValidCardIds时，会报空指针异常
        if (cardIdList != null && !cardIdList.isEmpty()) {
            cardIdList = keepValidCardIds(cardIdList);
            //判断过滤掉无效值的cardIdList是否为空
            if (cardIdList == null || cardIdList.isEmpty()) {
                log.info("用户传入了无效的会员卡号");
                returnData.put("msg", "请选择有效的会员卡");
                return ResponseEntity.ok(returnData);
            }

            courseDTO.setCardListStr(cardIdList);

            //前端checkbox有两个选项，无限制，有限制+岁数，无限制时，前端传入Radio值-1，此时要把limitAge和limitCounts设置为-1,表示无限制
            if (courseDTO.getLimitAgeRadio()==0){
                courseDTO.setLimitAge(0);
            }
            if (courseDTO.getLimitCountsRadio()==0){
                courseDTO.setLimitCounts(0);
            }

            if (courseService.updateCourseDTO(courseDTO)) {
                log.info("编辑成功");
                returnData.put("code", 0);
                returnData.put("msg","编辑成功");
            } else {
                log.error("编辑失败");
                returnData.put("msg", "未知错误");
            }
            return ResponseEntity.ok(returnData);
        } else {
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

}