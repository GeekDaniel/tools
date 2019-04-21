# deepcopier 设计文档
## 需求分析
可以通过一个注释@deepCopy 生成 <T> T deepCopy();方法.<br/>
满足以下语义 T copy=t.deepCopy();<br/>
为了实现上诉功能,需要以下支持.
- 在编译期修改/添加代码的可能
[Java-JSR-269-插入式注解处理器](https://liuyehcf.github.io/2018/02/02/Java-JSR-269-%E6%8F%92%E5%85%A5%E5%BC%8F%E6%B3%A8%E8%A7%A3%E5%A4%84%E7%90%86%E5%99%A8/)
给java开发者提供了在编译期修改/添加代码的可能.
- (深度优先/广度优先),递归遍历属性,并将属性初始化,然后set回去.

## 实施
1. 生成一个签名的空方法
<T> T deepCopy(){
return null;
}
2. 完成一级属性赋值
