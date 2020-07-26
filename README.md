# 自定义view实现TextView两端对齐

因为需求有用到textview两端对齐，官方的布局不能实现，在网上也找不到满意的方案，所以自己写了个自定义view实现两端对齐功能。支持末尾加冒号或者其他符号，并且自定义冒号左边距和右边距。有两种样式，一种是两端对齐，另外一种是中间分散对齐，具体可以看demo。目前如果只有一个字，就居中显示。

## 以下是效果图

![在这里插入图片描述](https://raw.githubusercontent.com/jiangkt/align/master/align.png)

## 项目地址

[github项目地址](https://github.com/jiangkt/align)
## 使用gradle依赖

``` implementation 'com.github.jiangkt:align:1.0.0'```