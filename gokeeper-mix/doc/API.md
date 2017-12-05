# API

###信息删除
```
DELETE /gokeeper/msg/{msgId}
```

参数
```
```

返回
```
    "code": 0,
    "msg": "成功",
    "data": []
```

###信息置顶
```
POST /gokeeper/msg/{msgId}
```

参数
```
```

返回
```
    "code": 0,
    "msg": "成功",
    "data": []
```

###信息界面展示And搜索消息
说明：此界面比较复杂，需要展示的信息规则不统一，大体分为以下几类：
1.多人参与系统监督类：例如运动类和聚餐类发的是昨日战况总结
2.他人监督类活动：显示的是另一套模板消息
3.邀请类消息(点进去就是一个加入页面ttp详情页)

```
GET /gokeeper/msg
```
参数
```
hidden: 1   //1查找所有公开消息，0查询所有隐藏消息
```
返回
```
{
    "code": 0,
    "msg": "成功",
    "data": [{
            "systemNewsList": [
              {
                  "id": "123423423",  //置顶需要传回对应id来改变weight
                  "newstype": 1,  //消息类型：系统消息
                  "username": "akk", //发起人name
                  "userIcon": "www.akak.cn/1.jpg", //用户头像
                  "newsname": "xx消息", //消息名称
                  "newsstatus": 0, //已读(0),未读(1)
                  "previewText": "预览信息",
                  "text": "aaaaa", //正文
                  "updateTime": "2017/10/01 16:01" //更新时间
                  "weight": 1,  //10为置顶消息，1为正常
              }
            ],
            "ttpNewsList": [
              {
                  "id": "1",  
                  "userTtpId": "12342342342" //对应user-ttpId
                  "ttpId": "abc123", //对应ttpid,拼接支付界面用
                  "ttpStatus": 1, //1准备2进行中3完结
                  "newstype": 2, //消息类型：ttp消息
                  "username": "akk", //发起人name
                  "userIcon": "www.akak.cn/1.jpg", //用户头像
                  "newsname": "xx消息", //消息名称
                  "newsstatus": 0, //已读(0),未读(1)
                  "startTime": "2017/10/01 18:00",   //开始时间精确到分
                  "finishTime": "2017/10/08 18:00",         //结束时间
                  "ifFinish": 1, //是否完成：0未完成，1完成，2请假
                  "finishnums": 4, //"完成人数"
                  "nofinishnums": 5 //未完成人数
                  "leavenums": 5,  //请假人数
                  "userDayBouns": 4.0,  //奖金变化数量
                  "userTotalBouns": 4.0,  //奖金总数
                  "previewText": "预览信息"
                  "updateTime": "2017/10/01 16:01" //更新时间
                  "weight": 1,  //10为置顶消息，1为正常
              }
            ],
            "inviteNewsList": [
              {
                  "id": "53463456354",
                  "newstype": 3, //消息类型：邀请消息
                  "username": "akk", //发起人name
                  "userIcon": "www.akak.cn/1.jpg", //用户头像
                  "newsname": "xx消息", //消息名称
                  "newsstatus": 0, //已读(0),未读(1)
                  "previewText": "预览信息",
                  "ttpId": "abc123", //邀请的ttpid,拼接加入界面用
                  "updateTime": "2017/10/01 16:01" //更新时间
                  "weight": 1,  //10为置顶消息，1为正常
              }
            ]
    }]
}
```

###消息详情页面

```
GET /gokeeper/msg/{msgId}
```
参数
```
```
返回(点击什么类型消息就返回什么类型的数据，前端做页面跳转时判断用什么页面模板)
```
{
    "code": 0,
    "msg": "成功",
    "data": [{
            "systemNewsList": [
              {
                  "id": "123423423",  //置顶需要传回对应id来改变weight
                  "newstype": 1,  //消息类型：系统消息
                  "username": "akk", //发起人name
                  "userIcon": "www.akak.cn/1.jpg", //用户头像
                  "newsname": "xx消息", //消息名称
                  "newsstatus": 0, //已读(0),未读(1)
                  "previewText": "预览信息",
                  "text": "aaaaa", //正文
                  "updateTime": "2017/10/01 16:01" //更新时间
                  "weight": 1,  //10为置顶消息，1为正常
              }
            ],
            "ttpNewsList": [
              {
                  "id": "1",  
                  "userTtpiD": "12342342342" //对应user-ttpId
                  "ttpId": "abc123", //对应ttpid,拼接支付界面用
                  "ttpStatus": 1, //1准备2进行中3完结
                  "newstype": 2, //消息类型：ttp消息
                  "username": "akk", //发起人name
                  "userIcon": "www.akak.cn/1.jpg", //用户头像
                  "newsname": "xx消息", //消息名称
                  "newsstatus": 0, //已读(0),未读(1)
                  "startTime": "2017/10/01 18:00",   //开始时间精确到分
                  "finishTime": "2017/10/08 18:00",         //结束时间
                  "ifFinish": 1, //是否完成：0未完成，1完成，2请假
                  "finishnums": 4, //"完成人数"
                  "nofinishnums": 5 //未完成人数
                  "leavesnums": 5,  //请假人数
                  "userDayBouns": 4.0,  //奖金变化数量
                  "userTotalBouns": 4.0,  //奖金总数
                  "previewText": "预览信息"
                  //"text": "aaaaa", //正文
                  "updateTime": "2017/10/01 16:01" //更新时间
                  "weight": 1,  //10为置顶消息，1为正常
              }
            ],
            "inviteNewsList": [
              {
                  直接跳到加入页面
              }
            ]
    }]
}
```

