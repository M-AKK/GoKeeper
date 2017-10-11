# API

###Go界面信息展示，包括详情页面的信息

```
GET /gokeeper/go/list
```

参数

```
userId: "1234567"
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
            "ttpName": "100天疯狂跑",
            "startTime": "2017/10/1 18:00",   //开始时间精确到分
            "finishTime": "2017/10/8 18:00"         //结束时间
            "userTotalBouns": 350,
            "ttpSchedule": 25,  //完成进度
    //待定   "highAverage": "高", //这里后台直接返还高/低，前端只用做拼接工作即可，减少压力
    //待定   "rank": 1,  //在一个ttp中
            "ttpTarget": "每日跑3公里",
            "joinMoney": 1000,
            "leaveNotesNums": 10, //总假条数量
            "leaveNotes": 2,  //已使用数量
            "ifQuit": 1, //1代表不允许
            "ifOpen": 0, //0代表公开
            "userRecordList": [
              {
                  "days": 2017/10/1,  //记录的时间
                  "dayStatus": "完成" //完成状态
              }
            ],  //不知道这里用不用加逗号
            "othersRecordList": [     //当天其他人的完成记录
              {
                  "userName": "阿卡卡", //同一个ttp同伴名字
                  "finishTime": "19:07" //完成的具体时间
              }]
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
startTime: 2017/10/1 //ttp开始时间
finishTime: 201710/10 //ttp结束时间
ttpTarget: 1(非必填)目标，只有某些ttp有
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
      "ttpType": "运动-跑步",
      "createTime": "2017/10/1 17:00", //创建时间
      "startTime": "2017/10/1 18:00",   //开始时间精确到分
      "finishTime": "2017/10/8 18:00"         //结束时间
      "ttpTarget": "每日跑3公里",        //建议放在详情页面
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
      "createTime": "2017/10/1 17:00", //创建时间
      "startTime": "2017/10/1 18:00",   //开始时间精确到分
      "finishTime": "2017/10/1 22:00"         //结束时间
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

###查询订单详情

```
GET /gokeeper/join/search
```

参数

```
openid: 18eu2jwk2kse3r42e2e
orderId: 161899085773669363
```

返回

```
{
    "code": 0,
    "msg": "成功",
    "data": {
          "orderId": "161899085773669363",
          "buyerName": "李四",
          "buyerPhone": "18868877111",
          "buyerAddress": "慕课网总部",
          "buyerOpenid": "18eu2jwk2kse3r42e2e",
          "orderAmount": 18,
          "orderStatus": 0,
          "payStatus": 0,
          "createTime": 1490177352,
          "updateTime": 1490177352,
          "orderDetailList": [
            {
                "detailId": "161899085974995851",
                "orderId": "161899085773669363",
                "productId": "157875196362360019",
                "productName": "招牌奶茶",
                "productPrice": 9,
                "productQuantity": 2,
                "productIcon": "http://xxx.com",
                "productImage": "http://xxx.com"
            }
        ]
    }
}
```

###取消订单

```
POST /sell/buyer/order/cancel
```

参数

```
openid: 18eu2jwk2kse3r42e2e
orderId: 161899085773669363
```

返回

```
{
    "code": 0,
    "msg": "成功",
    "data": null
}
```

###获取openid

```
重定向到 /sell/wechat/authorize
```

参数

```
returnUrl: http://xxx.com/abc  //【必填】
```

返回

```
http://xxx.com/abc?openid=oZxSYw5ldcxv6H0EU67GgSXOUrVg
```

###支付订单
```
重定向 /sell/pay/create
```

参数

```
orderId: 161899085773669363
returnUrl: http://xxx.com/abc/order/161899085773669363
```

返回

```
http://xxx.com/abc/order/161899085773669363
```


