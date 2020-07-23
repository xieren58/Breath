package com.zkp.breath.review;


/**
 * 作用在代码的注解是：
 *
 * @Override - 检查该方法是否是重写方法。如果发现其父类，或者是引用的接口中并没有该方法时，会报编译错误。
 * @Deprecated - 标记过时方法。如果使用该方法，会报编译警告。
 * @SuppressWarnings - 指示编译器去忽略注解中声明的警告。
 *      deprecation  -- 使用了不赞成使用的类或方法时的警告
 *      unchecked    -- 执行了未检查的转换时的警告，例如当使用集合时没有用泛型 (Generics) 来指定集合保存的类型。
 *      fallthrough  -- 当 Switch 程序块直接通往下一种情况而没有 Break 时的警告。
 *      path         -- 在类路径、源文件路径等中有不存在的路径时的警告。
 *      serial       -- 当在可序列化的类上缺少 serialVersionUID 定义时的警告。
 *      finally      -- 任何 finally 子句不能正常完成时的警告。
 *      all          -- 关于以上所有情况的警告。
 *
 * <p>
 * 作用在其他注解的注解(或者说 元注解)是:
 * @Retention - 标识这个注解怎么保存，是只在代码中，还是编入class文件中，或者是在运行时可以通过反射访问。
 * @Documented - 标记这些注解是否包含在用户文档中。
 * @Target - 标记这个注解应该是哪种 Java 成员。
 * @Inherited - 标记这个注解是继承于哪个注解类(默认 注解并没有继承于任何子类)
 *
 * <p>
 * 从 Java 7 开始，额外添加了 3 个注解:
 * @SafeVarargs - Java 7 开始支持，忽略任何使用参数为泛型可变参数的方法或构造函数调用产生的警告。
 * @FunctionalInterface - Java 8 开始支持，标识一个匿名函数或函数式接口,接口只能有一个抽象方法（其实就是标识可以使用lambda表达式）
 * @Repeatable - Java 8 开始支持，标识某注解可以在同一个声明上使用多次。
 * <p>
 * <p>
 * 注解主干类"：
 *      1.Annotation
 *      2. ElementType
 *               TYPE,                 类、接口（包括注释类型）或枚举声明
 *               FIELD,               字段声明（包括枚举常量）
 *               METHOD,              方法声明
 *               PARAMETER,           参数声明
 *               CONSTRUCTOR,         构造方法声明
 *               LOCAL_VARIABLE,      局部变量声明
 *               ANNOTATION_TYPE,     注释类型声明
 *               PACKAGE              包声明
 *      3.RetentionPolicy
 *               SOURCE         Annotation信息仅存在于编译器处理期间，编译器处理完之后就没有该Annotation信息了
 *               CLASS          编译器将Annotation存储于类对应的.class文件中。默认行为
 *               RUNTIME        编译器将Annotation存储于class文件中，并且可由JVM读入
 *
 *  总结：@Retention，@Documented，@Target，@Inherited，基本都可以不用声明，使用默认就行。
 */


/**
 *  "@interface"修饰的类为注解类，其实现了 java.lang.annotation.Annotation 接口
 */
public @interface JavaAnnotationDemo {

}

