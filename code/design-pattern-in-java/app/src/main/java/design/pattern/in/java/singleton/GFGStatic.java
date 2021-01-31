package design.pattern.in.java.singleton;

// Java code to create singleton class 
// Using Static block 
public class GFGStatic {
    // public instance
    public static GFGStatic instance;

    private GFGStatic() {
        // private constructor
    }

    static {
        // static block to initialize instance
        instance = new GFGStatic();
    }
}

// - 优点
// 1.实施起来非常简单
// 2.无需实现getInstance()方法。实例可以直接访问
// 3.可以在静态块中处理异常

// - 缺点
// 1.可能导致资源浪费。因为总是创建类的实例，所以是否需要
// 2.如果不需要，在创建实例时也会浪费CPU时间