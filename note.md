### mybatis plus 比较函数。。

> 原符号       <       <=      >       >=      <>
对应函数 lt()     le()    gt()    ge()    ne()
Mybatis-plus写法： queryWrapper.ge("create_time", localDateTime); Mybatis写法： where create_time >= #{localDateTime}


### 复合主键更新



### 在CourseCardServiceImpl.java中对插入成功的返回判断？？？？


传入为null ==> db也是空-----什么都不做
传入为null ==>db有数据-----删除数据
传入不为null ==>db是空-----添加
传入不为null ==>db有数据----对比添加或删除

全部删，
if (null empty){}
else if(null,some) {delete}
else if(some ,null) {insert}
else if (some ,some) {
## -------------------------------------------------------------------
会员绑定后的跳转怎么改变头部标签

### 添加预约  新增排课的时间冲入判断和复制排课的时间冲突判断 已完成
### 课程表预约和取消预约与全局预约设置的时间冲突  已完成
### 前端页面的单选框使用thymeleaf 判断选中可以使用
```html
<input id="deadlineMode_2" name="appointmentDeadlineMode"
                               th:field="${GLOBAL_SET.appointmentDeadlineMode}" type="radio"
                               value="2">

详见x_course_reservation.html
```

### 没有确认上课的也要超时自动扣次数



### 添加预约时 的卡次提醒错误       √

@Valid的错误回显和清楚重复错误提示
```java
                    for (var k in errors) {
                        //移除重复提示 - 会员添加
                        $("[tip="+k+"]").html("");
	        	        //错误提示回显
                        $("<span class='text-danger'>"+ errors[k] +"</span>").appendTo($("[tip="+k+"]"));
                    }
```

### 
>@NotNull：不能为null，但可以为empty，用在基本类型上
@NotEmpty：不能为null，而且长度必须大于0，用在集合类上面
@NotBlank：只能作用在String上，不能为null，而且调用trim()后，长度必须大于0


### 会员添加绑定的取消按钮         √
### 创建会员的创建时间没确定        √


## 会员单独绑定时的操作记录和充值记录    ----已经合并

## 重写会员列表返回

##避免重复绑卡        √

##预约和取消预约的操作记录？？应该是以上课做记录

=================================
###添加课程中如果全选会员卡的str会多个on √

##添加课程和更新课程的jsr303  添加会员预约表单

##一键扣费判空            √

##添加预约人数加到该课程的预约人数

## 注销会员和添加会员添加到操作记录 操作记录应该聚焦在会员卡！

