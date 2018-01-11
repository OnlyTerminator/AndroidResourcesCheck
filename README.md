# AndroidResourcesCheck
Android项目多Module之间资源文件冲突检查

## 如何使用
首先我们要在项目最外层的build.gradle里面引用我上传的项目
```java
apply plugin: 'geekplugin'
```
其次加载其代码
```java
classpath 'com.geek.check:AndroidResourceCheck:1.0.0'
```
然后设置参数，用来配置我们需要检测的资源
```java
checkResources{
    checkString true
    checkColors true
    checkDimens true
}
```
最后就是运行这个插件
我们可以在项目的根目录运行这个Task
```java
gradle checkResources
```
如果我们有资源冲突文件，最后会在项目的跟目录生成ResourcesError目录，对应的冲突文件在里面，大家可以查看。

### 有朋友说还是不太清楚，给一个完整的build.gradle文件吧。
```java
//下面这行是我们添加的
apply plugin: 'geekplugin'
buildscript {
    
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.0.0'
        //下面这行是我们是我们要添加的
        classpath 'com.geek.check:AndroidResourceCheck:1.0.0'
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
//下面的对应的参数是我们需要添加的
checkResources{
    checkString true
    checkColors true
    checkDimens true
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}

```
## 详细说明
大家想简单了解一下原理的可以阅读一下[Android资源冲突检测插件](https://www.jianshu.com/p/9d2a047f2c22)