###创建TTP

```
POST /gokeeper/ttp/create
```

参数

```
ttpName: "我的跑步"
startTime: "2017/10/01 21:08" //ttp开始时间
finishTime: "2017/10/10 21:08" //ttp结束时间
ttpTarget: 1(非必填)目标，只有某些ttp有,现在为运动类
address: 长阳路//ttp地点(非必填)，只有某些ttp有
leaveNotesNums: 3(非必填)请假条默认值为0，只有某些ttp有
ifOpen: 0 //前端默认为0，不公开，只有公开了才有允许中途加入的按钮
ifJoin: 0 //前端默认为0，不允许中途加入
ifQuit: 0 //前端默认为0，不允许退出
deductionRation: 20 //扣减比例，默认为20，只有允许中途退出后才能更改
joinMoney: 1000 //加入金额
ttpType: 1 //根据用户的点击系统默认选择类型
faqiType: 1   //出资方式(1:集体出资；2:独立出资)
joinSelf: 1  //是否自己参加，1为默认自己参加
joinSelfMoney: 1000 //独立出资时的发起金额
/******以下属于创建人为监督方式ttp的新元素******/
supervisionperson: userId, //监督人
besupervisionperson: userId, //被监督人

```

返回

```
{
  "code": 0,
  "msg": "成功",
  "data": {
      "ttpId": "147283992738221" 
  }
}
```

###发起界面的ttp类型
说明：发起界面的ttp类型列表

```
GET /gokeeper/ttp/alltype
```

参数
```

```

返回

```
{
  "code": 0,
  "msg": "成功",
  "data": {
      "id": "147283992738221",
      "typeName": "运动",
      "imgUrl": "www.akaka.cn/1.jpg",
      "parentId": 1,
      "orderNum": 1
  }
}
```

###Go页预览信息列表
说明：Go界面的提醒消息要写成统一的外观格式，就是：websocket和ttp状态切换时会有提醒

```
GET /gokeeper/go
```

参数
```
currentDate: "2017/10/01" //当前时间，字符串即可，格式要正确
```

返回
```
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "userName": "安吉丽娜",
            "userIcon": "www.akaka.cn/icon.jpg",
            "ttpId": "abc1223", //隐藏字段，邀请好友时传递
            "ttpName": "100天疯狂跑",
            "ttpStatus": "准备开始",  //进行中、完结           
            "userTotalBouns": 350,
            "highAverage": "高于", //这里后台直接返还高/低，前端只用做拼接工作即可，减少压力
    //待定   "rank": 1,  //在一个ttp中
            "userCurrentRecord": "完成" //用户当天完成状态             
        }]
}
```

###Go界面信息展示，包括详情页面的信息
说明：Go界面的提醒消息要写成统一的外观格式，就是：websocket和ttp状态切换时会有提醒

```
GET /gokeeper/go/{ttpId}
```

参数
```
currentDate: "2017/10/01" //字符串即可,格式要正确
```

