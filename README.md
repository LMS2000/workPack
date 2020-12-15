# 5教学系统



# 项目背景：

1，现在学校需要重.............................................................................







# 需求分析：

1.用户登陆有三个身份 学生（student），教师（teacher）,管理员（admin）

2 .每个用户登陆之后所提供的功能都不相同，比如学生只能查看课程，自己的一些信息，教师可以选择开通一些课程

3.菜单子选项有 课程管理，学生管理，用户管理，日志管理，教师管理，

4，管理员可以发布一些信息到页面头部

5 . 管理员可以录入删除教师，学生

# 使用的技术栈：

## 前端： vue  ,jquery  ,css ,html,   element _ui ,       

## 后端： springboot  ,  poi ,

用户管理（学生端）





- 数据表user

- 

| 字段          | 类型         | 描述               | 作用                                                         |
| ------------- | ------------ | ------------------ | ------------------------------------------------------------ |
| uid           | bigint(11)   | 主键，用户账号的id | 提供后面student，teacher，admin类型用户指向绑定账号          |
| create_time   | datetime     | 用户账号创建时间   | 记录用户的账号注册时间                                       |
| udate_time    | datetime     | 用户账号修改时间   | 记录用户账号修改时间                                         |
| user_name     | varchar(255) | 用户名             | 用户名就是student类型用户的学号，teacher类型用户的工号，admin类型的特殊代码 |
| password      | varchar(255) | 密码               | 记录用户的密码                                               |
| role          | tinyint(2)   | 用户的角色类型     | 有三种1，2，3 （1表示学生，2表示admin,3表示教师）            |
| phone         | varchar(11)  | 用户的电话         | 方便联系                                                     |
| email         | varchar(255) | 用户的邮箱         | .....                                                        |
| hand_portrait | mediumblob   | 头像               | 后期去实现。。。                                             |





- 用户的登陆功能

  采用token+拦截器实现用户的登陆，同时在token载荷中存放有用户的id和用户名，方便了查找数据，比起传统的拦截器登陆提高了安全性。

![image-20201207123846177](C:\Users\YD\AppData\Roaming\Typora\typora-user-images\image-20201207123846177.png)



- 登陆的接口

- | 请求        | method | 是否带参数        | 返回数据                         |
  | ----------- | ------ | ----------------- | -------------------------------- |
  | /user/token | post   | userName,password | 一个map集合存放一个token和权限id |


- PS(学生的账号一般由教师端控制注册)



- 学生的选课功能

  1. 选修课

     选修课接口

     | 请求                      | method | 是否带参数                                              | 返回数据                                        |
     | ------------------------- | ------ | ------------------------------------------------------- | ----------------------------------------------- |
     | /course/courseList/{role} | get    | 需要一个role值，还需要从请求头中获取token中封装的userNo | 一个map集合存放状态码，描述信息和一个list结果集 |

   ps(其中的role参数是指用户的权限id，根据用户的权限选择要查找的内容)

- 学生查看自己的个人信息

  | 请求            | method | 是否带参数 | 返回数据                                        |
  | --------------- | ------ | ---------- | ----------------------------------------------- |
  | /student/getOne | get    | 无         | 一个map集合存放状态码，描述信息和一个list结果集 |

  

- 学生查看自己课表的功能

  1. 个人课表

     课表接口

     | 请求            | method | 是否带参数                        | 返回数据                                        |
     | --------------- | ------ | --------------------------------- | ----------------------------------------------- |
     | /course/student | get    | 需要从请求头中获取token中的userNo | 一个map集合存放状态码，描述信息和一个list结果集 |

     

​           ps( 课程查看成绩也是同一个接口)



- 学生退选课程功能

  1.退选接口

     

  | 请求                     | method | 是否带参数 | 返回数据                                        |
  | ------------------------ | ------ | ---------- | ----------------------------------------------- |
  | /course/findCourseDelete | get    | 无         | 一个map集合存放状态码，描述信息和一个list结果集 |
  | /course/{cid}            | delete | cid 课程名 | 一个map集合存放状态码，描述信息和一个list结果集 |




**教师管理**（教师端）



- teacher数据表

- | 字段         | 类型         | 描述         | 作用 |
  | ------------ | ------------ | ------------ | ---- |
  | tid          | bight(11)    | teacherId    |      |
  | create_time  | date time    | 记录创建时间 |      |
  | update_time  | date time    | 记录修改时间 |      |
  | gender       | tinyint（2） | 性别         |      |
  | class_no     | bight(11)    | 可以为空，   |      |
  | college_name | varchar(255) | 所属院系     |      |
  | teacher_no   | char（8）    | 教师工号     |      |
  | user_no      | bight(11)    | 对应user信息 |      |
  | teacher_name | varchar(255) | 教师姓名     |      |
  |              |              |              |      |

  

- 教师的权限：

- 可以任教课程，如果有班级的话可以管理班级的学生



