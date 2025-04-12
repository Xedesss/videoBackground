# 养生视频系统接口文档
这是一个基于springboot的后端项目，我的数据库链接方式是mysql://root:cb8r6dxv@health-mysql.ns-nm8yoiei.svc:3306
## 目录
1. [Banner轮播图接口](#1-banner轮播图接口)
2. [视频列表接口](#2-视频列表接口)
3. [视频详情接口](#3-视频详情接口)
4. [观看记录接口](#4-观看记录接口)
5. [积分相关接口](#5-积分相关接口)
6. [用户信息接口](#6-用户信息接口)

## 1. Banner轮播图接口

### 1.1 获取首页轮播图

**接口描述**：获取首页展示的轮播图数据

**请求方式**：GET

**接口地址**：`/api/banner/list`

**请求参数**：

| 参数名 | 类型 | 必填 | 描述 |
| ----- | ---- | ---- | ---- |
| type | String | 否 | 轮播图类型，默认为"home" |
| limit | Number | 否 | 返回数量限制，默认为5 |

**返回参数**：

```json
{
  "code": 0,
  "message": "success",
  "data": [
    {
      "id": 1,
      "title": "八段锦养生系列",
      "desc": "传统养生精髓，每天十分钟",
      "imageUrl": "https://example.com/images/banner1.jpg",
      "type": "video",
      "url": "/pages/video-detail/video-detail?id=1",
      "sort": 1
    },
    {
      "id": 2,
      "title": "春季养生特辑",
      "desc": "调理体质，增强免疫力",
      "imageUrl": "https://example.com/images/banner2.jpg",
      "type": "video",
      "url": "/pages/video-detail/video-detail?id=2",
      "sort": 2
    },
    {
      "id": 3,
      "title": "中医养生大讲堂",
      "desc": "权威专家在线授课",
      "imageUrl": "https://example.com/images/banner3.jpg",
      "type": "webview",
      "url": "https://mp.weixin.qq.com",
      "sort": 3
    }
  ]
}
```

**参数说明**：

| 字段 | 类型 | 描述 |
| ---- | ---- | ---- |
| id | Number | 轮播图ID |
| title | String | 轮播图标题 |
| desc | String | 轮播图描述 |
| imageUrl | String | 轮播图图片URL |
| type | String | 点击类型，"video"表示视频详情，"webview"表示网页 |
| url | String | 点击后跳转的URL或页面路径 |
| sort | Number | 排序字段，数字越小越靠前 |

## 2. 视频列表接口

### 2.1 获取视频列表

**接口描述**：获取视频列表数据，支持分页和筛选

**请求方式**：GET

**接口地址**：`/api/video/list`

**请求参数**：

| 参数名 | 类型 | 必填 | 描述 |
| ----- | ---- | ---- | ---- |
| page | Number | 否 | 页码，从1开始，默认为1 |
| pageSize | Number | 否 | 每页数量，默认为10 |
| keyword | String | 否 | 搜索关键词 |
| status | String | 否 | 视频状态，可选值：全部、进行中、已结束、未开始 |
| courseType | String | 否 | 课程类型 |
| sortField | String | 否 | 排序字段，可选值：createTime、viewCount |
| sortOrder | String | 否 | 排序方式，可选值：asc、desc |

**返回参数**：

```json
{
  "code": 0,
  "message": "success",
  "data": {
    "total": 100,
    "list": [
      {
        "id": 1,
        "courseName": "藏密珍宝",
        "title": "《藏密珍宝》0402C",
        "status": "进行中",
        "coverUrl": "https://example.com/images/cover1.jpg",
        "startDate": "3月25日-4月4日",
        "startTime": "13:47",
        "endTime": "23:59",
        "countdown": "02分57秒",
        "entryId": "ID 📋",
        "viewCount": 2356,
        "duration": "45:30",
        "createTime": "2023-03-25 13:47:00"
      },
      {
        "id": 2,
        "courseName": "藏密珍宝",
        "title": "《藏密珍宝》0221C（正式）",
        "status": "进行中",
        "coverUrl": "https://example.com/images/cover2.jpg",
        "startDate": "4月4日",
        "startTime": "00:00",
        "endTime": "23:59",
        "countdown": "46分41秒",
        "entryId": "第四十三期",
        "viewCount": 1890,
        "duration": "32:15",
        "createTime": "2023-04-04 00:00:00"
      }
    ],
    "page": 1,
    "pageSize": 10,
    "pages": 10
  }
}
```

**参数说明**：

| 字段 | 类型 | 描述 |
| ---- | ---- | ---- |
| total | Number | 总记录数 |
| list | Array | 视频列表数据 |
| page | Number | 当前页码 |
| pageSize | Number | 每页记录数 |
| pages | Number | 总页数 |

**list数组项参数说明**：

| 字段 | 类型 | 描述 |
| ---- | ---- | ---- |
| id | Number | 视频ID |
| courseName | String | 课程名称 |
| title | String | 视频标题 |
| status | String | 状态，值：进行中、已结束、未开始 |
| coverUrl | String | 封面图URL |
| startDate | String | 开课日期 |
| startTime | String | 开始时间 |
| endTime | String | 结束时间 |
| countdown | String | 倒计时 |
| entryId | String | 课程ID或期数 |
| viewCount | Number | 观看次数 |
| duration | String | 视频时长 |
| createTime | String | 创建时间 |

### 2.2 获取视频分类

**接口描述**：获取视频分类列表

**请求方式**：GET

**接口地址**：`/api/video/categories`

**请求参数**：无

**返回参数**：

```json
{
  "code": 0,
  "message": "success",
  "data": [
    {
      "id": 1,
      "name": "藏密珍宝",
      "icon": "https://example.com/icons/category1.png",
      "sort": 1
    },
    {
      "id": 2,
      "name": "八段锦",
      "icon": "https://example.com/icons/category2.png",
      "sort": 2
    },
    {
      "id": 3,
      "name": "太极养生",
      "icon": "https://example.com/icons/category3.png",
      "sort": 3
    }
  ]
}
```

## 3. 视频详情接口

### 3.1 获取视频详情

**接口描述**：获取视频详细信息

**请求方式**：GET

**接口地址**：`/api/video/detail`

**请求参数**：

| 参数名 | 类型 | 必填 | 描述 |
| ----- | ---- | ---- | ---- |
| id | Number | 是 | 视频ID |

**返回参数**：

```json
{
  "code": 0,
  "message": "success",
  "data": {
    "id": 1,
    "courseName": "藏密珍宝",
    "title": "《藏密珍宝》0402C",
    "status": "进行中",
    "coverUrl": "https://example.com/images/cover1.jpg",
    "videoUrl": "https://example.com/videos/video1.mp4",
    "startDate": "3月25日-4月4日",
    "startTime": "13:47",
    "endTime": "23:59",
    "countdown": "02分57秒",
    "entryId": "ID 📋",
    "viewCount": 2356,
    "duration": "45:30",
    "createTime": "2023-03-25 13:47:00",
    "description": "《藏密珍宝》是一套古老的养生功法，源自藏地...",
    "hasQuestions": true,
    "questionIds": [101, 102, 103]
  }
}
```

### 3.2 上报观看进度

**接口描述**：上报视频观看进度

**请求方式**：POST

**接口地址**：`/api/video/progress`

**请求参数**：

| 参数名 | 类型 | 必填 | 描述 |
| ----- | ---- | ---- | ---- |
| videoId | Number | 是 | 视频ID |
| watchTime | Number | 是 | 已观看时间（秒） |
| totalTime | Number | 是 | 视频总时长（秒） |

**返回参数**：

```json
{
  "code": 0,
  "message": "success",
  "data": {
    "videoId": 1,
    "watchTime": 180,
    "totalTime": 2730,
    "progress": 6.59 
  }
}
```

### 3.3 获取视频相关题目

**接口描述**：获取与视频关联的题目列表

**请求方式**：GET

**接口地址**：`/api/video/questions`

**请求参数**：

| 参数名 | 类型 | 必填 | 描述 |
| ----- | ---- | ---- | ---- |
| videoId | Number | 是 | 视频ID |

**返回参数**：

```json
{
  "code": 0,
  "message": "success",
  "data": {
    "videoId": 1,
    "title": "《藏密珍宝》0402C",
    "questions": [
      {
        "id": 101,
        "title": "练习藏密珍宝功法时，正确的呼吸方式是？",
        "type": "single",
        "options": [
          {"id": "A", "text": "快速短促呼吸"},
          {"id": "B", "text": "深长缓慢呼吸"},
          {"id": "C", "text": "屏住呼吸"},
          {"id": "D", "text": "随意呼吸"}
        ],
        "correctAnswer": "B",
        "explanation": "练习藏密珍宝功法时应保持深长缓慢的呼吸，有助于气血流通。"
      },
      {
        "id": 102,
        "title": "以下哪项不是本视频中提到的藏密珍宝功法的好处？",
        "type": "single",
        "options": [
          {"id": "A", "text": "增强免疫力"},
          {"id": "B", "text": "改善睡眠"},
          {"id": "C", "text": "减轻体重"},
          {"id": "D", "text": "提高记忆力"}
        ],
        "correctAnswer": "C",
        "explanation": "本视频中提到藏密珍宝功法的主要好处包括增强免疫力、改善睡眠和提高记忆力，但未提及减轻体重的效果。"
      }
    ],
    "totalPoints": 10,
    "passPoints": 8,
    "timeLimit": 300
  }
}
```

### 3.4 提交题目答案

**接口描述**：提交视频相关题目的答案

**请求方式**：POST

**接口地址**：`/api/video/answer-submit`

**请求参数**：

| 参数名 | 类型 | 必填 | 描述 |
| ----- | ---- | ---- | ---- |
| videoId | Number | 是 | 视频ID |
| answers | Array | 是 | 答案数组，格式为 [{questionId: 101, answer: 'B'}, ...] |

**返回参数**：

```json
{
  "code": 0,
  "message": "success",
  "data": {
    "videoId": 1,
    "correctCount": 2,
    "totalCount": 2,
    "score": 10,
    "isPassed": true,
    "pointsEarned": 5,
    "answers": [
      {
        "questionId": 101,
        "userAnswer": "B",
        "isCorrect": true
      },
      {
        "questionId": 102,
        "userAnswer": "C",
        "isCorrect": true,
        "correctAnswer": "C"
      }
    ]
  }
}
```

## 4. 观看记录接口

### 4.1 获取观看记录

**接口描述**：获取用户观看记录

**请求方式**：GET

**接口地址**：`/api/user/watch-history`

**请求参数**：

| 参数名 | 类型 | 必填 | 描述 |
| ----- | ---- | ---- | ---- |
| page | Number | 否 | 页码，从1开始，默认为1 |
| pageSize | Number | 否 | 每页数量，默认为10 |

**返回参数**：

```json
{
  "code": 0,
  "message": "success",
  "data": {
    "total": 25,
    "list": [
      {
        "id": 1,
        "videoId": 1,
        "title": "《藏密珍宝》0402C",
        "coverUrl": "https://example.com/images/cover1.jpg",
        "duration": "45:30",
        "progress": 85,
        "watchTime": "3天前观看",
        "lastPosition": 2320
      },
      {
        "id": 2,
        "videoId": 2,
        "title": "《藏密珍宝》0221C（正式）",
        "coverUrl": "https://example.com/images/cover2.jpg",
        "duration": "32:15",
        "progress": 60,
        "watchTime": "1周前观看",
        "lastPosition": 1160
      }
    ],
    "page": 1,
    "pageSize": 10,
    "pages": 3
  }
}
```

## 5. 积分相关接口

### 5.1 获取用户积分明细

**接口描述**：获取用户积分收支明细

**请求方式**：GET

**接口地址**：`/api/user/points/records`

**请求参数**：

| 参数名 | 类型 | 必填 | 描述 |
| ----- | ---- | ---- | ---- |
| page | Number | 否 | 页码，从1开始，默认为1 |
| pageSize | Number | 否 | 每页数量，默认为10 |
| type | String | 否 | 类型，可选值：all(全部)、income(收入)、expense(支出) |

**返回参数**：

```json
{
  "code": 0,
  "message": "success",
  "data": {
    "total": 30,
    "list": [
      {
        "id": 1,
        "title": "观看视频奖励",
        "time": "2023-04-04 14:30",
        "points": 10,
        "type": "income",
        "remark": "观看视频《藏密珍宝》0402C"
      },
      {
        "id": 2,
        "title": "分享视频奖励",
        "time": "2023-04-03 09:15",
        "points": 20,
        "type": "income",
        "remark": "分享视频到微信好友"
      },
      {
        "id": 3,
        "title": "兑换会员月卡",
        "time": "2023-03-25 16:20",
        "points": 300,
        "type": "expense",
        "remark": "兑换养生视频会员月卡"
      }
    ],
    "page": 1,
    "pageSize": 10,
    "pages": 3,
    "summary": {
      "totalPoints": 1240,
      "thisMonthIncome": 250,
      "thisMonthExpense": 300
    }
  }
}
```

### 5.2 积分兑换列表

**接口描述**：获取可兑换的商品/权益列表

**请求方式**：GET

**接口地址**：`/api/points/exchange/list`

**请求参数**：

| 参数名 | 类型 | 必填 | 描述 |
| ----- | ---- | ---- | ---- |
| page | Number | 否 | 页码，从1开始，默认为1 |
| pageSize | Number | 否 | 每页数量，默认为10 |
| category | String | 否 | 分类，例如：会员、课程、实物等 |

**返回参数**：

```json
{
  "code": 0,
  "message": "success",
  "data": {
    "total": 10,
    "list": [
      {
        "id": 1,
        "title": "养生视频会员月卡",
        "desc": "畅享所有养生视频资源",
        "points": 300,
        "imageUrl": "https://example.com/images/exchange1.jpg",
        "category": "会员",
        "stock": 999,
        "exchangeCount": 156
      },
      {
        "id": 2,
        "title": "精品养生电子书",
        "desc": "中医养生精华知识",
        "points": 100,
        "imageUrl": "https://example.com/images/exchange2.jpg",
        "category": "课程",
        "stock": 500,
        "exchangeCount": 85
      }
    ],
    "page": 1,
    "pageSize": 10,
    "pages": 1
  }
}
```

### 5.3 积分兑换

**接口描述**：使用积分兑换商品/权益

**请求方式**：POST

**接口地址**：`/api/points/exchange`

**请求参数**：

| 参数名 | 类型 | 必填 | 描述 |
| ----- | ---- | ---- | ---- |
| itemId | Number | 是 | 兑换项ID |
| count | Number | 否 | 兑换数量，默认为1 |

**返回参数**：

```json
{
  "code": 0,
  "message": "success",
  "data": {
    "orderNo": "EX20230404123456",
    "title": "养生视频会员月卡",
    "points": 300,
    "exchangeTime": "2023-04-04 12:34:56",
    "remainPoints": 940
  }
}
```
## 6. 用户信息接口

### 6.1 获取用户基本信息

**接口描述**：获取登录用户的基本信息

**请求方式**：GET

**接口地址**：`/api/user/info`

**请求参数**：无

**返回参数**：

```json
{
  "code": 0,
  "message": "success",
  "data": {
    "id": 10001,
    "nickname": "用户昵称",
    "avatar": "https://example.com/avatars/user1.jpg",
    "points": 1240,
    "watchCount": 23,
    "level": {
      "level": 3,
      "name": "养生达人",
      "icon": "https://example.com/level/3.png",
      "progress": 65,
      "nextLevel": {
        "level": 4,
        "name": "养生专家",
        "needPoints": 500
      }
    },
    "vipInfo": {
      "isVip": true,
      "vipLevel": 1,
      "vipName": "普通会员",
      "expireDate": "2023-05-04"
    }
  }
}
```

## API状态码说明

| 状态码 | 说明 |
| ----- | ---- |
| 0 | 成功 |
| 1001 | 参数错误 |
| 1002 | 未登录或登录已过期 |
| 2001 | 视频不存在或已下架 |
| 2002 | 无观看权限 |
| 3001 | 积分不足 |
| 3002 | 兑换失败，库存不足 |
| 5000 | 服务器内部错误 |

## API请求示例

### axios请求示例

```javascript
import axios from 'axios';

// 获取视频列表
async function getVideoList(page = 1, pageSize = 10) {
  try {
    const response = await axios.get('/api/video/list', {
      params: {
        page,
        pageSize
      }
    });
    return response.data;
  } catch (error) {
    console.error('获取视频列表失败:', error);
    return null;
  }
}

// 上报视频观看进度
async function reportVideoProgress(videoId, watchTime, totalTime) {
  try {
    const response = await axios.post('/api/video/progress', {
      videoId,
      watchTime,
      totalTime
    });
    return response.data;
  } catch (error) {
    console.error('上报观看进度失败:', error);
    return null;
  }
}
```

### uni-app请求示例

```javascript
// 获取轮播图
function getBannerList() {
  return new Promise((resolve, reject) => {
    uni.request({
      url: '/api/banner/list',
      method: 'GET',
      success: (res) => {
        if (res.data.code === 0) {
          resolve(res.data.data);
        } else {
          reject(res.data.message);
        }
      },
      fail: (err) => {
        reject(err);
      }
    });
  });
}

// 积分兑换
function exchangePoints(itemId, count = 1) {
  return new Promise((resolve, reject) => {
    uni.request({
      url: '/api/points/exchange',
      method: 'POST',
      data: {
        itemId,
        count
      },
      success: (res) => {
        if (res.data.code === 0) {
          resolve(res.data.data);
        } else {
          reject(res.data.message);
        }
      },
      fail: (err) => {
        reject(err);
      }
    });
  });
}
``` 