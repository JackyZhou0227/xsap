package com.kclm.xsap.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kclm.xsap.entity.*;
import com.kclm.xsap.service.*;
import com.kclm.xsap.utils.R;
import com.kclm.xsap.utils.exception.RRException;
import com.kclm.xsap.utils.file.UploadImg;
import com.kclm.xsap.vo.ModifyPassword;
import com.kclm.xsap.vo.TeacherClassRecordVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 员工表
 *
 * @author fangkai
 * @email fk_qing@163.com
 * @date 2021-12-04 16:18:21
 */

@Slf4j
@Controller
@RequestMapping("/user")
public class EmployeeController {

    private static final String UPLOAD_IMAGES_TEACHER_IMG = "upload/images/teacher_img/";


    @Resource
    private EmployeeService employeeService;

    @Resource
    private ScheduleRecordService scheduleRecordService;

    @Resource
    private CourseService courseService;

    @Resource
    private ClassRecordService classRecordService;

    @Resource
    private MemberService memberService;

    @Resource
    private ReservationRecordService reservationRecordService;


    /**
     * 前往登录页面
     *
     * @return java.lang.string
     * @author fangkai
     * @date 2021/12/4 0004 16:43
     */
    @GetMapping({"/toLogin", "/", "/login"})
    public String toLogin() {
        return "x_login";
    }

    /**
     * 登录表单
     *
     * @param username 登录表单用户名
     * @param password 登录表单密码
     * @param session  登录成功保存session
     * @param model    登录失败返回页面携带数据
     * @return java.lang.String
     * @author fangkai
     * @date 2021/12/4 0004 16:45
     */
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        EmployeeEntity isExistEmp = employeeService.isExistEmp(username, password);
        Boolean flag = (isExistEmp != null);