- 教师任教

  1.任教接口

  | 请求                     | method | 是否带参数  | 返回值                                                       |
  | ------------------------ | ------ | ----------- | ------------------------------------------------------------ |
  | /course/courseList/{cid} | get    | cid         | 返回一个map集合带由一个状态码一个msg信息，一个list结果集     |
  | /course/teacher/{cid}    | post   | cid(课程id) | 返回一个map集合由一个状态码一个msg信息，一个list结果集（成功的list或失败的list） |

  ps（第一个是获取未任教课程的信息，第二个是给指定的课程授课）

  2.管理学生的接口

    

  | 请求     | method | 是否带参数               | 返回值                                                       |
  | -------- | ------ | ------------------------ | ------------------------------------------------------------ |
  | /student | post   | 需要一个student pojO对象 | 返回一个map集合由一个状态码一个msg信息，一个list结果集（成功或者失败） |
  | /student | put    | 需要一个student pojo     | 返回一个map集合由一个状态码一个msg信息，一个list结果集（成功或者失败） |

  ps(第一个方法是插入一个学生信息到数据库，第二个是修改指定学生的信息)

 

​       3.教师评分接口

​         

| 请求          | method | 是否带参数                         | 返回值                                                       |
| ------------- | ------ | ---------------------------------- | ------------------------------------------------------------ |
| /course/score | get    | 无                                 | 返回一个map集合由一个状态码一个msg信息，一个list结果集       |
| /course/score | put    | 一个course_student 中间表 kojo对象 | 返回一个map集合由一个状态码一个msg信息，一个list结果集（修改成功或者失败） |


admin管理



- 教师管理

  1.插入教师

    

  | 请求     | method | 是否带参数           | 返回值                                                       |
  | -------- | ------ | -------------------- | ------------------------------------------------------------ |
  | /teacher | post   | 一个teacher pojo对象 | 一个map集合由一个状态码一个msg信息，一个list返回结果集(插入成功或者失败) |

  2.获取全部教师

  | 请求                    | method | 是否带参数 | 返回值                                                 |
  | ----------------------- | ------ | ---------- | ------------------------------------------------------ |
  | /teacher/getTeacherList | get    | 无         | 一个map集合由一个状态码一个msg信息，一个list返回结果集 |



- 课程管理

  1.获取全部的课程

  | 请求                      | method | 是否带参数         | 返回值                                                 |
  | ------------------------- | ------ | ------------------ | ------------------------------------------------------ |
  | /course/courseList/{role} | get    | 需要一个role权限id | 一个map集合由一个状态码一个msg信息，一个list返回结果集 |

  2.插入课程

  | 请求    | method | 是否带参数           | 返回值                                                 |
  | ------- | ------ | -------------------- | ------------------------------------------------------ |
  | /course | post   | 一个course的pojo对象 | 一个map集合由一个状态码一个msg信息，一个list返回结果集 |

  3.导出课程到表格

    

  | 请求   | method | 是否带参数                    | 返回值          |
  | ------ | ------ | ----------------------------- | --------------- |
  | /print | post   | 一个formData添加一个excel文件 | 一个excel文件流 |

  导入课程

    

  | 请求   | method | 是否带参数    | 返回值                                                 |
  | ------ | ------ | ------------- | ------------------------------------------------------ |
  | /print | post   | 一个excel文件 | 一个map集合由一个状态码一个msg信息，一个list返回结果集 |

  

- 班级管理

  1.按照班级导入公修课

    （1）获取全部的班级名

  | 请求              | method | 是否带参数 | 返回值                                         |
  | ----------------- | ------ | ---------- | ---------------------------------------------- |
  | /class/allClassNo | get    | 无         | 一个map集合由一个状态码一个msg信，一个list集合 |

  ​     （2）获取全部公修课的课程名

  ​    

  | 请求               | method | 是否带参数 | 返回值                                         |
  | ------------------ | ------ | ---------- | ---------------------------------------------- |
  | /course/findAllCid | get    | 无         | 一个map集合由一个状态码一个msg信，一个list集合 |

     （3）按照班级将学生批量选课

  ​                       

  | 请求                    | method | 是否带参数                         | 返回值                                                       |
  | ----------------------- | ------ | ---------------------------------- | ------------------------------------------------------------ |
  | /course/{classNo}/{cid} | post   | classNo:指定班级名；cid:指定课程名 | 一个map集合由一个状态码一个msg信，一个list集合（修改成功或者失败） |

  2插入班级

   

  | 请求   | method | 是否带参数         | 返回值                                                       |
  | ------ | ------ | ------------------ | ------------------------------------------------------------ |
  | /class | post   | 一个class pojo对象 | 一个map集合由一个状态码一个msg信，一个list集合（插入成功或者失败） |

  3修改指定班级信息

  

​               

| 请求   | method | 是否带参数         | 返回值                                                       |
| ------ | ------ | ------------------ | ------------------------------------------------------------ |
| /class | put    | 一个class pojo对象 | 一个map集合由一个状态码一个msg信，一个list集合（修改成功或者失败） |

   4. 查找全部班级

      | 请求   | method | 是否带参数 | 返回值                                         |
      | ------ | ------ | ---------- | ---------------------------------------------- |
      | /class | get    | 无         | 一个map集合由一个状态码一个msg信，一个list集合 |

      
      结果集结构由一个状态码、一个msg信息、一个list结果集

- 状态码： 默认200
- msg信息： 默认"ok"
- data：如果get请求查找的集合中一般就是一个list集合，如果是post请求写入的请求一般就是一个map集合，里面封装了一个msg内容是相应的处理结果的信息 ，和一个resCodce 处理结果的状态码，成功：0200 ，失败：0406



   
