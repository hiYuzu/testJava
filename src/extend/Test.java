package extend;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2020/7/13 10:21
 */
public class Test {
    public static void main(String[] args) {
        ParentClass parentClass = new ParentClass();
        ChildClass1 child1 = new ChildClass1();
        ChildClass2 child2 = new ChildClass2();
        System.out.println(parentClass.getName());
        child1.setName("123");
        System.out.println(parentClass.getName());
        System.out.println(child1.getName());
        System.out.println(child2.getName());
    }
}
