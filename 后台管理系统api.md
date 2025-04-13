# 健康管理系统API接口文档

**API根地址**: `https://nxpstfilnxtm.sealoshzh.site`

## 目录

1. [用户认证](#1-用户认证)
2. [视频管理](#2-视频管理)
3. [分销商管理](#3-分销商管理)
4. [数据统计](#4-数据统计)
5. [通用接口](#5-通用接口)

## 1. 用户认证

### 1.1 登录

- **接口URL**: `/api/auth/login`
- **请求方式**: POST
- **接口描述**: 用户登录接口
- **请求参数**:

| 参数名 | 类型 | 必填 | 描述 |
| ------ | ---- | ---- | ---- |
| username | string | 是 | 用户名 |
| password | string | 是 | 密码 |

- **返回示例**:

```json
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "id": "1",
    "username": "admin",
    "role": "admin",
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "permissions": ["video.upload", "video.edit", "distributor.manage"]
  }
}
```

### 1.2 登出

- **接口URL**: `/api/auth/logout`
- **请求方式**: POST
- **接口描述**: 用户登出接口
- **请求头**: Authorization: Bearer {token}
- **返回示例**:

```json
{
  "code": 200,
  "message": "登出成功",
  "data": null
}
```

### 1.3 获取用户信息

- **接口URL**: `/api/auth/userinfo`
- **请求方式**: GET
- **接口描述**: 获取当前登录用户的信息
- **请求头**: Authorization: Bearer {token}
- **返回示例**:

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": "1",
    "username": "admin",
    "role": "admin",
    "avatar": "https://nxpstfilnxtm.sealoshzh.site/api/files/images/avatar.jpg",
    "permissions": ["video.upload", "video.edit", "distributor.manage"]
  }
}
```

## 2. 视频管理

### 2.1 获取视频列表

- **接口URL**: `/api/videos`
- **请求方式**: GET
- **接口描述**: 获取视频列表，支持分页、排序和筛选
- **请求头**: Authorization: Bearer {token}
- **请求参数**:

| 参数名 | 类型 | 必填 | 描述 |
| ------ | ---- | ---- | ---- |
| page | number | 否 | 页码，默认1 |
| size | number | 否 | 每页数量，默认10 |
| title | string | 否 | 视频标题搜索 |
| status | string | 否 | 视频状态筛选（published/draft/disabled） |
| orderBy | string | 否 | 排序字段（uploadTime/viewCount/size） |
| orderType | string | 否 | 排序方式（asc/desc） |

- **返回示例**:

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "total": 100,
    "list": [
      {
        "id": "1",
        "title": "养生太极教学",
        "cover": "https://nxpstfilnxtm.sealoshzh.site/api/files/covers/taiji.jpg",
        "duration": 1800,
        "size": 256000000,
        "uploadTime": "2023-10-15 10:22:36",
        "viewCount": 1230,
        "status": "published",
        "isRecommended": true
      },
      // 更多视频数据...
    ]
  }
}
```

### 2.2 上传视频

- **接口URL**: `/api/videos/upload`
- **请求方式**: POST
- **接口描述**: 上传视频文件
- **请求头**: 
  - Authorization: Bearer {token}
  - Content-Type: multipart/form-data
- **请求参数**:

| 参数名 | 类型 | 必填 | 描述 |
| ------ | ---- | ---- | ---- |
| title | string | 是 | 视频标题 |
| file | file | 是 | 视频文件 |
| cover | file | 否 | 视频封面图 |
| description | string | 否 | 视频描述 |
| tags | string[] | 否 | 视频标签 |
| status | string | 否 | 视频状态，默认draft |

- **返回示例**:

```json
{
  "code": 200,
  "message": "上传成功",
  "data": {
    "id": "123",
    "title": "养生太极教学",
    "url": "https://nxpstfilnxtm.sealoshzh.site/api/files/videos/123.mp4",
    "cover": "https://nxpstfilnxtm.sealoshzh.site/api/files/covers/123.jpg",
    "uploadTime": "2024-04-13 14:30:25"
  }
}
```

### 2.3 视频详情

- **接口URL**: `/api/videos/{id}`
- **请求方式**: GET
- **接口描述**: 获取视频详细信息
- **请求头**: Authorization: Bearer {token}
- **路径参数**:

| 参数名 | 类型 | 必填 | 描述 |
| ------ | ---- | ---- | ---- |
| id | string | 是 | 视频ID |

- **返回示例**:

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": "123",
    "title": "养生太极教学",
    "description": "本视频详细讲解太极基本动作及养生要点...",
    "url": "https://nxpstfilnxtm.sealoshzh.site/api/files/videos/123.mp4",
    "cover": "https://nxpstfilnxtm.sealoshzh.site/api/files/covers/123.jpg",
    "duration": 1800,
    "size": 256000000,
    "uploadTime": "2023-10-15 10:22:36",
    "viewCount": 1230,
    "likeCount": 356,
    "commentCount": 42,
    "status": "published",
    "isRecommended": true,
    "tags": ["太极", "养生", "教学"],
    "distributor": {
      "id": "1",
      "name": "华北总经销"
    }
  }
}
```

### 2.4 更新视频信息

- **接口URL**: `/api/videos/{id}`
- **请求方式**: PUT
- **接口描述**: 更新视频信息
- **请求头**: Authorization: Bearer {token}
- **路径参数**:

| 参数名 | 类型 | 必填 | 描述 |
| ------ | ---- | ---- | ---- |
| id | string | 是 | 视频ID |

- **请求参数**:

| 参数名 | 类型 | 必填 | 描述 |
| ------ | ---- | ---- | ---- |
| title | string | 否 | 视频标题 |
| description | string | 否 | 视频描述 |
| cover | file | 否 | 视频封面图 |
| tags | string[] | 否 | 视频标签 |
| status | string | 否 | 视频状态 |
| isRecommended | boolean | 否 | 是否推荐 |

- **返回示例**:

```json
{
  "code": 200,
  "message": "更新成功",
  "data": {
    "id": "123",
    "title": "养生太极教学（修订版）",
    "status": "published"
  }
}
```

### 2.5 删除视频

- **接口URL**: `/api/videos/{id}`
- **请求方式**: DELETE
- **接口描述**: 删除视频
- **请求头**: Authorization: Bearer {token}
- **路径参数**:

| 参数名 | 类型 | 必填 | 描述 |
| ------ | ---- | ---- | ---- |
| id | string | 是 | 视频ID |

- **返回示例**:

```json
{
  "code": 200,
  "message": "删除成功",
  "data": null
}
```

### 2.6 视频分配权限

- **接口URL**: `/api/videos/{id}/permissions`
- **请求方式**: POST
- **接口描述**: 分配视频的访问权限
- **请求头**: Authorization: Bearer {token}
- **路径参数**:

| 参数名 | 类型 | 必填 | 描述 |
| ------ | ---- | ---- | ---- |
| id | string | 是 | 视频ID |

- **请求参数**:

| 参数名 | 类型 | 必填 | 描述 |
| ------ | ---- | ---- | ---- |
| distributorIds | string[] | 是 | 分销商ID列表 |

- **返回示例**:

```json
{
  "code": 200,
  "message": "权限设置成功",
  "data": {
    "videoId": "123",
    "distributors": [
      {
        "id": "1",
        "name": "华北总经销"
      },
      {
        "id": "2",
        "name": "华东总经销"
      }
    ]
  }
}
```

## 3. 分销商管理

### 3.1 获取分销商列表

- **接口URL**: `/api/distributors`
- **请求方式**: GET
- **接口描述**: 获取分销商列表，支持分页和筛选
- **请求头**: Authorization: Bearer {token}
- **请求参数**:

| 参数名 | 类型 | 必填 | 描述 |
| ------ | ---- | ---- | ---- |
| page | number | 否 | 页码，默认1 |
| size | number | 否 | 每页数量，默认10 |
| name | string | 否 | 账号名称搜索 |
| type | string | 否 | 账号类型（general/distributor） |
| status | string | 否 | 账号状态（active/disabled） |

- **返回示例**:

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "total": 50,
    "list": [
      {
        "id": "1",
        "name": "华北总经销",
        "type": "general",
        "contact": "张三",
        "phone": "13800138000",
        "createTime": "2023-10-15 10:22:36",
        "customerCount": 356,
        "orderCount": 1230,
        "status": "active",
        "channelCode": "GD001",
        "children": [
          {
            "id": "11",
            "name": "北京分销商",
            "type": "distributor",
            "contact": "李四",
            "phone": "13900139000",
            "createTime": "2023-10-16 14:30:25",
            "customerCount": 120,
            "orderCount": 430,
            "status": "active",
            "channelCode": "DS011",
            "parentId": "1"
          }
        ]
      },
      // 更多分销商数据...
    ]
  }
}
```

### 3.2 创建分销商

- **接口URL**: `/api/distributors`
- **请求方式**: POST
- **接口描述**: 创建新的分销商账号
- **请求头**: Authorization: Bearer {token}
- **请求参数**:

| 参数名 | 类型 | 必填 | 描述 |
| ------ | ---- | ---- | ---- |
| name | string | 是 | 账号名称 |
| type | string | 是 | 账号类型（general/distributor） |
| contact | string | 是 | 联系人 |
| phone | string | 是 | 联系电话 |
| parentId | string | 否 | 父级分销商ID（子分销商必填） |
| status | string | 否 | 账号状态，默认active |
| password | string | 是 | 登录密码 |

- **返回示例**:

```json
{
  "code": 200,
  "message": "创建成功",
  "data": {
    "id": "3",
    "name": "西南总经销",
    "type": "general",
    "channelCode": "GD003",
    "createTime": "2024-04-13 15:30:22"
  }
}
```

### 3.3 获取分销商详情

- **接口URL**: `/api/distributors/{id}`
- **请求方式**: GET
- **接口描述**: 获取分销商详细信息
- **请求头**: Authorization: Bearer {token}
- **路径参数**:

| 参数名 | 类型 | 必填 | 描述 |
| ------ | ---- | ---- | ---- |
| id | string | 是 | 分销商ID |

- **返回示例**:

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": "1",
    "name": "华北总经销",
    "type": "general",
    "contact": "张三",
    "phone": "13800138000",
    "createTime": "2023-10-15 10:22:36",
    "customerCount": 356,
    "orderCount": 1230,
    "status": "active",
    "channelCode": "GD001",
    "parent": null,
    "children": [
      {
        "id": "11",
        "name": "北京分销商",
        "type": "distributor"
      },
      {
        "id": "12",
        "name": "天津分销商",
        "type": "distributor"
      }
    ],
    "accessibleVideos": [
      {
        "id": "1",
        "title": "养生太极教学"
      },
      {
        "id": "2",
        "title": "八段锦详解"
      }
    ]
  }
}
```

### 3.4 更新分销商信息

- **接口URL**: `/api/distributors/{id}`
- **请求方式**: PUT
- **接口描述**: 更新分销商信息
- **请求头**: Authorization: Bearer {token}
- **路径参数**:

| 参数名 | 类型 | 必填 | 描述 |
| ------ | ---- | ---- | ---- |
| id | string | 是 | 分销商ID |

- **请求参数**:

| 参数名 | 类型 | 必填 | 描述 |
| ------ | ---- | ---- | ---- |
| name | string | 否 | 账号名称 |
| contact | string | 否 | 联系人 |
| phone | string | 否 | 联系电话 |
| status | string | 否 | 账号状态 |
| password | string | 否 | 登录密码（修改密码时填写） |

- **返回示例**:

```json
{
  "code": 200,
  "message": "更新成功",
  "data": {
    "id": "1",
    "name": "华北总经销",
    "status": "active"
  }
}
```

### 3.5 删除分销商

- **接口URL**: `/api/distributors/{id}`
- **请求方式**: DELETE
- **接口描述**: 删除分销商账号
- **请求头**: Authorization: Bearer {token}
- **路径参数**:

| 参数名 | 类型 | 必填 | 描述 |
| ------ | ---- | ---- | ---- |
| id | string | 是 | 分销商ID |

- **返回示例**:

```json
{
  "code": 200,
  "message": "删除成功",
  "data": null
}
```

### 3.6 获取分销商渠道二维码

- **接口URL**: `/api/distributors/{id}/qrcode`
- **请求方式**: GET
- **接口描述**: 获取分销商专属渠道二维码
- **请求头**: Authorization: Bearer {token}
- **路径参数**:

| 参数名 | 类型 | 必填 | 描述 |
| ------ | ---- | ---- | ---- |
| id | string | 是 | 分销商ID |

- **返回示例**:

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": "1",
    "channelCode": "GD001",
    "qrcodeUrl": "https://nxpstfilnxtm.sealoshzh.site/api/files/qrcodes/GD001.png"
  }
}
```

