package com.android.lixiang.liangwei.presenter.data.bean;

import java.util.List;

public class ListExamInfoBean {

    /**
     * data : {"list":[{"createtime":1539848007000,"id":3,"info":"dagdga","infonumber":"L471539830060039","label":"agadg","lost":"hreherh","number":"S901539848006541","spnumber":"234235","work":"fgwegerg"}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * createtime : 1539848007000
             * id : 3
             * info : dagdga
             * infonumber : L471539830060039
             * label : agadg
             * lost : hreherh
             * number : S901539848006541
             * spnumber : 234235
             * work : fgwegerg
             */

            private long createtime;
            private int id;
            private String info;
            private String infonumber;
            private String label;
            private String lost;
            private String number;
            private String spnumber;
            private String work;

            public long getCreatetime() {
                return createtime;
            }

            public void setCreatetime(long createtime) {
                this.createtime = createtime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public String getInfonumber() {
                return infonumber;
            }

            public void setInfonumber(String infonumber) {
                this.infonumber = infonumber;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getLost() {
                return lost;
            }

            public void setLost(String lost) {
                this.lost = lost;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getSpnumber() {
                return spnumber;
            }

            public void setSpnumber(String spnumber) {
                this.spnumber = spnumber;
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
