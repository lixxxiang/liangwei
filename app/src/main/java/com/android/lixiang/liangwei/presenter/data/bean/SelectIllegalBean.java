package com.android.lixiang.liangwei.presenter.data.bean;

public class SelectIllegalBean {

    /**
     * data : {"info":{"address":"hrtjtj","area":"124","createtime":1539830060000,"flag":1,"floor":21,"id":10,"iid":"2207847354","info":"fewert","isdanger":1,"isliangwei":1,"line":"7636358","name":"325325","number":"L471539830060039","perimeter":"32","phone":"1111111111","status":6,"time":1252425600000,"type":1,"url":"grehre","work":"fgrher"}}
     * message : success
     * status : 200
     */

    private DataBean data;
    private String message;
    private int status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * info : {"address":"hrtjtj","area":"124","createtime":1539830060000,"flag":1,"floor":21,"id":10,"iid":"2207847354","info":"fewert","isdanger":1,"isliangwei":1,"line":"7636358","name":"325325","number":"L471539830060039","perimeter":"32","phone":"1111111111","status":6,"time":1252425600000,"type":1,"url":"grehre","work":"fgrher"}
         */

        private InfoBean info;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * address : hrtjtj
             * area : 124
             * createtime : 1539830060000
             * flag : 1
             * floor : 21
             * id : 10
             * iid : 2207847354
             * info : fewert
             * isdanger : 1
             * isliangwei : 1
             * line : 7636358
             * name : 325325
             * number : L471539830060039
             * perimeter : 32
             * phone : 1111111111
             * status : 6
             * time : 1252425600000
             * type : 1
             * url : grehre
             * work : fgrher
             */

            private String address;
            private String area;
            private long createtime;
            private int flag;
            private int floor;
            private int id;
            private String iid;
            private String info;
            private int isdanger;
            private int isliangwei;
            private String line;
            private String name;
            private String number;
            private String perimeter;
            private String phone;
            private int status;
            private long time;
            private int type;
            private String url;
            private String work;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public long getCreatetime() {
                return createtime;
            }

            public void setCreatetime(long createtime) {
                this.createtime = createtime;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public int getFloor() {
                return floor;
            }

            public void setFloor(int floor) {
                this.floor = floor;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getIid() {
                return iid;
            }

            public void setIid(String iid) {
                this.iid = iid;
            }

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public int getIsdanger() {
                return isdanger;
            }

            public void setIsdanger(int isdanger) {
                this.isdanger = isdanger;
            }

            public int getIsliangwei() {
                return isliangwei;
            }

            public void setIsliangwei(int isliangwei) {
                this.isliangwei = isliangwei;
            }

            public String getLine() {
                return line;
            }

            public void setLine(String line) {
                this.line = line;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getPerimeter() {
                return perimeter;
            }

            public void setPerimeter(String perimeter) {
                this.perimeter = perimeter;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getWork() {
                return work;
            }

            public void setWork(String work) {
                this.work = work;
            }
        }
    }
}
