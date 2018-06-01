# BaseAAC
## 基于Android Architecture Components架构搭建的基本项目。

1. 使用dagger2 2.15版本，为了最少化子activity/fragment的代码。把注入统一写入了BaseAacActivity、BaseAacFragment、BaseMvvmActivity、BaseMvvmFragment中。
2. 当你不需要注入的时候直接extend 最高级BaseActivity/BaseFragment(简单页面如过渡页、说明页、用户协议页).
3. 当你需要注入其他(如字体库、sharepreference、部分工具类)又不需要注入viewmodel时，BaseAacActivity<>泛型不传递viewmodel即可。
4. BaseAacActivity和BaseMvvmActivity区别在于是否使用了databinding。因为之前项目使用组件化开发，作为module的时候不能使用butterknife，所以我个人引入了databinding代替findviewbyid功能。
5. 整体架构参照google官方推荐，ViewModel层只作为数据存储层。Repository层作为数据逻辑处理层。
6. 整体分包模式为组件化分包。复杂项目亦可以用组件化+功能化分包方式。自行选择。但不推荐只使用功能化分包。
7. BaseActivity/BaseFragment中有部分toolbar逻辑代码(已注释)。按理来讲还应该包含沉浸栏、EventBus、showDialog、dismissDialog等,依据项目可自行添加。
8. 推荐使用一个baseAdapter。可依据团队使用情况找一个合适的或者封装一个适合项目的。
