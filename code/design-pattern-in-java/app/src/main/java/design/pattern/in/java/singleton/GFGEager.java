package design.pattern.in.java.singleton;

// Java code to create singleton class by  
// Eager Initialization 
public class GFGEager {
  // public instance initialized when loading the class
  private static final GFGEager instance = new GFGEager();

  private GFGEager() {
    // private constructor
  }

  public static GFGEager getInstance() {
    return instance;
  }
}

// - 优点
// 非常简单

// - 缺点
// 1.可能导致资源浪费。因为总是创建类的实例，所以是否需要它
// 2.如果不需要，在创建实例时也会浪费CPU时间
// 3.不可能进行异常处理