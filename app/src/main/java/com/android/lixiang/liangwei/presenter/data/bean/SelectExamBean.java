package com.android.lixiang.liangwei.presenter.data.bean;

public class SelectExamBean {


    /**
     * data : {"info":{"createtime":1540452797521,"id":12,"info":"","infonumber":"L441540447416909","label":"","lost":"ieke","number":"S411540452797521","spnumber":"lll","sptime":1540396800000,"url":"43.97528981975151, 125.37569737861213, 43.97109139597845, 125.37558425695659, 43.970777374132524, 125.3820160487362, 43.97525493102009, 125.38305030635706","work":"sjw"}}
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
         * info : {"createtime":1540452797521,"id":12,"info":"","infonumber":"L441540447416909","label":"","lost":"ieke","number":"S411540452797521","spnumber":"lll","sptime":1540396800000,"url":"43.97528981975151, 125.37569737861213, 43.97109139597845, 125.37558425695659, 43.970777374132524, 125.3820160487362, 43.97525493102009, 125.38305030635706","work":"sjw"}
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
             * createtime : 1540452797521
             * id : 12
             * info :
             * infonumber : L441540447416909
             * label :
             * lost : ieke
             * number : S411540452797521
             * spnumber : lll
             * sptime : 1540396800000
             * url : 43.97528981975151, 125.37569737861213, 43.97109139597845, 125.37558425695659, 43.970777374132524, 125.3820160487362, 43.97525493102009, 125.38305030635706
             * work : sjw
             */

            private long createtime;
            private int id;
            private String info;
            private String infonumber;
            private String label;
            private String lost;
            private String number;
            private String spnumber;
            private long sptime;
            private String url;
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

            public long getSptime() {
                return sptime;
            }

            public void setSptime(long sptime) {
                this.sptime = sptime;
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
