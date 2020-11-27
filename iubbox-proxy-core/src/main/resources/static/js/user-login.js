new Vue({
    el: "#app",
    data: function () {
        return {
            userInfo: {
                username: '',
                password: ''
            }
        };
    },

    methods: {
        userLogin: function () {
            var _this = this;
            axios.post(getUserLoginUrl(), {
                "username": this.userInfo.username,
                "password": this.userInfo.password
            }).then(function (response) {
                console.log(response);
                if (response.data.data != null) {
                    localStorage.setItem('iubbox-token', response.data.data);
                    window.location.href = "./test-dubbo-web.html";
                } else {
                    _this.$message({
                        message: "账号异常",
                        type: 'warning'
                    });
                }
            });

        },
        userRegister: function () {
            var _this = this;
            if (!(this.userInfo.username.trim() != '' && this.userInfo.password.trim() != '')) {
                _this.$message({
                    message: "用户名和密码不能为空",
                    type: 'warning'
                });
                return;
            }
            axios.post(getUserRegisterUrl(), {
                "username": this.userInfo.username,
                "password": this.userInfo.password
            }).then(function (response) {
                console.log(response);
                if (response.data.data != null) {
                    localStorage.setItem('iubbox-token', response.data.data);
                    window.location.href = "./test-dubbo-web.html";
                } else {
                    _this.$message({
                        message: response.data.msg,
                        type: 'warning'
                    });
                }
            });
        }
    }
});