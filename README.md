# 密钥管理系统

## 接口说明

所有接口通过 `REST APis` 实现，通过`HTTP`协议封装`JSON`对象，为应用系统或相关工具提供非对称密钥的管理服务.

### 接口安全性设计

接口需要授权才能访问，授权及验证规则如下:

  * 定义一个客户端CID(一个32字节的随机数据), 告知客户端
  * 定义一个会话验证密钥KEY(SM4), 告知客户端
  * 客户端用CID,KEY来计算TOKEN,服务器进行验证
  * 服务器对客户端IP的验证是可选项，生产环节验证，测试开发环节不验证

TOKEN 分为两部分组成，及数据部分||验证部分。

  1. 客户端请求服务器进行连接,服务器返回16字节的随机数据 RData, 服务器缓存 RData, 与 CID 关联
  2. 客户端计算数据部分=SM4_CBC(RData, KEY)
  3. 客户端计算验证数据=SM3(Data||CID)
  4. TOKEN = 数据部分||验证部分, hexdecial编码后使用
  5. 客户端每次请求的数据都需要提供Token, 服务器端进行验证后执行相应的服务

#### Token 生成接口

* URL: http://hosts:port/api_path/access
* METHOD: POST
* 参数: CID=
* 返回: 16字节的随机数

### 扩展的对称非对称密钥管理接口

#### 1. 申请新的非对称密钥的接口

* URL: http://hosts:port/api_path/keys/apply
* METHOD: POST
* 参数: type=SM2&token=安全令牌
* 返回: 

```json
{
  "success": true,
  "message": "操作结果说明,失败时可以提示错误信息",
  "retcode": 0,   # 可以用于返回码错误码
  "type": "SM2"， # 或者 RSA 配置请求时输入的type
  "public": "公钥, (X32+Y32)的hexdecial编码显示",
  "value": "私钥, 被预值保护密钥加密, 最后进行hexdicial编码"
}
```

私钥的保护密钥为从TOKEN中还原的Data(32字节，前16字节作为密钥，后16字节作为IV)，SM4_CBC保护即可.

#### 2. 密钥绑定关系接口

* URL: http://hosts:port/api_path/keys/bind
* METHOD: POST
* 参数: public={public} & certificate={certficate} & serial={serialNumber} & token = {安全令牌}
* 返回:

```json
{
  "success": true,
  "message": "操作结果说明,失败时可以提示错误信息",
  "retcode": 0,   # 可以用于返回码错误码
}
```

#### 3. 密钥停用的接口

* 请求: http://hosts:port/api_paths/keys/revoke
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

* 请求: http://hosts:port/api_paths/org/info
* METHOD: POST
* 参数: token={安全令牌}&OrgID={机构编码}
* 返回:

```json
{
  "success": true,
  "message": "操作结果说明,失败时可以提示错误信息",
  "retcode": 0,   # 可以用于返回码错误码
  "keySets:" {"上位机密钥集":{[密钥1,...,密钥n]}, "控制器密钥集":{[密钥1,...,密钥n]}},
  "properties": {自定义信息}
```

其中的密钥为一个JSON对象，内容如 `{id:ID,name:name,value:密钥密文,code:"校验值", version:x}`
密钥值为被KEY加密的密文

### 机构下的设备注册接口

* 请求: http://posts:port/api_path/org/{orgid}/device/add
* METHOD: POST
* 参数: token={安全令牌}&ID={设备ID}&Name={设备名称}&IP={设备IP}&kSet={密钥集编号}
* 返回:

```json
{
  "success": true,
  "message": "操作结果说明,失败时可以提示错误信息",
  "retcode": 0,   # 可以用于返回码错误码
}
```