## 4. 数据统计

### 4.1 获取总体数据概览

- **接口URL**: `/api/statistics/overview`
- **请求方式**: GET
- **接口描述**: 获取系统总体数据概览
- **请求头**: Authorization: Bearer {token}
- **返回示例**:

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "totalUsers": 12680,
    "totalVideos": 256,
    "totalViews": 1356000,
    "totalDistributors": 50,
    "activeUsers": 3560,
    "newUsersToday": 128,
    "viewsToday": 5680
  }
}
```

### 4.2 获取用户增长趋势

- **接口URL**: `/api/statistics/user-growth`
- **请求方式**: GET
- **接口描述**: 获取用户增长趋势数据
- **请求头**: Authorization: Bearer {token}
- **请求参数**:

| 参数名 | 类型 | 必填 | 描述 |
| ------ | ---- | ---- | ---- |
| period | string | 否 | 时间周期（day/week/month/year），默认month |
| startDate | string | 否 | 开始日期（YYYY-MM-DD） |
| endDate | string | 否 | 结束日期（YYYY-MM-DD） |

- **返回示例**:

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "timeLabels": ["2023-10", "2023-11", "2023-12", "2024-01", "2024-02", "2024-03"],
    "series": [
      {
        "name": "新增用户",
        "data": [1245, 1380, 1560, 1680, 1420, 1580]
      },
      {
        "name": "累计用户",
        "data": [8500, 9880, 11440, 13120, 14540, 16120]
      }
    ]
  }
}
```

