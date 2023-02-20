# bilibili_hlj_cmcc_android

B站黑龙江移动安卓前端

#
#### 推荐位 => 打开apk
```
包名：tv.huan.bilibili.heilongjiang
类名：tv.huan.bilibili.ui.welcome.WelcomeActivity
命令：adb shell am start -n tv.huan.bilibili.heilongjiang/tv.huan.bilibili.ui.welcome.WelcomeActivity
```

#
#### 推荐位 => 
```
包名：tv.huan.bilibili.heilongjiang
类名：tv.huan.bilibili.ui.welcome.WelcomeActivity
命令：adb shell am start -n tv.huan.bilibili.heilongjiang/tv.huan.bilibili.ui.welcome.WelcomeActivity -e type "1" -e exit "1" -e voiceId "491893"
```

#
#### 机顶盒开adb方法
```
1.数码（M201-D、HM201）：设置-更多设置-高级（密码10086）-开启adb（密码6321）-端口31015。
2.创维（CM201-1、EV900）：设置-更多设置-选中高级一直按遥控器右键-选择下方弹出的usb调试-端口60001。
3.中兴：按住遥控器返回键（5秒以上）然后松开迅速按遥控器左键-弹出二维码-将二维码提供中兴同事，获取授权（如已授权，直接打开adb权限即可），无端口。
4.长虹：设置-更多设置-清楚缓存（一直按确定或者OK），然后直接可链接，无端口
5.朝歌：需使用朝歌软件使用，请联系朝歌同事获取。
6.易视腾：联系易视腾获取授权文件，放到U盘根目录，插入终端，端口5114。     
7.MG101：开机状态反复按黄蓝或者黄绿。弹出界面找到adb开关，开启adb即可连接，无端口。
8.新款盒子M301-h  adb在高级里，密码8288.6321
```