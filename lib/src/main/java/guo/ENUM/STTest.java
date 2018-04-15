package guo.ENUM;

import org.junit.Test;

import java.util.EnumSet;
/**
 * 作者：author
 * 时间：2017/12/8:11:13
 * 说明：
 */

public class STTest {

    @Test
    public void testEnumWeek(){
        for (WeekEnum week: WeekEnum.values()) {
            //week.getDeclaringClass():class guo.ENUM.WeekEnum,    week.ordinal():0,    week.toString():MON
            System.out.println("week.getDeclaringClass():"+week.getDeclaringClass()
                    +",    week.ordinal():" + week.ordinal()+",    week.toString():"+week.toString());
        }
    }

    @Test
    public void testMethonEnum(){
        for (MethonEnum methonEnum:MethonEnum.values()) {
            System.out.println("methonEnum "+methonEnum.name()+",   methonEnum:"+methonEnum.getNum());
        }
    }

    @Test
    public void testClassEnum() throws Exception {
        Class clazz1 = Class.forName("guo.ENUM.WeekEnum");
        Object[] enumConstants = clazz1.getEnumConstants();
        for (Object obj:clazz1.getEnumConstants()) {//获取enum的值
            System.out.println("getEnumConstants   :"+obj);
        }

    }
    //使用接口组织枚举
    interface  Testaa{
        enum TESTBB{
            GUO,GUOG,GUOGG;
        }
    }
    @Test
    public void testEnumSet(){
        EnumSet enumSet = EnumSet.noneOf(WeekEnum.class);
        enumSet.add(WeekEnum.MON);
        enumSet.addAll(EnumSet.of(WeekEnum.TUE,WeekEnum.SAT,WeekEnum.FRI));
        enumSet.addAll(EnumSet.allOf(WeekEnum.class));//[MON, TUE, WED, THUR, FRI, SAT, SUN]
        System.out.println("enumSet:"+enumSet);
        enumSet.removeAll(EnumSet.range(WeekEnum.MON,WeekEnum.FRI));//[SAT, SUN]
        System.out.println("after remove  numSet:"+enumSet);
        enumSet = EnumSet.complementOf(enumSet);//[MON, TUE, WED, THUR, FRI]
        System.out.println("complementOf  numSet:"+enumSet);

    }
    @Test
    public void testEnumMap(){

    }

}
