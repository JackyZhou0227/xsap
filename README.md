# xsap

#### 介绍
X+ Student Appointment platform, X+学员约课平台，主要是针对K12的培训机构的会员、课程、约课安排的管理平台，支持操作人员后台约课、排课、日程计划等工作。

#### 开发者
Jacky Zhou - 2024.3
E-mail: 1143075643@qq.com

#### 部署说明
##### 数据库
数据库使用MySql，配置文件中默认数据库配置是本地MySql，数据库名称: xsap_dev7，数据库用户名：root，数据库密码：123456。
如果你想使用自己的数据库，请在配置文件中设置好数据库连接。
sql文件在db_design目录下，分为有示例数据和无数据版本，请自行导入数据库。
需要在t_member_card表和t_schedule_record表中自己加一下逻辑删除is_deleted字段，0表示未删除，1表示已删除。