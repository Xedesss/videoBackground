# 健康视频平台接口文档

## 通用说明

### 基础路径
所有接口都基于以下基础路径：
```
/api
```

### 通用返回格式
所有接口返回JSON格式数据，基本结构如下：
```json
{
  "code": 0,       // 状态码：0表示成功，非0表示失败
  "message": "",   // 提示信息
  "data": {}       // 返回数据，可能是对象、数组或null
}
```

### 状态码说明
| 状态码 | 说明 |
| ------ | ---- |
| 0 | 成功 |
| 1001 | 参数错误 |
| 1002 | 未登录或登录已过期 |
| 2001 | 视频不存在或已下架 |
| 2002 | 无观看权限 |
| 3001 | 积分不足 |
| 3002 | 兑换失败，库存不足 |
| 5000 | 服务器内部错误 |

## 1. Banner接口

### 1.1 获取轮播图列表

- **接口URL**: `/banner/list`
- **请求方式**: GET
- **请求参数**:

| 参数名 | 类型 | 必填 | 默认值 | 说明 |
| ------ | ---- | ---- | ------ | ---- |
| type | String | 否 | home | Banner类型 |
| limit | Integer | 否 | 5 | 数量限制 |

- **成功响应**:
```json
{
  "code": 0,
  "message": "success",
  "data": [
    {
      "id": 1,
      "imageUrl": "https://example.com/banner1.jpg",
      "linkUrl": "https://example.com/detail/1",
      "title": "健康生活banner",
      "type": "home"
    }
  ]
}
```

- **失败响应**:
```json
{
  "code": 5000,
  "message": "服务器内部错误",
  "data": null
}
```

## 2. 视频接口

### 2.1 获取视频分类列表

- **接口URL**: `/video/categories`
- **请求方式**: GET
- **请求参数**: 无

- **成功响应**:
```json
{
  "code": 0,
  "message": "success",
  "data": [
    {
      "id": 1,
      "name": "健康饮食",
      "icon": "https://example.com/icons/food.png",
      "sort": 1
    },
    {
      "id": 2,
      "name": "运动健身",
      "icon": "https://example.com/icons/fitness.png",
      "sort": 2
    }
  ]
}
```

- **失败响应**:
```json
{
  "code": 5000,
  "message": "服务器内部错误",
  "data": null
}
```

### 2.2 获取视频列表

- **接口URL**: `/video/list`
- **请求方式**: GET
- **请求参数**:

| 参数名 | 类型 | 必填 | 默认值 | 说明 |
| ------ | ---- | ---- | ------ | ---- |
| page | Integer | 否 | 1 | 页码 |
| pageSize | Integer | 否 | 10 | 每页数量 |
| keyword | String | 否 | 无 | 搜索关键词 |
| status | String | 否 | 无 | 视频状态 |
| courseType | String | 否 | 无 | 课程类型 |
| sortField | String | 否 | 无 | 排序字段 |
| sortOrder | String | 否 | 无 | 排序方式 |

- **成功响应**:
```json
{
  "code": 0,
  "message": "success",
  "data": {
    "page": 1,
    "pageSize": 10,
    "pages": 5,
    "total": 45,
    "list": [
      {
        "id": 1,
        "title": "健康饮食指南",
        "coverUrl": "https://example.com/covers/food.jpg",
        "duration": 1200,
        "categoryId": 1,
        "categoryName": "健康饮食",
        "createTime": "2023-05-20 12:30:00",
        "viewCount": 1234
      }
    ]
  }
}
```

- **失败响应**:
```json
{
  "code": 5000,
  "message": "服务器内部错误",
  "data": null
}
```

### 2.3 获取视频详情

- **接口URL**: `/video/detail`
- **请求方式**: GET
- **请求参数**:

| 参数名 | 类型 | 必填 | 默认值 | 说明 |
| ------ | ---- | ---- | ------ | ---- |
| id | Long | 是 | 无 | 视频ID |

- **成功响应**:
```json
{
  "code": 0,
  "message": "success",
  "data": {
    "id": 1,
    "title": "健康饮食指南",
    "description": "本视频介绍健康饮食基本原则...",
    "coverUrl": "https://example.com/covers/food.jpg",
    "videoUrl": "https://example.com/videos/food.mp4",
    "duration": 1200,
    "categoryId": 1,
    "categoryName": "健康饮食",
    "tags": ["饮食", "健康", "营养"],
    "createTime": "2023-05-20 12:30:00",
    "viewCount": 1234,
    "favoriteCount": 567,
    "commentCount": 89,
    "progress": 0.5
  }
}
```

- **失败响应**:
```json
{
  "code": 2001,
  "message": "视频不存在或已下架",
  "data": null
}
```

或

```json
{
  "code": 2002,
  "message": "无观看权限",
  "data": null
}
```

### 2.4 上报视频观看进度

- **接口URL**: `/video/progress`
- **请求方式**: POST
- **请求参数**:

| 参数名 | 类型 | 必填 | 默认值 | 说明 |
| ------ | ---- | ---- | ------ | ---- |
| videoId | Long | 是 | 无 | 视频ID |
| watchTime | Integer | 是 | 无 | 已观看时间（秒） |
| totalTime | Integer | 是 | 无 | 视频总时长（秒） |

- **成功响应**:
```json
{
  "code": 0,
  "message": "success",
  "data": {
    "videoId": 1,
    "watchTime": 600,
    "totalTime": 1200,
    "progress": 0.5
  }
}
```

- **失败响应**:
```json
{
  "code": 1001,
  "message": "参数错误",
  "data": null
}
```

## 3. 用户接口

### 3.1 获取用户观看记录

- **接口URL**: `/user/watch-history`
- **请求方式**: GET
- **请求参数**:

| 参数名 | 类型 | 必填 | 默认值 | 说明 |
| ------ | ---- | ---- | ------ | ---- |
| page | Integer | 否 | 1 | 页码 |
| pageSize | Integer | 否 | 10 | 每页数量 |

- **成功响应**:
```json
{
  "code": 0,
  "message": "success",
  "data": {
    "page": 1,
    "pageSize": 10,
    "pages": 3,
    "total": 25,
    "list": [
      {
        "id": 1,
        "videoId": 1,
        "videoTitle": "健康饮食指南",
        "videoCoverUrl": "https://example.com/covers/food.jpg",
        "watchTime": 600,
        "totalTime": 1200,
        "progress": 0.5,
        "lastWatchTime": "2023-06-01 15:30:00"
      }
    ]
  }
}
```

- **失败响应**:
```json
{
  "code": 1002,
  "message": "未登录或登录已过期",
  "data": null
}
```

或

```json
{
  "code": 5000,
  "message": "服务器内部错误",
  "data": null
}
``` 