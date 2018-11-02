package com.android.lixiang.liangwei.presenter.data.bean;

public class IfExamBean {

    /**
     * data : {"res":true}
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
         * res : true
         */

        private boolean res;

        public boolean isRes() {
            return res;
        }

        public void setRes(boolean res) {
            this.res = res;
        }
    }
}
