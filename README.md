# 密钥管理系统

## 环境要求

1. jdk8
2. MySQL5.7

## 接口说明

所有接口通过 `REST APis` 实现，通过`HTTP`协议封装`JSON`对象，为应用系统或相关工具提供非对称密钥的管理服务.

### 接口安全性设计

接口需要授权才能访问，授权及验证规则如下:

  * 定义一个客户端CID(一个32字节的随机数据), 告知客户端
  * 定义一个会话验证密钥KEY+IV(SM4), 告知客户端
  * 客户端用CID,KEY来计算TOKEN,服务器进行验证
  * 服务器对客户端IP的验证是可选项，生产环节验证，测试开发环节不验证

TOKEN 分为两部分组成，及数据部分||验证部分。

  1. 客户端请求服务器进行连接,服务器返回16字节的随机数据 RData, 服务器缓存 RData, 与 CID 关联
  2. 客户端计算数据部分=SM4_CBC(RData, KEY)
  3. 客户端计算验证数据=SM3(RData||CID)
  4. TOKEN = 数据部分||验证部分, hexdecial编码后使用
  5. 客户端每次请求的数据都需要提供Token, 服务器端进行验证后执行相应的服务

#### Token 生成接口

* URL: http://hosts:port/api/access
* METHOD: POST
* 参数: cid={CID}
* 返回:

```json
{
  "success": true,
  "message": "成功时为32字节的 hexadecimal 字符串, 失败时则为提示的错误信息",
  "retcode": 0
}
```
_注_: retcode 0 表示成功，其它表示返回的错误码

### 对称密钥管理接口

天地项目使用

#### 1. 申请新对称密钥的接口

* URL: http://hosts:port/api/skey/apply
* METHOD: POST
* 参数: bits={128}&type={sm4}&token={安全令牌}
* 返回: 
*
```json
{
  "success": true,
  "message": "操作结果说明,失败时可以提示错误信息",
  "retcode": 0,
  "type": "SM4",
  "keys": [
    {
      "id": 1, 
      "val": "密钥值, 被预值保护密钥加密, 最后进行hexdicial编码显示",
      "checkcode": "校验码,hexdicial编码显示,8字节长度",
      "version": "1"
    },
    {}
  ]
}
```

密钥值的保护密钥为从TOKEN中还原的Data(32字节，前16字节作为密钥，后16字节作为IV)，SM4_CBC加密的密文.
checkcode, 密钥校验码为密钥原文加密8字节全0数据得到的密文的最后四个字节

#### 2. 恢复指定ID的对称密钥的接口

* URL: http://hosts:port/api/skey/recovery
* METHOD: POST
* 参数: startid={id}&limit={连续个数}&version={版本}&token={安全令牌}
* 返回: 

```json
{
  "success": true,
  "message": "操作结果说明,失败时可以提示错误信息",
  "retcode": 0,
  "type": "SM4",
  "keys": [
    {
      "id": 1, 
      "val": "密钥值, 被预值保护密钥加密, 最后进行hexdicial编码显示",
      "checkcode": "校验码,hexdicial编码显示,8字节长度",
      "version": "1"
    },
    {}
  ]
}
```

_注_: version 仅在 limit 为 1 时生效，表示提取指定 id 的特定版本的密钥值.

#### 3. 销毁指定ID的对称密钥的接口

* URL: http://hosts:port/api/skey/destroy
* METHOD: POST
* 参数: startid={id}&limit={连续个数}&version={版本}&token={安全令牌}
* 返回: 

```json
{
  "success": true,
  "message": "操作结果说明,失败时可以提示错误信息",
  "retcode": 0,
  "type": "SM4",
  "keys": [
    {
      "id": 1,
      "status": 0,
      "version": "1"
    },
    {}
  ]
}
```

_注_: version 仅在 limit 为 1 时生效，表示提取指定 id 的特定版本的密钥值.

#### 4. 更新指定ID的对称密钥的接口

* URL: http://hosts:port/api/skey/recovery
* METHOD: POST
* 参数: startid={id}&limit={连续个数}&token={安全令牌}
* 返回: 

