# API

###信息界面展示And搜索消息
说明：此界面比较复杂，需要展示的信息规则不统一，大体分为以下几类：
1.多人参与系统监督类：例如运动类和聚餐类发的是昨日战况总结
2.他人监督类活动：显示的是另一套模板消息
3.邀请类消息(点进去就是一个加入页面ttp详情页)

```
GET /gokeeper/msg/list
```
参数
```
hidden: 0   //0查找所有公开消息，1查询所有隐藏消息
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
                  "id": "12342342342",  //对应user-ttpId
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
                  "id": "53463456354",
                  "newstype": 3, //消息类型：邀请消息
                  "username": "akk", //发起人name
                  "userIcon": "www.akak.cn/1.jpg", //用户头像
                  "newsname": "xx消息", //消息名称
                  "newsstatus": 0, //已读(0),未读(1)
                  "previewText": "预览信息",
                  //"text": "aaaaa", //正文
                  "ttpId": "abc123", //邀请的ttpid,拼接加入界面用
                  "updateTime": "2017/10/01 16:01" //更新时间
                  "weight": 1,  //10为置顶消息，1为正常
              }
            ]
    }]
}


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


###Go界面信息展示，包括详情页面的信息
说明：Go界面的提醒消息要写成统一的外观格式，就是：websocket和ttp状态切换时会有提醒

```
GET /gokeeper/go/list
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
            "ttpStatus": "准备开始",  //进行中、完结
            "startTime": "2017/10/01 18:00",   //开始时间精确到分
            "finishTime": "2017/10/08 18:00"         //结束时间
            "userTotalBouns": 350,
    //待定   "ttpSchedule": 25,  //完成进度
            "highAverage": "高于", //这里后台直接返还高/低，前端只用做拼接工作即可，减少压力
    //待定   "rank": 1,  //在一个ttp中
            "ttpTarget": "每日跑3公里",  //现只有运动类ttp有这个
            "joinMoney": 1000,
            "leaveNotesNums": 10, //总假条数量
            "leaveNotes": 2,  //已使用数量
            "ifQuit": "允许中途退出", 
            "ifJoin": "允许中途加入",
            "ifOpen": "公开", 
            "userRecordList": [
              {
                  "days": 2017/10/01,  //记录的时间
                  "dayStatus": "完成" //完成状态
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
                  "dayStatus": "请假/null"
            }],
            "newsStatus": 1   //默认为1未读，为0时说明是websocket信息需要改变css
        }]
}
```

###加入界面信息展示(显示所有ttp)

```
GET /gokeeper/join/list
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
    },
    {
      "username": "阿卡卡", //发起人姓名
      "userIcon": "www.akaka.cn/icon.jpg",
      "ttpName": "聚餐",
      "ttpType": "生活-聚餐",
      "createTime": "2017/10/01 17:00", //创建时间
      "startTime": "2017/10/01 18:00",   //开始时间精确到分
      "finishTime": "2017/10/01 22:00"         //结束时间
      //"ttpTarget": "",        //此活动就没有明确目标为空
      "joinMoney": 10,  //建议列表页显示为加入金额
      "allMoney": 1001,   //当前ttp奖金总额
      "joinPeopleNums": 100, //当前已加入人数
      "leaveNotesNums": 0, //假条数量为0默认的
      "ifQuit": "不允许",
      "ifJoin": "允许"
    }]
}
```

###加入一个ttp
说明：用户在ttp详情页加入一个ttp，后台在user-ttp关联表加入关联信息并创建用户记录表
返回是否创建成功
？不知道是否该发送消息提示，这个还是需要先把消息提示模板想好。
A：加入后不发送消息，只在Go界面有推送ttp详情

```
POST /gokeeper/join/attend
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

###邀请好友(分为2步，查找好友和发送邀请)
说明：用户可以在ttp准备开始和进行中(需判断是否允许中途加入)点击邀请好友按钮，
按常理：ttp为完结状态时，应该不允许用户进行界面操作(按钮变灰，为不可点击状态)
邀请的好友只有在线时，websocket的方式才有效，否则消息还是通过数据库查询的方式返回的
首先点击邀请后，进行查询好友(手机号),显示好友，点击邀请后显示请求已发送

```
GET /gokeeper/user/getUserByPhone
```
参数
```
phonenumber: "17671732167"  //邀请的好友的手机号(通过手机号查询邀请好友id)
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




