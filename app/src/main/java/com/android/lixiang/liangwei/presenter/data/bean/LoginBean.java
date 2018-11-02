package com.android.lixiang.liangwei.presenter.data.bean;

public class LoginBean {
    /**
     * data : {"user":{"createtime":1540827784297,"id":3,"name":"lixiang","password":"FAE869591BB6FEE78CCC10AA4B9D03F7","phone":"13331749289","salt":"361a3"}}
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
         * user : {"createtime":1540827784297,"id":3,"name":"lixiang","password":"FAE869591BB6FEE78CCC10AA4B9D03F7","phone":"13331749289","salt":"361a3"}
         */

        private UserBean user;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * createtime : 1540827784297
             * id : 3
             * name : lixiang
             * password : FAE869591BB6FEE78CCC10AA4B9D03F7
             * phone : 13331749289
             * salt : 361a3
             */

            private long createtime;
            private int id;
            private String name;
            private String password;
            private String phone;
            private String salt;

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getSalt() {
                return salt;
            }

            public void setSalt(String salt) {
                this.salt = salt;
            }
        }
    }


    /**
     * message : success
     * status : 200
     */


}