返回
```
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "username": "安吉丽娜",
            "userIcon": "www.akaka.cn/icon.jpg",
            "ttpId": "abc1223", //隐藏字段，邀请好友时传递
            "ttpName": "100天疯狂跑",
            "ttpStatus": 1,  //1准备开始，2进行中，3完结，4中途退出
            "startTime": "2017/10/01 18:00",   //开始时间精确到分
            "finishTime": "2017/10/08 18:00"         //结束时间
            "userTotalBouns": 350,
            "ttpSchedule": 25,  //完成进度
            "faqiType": 1, //1, "集体出资";2, "独立出资"
            "highAverage": "高于", //这里后台直接返还高/低，前端只用做拼接工作即可，减少压力
    //待定   "rank": 1,  //在一个ttp中
            "ttpTarget": "每日跑3公里",  //现只有运动类ttp有这个
            "joinMoney": 1000,
            "leaveNotesNums": 10, //总假条数量
            "leaveNotes": 2,  //已使用数量
            "ifQuit": "允许中途退出", 
            "ifJoin": "允许中途加入",
            "ifOpen": "公开", 
            "userCurrentRecord": "完成" //用户当天完成状态
            "userRecordList": [
              {//不止一天
                  "days": 2017/10/01,  //记录的时间
                  "dayStatus": 0 //完成状态:0未完成；1完成；2请假
              }
            ],
            "othersfinishList": [     //当天其他人的完成记录
              {
                  "username": "阿卡卡", //同一个ttp同伴名字
                  "dayStatus": "19:07" //完成的具体时间
              }],
            "othersnofinishList": [
              {
                  "username": "李子安",
                  "dayStatus": "2/null"
            }],
            "supervisionperson": "监督人username",
            "besupervisionperson": "被监督人username",
            "beuserRecordList": [
            {
                  "days": 2017/10/01,  //记录的时间
                  "dayStatus": "1 //完成状态
              }]
        }]
}
```

###Go界面完成功能
```
POST /gokeeper/finish/{ttpId}
```

参数
```
ttpId: "123123"
currentDate: "2017/10/01"
```

返回
```
{
  "code": 0,
  "msg": "成功",  //自己重新写成功的显示消息
  "data": []
}
```

###Go界面中途退出功能
```
POST /gokeeper/quit/{ttpId}
```

参数
```
ttpId: "123123"
currentDate: "2017/10/01"
```

返回
```
{
  "code": 0,
  "msg": "成功",  //自己重新写成功的显示消息
  "data": []
}
```

###加入界面预览ttp信息(所有公开ttp)
```
GET /gokeeper/join
```

参数
```
```

返回
```
{
  "code": 0,
  "msg": "成功",
  "data": [
    {
      "username": "安吉丽娜", //发起人姓名
      "userIcon": "www.akaka.cn/icon.jpg",
      "ttpName": "100天疯狂跑",
      "ttpId": "abc123",  //隐藏字段，点击加入时传递给后端使用
      "ttpType": "运动",  //建议只显示最大的分类
      "createTime": "2017/10/01 17:00", //创建时间
      "startTime": "2017/10/01 17:00", //开始时间
      "ttpTarget": "每日跑3公里",        //建议放在详情页面，现只有运动类有
      "allMoney": 1001,   //当前ttp奖金总额
      "joinPeopleNums": 100, //当前已加入人数
    },
    {
      "username": "阿卡卡", //发起人姓名
      "userIcon": "www.akaka.cn/icon.jpg",
      "ttpName": "聚餐",
      "ttpId": "abc123",  //隐藏字段，点击加入时传递给后端使用
      "ttpType": "生活-聚餐",
      "createTime": "2017/10/01 17:00", //创建时间
      "startTime": "2017/10/01 17:00", //开始时间
      //"ttpTarget": "",        //此活动就没有明确目标为空
      "allMoney": 1001,   //当前ttp奖金总额
      "joinPeopleNums": 100, //当前已加入人数
    }]
}
```


###加入界面某个ttp详情
```
GET /gokeeper/join/{ttpId}
```

参数
```
```

返回
```
{
  "code": 0,
  "msg": "成功",
  "data": [
    {
      "username": "安吉丽娜", //发起人姓名
      "userIcon": "www.akaka.cn/icon.jpg",
      "ttpName": "100天疯狂跑",
      "ttpId": "abc123",  //隐藏字段，点击加入时传递给后端使用
      "ttpType": "运动",  //建议只显示最大的分类
      "createTime": "2017/10/01 17:00", //创建时间
      "startTime": "2017/10/01 18:00",   //开始时间精确到分
      "finishTime": "2017/10/08 18:00"         //结束时间
      "ttpTarget": "每日跑3公里",        //建议放在详情页面，现只有运动类有
      "joinMoney": 1000,  //建议列表页显示为加入金额
      "allMoney": 1001,   //当前ttp奖金总额
      "joinPeopleNums": 100, //当前已加入人数
      "leaveNotesNums": 10, //假条数量
      "ifQuit": "不允许",
      "ifJoin": "允许"
    }]
}
```

###加入一个ttp
说明：创建成功后，会跳转到支付页面，期间会产生一条新ttp消息并发送给用户，如果用户操作不中断直接选择付款，后台则会刷新此消息的状态信息，所以最终返还给用户的消息时最新的