### 4.3 获取视频数据分析

- **接口URL**: `/api/statistics/video-analysis`
- **请求方式**: GET
- **接口描述**: 获取视频观看和互动数据分析
- **请求头**: Authorization: Bearer {token}
- **请求参数**:

| 参数名 | 类型 | 必填 | 描述 |
| ------ | ---- | ---- | ---- |
| period | string | 否 | 时间周期（day/week/month/year），默认month |
| startDate | string | 否 | 开始日期（YYYY-MM-DD） |
| endDate | string | 否 | 结束日期（YYYY-MM-DD） |

- **返回示例**:

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "timeLabels": ["2023-10", "2023-11", "2023-12", "2024-01", "2024-02", "2024-03"],
    "viewsSeries": [58600, 62400, 75300, 84500, 79200, 86400],
    "topVideos": [
      {
        "id": "1",
        "title": "养生太极教学",
        "views": 12680,
        "likes": 3420
      },
      {
        "id": "2",
        "title": "八段锦详解",
        "views": 10240,
        "likes": 2860
      },
      {
        "id": "3",
        "title": "五禽戏演示",
        "views": 9650,
        "likes": 2540
      }
    ],
    "categoryDistribution": [
      {
        "name": "太极",
        "value": 35
      },
      {
        "name": "气功",
        "value": 28
      },
      {
        "name": "养生操",
        "value": 22
      },
      {
        "name": "其他",
        "value": 15
      }
    ]
  }
}
```

### 4.4 获取渠道转化率

- **接口URL**: `/api/statistics/channel-conversion`
- **请求方式**: GET
- **接口描述**: 获取各分销渠道的用户转化率数据
- **请求头**: Authorization: Bearer {token}
- **请求参数**:

| 参数名 | 类型 | 必填 | 描述 |
| ------ | ---- | ---- | ---- |
| period | string | 否 | 时间周期（day/week/month/year），默认month |

- **返回示例**:

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "totalConversion": 3.6,
    "channels": [
      {
        "id": "1",
        "name": "华北总经销",
        "visits": 12580,
        "registrations": 560,
        "conversion": 4.5,
        "orders": 128
      },
      {
        "id": "2",
        "name": "华东总经销",
        "visits": 15620,
        "registrations": 620,
        "conversion": 4.0,
        "orders": 145
      },
      {
        "id": "3",
        "name": "西南总经销",
        "visits": 9850,
        "registrations": 320,
        "conversion": 3.2,
        "orders": 86
      }
    ]
  }
}
```