        model.addAttribute("USER_NOT_EXIST", flag);
        if (flag) {
            session.setAttribute("LOGIN_USER", isExistEmp);
            return "redirect:/index";
        } else {
            return "x_login";
        }

    }

    /**
     * 跳转到老师员工管理页面
     *
     * @return x_teacher_list.html
     */
    @GetMapping("/x_teacher_list.do")
    public String togoTeacherList() {
        return "employee/x_teacher_list";
    }

    /**
     * 跳转添加老师页面
     *
     * @return x_teacher_add.html
     */
    @GetMapping("/x_teacher_add.do")
    public String togoTeacherAdd() {
        return "employee/x_teacher_add";
    }

    /**
     * 前端点击编辑后跳转到更新页面
     *
     * @param id 要编辑的id
     * @return x_teacher_update.html
     */
    @GetMapping("/x_teacher_update.do")
    public ModelAndView togoTeacherUpdate(@RequestParam("id") Long id) {
        EmployeeEntity teacherById = employeeService.getById(id);

        log.debug("\n==>根据前端传入的id查询数据库中的员工信息teacherById==>{}", teacherById);
        int samePhoneCount = employeeService.count(new QueryWrapper<EmployeeEntity>().eq("phone", teacherById.getPhone()));

        ModelAndView mv = new ModelAndView();
        if (samePhoneCount > 1) {
            mv.addObject("CHECK_PHONE_EXIST", true);
        }
        mv.setViewName("employee/x_teacher_update");
        mv.addObject("teacherMsg", teacherById);
        mv.addObject("birthdayStr", teacherById.getBirthday());
        log.debug("\n==>即将携带该员工信息跳转到员工编辑更新页面...");
        return mv;
    }

    /**
     * 跳转老师详情页
     *
     * @param id    老师id
     * @param model 携带老师id
     * @return x_teacher_list_data.html
     */
    @GetMapping("/x_teacher_list_data.do")
    public String togoTeacherListData(@RequestParam("id") Long id, Model model) {
        log.debug("\n==>前台传入的id：==>{}", id);
        model.addAttribute("ID", id);

        return "employee/x_teacher_list_data";
    }

    /**
     * 用户点击忘记密码跳转到充值页面
     *
     * @return x_ensure_user.html
     */
    @GetMapping("/toEnsureUser")
    public String toEnsureUser() {
        return "x_ensure_user";
    }

    /**
     * 携带数据跳转老师更新页面
     *
     * @param id 老师id
     * @return x_teacher_update.html（携带老师信息）
     */
    @GetMapping("/x_teacher_update")
    public ModelAndView updateTeacher(@RequestParam("id") Long id) {
        EmployeeEntity teacherById = employeeService.getById(id);

        ModelAndView mv = new ModelAndView();
        mv.addObject("teacherMsg", teacherById);
        mv.setViewName("employee/x_teacher_update");

        return mv;
    }

    /**
     * 跳转页面
     *
     * @param userPhoneOrEmail 表单提交的要重置密码的用户的手机号或者邮箱，注意电话和邮箱都没有做重复检查
     * @param model            页面携带数据
     * @return 返回页面
     */
    @GetMapping("/toResetPwd")
    public String toResetPwd(String userPhoneOrEmail, Model model) {
        //
        log.debug("\n==>打印用户要充值的用户手机号或者邮箱==>{}", userPhoneOrEmail);
        String emailRegex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        String phoneRegex = "^1[0-9]{10}$";

        boolean isAEmail = Pattern.compile(emailRegex).matcher(userPhoneOrEmail).matches();
        boolean isAPhone = Pattern.compile(phoneRegex).matcher(userPhoneOrEmail).matches();
        if (isAEmail || isAPhone) {
            //根据邮箱查询该用户     //由于保存的时候没有设置邮箱去重检查，所以假设可以存在相同邮箱【相容用户】
            List<EmployeeEntity> userOne = employeeService.list(new QueryWrapper<EmployeeEntity>().eq("role_email", userPhoneOrEmail).or().eq("phone", userPhoneOrEmail));
            //由于可能会用到该用户的信息，故不用count查个数
            if (userOne.isEmpty()) {
                //没查到该用户信息,返回提示到前台
                model.addAttribute("CHECK_USER_ERROR", true);
                return "x_ensure_user";
            }
            //查到信息，跳转页面
            return "send_mail_ok";
        } else {
            //格式不正确，返回提示到前台
            model.addAttribute("CHECK_INPUT_FORMAT", true);
            return "x_ensure_user";
        }


    }


    /**
     * 菜单栏点击管理员类型跳转修改密码页面
     *
     * @return x_modify_password.html
     */
    @GetMapping("/x_modify_password.do")
    public String modifyPassword() {
        return "x_modify_password";
    }


    /**
     * 修改密码操作
     *
     * @param entity 修改密码页面传入的表单数据
     * @param model  跳转页面携带数据
     * @return 跳转页面
     */
    @PostMapping("/modifyPwd.do")
    public String modifyPwd(ModifyPassword entity, Model model) {
        log.debug("\n==>打印前台传入的修改密码表单数据==>{}", entity);

        if (null == entity) {
            throw new RRException("修改数据表单为空", 22404);
        }
        if (entity.getOldPwd().isEmpty()) {
            model.addAttribute("CHECK_PWD_ERROR", 0);
            log.debug("\n==>原密码为空");
            return "x_modify_password";
        } else {
            if (!entity.getNewPwd().equals(entity.getPwd2())) {
                model.addAttribute("CHECK_PWD_ERROR", 2);
                log.debug("\n==>新密码两次不一样");
                return "x_modify_password";
            }
            EmployeeEntity employeeEntity = employeeService.getById(entity.getId());
            if (!employeeEntity.getRolePassword().equals(entity.getOldPwd())) {
                model.addAttribute("CHECK_PWD_ERROR", 1);
                log.debug("\n==>原密码不正确");
                return "x_modify_password";
            } else {
                employeeEntity.setRolePassword(entity.getNewPwd()).setVersion(employeeEntity.getVersion() + 1).setLastModifyTime(LocalDateTime.now());
                boolean isUpdatePwd = employeeService.updateById(employeeEntity);
                if (isUpdatePwd) {
                    log.debug("\n==>修改成功！");
                    return "redirect:/index/logout";
                } else {
                    throw new RRException("修改密码失败", 22405);
                }
            }
        }
    }


    /**
     * 菜单栏点击管理员类型跳转个人资料页面
     *
     * @return x_profile.html
     */
    @GetMapping("/x_profile.do")
    public String profile(Long id, Model model) {
        EmployeeEntity employeeServiceById = employeeService.getById(id);
        model.addAttribute("userInfo", employeeServiceById);
        return "x_profile";
    }


    /**
     * 返回所有老师信息给suggest提供搜索建议
     *
     * @return 所有老师信息
     */
    @GetMapping("/toSearch.do")
    @ResponseBody
    public R toSearch() {
        List<EmployeeEntity> allEmployeeList = employeeService.list();
        log.debug("\n==>返回到前端的所有老师信息allEmployeeList==>{}", allEmployeeList);
        return new R().put("value", allEmployeeList);
    }


    /**
     * 获取所有员工信息并返回给前端
     *
     * @return R ->员工数据【json】
     */
    @PostMapping("/teacherList.do")
    @ResponseBody
    public R teacherList() {
        List<EmployeeEntity> teachers = employeeService.list();
        return new R().put("data", teachers);
    }


    /**
     * 更新保存员工信息
     *
     * @param entity 前端传入的表单信息封装
     * @return R-> 更新是否成功
     */
    @PostMapping("/teacherEdit.do")
    @ResponseBody
    public R teacherEdit(EmployeeEntity entity) {
        //todo 加入jsr303
        log.debug("\n==>前端传入的要更新的员工信息的封装:entity==>{}", entity);

        //更新操作
        boolean isUpdate = employeeService.updateById(entity);
        log.debug("\n==>更新老师信息的结果==>{}", isUpdate);
        if (isUpdate) {
            return R.ok("更新成功！");
        } else {
            return R.error("更新失败！！");
        }
    }


    /**
     * 返回老师详情信息
     *
     * @param id 老师id
     * @return r-> teacherInfo
     */
    @PostMapping("/teacherDetail.do")
    @ResponseBody
    public R teacherDetail(@RequestParam("tid") Long id) {
        EmployeeEntity teacherInfo = employeeService.getById(id);
        log.debug("\n==>打印返回到前端的老师详情信息==>{}", teacherInfo);
        return R.ok().put("data", teacherInfo);
    }


    /**
     * 封装老师管理中的上课记录信息
     *
     * @param id 老师id
     * @return r-> 上课记录
     */
    @PostMapping("/teacherClassRecord.do")
    @ResponseBody
    public R teacherClassRecord(@RequestParam("tid") Long id) {
        log.debug("\n==>打印传入的teacherId==>{}", id);

        List<ScheduleRecordEntity> scheduleForTeacher = scheduleRecordService.list(new QueryWrapper<ScheduleRecordEntity>().eq("teacher_id", id));
        log.debug("\n==>打印该老师的所有排课计划==>{}", scheduleForTeacher);
        List<TeacherClassRecordVo> teacherClassRecordVos = scheduleForTeacher.stream().map(entity -> {
            //获取当前排课记录的id
            Long scheduleId = entity.getId();
            //获取当前排课的课程id
            Long courseId = entity.getCourseId();
            //根据课程id获取当前课程信息
            CourseEntity courseById = courseService.getById(courseId);
            //从课程信息里获取课程名
            String courseName = courseById.getName();
            //获取上课日期+时间
            LocalDateTime classTime = LocalDateTime.of(entity.getStartDate(), entity.getClassTime());
            //获取课程单位消耗次数
            Integer timesCost = courseById.getTimesCost();
            //在上课记录中查询所有上了当前课程的会员的id的list
            List<Long> memberIdList = classRecordService.list(new QueryWrapper<ClassRecordEntity>().select("member_id").eq("schedule_id", scheduleId).eq("check_status", 1)).stream().map(ClassRecordEntity::getMemberId).collect(Collectors.toList());

            //初始化创建一个用于存放会员名的list，当会员id为空时
            List<String> memberNames = new ArrayList<>();

            //只有当查到的会员id不为空时，才进行查询
            if (!memberIdList.isEmpty()) {
                memberNames = memberService.list(new QueryWrapper<MemberEntity>().select("name").in("id", memberIdList)).stream().map(MemberEntity::getName).collect(Collectors.toList());
            }
            //创建一个存放会员名的builder
            StringBuilder memberNameBuilder = new StringBuilder();
            if (!memberNames.isEmpty()) {
                memberNameBuilder.append("【");
                //将所有会员名添加进去，补上','
                memberNames.forEach(name -> memberNameBuilder.append(name).append(","));
                //将最后一个','删掉
                memberNameBuilder.deleteCharAt(memberNameBuilder.lastIndexOf(","));
                memberNameBuilder.append("】");
            } else {
                memberNameBuilder.append("  --");
            }

            //赋值vo并收集成list
            return new TeacherClassRecordVo()
                    .setCourseName(courseName)
                    .setClassTime(classTime)
//                    .setCardName(Arrays.toString(cardName))
                    .setCardName(memberNameBuilder.toString())
                    .setTimesCost(timesCost);
        }).collect(Collectors.toList());

        log.debug("\n==>打印返回到前台的老师上课记录信息是：==>{}", teacherClassRecordVos);

        return R.ok().put("data", teacherClassRecordVos);
    }


    /**
     * 头像更新
     * todo 回显。。
     *
     * @param id   要更新头像的老师的id
     * @param file 要更新的头像图片文件
     * @return r -> 更新结果
     */
    @PostMapping("/modifyUserImg.do")
    @ResponseBody
    public R modifyUserImg(@RequestParam("id") Long id,
                           @RequestParam("avatarFile") MultipartFile file) {

        if (!file.isEmpty()) {
            log.debug("\n==>文件上传...");
            String fileName = UploadImg.uploadImg(file, UPLOAD_IMAGES_TEACHER_IMG);
            if (StringUtils.isNotBlank(fileName)) {
                EmployeeEntity entity = new EmployeeEntity().setId(id).setAvatarUrl(fileName).setVersion(+1);
                boolean isUpdateAvatarUrl = employeeService.updateById(entity);
                log.debug("\n==>更新头像是否成功==>{}", isUpdateAvatarUrl);
                return new R().put("data", entity);
            } else {
                return R.error("头像上传失败");
            }

        }
        return R.error("头像未上传");

    }


    /**
     * 异步添加老师
     *
     * @param entity 前端封装的老师信息
     * @return r->添加是否成功
     */
    @PostMapping("/teacherAdd.do")
    @ResponseBody
    public R teacherAdd(@Valid EmployeeEntity entity, BindingResult bindingResult) {
        log.debug("\n==>前台传入的添加老师表单信息：==>{}", entity);

        if (bindingResult.hasErrors()) {
            HashMap<String, String> map = new HashMap<>();
            bindingResult.getFieldErrors().forEach(item -> {
                String message = item.getDefaultMessage();
                String field = item.getField();
                map.put(field, message);
            });
            return R.error(400, "非法参数").put("errorMap", map);
        }

        entity.setCreateTime(LocalDateTime.now()).setRolePassword("123");
        boolean isSave = employeeService.save(entity);
        log.debug("\n==>保存老师是否成功==>{}", isSave);
        if (isSave) {
            return R.ok("添加成功!");
        } else {
            return R.error("添加失败");
        }
    }


    /**
     * 删除老师
     * 1.删除成功。该老师已没有排课记录和计划）
     * 2.删除成功。该老师存在未完成的排课计划，但尚没有预约记录，
     * 2.1该老师没有已完成的排课记录，删除该老师信息后未完成的排课计划也会被删除
     * 2.2该老师存在已完成的排课记录，删除该老师信息后未完成的排课计划也会被删除，但已完成的排课记录会被保留
     * 3.删除失败。该老师存在未完成的排课记录且有预约，无法删除该老师
     * 4.删除成功。该老师没有未完成的排课计划，仅删除该老师信息后保留已有的排课记录
     *
     * @param id 老师id
     * @return r -> 删除结果和信息
     */
    //全在控制层异常都不好抛！！！找时间改回来！todo
    @PostMapping("/deleteOne.do")
    @ResponseBody
    @Transactional
    public R deleteOne(@RequestParam("id") Long id) {
        log.debug("\n==>前端传入的要删除的老师id：==>{}", id);
        //检查该老师的排课计划表
        List<ScheduleRecordEntity> allScheduleForCurrentTeacher = scheduleRecordService.list(new QueryWrapper<ScheduleRecordEntity>()
                .select("id","order_nums", "start_date", "class_time").eq("teacher_id", id));
        log.debug("\n==>打印该老师的所有排课计划表==>{}", allScheduleForCurrentTeacher);
        //当该老师没有排课记录和计划时,直接删除老师信息
        if (allScheduleForCurrentTeacher.isEmpty()) {
            log.debug("\n==>该老师已没有排课记录和计划,删除成功！！");
            boolean isRemove = employeeService.removeById(id);
            if (isRemove) {
                log.debug("\n==>删除成功@！");
                return R.ok("该老师已没有排课记录和计划");
            } else {
                log.debug("\n==>删除老师失败");
                throw new RuntimeException("删除失败！");
            }
        } else {
            //该老师有排课记录
            //取出该老师所有还未完成的排课计划
            List<ScheduleRecordEntity> allScheduleAfterThisMoment = allScheduleForCurrentTeacher.stream().filter(schedule -> {
                //获取上课精确完整日期
                LocalDateTime classStartDateTime = LocalDateTime.of(schedule.getStartDate(), schedule.getClassTime());
                //过滤留下该老师所有还未完成的排课计划【即此时此刻之后的排课计划】
                return classStartDateTime.isAfter(LocalDateTime.now());
            }).collect(Collectors.toList());

            if (allScheduleAfterThisMoment.isEmpty()) {
                //该老师没有未完成的排课计划，删除该老师信息后保留已有的排课记录
                boolean isRemove = employeeService.removeById(id);
                log.debug("\n==>该老师没有未完成的排课计划，仅删除该老师信息后保留已有的排课记录");
                if (isRemove) {
                    log.debug("\n==>删除老师信息成功!");
                    return R.ok("该老师存在排课记录但没有未完成的排课计划，仅删除该老师信息后保留已有的排课记录");
                } else {
                    log.debug("\n==>删除老师失败");
                    throw new RuntimeException("删除失败！");
                }
            } else {
                //该老师存在未完成的排课记录
                //获取所有未完成的排课计划的预约人数的和
                int sum = allScheduleAfterThisMoment.stream().mapToInt(ScheduleRecordEntity::getOrderNums).sum();
                log.debug("\n==>打印未完成的排课计划的预约人数之和==>{}", sum);
                if (sum > 0) {
                    //未完成的计划中已有会员进行预约，无法删除
                    log.debug("\n==>该老师未完成的排课计划中已有预约，预约总数：==>{}", sum);
                    return R.error("该老师存在未完成的排课记录且有预约，无法删除该老师");
                } else {
                    //获取未开始的全部排课的id-list
                    List<Long> scheduleIds = allScheduleAfterThisMoment.stream().map(ScheduleRecordEntity::getId).collect(Collectors.toList());
                    if (allScheduleAfterThisMoment.size() == allScheduleForCurrentTeacher.size()) {
                        //该老师只有未完成的排课记录，删除该老师信息后未完成的排课计划也会被删除，且没有预约记录
                        //删除老师
                        boolean isRemoveTeacher = employeeService.removeById(id);
                        //删除预约后取消预约导致预约人数显示0 的预约记录
                        reservationRecordService.remove(new QueryWrapper<ReservationRecordEntity>().in("schedule_id", scheduleIds));
                        //最后删除该老师的全部排课记录【因为没有已完成的记录】
                        boolean isRemoveSchedule = scheduleRecordService.remove(new QueryWrapper<ScheduleRecordEntity>().eq("teacher_id", id));
                        if (isRemoveSchedule && isRemoveTeacher) {
                            log.debug("\n==>删除成功");
                            return R.ok("该老师只有未完成的排课记录，删除该老师信息后未完成的排课计划也会被删除");
                        } else {
                            log.debug("\n==>删除老师信息或该老师排课记录失败");
                            //抛出异常回滚
                            throw new RRException("删除老师信息或该老师排课记录失败", 22405);
                        }
                    } else {

                        //该老师有已完成和未完成的排课记录，但未完成的排课计划没有预约记录 ，删除该老师信息后未完成的排课计划也会被删除，但已完成的排课记录会被保留
                        //首先通过老师id删除该老师
                        boolean isRemoveTeacher = employeeService.removeById(id);
                        //再通过该老师的所有未开始的全部排课计划的排课id删除所有未开始的排课的预约记录【目的是删除有预约但取消了的记录，此时预约人数未0，不用担心删错预约记录；而先删预约记录为了避免外键冲突】
                        boolean isRemoveReserveOfCancel = reservationRecordService.remove(new QueryWrapper<ReservationRecordEntity>().in("schedule_id", scheduleIds));
                        if (isRemoveReserveOfCancel) {
                            log.debug("\n==>删除了这些预约id==>{}在预约该课程后取消的用户", scheduleIds);
                        }
                        //最后删除这些未完成的排课记录【此时已经没有了外键约束】
                        boolean isRemoveSchedule = scheduleRecordService.removeByIds(scheduleIds);

                        if (isRemoveSchedule && isRemoveTeacher) {
                            log.debug("\n==>删除成功！");
                            return R.ok("该老师存在未完成的排课计划，删除该老师信息后未完成的排课计划也会被删除，但已完成的排课记录会被保留");
                        } else {
                            log.debug("\n==>删除老师信息或该老师排课记录失败");
                            //抛出异常 回滚
                            throw new RRException("删除老师信息或该老师排课记录失败", 22406);
                        }
                    }
                }
            }


        }

    }
}