```
POST /gokeeper/join/{ttpId}
```
参数
```
ttpId: "1234567890"
```
返回
```
{
    "code": 0,
    "msg": "成功",
    "data": null
}
```

###高级搜索
```
GET /gokeeper/search
```
参数
```
ttpType: 1    //int类型（必传）
minMoney: "100.01"  //字符串
maxMoney: "100.02"  
startTime: "2017/11/11 11:11"   //字符串
finishTime: "2017/11/21 11:11" 
```
返回
```
{
    "code": 0,
    "msg": "成功",
    "data": 同加入列表页预览消息
}
```

###邀请好友(分为2步，查找好友和发送邀请消息)
说明：用户可以在ttp准备开始和进行中(需判断是否允许中途加入)点击邀请好友按钮，
按常理：ttp为完结状态时，应该不允许用户进行界面操作(按钮变灰，为不可点击状态)
邀请的好友只有在线时，websocket的方式才有效，否则消息还是通过数据库查询的方式返回的
首先点击邀请后，进行查询好友(手机号),显示好友，点击邀请后显示请求已发送
1.查找好友
```
GET /gokeeper/user/getUserByPhone
```

参数
```
searchmap: "17671732167"或"用户名"  //输入手机号和用户名
```

返回
```
{
    "code": 0,
    "msg": "成功",
    "data": [{
      username: "akk",
      userIcon: "www.akaka.cn/1.jpg"
    }]
}
```

2.发送邀请消息
```
POST /gokeeper/websocket/go/invite
```
参数
```
userId: "abd123" //接收者的id
ttpId: "12345567"
```
返回(前端用websocket接收)
```
{
    "code": 0,
    "msg": "成功",
    "data": [{
        "id": "abc123",
        "newstype": 3,  //消息类型
        "username": "akk", //发起人name
        "userIcon": "www.aka.cn/1.jpg", //发起人头像
        "newsname": "消息名称",
        "newsstatus": 0,  //状态，已读(0),未读(1);
        "previewText": "预览消息", 
        "ttpId": "abc123", //邀请的ttpid,拼接加入界面用"
        "updateTime": "2017/10/01 16:01" //更新时间
        "weight": 1,  //10为置顶消息，1为正常
    }]
}
```

###设置界面
```
GET /gokeeper/setting
```

参数
```
```

返回
```
{
    "code": 0,
    "msg": "成功",
    "data": [{
        "wxOpenid": "wnasfsdafasd",  //微信openid
        "phonenumber": "12124132124, //手机号
        "weiboId": "1111111111", //微博id
        "qqOpenid": "2222222222,  //QQopenId
        "username": "akk",  //用户名
        "birthday": "2017/10/01", 
        "sex": "1" //女
        "city": "孝感",
        "userIcon": "www.akkaka.cm?1.jpg", //用户头像
        "level": "0级赏金猎人",  //用户等级   
    }]
}
```

###设置界面用户信息的修改
```
POST /gokeeper/setting
```

参数
```
userIcon: "www.asdfsad.sadf",  //用户头像地址
username: "abc",  //用户名，必填
sex: 1, //女 必须为1或0或者为空
birthday: 2017/11/20, //生日，按这个格式即可
city: "孝感", //地点，可以为空
phonenumber: "17671732167"  //手机号 格式要正确，灰色信息：填写后可用手机号登录
```

返回
```
{
    "code": 0,
    "msg": "成功",
    "data": []
}
```

###设置界面QQ的绑定与解绑（成功后前端对样式做出改变即可）
绑定
```
POST /gokeeper/setting/qq/binding
```

参数
```
openid: "abc123"  //qq的openid
```

返回
```
{
    "code": 0,
    "msg": "成功",
    "data": []
}
```

解绑
```
POST /gokeeper/setting/qq/unbundling
```

参数
```
```

返回
```
{
    "code": 0,
    "msg": "成功",
    "data": []
}
```

###设置界面微信的绑定与解绑
绑定
```
POST /gokeeper/setting/wx/binding
```

参数
```
openid: "abc123"  //微信的openid
```

返回
```
{
    "code": 0,
    "msg": "成功",
    "data": []
}
```

解绑
```
POST /gokeeper/setting/wx/unbundling
```

参数
```
```

返回
```
{
    "code": 0,
    "msg": "成功",
    "data": []
}
```

###支付订单

重定向到：/gokeeper/wxpay/create

###注销
```
DELETE /gokeeper/session
```

参数
```
```

返回
```
{
}
```

###支付订单