### 4.5 获取实时在线用户

- **接口URL**: `/api/statistics/online-users`
- **请求方式**: GET
- **接口描述**: 获取实时在线用户数据
- **请求头**: Authorization: Bearer {token}
- **返回示例**:

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "currentOnline": 356,
    "todayPeak": 580,
    "hourlyData": [
      { "hour": "00:00", "count": 120 },
      { "hour": "01:00", "count": 85 },
      { "hour": "02:00", "count": 65 },
      // 更多小时数据...
      { "hour": "23:00", "count": 145 }
    ],
    "deviceDistribution": [
      { "name": "iOS", "value": 45 },
      { "name": "Android", "value": 40 },
      { "name": "Web", "value": 15 }
    ]
  }
}
```

## 5. 通用接口

### 5.1 文件上传

- **接口URL**: `/api/upload`
- **请求方式**: POST
- **接口描述**: 通用文件上传接口
- **请求头**: 
  - Authorization: Bearer {token}
  - Content-Type: multipart/form-data
- **请求参数**:

| 参数名 | 类型 | 必填 | 描述 |
| ------ | ---- | ---- | ---- |
| file | file | 是 | 文件数据 |
| type | string | 是 | 文件类型（image/video/document） |

- **返回示例**:

```json
{
  "code": 200,
  "message": "上传成功",
  "data": {
    "url": "https://nxpstfilnxtm.sealoshzh.site/api/files/uploads/image123.jpg",
    "filename": "image123.jpg",
    "size": 125000,
    "mimeType": "image/jpeg"
  }
}
```

### 5.2 获取系统配置

- **接口URL**: `/api/config`
- **请求方式**: GET
- **接口描述**: 获取系统配置信息
- **请求头**: Authorization: Bearer {token}
- **返回示例**:

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "uploadLimits": {
      "maxFileSize": 512000000,
      "allowedFormats": ["mp4", "mov", "avi", "wmv"]
    },
    "systemName": "健康管理系统",
    "version": "1.0.0",
    "contactEmail": "support@example.com"
  }
}
```

## 错误码说明

| 错误码 | 描述 |
| ------ | ---- |
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未授权或token过期 |
| 403 | 权限不足 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

## 请求约定

1. 所有接口都应返回统一格式的JSON数据，包含`code`、`message`和`data`三个字段
2. 分页接口的返回数据中，应包含`total`（总记录数）和`list`（当前页数据）
3. 所有时间字段格式为：YYYY-MM-DD HH:mm:ss
4. 所有接口（除登录外）都需要在请求头中携带token进行身份验证
5. 文件上传接口最大支持500MB的文件大小

## 更新记录

| 版本 | 日期 | 描述 |
| ---- | ---- | ---- |
| 1.0.0 | 2024-04-13 | 初版接口文档 |
| 1.0.1 | 2025-04-13 | 更新API根地址 | 