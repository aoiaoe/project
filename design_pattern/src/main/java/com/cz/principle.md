## 五大原则(SOLID)：

单一职责原则（SRP）：就一个类而言，应该仅有一个引起它变化的原因。即一个类中应该只有一类逻辑。

开放-封闭原则（OCP）：软件实体（类、模块、函数等等）应该可以扩展，但是不可修改。即开放扩展，封闭修改。

依赖倒转原则（DIP）：高层模块不应该依赖底层模块。两个都应该依赖抽象；抽象不应该依赖细节。细节应该依赖抽象。即面向接口编程，而不是面向实现编程。

里氏代换原则（LSP）：子类型必须能够替换掉他们的父类型。

合成/聚合复用原则（CARP）：尽量使用合成/聚合，尽量不要使用类继承。

迪米特法则：如果两个类不必彼此直接通信，那么这两个类就不应当发生直接的相互作用。如果其中一个类需要调用另一个类的某一方法的话，可以通过第三者转发这个调用