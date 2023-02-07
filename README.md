 # Compose 脚手架
全程使用Compose 

```kotlin
ext {
    compose_version = '1.3.2'
    accompanistVersion = '0.28.0'
}
```
#### 使用库
compose、官方的accompanist、Paging、Hilt、okhttp、retrofit

#### MVI架构
对MVI使用，解释 在 LoginPageViewModel.kt 中

#### 网络请求
分开了网络请求，一个域名对应一个OkHttp对象，对应一个retrofit对象，最好图片上传的url也用另一个对象

如果统一okhttp的拦截器会比较麻烦，判断很多

#### RefreshTokenInterceptor拦截器
增加了 token失效 重新刷新token的方式

如果是401 或者是 json 返回的的code 表示token失效，使用synchronized刷新token

刷新后重新请求接口

#### 其他
OnceScaffoldTheme 里面使用了 `CompositionLocalProvider`  进行了穿透

AdPage 界面，使用了穿透的方式可以统一修改颜色和字体，只是做一个示例。没有全局修改成暗黑模式

主界面 AndroidManifestPage 使用了 `AnimatedNavHost` 导航切换界面，也有全局的导航对象

MainPage界面，使用了material包 的 Scaffold进行导航，并且切换Tab也做了处理，参照注释

HomePage界面，用了wanandroid的API，Paging列表显示数据 

NewsPage界面，做了一个简单的弹窗，这里打算做成组件集合，展示一些其他的Compose组件

QRPage界面 增加了，列表向上滑动拉出键盘，用的是 accompanist-insets-ui 包里的 Scaffold ， 需要API 30+

test包里面是，凯哥的一些教学示例，可以删除

代码持续更新中。。。

> 如果你觉得不错，麻烦点个赞\~
