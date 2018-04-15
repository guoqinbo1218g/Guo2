package guo.st_reflect;

import java.util.HashMap;
import java.util.List;

import guo.annotation.HelloAnnotation;

/**
 *  这里的@HelloAnnotation(value = "hello",name = "annotation")
 *   如果只写@HelloAnnotation("annotation") 则默认给 value元素赋值 ,如果没有value元素报错
 */

@HelloAnnotation(value = "annotation",name = "hello")
public class RefBean {
    private String name;
    private int age;
    public  String address;
    private List<String> stringList;
    private HashMap<String,Integer> stringHashMap;
    private String [] strings;

    public RefBean() {
    }

    public RefBean(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }
    private RefBean(String name) {
        this.name = name;
    }
    private RefBean(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private static void testStaticMethod(String test){
        System.out.println("  RefBean: testStaticMethod ");
    }

    private void test(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "name :"+name+"  ,age :"+age+"   ,address :"+address;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public HashMap<String, Integer> getStringHashMap() {
        return stringHashMap;
    }

    public void setStringHashMap(HashMap<String, Integer> stringHashMap) {
        this.stringHashMap = stringHashMap;
    }
}
