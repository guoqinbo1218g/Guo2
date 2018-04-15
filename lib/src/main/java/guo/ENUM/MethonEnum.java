package guo.ENUM;

/**
 * 作者：author
 * 时间：2017/12/8:11:55
 * 说明： enum中添加方法
 */

public enum MethonEnum {
    GUO(1),GUOGUO(11),GUO2;
    private int num;
    MethonEnum(){}

    MethonEnum(int num) {
        this.num = num;
    }
    //如果 是空参构造 返回 0
    public int getNum(){
        return num;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
