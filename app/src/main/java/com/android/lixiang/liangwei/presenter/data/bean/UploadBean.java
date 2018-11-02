package com.android.lixiang.liangwei.presenter.data.bean;

public class UploadBean {

    /**
     * data : {"url":"a8eadd271f914d9ab125ec983257a90b.png,1a2fb869fbea40f2bd719cabe093c03e.png"}
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
         * url : a8eadd271f914d9ab125ec983257a90b.png,1a2fb869fbea40f2bd719cabe093c03e.png
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
