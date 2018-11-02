package com.android.lixiang.liangwei.presenter.data.bean;

public class RegBean {

    /**
     * data : {"user":{"id":7,"name":"lixiang","password":"F1BA77CA9E67FA0D3F0BE482721E58F4","phone":"13331749289","salt":"b3871"}}
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
         * user : {"id":7,"name":"lixiang","password":"F1BA77CA9E67FA0D3F0BE482721E58F4","phone":"13331749289","salt":"b3871"}
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
             * id : 7
             * name : lixiang
             * password : F1BA77CA9E67FA0D3F0BE482721E58F4
             * phone : 13331749289
             * salt : b3871
             */

            private int id;
            private String name;
            private String password;
            private String phone;
            private String salt;

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
}
