# K-Backend
添加青龙面板环境变量 JD_COOKIE 过期CK邮箱通知
[上车](http://81.69.228.131:9002)
## 启动
创建application.properties文件
![image](https://github.com/Fanta024/K-Backend/assets/80446671/32b2db47-9271-482e-b17c-d291f2b74c80)
```properties
ckServer.url=      #你的青龙面板开放api地址 例如http://xxx:xxx/open
ckServer.clientId=    #青龙面板Client ID
ckServer.clientSecret=    #青龙面板Client Secret
#以下为过期CK邮箱通知
mailServer.host=smtp.qq.com
mailServer.port=587
mailServer.from=
mailServer.username=
mailServer.password=
```
![image](https://github.com/Fanta024/K-Backend/assets/80446671/774e08b1-cd06-4f39-9031-91044a1f8e3c)

