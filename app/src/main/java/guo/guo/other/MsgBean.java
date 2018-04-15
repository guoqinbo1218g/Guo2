package guo.guo.other;

import java.util.List;

/**
 * 作者：author
 * 时间：2017/11/16:15:22
 * 说明：
 *
 *          获取首页信息的bean
 */

public class MsgBean {
    private List<我的首页Bean> 我的首页;
    private List<公共模块Bean> 公共模块;

    public List<我的首页Bean> get我的首页() {
        return 我的首页;
    }

    public void set我的首页(List<我的首页Bean> 我的首页) {
        this.我的首页 = 我的首页;
    }

    public List<公共模块Bean> get公共模块() {
        return 公共模块;
    }

    public void set公共模块(List<公共模块Bean> 公共模块) {
        this.公共模块 = 公共模块;
    }

    public static class 我的首页Bean {

        private String ParentID;
        private String RoleID;
        private String Address;
        private String Ico;
        private int Sort;
        private Object P;
        private String ID;
        private int Type;
        private String Params;
        private String Title;
        private String AppID;

        public String getParentID() {
            return ParentID;
        }

        public void setParentID(String ParentID) {
            this.ParentID = ParentID;
        }

        public String getRoleID() {
            return RoleID;
        }

        public void setRoleID(String RoleID) {
            this.RoleID = RoleID;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getIco() {
            return Ico;
        }

        public void setIco(String Ico) {
            this.Ico = Ico;
        }

        public int getSort() {
            return Sort;
        }

        public void setSort(int Sort) {
            this.Sort = Sort;
        }

        public Object getP() {
            return P;
        }

        public void setP(Object P) {
            this.P = P;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public int getType() {
            return Type;
        }

        public void setType(int Type) {
            this.Type = Type;
        }

        public String getParams() {
            return Params;
        }

        public void setParams(String Params) {
            this.Params = Params;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getAppID() {
            return AppID;
        }

        public void setAppID(String AppID) {
            this.AppID = AppID;
        }
    }

    public static class 公共模块Bean {


        private String ParentID;
        private String RoleID;
        private String Address;
        private Object Ico;
        private int Sort;
        private Object P;
        private String ID;
        private int Type;
        private String Params;
        private String Title;
        private String AppID;

        public String getParentID() {
            return ParentID;
        }

        public void setParentID(String ParentID) {
            this.ParentID = ParentID;
        }

        public String getRoleID() {
            return RoleID;
        }

        public void setRoleID(String RoleID) {
            this.RoleID = RoleID;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public Object getIco() {
            return Ico;
        }

        public void setIco(Object Ico) {
            this.Ico = Ico;
        }

        public int getSort() {
            return Sort;
        }

        public void setSort(int Sort) {
            this.Sort = Sort;
        }

        public Object getP() {
            return P;
        }

        public void setP(Object P) {
            this.P = P;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public int getType() {
            return Type;
        }

        public void setType(int Type) {
            this.Type = Type;
        }

        public String getParams() {
            return Params;
        }

        public void setParams(String Params) {
            this.Params = Params;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getAppID() {
            return AppID;
        }

        public void setAppID(String AppID) {
            this.AppID = AppID;
        }
    }
}
