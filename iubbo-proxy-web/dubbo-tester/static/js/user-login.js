new Vue({
    el: "#app",
    data: function () {
        return {
            userInfo:{
                username:'',
                password:''
            }
        };
    },

    methods:  {
        userLogin: function() {
            var _this=this;
            axios.post(getUserLoginUrl(), {
                "username": this.userInfo.username,
                "password": this.userInfo.password
            }).then(function (response) {
                console.log(response);
                if(response.data.data!=null){
                    localStorage.setItem('iubbox-token',response.data.data);
                    window.location.href="./test-dubbo-web.html";
                }else{
                    _this.$message({
                        message: "账号异常",
                        type: 'warning'
                    });
                }
            });

        }
    }
});