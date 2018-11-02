package com.android.lixiang.liangwei.presenter.data.bean;

import java.util.List;

public class ListLineBean {

    /**
     * data : {"lines":[{"line":"43.962276255259695, 125.37266119419428, 43.95954460457571, 125.37240983286574, 43.95910770446554, 125.37711212861586, 43.96103180277052, 125.37767002807732","number":"L371540272868218"},{"line":"43.96238216495922, 125.37375246999454, 43.96016684942134, 125.37381377771248, 43.960158022624114, 125.37645613663858, 43.96238657771818, 125.37715504394737","number":"L151540273292889"},{"line":"43.96242188074555, 125.37206037937608, 43.960188914053845, 125.37232400217738, 43.95957108240739, 125.37728992055133","number":"L471540273400462"}]}
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
        private List<LinesBean> lines;

        public List<LinesBean> getLines() {
            return lines;
        }

        public void setLines(List<LinesBean> lines) {
            this.lines = lines;
        }

        public static class LinesBean {
            /**
             * line : 43.962276255259695, 125.37266119419428, 43.95954460457571, 125.37240983286574, 43.95910770446554, 125.37711212861586, 43.96103180277052, 125.37767002807732
             * number : L371540272868218
             */

            private String line;
            private String number;

            public String getLine() {
                return line;
            }

            public void setLine(String line) {
                this.line = line;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }
        }
    }
}
