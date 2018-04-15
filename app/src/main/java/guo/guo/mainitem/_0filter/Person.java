package guo.guo.mainitem._0filter;

import static android.R.attr.id;

/**
 * 作者：author
 * 时间：2017/8/16:18:41
 * 说明：
 */

public class Person {
    private String age;
    private String name;

    public Person() {
    }

    public Person(String age, String name) {
        this.age = age;
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "id是"+id+"        name为"+name;
    }
}
