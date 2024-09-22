## 本项目实现的最终作用是基于SSH高校球场体育场预定管理系统
## 分为2个角色
### 第1个角色为管理员角色，实现了如下功能：
 - 会员信息管理
 - 公告管理
 - 球馆管理
 - 留言板管理
 - 管理员登录
 - 评论管理
 - 预订信息管理
### 第2个角色为用户首页，实现了如下功能：
 - 我的预定
 - 用户登录
 - 用户首页
 - 留言板
 - 预定场地
## 数据库设计如下：
# 数据库设计文档

**数据库名：** ssh_changguan_yuding

**文档版本：** 


| 表名                  | 说明       |
| :---: | :---: |
| [t_admin](#t_admin) | 管理员表 |
| [t_catelog](#t_catelog) |  |
| [t_gonggao](#t_gonggao) |  |
| [t_liuyan](#t_liuyan) |  |
| [t_pinglun](#t_pinglun) |  |
| [t_qch](#t_qch) |  |
| [t_user](#t_user) | 用户表 |
| [t_yuding](#t_yuding) |  |
| [t_yudingshijian](#t_yudingshijian) |  |
| [t_zlxx](#t_zlxx) |  |

**表名：** <a id="t_admin">t_admin</a>

**说明：** 管理员表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | UserId |   int   | 10 |   0    |    N     |  Y   |       | 用户ID  |
|  2   | username |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 用户名  |
|  3   | userPw |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 密码  |

**表名：** <a id="t_catelog">t_catelog</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | catelog_id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | catelog_name |   varchar   | 255 |   0    |    Y     |  N   |   NULL    |   |
|  3   | catelog_del |   varchar   | 255 |   0    |    Y     |  N   |   NULL    |   |

**表名：** <a id="t_gonggao">t_gonggao</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | gonggao_id |   int   | 10 |   0    |    N     |  Y   |       | 公告ID  |
|  2   | gonggao_title |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 公告标题  |
|  3   | gonggao_content |   varchar   | 5000 |   0    |    Y     |  N   |   NULL    | 公告内容  |
|  4   | gonggao_data |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 公告日期  |
|  5   | gonggao_fabuzhe |   varchar   | 255 |   0    |    Y     |  N   |   NULL    |   |
|  6   | gonggao_del |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 是否删除  |
|  7   | gonggao_one1 |   varchar   | 255 |   0    |    Y     |  N   |   NULL    |   |
|  8   | gonggao_one2 |   varchar   | 255 |   0    |    Y     |  N   |   NULL    |   |
|  9   | gonggao_one3 |   varchar   | 255 |   0    |    Y     |  N   |   NULL    |   |
|  10   | gonggao_one4 |   varchar   | 255 |   0    |    Y     |  N   |   NULL    |   |
|  11   | gonggao_one5 |   datetime   | 19 |   0    |    Y     |  N   |   NULL    |   |
|  12   | gonggao_one6 |   datetime   | 19 |   0    |    Y     |  N   |   NULL    |   |
|  13   | gonggao_one7 |   int   | 10 |   0    |    Y     |  N   |   NULL    |   |
|  14   | gonggao_one8 |   int   | 10 |   0    |    Y     |  N   |   NULL    |   |

**表名：** <a id="t_liuyan">t_liuyan</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | liuyan_id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | liuyan_title |   varchar   | 255 |   0    |    Y     |  N   |   NULL    |   |
|  3   | liuyan_content |   varchar   | 5000 |   0    |    Y     |  N   |   NULL    |   |
|  4   | liuyan_date |   varchar   | 255 |   0    |    Y     |  N   |   NULL    |   |
|  5   | liuyan_user |   varchar   | 255 |   0    |    Y     |  N   |   NULL    |   |

**表名：** <a id="t_pinglun">t_pinglun</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | ID  |
|  2   | qch_id |   int   | 10 |   0    |    Y     |  N   |   NULL    |   |
|  3   | content |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 内容  |
|  4   | shijian |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 时间  |

**表名：** <a id="t_qch">t_qch</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | ID  |
|  2   | qcbh |   varchar   | 255 |   0    |    Y     |  N   |   NULL    |   |
|  3   | area |   varchar   | 255 |   0    |    Y     |  N   |   NULL    |   |
|  4   | jieshao |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 介绍  |
|  5   | fujian |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 附件  |
|  6   | qianshu |   int   | 10 |   0    |    Y     |  N   |   NULL    |   |
|  7   | catelog_id |   int   | 10 |   0    |    Y     |  N   |   NULL    |   |
|  8   | del |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 是否删除  |

**表名：** <a id="t_user">t_user</a>

**说明：** 用户表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | user_id |   int   | 10 |   0    |    N     |  Y   |       | 用户ID  |
|  2   | user_name |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 用户名  |
|  3   | user_pw |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 用户密码  |
|  4   | user_realname |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 用户真实名字  |
|  5   | user_address |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 用户地址  |
|  6   | user_sex |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 用户性别  |
|  7   | user_tel |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 用户电话  |
|  8   | user_email |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 用户邮箱  |
|  9   | user_qq |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 用户QQ  |
|  10   | fujian |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 附件  |
|  11   | user_type |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 用户类型  |
|  12   | user_del |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 是否删除  |
|  13   | userIDNo |   varchar   | 255 |   0    |    Y     |  N   |   NULL    |   |

**表名：** <a id="t_yuding">t_yuding</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | ID  |
|  2   | userid |   int   | 10 |   0    |    Y     |  N   |   NULL    | 用户ID  |
|  3   | qchId |   int   | 10 |   0    |    Y     |  N   |   NULL    |   |
|  4   | tianshu |   int   | 10 |   0    |    Y     |  N   |   NULL    |   |
|  5   | yudingzheTel |   varchar   | 255 |   0    |    Y     |  N   |   NULL    |   |
|  6   | shijian |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 时间  |
|  7   | del |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 是否删除  |
|  8   | sjdlist |   varchar   | 255 |   0    |    Y     |  N   |   NULL    |   |
|  9   | ydrq |   varchar   | 255 |   0    |    Y     |  N   |   NULL    |   |
|  10   | paystatus |   varchar   | 2 |   0    |    Y     |  N   |   '0'    | 0:未支付，1：已支付  |
|  11   | hystatus |   varchar   | 2 |   0    |    Y     |  N   |   '0'    | 0：未核验，1：已核验  |
|  12   | hycode |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 核验码  |
|  13   | jingdu |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 未开始，已完成  |

**表名：** <a id="t_yudingshijian">t_yudingshijian</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | qchId |   int   | 10 |   0    |    Y     |  N   |   NULL    |   |
|  3   | shijian |   varchar   | 255 |   0    |    Y     |  N   |   NULL    | 创建时间  |
|  4   | time1 |   varchar   | 22 |   0    |    Y     |  N   |   NULL    |   |
|  5   | time2 |   varchar   | 22 |   0    |    Y     |  N   |   NULL    |   |
|  6   | time3 |   varchar   | 22 |   0    |    Y     |  N   |   NULL    |   |
|  7   | time4 |   varchar   | 22 |   0    |    Y     |  N   |   NULL    |   |
|  8   | time5 |   varchar   | 22 |   0    |    Y     |  N   |   NULL    |   |
|  9   | time6 |   varchar   | 22 |   0    |    Y     |  N   |   NULL    |   |
|  10   | time7 |   varchar   | 22 |   0    |    Y     |  N   |   NULL    |   |
|  11   | time8 |   varchar   | 22 |   0    |    Y     |  N   |   NULL    |   |
|  12   | time9 |   varchar   | 22 |   0    |    Y     |  N   |   NULL    |   |
|  13   | time10 |   varchar   | 22 |   0    |    Y     |  N   |   NULL    |   |
|  14   | time11 |   varchar   | 22 |   0    |    Y     |  N   |   NULL    |   |
|  15   | time12 |   varchar   | 22 |   0    |    Y     |  N   |   NULL    |   |

**表名：** <a id="t_zlxx">t_zlxx</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |   0    |   |
|  2   | qch_id |   int   | 10 |   0    |    Y     |  N   |   NULL    |   |
|  3   | kehuming |   varchar   | 255 |   0    |    Y     |  N   |   NULL    |   |
|  4   | kehuzheng |   varchar   | 255 |   0    |    Y     |  N   |   NULL    |   |
|  5   | rushijian |   varchar   | 255 |   0    |    Y     |  N   |   NULL    |   |
|  6   | lishijian |   varchar   | 255 |   0    |    Y     |  N   |   NULL    |   |
|  7   | feiyong |   int   | 10 |   0    |    Y     |  N   |   NULL    |   |