```json
{
  "success": true,
  "message": "操作结果说明,失败时可以提示错误信息",
  "retcode": 0,
  "type": "SM4",
  "keys": [
    {
      "id": 1, 
      "val": "密钥值, 被预值保护密钥加密, 最后进行hexdicial编码显示",
      "checkcode": "校验码,hexdicial编码显示,8字节长度",
      "version": "2"
    },
    {}
  ]
}
```

### 扩展的对称非对称密钥管理接口

国核项目使用

#### 1. 申请新的非对称密钥的接口

* URL: http://hosts:port/api/keys/apply
* METHOD: POST
* 参数: type=SM2&token=安全令牌
* 返回: 

```json
{
  "success": true,
  "message": "操作结果说明,失败时可以提示错误信息",
  "retcode": 0,   # 可以用于返回码错误码
  "type": "SM2"，
  "public": "公钥, (X32+Y32)的hexdecial编码显示",
  "value": "私钥, 被预值保护密钥加密, 最后进行hexdicial编码"
}
```

私钥的保护密钥为从TOKEN中还原的Data(32字节，前16字节作为密钥，后16字节作为IV)，SM4_CBC保护即可.

#### 2. 密钥绑定关系接口

* URL: http://hosts:port/api/keys/bind
* METHOD: POST
* 参数: public={public} & certificate={certficate} & id ={deviceid} & token = {安全令牌}
* 返回:

```json
{
  "success": true,
  "message": "操作结果说明,失败时可以提示错误信息",
  "retcode": 0,   # 可以用于返回码错误码
}
```

#### 3. 密钥停用的接口

* 请求: http://hosts:port/apis/keys/revoke
* METHOD: POST
* 参数: public={public}&reason={停用原因}&token = {安全令牌}
* 返回:

```json
{
  "success": true,
  "message": "操作结果说明,失败时可以提示错误信息",
  "retcode": 0,   # 可以用于返回码错误码
}
```

TOKEN中的Data为随机数

### 结构信息及密钥提取接口

* 请求: http://hosts:port/apis/org/info
* METHOD: POST
* 参数: token={安全令牌}&orgid={机构编码}
* 返回:

```json
{
  "success": true,
  "message": "操作结果说明,失败时可以提示错误信息",
  "retcode": 0,   # 可以用于返回码错误码
  "keySets:" {"上位机密钥集":[密钥1,...,密钥n], "控制器密钥集":[密钥1,...,密钥n]},
  "properties": {'host':'上位机密钥集','controler':'控制器密钥集',PK2:'32字节随机数'}
```

其中的密钥为一个JSON对象，内容如 `{id:ID,name:name,value:密钥密文,code:"校验值", version:x}`
密钥值为被KEY加密的密文

### 机构下的设备注册接口

* 请求: http://posts:port/api/org/device/add
* METHOD: POST
* 参数: token={安全令牌}&id={设备ID}&name={设备名称}&ip={设备IP}&kset={密钥集编号}&orgid={orgid}
* 返回:

```json
{
  "success": true,
  "message": "操作结果说明,失败时可以提示错误信息",
  "retcode": 0,   # 可以用于返回码错误码
}
```

### 提取某个对称密钥的指定版本的密钥值

* 请求: http://posts:port/api/org/key
* METHOD: POST
* 参数: token={安全令牌}&id={密钥ID}&name={密钥名称}&orgid={orgid}&version=n
* 返回:

```json
{
  "success": true,
  "message": "成功时为32字节的 hexadecimal 字符串:8个字节的密钥验证码hexadecimal, 失败时则为提示的错误信息",
  "retcode": 0,  // 0 表示成功，其它表示返回的错误码
}
```

_注_:  id,name 至少存在一个作搜索条件，version不存在则用最后版本,应该是0个或一个结果.


## 使用流程

1. 通过`Token 生成接口`获取随机数，客户端计算生成token.
2. 通过`结构信息及密钥提取接口` 获取特定结构的信息.
3. 通过`申请新的非对称密钥的接口` 获得一个SM2密钥.
4. 客户端使用刚申请的密钥到CA系统申请证书。
5. 通过`机构下的设备注册接口`向KMS系统注册一个设备。
6. 通过`密钥绑定关系接口`，绑定设备、密钥与证书。
7. 通过`密钥停用的接口`，将密钥置为禁用状态，不在使用。

