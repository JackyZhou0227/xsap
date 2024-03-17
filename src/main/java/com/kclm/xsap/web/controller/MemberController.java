package com.kclm.xsap.web.controller;

import com.kclm.xsap.model.entity.MemberEntity;
import com.kclm.xsap.model.vo.CardInfoVo;
import com.kclm.xsap.service.MemberCardService;
import com.kclm.xsap.service.MemberService;
import com.kclm.xsap.utils.BeanError;
import com.kclm.xsap.utils.file.ImgManger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final static Logger log = LoggerFactory.getLogger(MemberController.class);

    private final static String MEMBER_IMG_DIR = "member_img";
    @Resource
    private MemberService memberService;

    @Resource
    private MemberCardService memberCardService;

    @GetMapping("/x_member_import.do")
    public String toMemberImport() {
        log.info("前往member_import页面");
        return "member/x_member_import";
    }

    @GetMapping("/x_member_list.do")
    public String toMemberList() {
        log.info("toMemberList");
        return "member/x_member_list";
    }

    @PostMapping("/memberList.do")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> memberList() {
        Map<String, Object> returnData = new HashMap<>();
        returnData.put("memberList", memberService.getMemberVoList());
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    @GetMapping("/x_member_add.do")
    public String toMemberAdd() {
        log.info("前往member_add页面");
        return "member/x_member_add";
    }

    /**
     * 添加会员信息
     *
     * @param memberMsg     会员实体对象，包含会员信息
     * @param bindingResult 数据验证结果，用于存放验证错误信息
     * @return 返回一个响应实体，包含操作响应实体，包含操作结果代码和错误结果代码和错误信息（如果有）
     */
    @PostMapping("/memberAdd.do")
    public ResponseEntity<Map<String, Object>> memberAdd(@Valid MemberEntity memberMsg, BindingResult bindingResult) {
        log.info("添加会员");
        Map<String, Object> returnData = new HashMap<>();
        Map<String, String> errorMap = new HashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("bean验证错误");
            errorMap = BeanError.getErrorDataMap(bindingResult);
            returnData.put("errorMap", errorMap);
            returnData.put("code", 400);
            return new ResponseEntity<>(returnData, HttpStatus.BAD_REQUEST);
        }
        if (memberService.isPhoneExists(memberMsg.getPhone())) {
            log.info("手机号已存在");
            returnData.put("code", 400);
            errorMap.put("phone", "该手机号码已存在");
            returnData.put("errorMap", errorMap);
            return new ResponseEntity<>(returnData, HttpStatus.BAD_REQUEST);
        }
        memberMsg.setCreateTime(LocalDateTime.now());
        memberMsg.setLastModifyTime(LocalDateTime.now());

        if (memberService.save(memberMsg)) {
            returnData.put("code", 0);
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        } else {
            log.error("保存失败，未知错误");
            return new ResponseEntity<>(returnData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/x_member_list_details.do")
    public String toMemberListDetails(@RequestParam("id") Long id, Model model) {
        log.info("前往member_list_details页面，id=" + id);
        model.addAttribute("ID", id);
        return "member/x_member_list_details";
    }

    @PostMapping("/memberDetail.do")
    public ResponseEntity<Map<String, Object>> memberDetail(@RequestParam("id") Long id) {
        log.info("memberDetail.do，id=" + id);
        Map<String, Object> returnData = new HashMap<>();
        MemberEntity memberEntity = memberService.getById(id);
        log.info("name: " + memberEntity.getName());
        returnData.put("data", memberEntity);
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }


    @GetMapping("/toSearcherAll.do")
    public ResponseEntity<Map<String, Object>> toSearcherAll(@RequestParam("keyword") String keyword) {
        log.info("查找会员，keyword =" + keyword);
        Map<String, Object> returnData = new HashMap<>();
        List<MemberEntity> memberList = memberService.searchMembersByNameOrPhone(keyword);
        returnData.put("value", memberList);
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    /**
     * 修改会员头像
     *
     * @param id 会员ID，用于标识需要修改头像的会员
     * @param avatarUrl 会员头像文件，使用MultipartFile接收上传的文件
     * @return ResponseEntity<Map<String, Object>> 包含操作结果信息的响应实体：
     *         - 当操作成功时，返回状态码200（HttpStatus.OK），并包含上传成功的信息及更新后的会员数据；
     *         - 当操作失败时，返回状态码400（HttpStatus.BAD_REQUEST），并包含失败的原因。
     */
    @PostMapping("/modifyMemberImg.do")
    public ResponseEntity<Map<String, Object>> modifyMemberImg(@RequestParam("id") Long id, @RequestParam("avatarFile") MultipartFile avatarUrl) {
        log.info("修改会员头像，id=" + id);
        Map<String, Object> returnData = new HashMap<>();
        ApplicationHome applicationHome = new ApplicationHome(getClass());

        // 检查会员ID是否为空，以及会员是否存在
        if (id == null || memberService.getById(id) == null) {
            log.error("会员id为空或会员不存在");
            returnData.put("msg", "用户id为空或用户不存在");
            return new ResponseEntity<>(returnData, HttpStatus.BAD_REQUEST);
        }

        // 检查上传的头像文件是否为空
        if (avatarUrl.isEmpty()) {
            log.error("上传头像为空");
            returnData.put("msg", "上传头像为空");
            return new ResponseEntity<>(returnData, HttpStatus.BAD_REQUEST);
        }

        // 上传头像文件
        String filename = ImgManger.uploadImg(avatarUrl, applicationHome, MEMBER_IMG_DIR);
        if (filename == null) {
            log.error("上传头像失败");
            returnData.put("msg", "上传头像失败");
            return new ResponseEntity<>(returnData, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            log.info("修改上传头像成功");
            // 更新会员头像信息
            MemberEntity memberEntity = memberService.getById(id);
            memberEntity.setAvatarUrl(filename);
            memberEntity.setLastModifyTime(LocalDateTime.now());
            memberService.updateById(memberEntity);

            // 返回成功信息及更新后的会员数据
            returnData.put("code", 0);
            returnData.put("msg", "上传头像成功");
            returnData.put("userData", memberEntity);
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
    }

    @PostMapping("/x_member_edit.do")
    public ResponseEntity<Map<String, Object>> toMemberEdit(@RequestParam("id") Long id) {
        log.info("修改member信息，id=" + id);
        Map<String, Object> returnData = new HashMap<>();
        returnData.put("data", memberService.getById(id));
        return ResponseEntity.ok(returnData);
    }

    /**
     * 修改会员信息
     *
     * @param memberMsg 会员实体对象，包含要修改的会员信息
     * @param bindingResult 数据验证结果，用于存放验证错误信息
     * @return ResponseEntity<Map<String, Object>> 包含操作结果的状态码、消息和数据
     */
    @PostMapping("/memberEdit.do")
    public ResponseEntity<Map<String, Object>> memberEdit(@Valid MemberEntity memberMsg, BindingResult bindingResult) {

        log.info("修改会员信息");

        Map<String, Object> returnData = new HashMap<>();

        // 验证输入数据的正确性
        if (bindingResult.hasErrors()) {
            log.info("bean验证错误");
            returnData.put("code", 400);
            returnData.put("msg", BeanError.getErrorData(bindingResult));
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }

        // 检查手机号是否已存在
        if (memberService.isPhoneExists(memberMsg.getPhone()) && !memberMsg.getPhone().equals(memberService.getById(memberMsg.getId()).getPhone())) {
            log.info("手机号已存在");
            returnData.put("code", 400);
            returnData.put("msg", "该手机号码已存在");
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }

        // 更新会员信息的最后修改时间
        memberMsg.setLastModifyTime(LocalDateTime.now());

        // 更新会员信息
        if (memberService.updateById(memberMsg)) {
            log.info("修改成功");
            returnData.put("code", 0);
            returnData.put("msg", "修改成功");
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        } else {
            log.error("修改失败，未知错误");
            return new ResponseEntity<>(returnData, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/cardInfo.do")
    public ResponseEntity<Map<String, Object>> getCardInfo(@RequestParam("id") Long id) {
        log.info("获取会员持卡信息，id=" + id);
        Map<String, Object> returnData = new HashMap<>();
        List<CardInfoVo> cardInfoVoList = memberCardService.getCardsInfoByMemberId(id);
        for (CardInfoVo cardInfoVo : cardInfoVoList) {
            log.info("会员卡：" + cardInfoVo.getName() + "激活状态：" + cardInfoVo.getActiveStatus());
        }
        returnData.put("data", cardInfoVoList);
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    //todo 预约记录
    //todo 上课记录
    //todo 操作记录


}