# Key Management System

## 扩展的对称非对称密钥管理接口

该接口通过 `REST APis` 实现，通过`HTTP`协议封装`JSON`对象，为应用系统或相关工具提供非对称密钥的管理服务.

### 接口定义

#### 安全性要求

接口需要授权才能访问，授权及验证规则如下:

  * 定义一个客户端CID(一个32字节的随机数据), 告知客户端
  * 定义一个会话验证密钥KEY(SM4), 告知客户端
  * 客户端用CID,KEY来计算TOKEN,服务器进行验证
  * 服务器对客户端IP的验证是可选项，生成环节验证，测试开发环节不验证

TOKEN 分为量部分组成，及数据部分||验证部分。

  1. 服务器提供数据Data，可随机生成，不小于16个字节
  2. 数据部分=SM4_CBC(Data)
  3. 验证数据=SM3(Data||CID)
  4. TOKEN =  数据部分||验证部分, hexdecial编码后使用

#### 接口定义
##### 1. 申请新的非对称密钥的接口

URL: http://hosts:port/api_path/keys/apply
METHOD: POST,GET
参数: type=SM2&token=安全令牌
返回: 

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

##### 2. 密钥绑定关系接口

URL: http://hosts:port/api_path/keys/bind
METHOD: POST
参数: public={public} & certificate={certficate} & serial={serialNumber} & token = {安全令牌}
返回:

```json
{
  "success": true,
  "message": "操作结果说明,失败时可以提示错误信息",
  "retcode": 0,   # 可以用于返回码错误码
}
```

TOKEN中的Data为随机数

##### 3. 密钥停用的接口

请求: http://hosts:port/api_paths/keys/revoke
METHOD: POST
参数: public={public}&reason={停用原因}&token = {安全令牌}
返回:

```json
{
  "success": true,
  "message": "操作结果说明,失败时可以提示错误信息",
  "retcode": 0,   # 可以用于返回码错误码
}
```

TOKEN中的Data为随机数

