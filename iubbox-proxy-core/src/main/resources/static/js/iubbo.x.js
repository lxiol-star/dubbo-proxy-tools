new Vue({
    el: "#app",
    data: function () {
        return {
            dubboService: [],
            allSelectNav: [],
            allSelectCommonzk: [],
            userAccountList: [],
            allSelectAccountList: [],
            copyAccountName: '',
            isLogin: false,
            commonzk: [],
            registerTypeList: [{
                type: 1,
                name: 'zookeeper'
            }
            ],
            //当前选中的参数索引下标
            currentReqArgIndex: -1,
            dialogVisible: false,
            selfEditdialogFormVisible: false,
            editDialogFormVisible: false,
            options: [{
                value: 'boolean',
                label: 'boolean'
            }, {
                value: 'short',
                label: 'short'
            }, {
                value: 'int',
                label: 'int'
            }, {
                value: 'long',
                label: 'long'
            }, {
                value: 'float',
                label: 'float'
            }, {
                value: 'double',
                label: 'double'
            }, {
                value: 'char',
                label: 'char'
            }, {
                value: 'byte',
                label: 'byte'
            }, {
                value: 'java.lang.String',
                label: 'java.lang.String'
            }, {
                value: 'java.lang.Double',
                label: 'java.lang.Double'
            }, {
                value: 'java.lang.Float',
                label: 'java.lang.Float'
            }, {
                value: 'java.lang.Integer',
                label: 'java.lang.Integer'
            }, {
                value: 'java.lang.Object',
                label: 'java.lang.Object'
            }, {
                value: 'java.util.List',
                label: 'java.util.List'
            }, {
                value: 'java.lang.Class',
                label: 'java.lang.Class'
            }, {
                value: 'java.lang.Long',
                label: 'java.lang.Long'
            }, {
                value: 'java.lang.Boolean',
                label: 'java.lang.Boolean'
            }, {
                value: 'java.util.Map',
                label: 'java.util.Map'
            }],
            editArg: {
                argType: '',
                argValue: ''
            },
            reqArg: {
                timeout: '3000',
                loadbalance: null,
                async: false,
                actives: '',
                retries: 0,
                version: '',
                group: '',
                zkHost: '',
                registerType: 'zookeeper',
                serviceAddr: '',
                type: '',
                namespace: '',
                interfaceName: "",
                methodName: "",
                argTypes: [],
                argObjects: [],
                attachments: {
                    "key": "value"
                },
                requestTimes: 1,
                parallelTimes: 1
            },
            // 自定义参数对象信息
            selfDefArg: {
                //为了兼容一些json转换的格式问题
                argType: '',
                argValue: ''
            },

            tableData: [],
            reqArgRecordList: [],
            editIndex: 0,
            argChooseType: 'baseArg',
            currentPage: 0,
            totalCount: 0

        }
    },

    /**
     * 初始化加载页面会触发
     */
    mounted() {
        console.log('初始化加载页面');
        var token = getToken();
        if (token != null && token != '') {
            this.isLogin = true;
            this.selectArgRecord(token, 6);
            this.getUserInfo(token);
            this.getZkList();
            this.getAllUser();
        } else {
            this.isLogin = false;
            this.getZkList();
        }
    },


    methods: {
        getZkList: function () {
            var _this = this;
            axios.get(getZkHost(), {}).then(function (response) {
                console.log(response.data);
                var objArr = response.data.data;
                var i = 0;
                for (i = 0; i < objArr.length; i++) {
                    _this.commonzk.push(objArr[i]);
                }
            });
        },

        getAllUser: function () {
            var _this = this;
            axios.get(getSelectAllUser(), {}).then(function (response) {
                console.log(response.data.data);
                var objArr = response.data.data;
                var i = 0;
                for (i = 0; i < objArr.length; i++) {
                    _this.userAccountList.push(objArr[i]);
                }
            });
            console.log('转移请求参数信息');
        },

        saveArg: function () {
            var arg = this.$data;
            arg.dubboService = null;
            arg.allSelectNav = null;
            arg.reqArgRecordList = null;

            axios.post(getSaveArgRecordUrl(), {
                "userToken": getToken(),
                "argJson": JSON.stringify(arg)
            }).then(function (response) {
                // _this.reqArgRecordList=response;
                var objArr = response.data.data;
                console.log(objArr);
                var i = 0;
                for (i = 0; i < objArr.length; i++) {
                    _this.reqArgRecordList.push(objArr[i]);
                }
            });

            this.$message({
                message: "保存成功",
                type: 'success'
            });
            this.selectArgRecord(getToken(), 6);

        },

        getCurArgDetail: function (item, idx) {
            var data = JSON.parse(item.argJson);
            Object.assign(this.$data, data);
            // this.$data.reqArg.requestTimes=10;
            this.$data.reqArg.registerType = 'zookeeper';
            var _this = this;
            this.selectArgRecord(getToken(), 6);
            // this.$data=data;
        },

        chooseTestCase: function () {
            var _this = this;
            axios.post(getChangeTestCaseUrl(), {
                "reqArgId": _this.currentReqArgIndex,
                "username": _this.copyAccountName
            }).then(function (response) {
                console.log(response.data);
                _this.$message({
                    message: "转移参数成功",
                    type: 'success'
                });
                _this.dialogVisible = false;
            })
        },

        copyTestCaseToOther: function (item) {
            this.dialogVisible = true;
            this.currentReqArgIndex = item.id;
        },

        getUserInfo: function (userToken) {
            var _this = this;
            axios.post(getUserInfoUrl(), {
                "token": userToken
            }).then(function (response) {
                console.log(response.data);
                document.getElementById("user-name-label").innerHTML = '当前账号名：' + response.data.data.username;
            })
        },

        selectArgRecord: function (userToken, pageSize) {
            var _this = this;
            axios.post(getSelectArgRecordUrl(), {
                "userToken": userToken,
                "page": _this.currentPage,
                "pageSize": pageSize
            }).then(function (response) {
                // _this.reqArgRecordList=response;
                _this.reqArgRecordList = [];
                var objArr = response.data.data.resultList;
                console.log(objArr);
                var i = 0;
                for (i = 0; i < objArr.length; i++) {
                    _this.reqArgRecordList.push(objArr[i]);
                    console.log(_this.reqArgRecordList[i] + 'asd');
                }
                _this.totalCount = response.data.data.totalCount;
            });
        },

        /**
         * 分页操作
         * @param val
         */
        handleCurrentPage: function (val) {
            this.currentPage = val;
            this.selectArgRecord(getToken(), 6);
        },

        /**
         * 添加参数
         */
        addArg: function () {
            if (this.editArg.argType == '') {
                this.$message({
                    message: "请选择参数类型",
                    type: 'warning'
                });
                return;
            }
            if (this.editArg.argValue == '') {
                this.$message({
                    message: "请输入参数",
                    type: 'warning'
                });
                return;
            }
            var argValJson = this.editArg.argValue;
            try {
                if (this.editArg.argType == 'java.util.List') {
                    argValJson = JSON.parse(this.editArg.argValue);
                } else if (this.editArg.argType == 'java.util.Map') {
                    argValJson = JSON.parse(this.editArg.argValue);
                }
            } catch (e) {
                this.$message({
                    message: "参数格式异常！",
                    type: 'warning'
                });
                return;
            }
            this.tableData.push(this.editArg);
            this.reqArg.argTypes.push(this.editArg.argType);
            this.reqArg.argObjects.push(argValJson);
            this.editArg = {
                argType: '',
                argValue: ''
            }

        },

        /**
         * 请求dubbo接口
         */
        reqDubbo: function () {
            var that = this;
            if (this.reqArg.loadbalance != null  && this.reqArg.url != '' ) {
                this.$message({
                    message: "直连和负载均衡设置出现冲突",
                    type: 'warning'
                });
                return;
            }
            if ((this.reqArg.serviceAddr == '' || this.reqArg.serviceAddr == null) && (this.reqArg.url == null || this.reqArg.url == '')) {
                this.$message({
                    message: "注册中心地址未填写",
                    type: 'warning'
                });
                return;
            }
            this.loadingStart();
            axios.post(getReqDubboUrl(), this.reqArg)
                .then(function (response) {
                    that.loadingEnd();
                    console.log(response);
                    that.$message({
                        message: "请求成功",
                        type: 'success'
                    });
                    console.log(response.data);
                    var jsonStr = JSON.stringify(response.data.response);
                    var reqJsonStr = JSON.stringify(response.data.dubboInvokerParamDTO);
                    var statistics = JSON.stringify(response.data.response.requestStatistics);
                    use(["json-viewer", "json-viewer"], function () {
                        // var jsonStr = $("#rpcResponse").val();
                        $('#json-renderer').jsonViewer(JSON.parse(jsonStr));
                        $('#json-renderer-response').jsonViewer(JSON.parse(reqJsonStr));
                        $('#json-renderer-statistics').jsonViewer(JSON.parse(statistics));

                    });
                    console.log('进行初始化json');
                }, err => {
                    that.loadingEnd();

                    that.$message({
                        message: "请求异常",
                        type: 'warning'
                    });
                    var jsonStr = JSON.stringify(err.response.data);
                    var reqJsonStr = JSON.stringify(that.reqArg);
                    use(["json-viewer", "json-viewer"], function () {
                        // var jsonStr = $("#rpcResponse").val();
                        $('#json-renderer').jsonViewer(JSON.parse(jsonStr));
                        $('#json-renderer-response').jsonViewer(JSON.parse(reqJsonStr));
                        $('#json-renderer-statistics').jsonViewer('');

                    });
                    console.log('请求dubbo出现异常！');
                });
        },

        /**
         * 拉取zk地址
         */
        reqServiceUrl: function () {
            if (this.reqArg.serviceAddr == null || this.reqArg.serviceAddr == '') {
                this.$message({
                    message: "服务地址不能为空！",
                    type: 'warning'
                });
                return;
            }
            console.log('serviceUrl is :' + this.reqArg.registerType);
            this.loadingStart();
            var serviceUrl;
            if (this.reqArg.registerType == 'zookeeper') {
                serviceUrl = getServiceAddr() + this.reqArg.serviceAddr;
            } else {
                serviceUrl = getServiceAddr() + this.reqArg.serviceAddr + "&&registerType=" + this.reqArg.registerType + "&&namespace=public";
            }
            var addressList;
            var that = this;
            var ajaxTimeOut = $.ajax({
                type: "GET", //请求方式
                async: true, // fasle表示同步请求，true表示异步请求
                contentType: "application/json;charset=UTF-8", //请求的媒体类型
                url: serviceUrl,//请求地址
                success: function (result) { //请求成功
                    if (result.code == 500) {
                        that.$message({
                            message: "服务拉取异常,请检查地址信息！",
                            type: 'warning'
                        });
                        that.loadingEnd();
                        return;
                    }
                    addressList = result.data;
                    that.dubboService = addressList;
                    that.loadingEnd();

                    that.$message({
                        message: "服务地址拉取成功！",
                        type: 'success'
                    });
                },
                error: function (e) {  //请求失败，包含具体的错误信息
                    console.log(e.status);
                    console.log(e.responseText);
                    that.loadingEnd();
                },
                complete: function (XMLHttpRequest, status) { //当请求完成时调用函数
                    if (status == 'error') {//status == 'timeout'意为超时,status的可能取值：success,notmodified,nocontent,error,timeout,abort,parsererror
                        // ajaxTimeOut.abort(); //取消请求
                        that.$message({
                            message: "服务拉取异常,请检查地址信息！",
                            type: 'warning'
                        });
                        return;
                    }
                    that.loadingEnd();

                }
            });

        }
        ,

        loadingStart: function () {
            console.log('loadingStart');
            $("#loadingLabel").show();
        }
        ,

        loadingEnd: function () {
            $("#loadingLabel").hide();
            console.log('loadingEnd');
        }
        ,

        /**
         * 注册中心的模糊搜索
         */
        zkAddrFilter(val) {
            //判断是否为空
            if (val) {
                //同时筛选Lable与value的值
                this.allSelectCommonzk = this.commonzk.filter((itemContent) => {
                    var item = itemContent.host;
                    if (!!~item.indexOf(val) || !!~item.toUpperCase().indexOf(val.toUpperCase()) || !!~item.indexOf(val) || !!~item.toUpperCase().indexOf(val.toUpperCase())) {
                        return true;
                    }
                });
                this.reqArg.zkHost = val;
            } else {
                //赋值还原
                this.allSelectCommonzk = this.commonzk;
            }
        },

        /**
         * 转让测试用例人名的模糊搜索
         */
        usernameFilter(val) {
            //判断是否为空
            if (val) {
                //同时筛选Lable与value的值
                this.allSelectAccountList = this.userAccountList.filter((itemContent) => {
                    var item = itemContent;
                    if (!!~item.indexOf(val) || !!~item.toUpperCase().indexOf(val.toUpperCase()) || !!~item.indexOf(val) || !!~item.toUpperCase().indexOf(val.toUpperCase())) {
                        return true;
                    }
                });
                this.copyAccountName = val;
            } else {
                //赋值还原
                this.allSelectAccountList = this.userAccountList;
            }
        },

        /**
         * dubbo服务的模糊搜索
         */
        selectFilter(val) {
            //判断是否为空
            if (val) {
                //同时筛选Lable与value的值

                this.allSelectNav = this.dubboService.filter((item) => {
                    if (!!~item.indexOf(val) || !!~item.toUpperCase().indexOf(val.toUpperCase()) || !!~item.indexOf(val) || !!~item.toUpperCase().indexOf(val.toUpperCase())) {
                        return true;
                    }
                });
                this.reqArg.interfaceName = val;
                console.log(val);
            } else {
                //赋值还原
                this.allSelectNav = this.dubboService;
            }
        }
        ,

        /**
         * 展示自定义参数的内容
         */
        showSelfArgDialog: function () {
            this.selfEditdialogFormVisible = true;
        }
        ,


        /**
         * 确定自定义参数的内容
         */
        sureSelfArg: function () {
            if (this.selfDefArg.argType == '') {
                this.$message({
                    message: "对象的全称输入不能为空",
                    type: 'warning'
                });
                return;
            }
            if (this.selfDefArg.argValue == '') {
                this.$message({
                    message: "json格式的参数不能为空",
                    type: 'warning'
                });
                return;
            }
            this.tableData.push(this.selfDefArg);
            this.reqArg.argTypes.push(this.selfDefArg.argType);
            var jsonParam = this.selfDefArg.argValue;
            try {
                jsonParam = JSON.parse(jsonParam);
            } catch (e) {
                console.log('当前参数不属于json格式')
            }
            this.reqArg.argObjects.push(jsonParam);
            this.selfDefArg = {
                argType: '',
                argValue: ''
            };
            this.selfEditdialogFormVisible = false;
        }
        ,

        /**
         * 确定自定义参数的内容
         */
        sureEditArg: function () {
            if (this.selfDefArg.argType == '') {
                this.$message({
                    message: "对象的全称输入不能为空",
                    type: 'warning'
                });
                return;
            }
            if (this.selfDefArg.argValue == '') {
                this.$message({
                    message: "json格式的参数不能为空",
                    type: 'warning'
                });
                return;
            }
            console.log(this.editIndex + ' this');

            var arrParam = this.selfDefArg.argValue;
            //兼容字符串问题
            if (this.selfDefArg.argType == 'java.util.List') {
                arrParam = "[" + arrParam + "]";
            }
            Vue.set(this.reqArg.argTypes, this.editIndex, this.selfDefArg.argType);
            try {
                Vue.set(this.reqArg.argObjects, this.editIndex, JSON.parse(arrParam));

            } catch (e) {
                Vue.set(this.reqArg.argObjects, this.editIndex, this.selfDefArg.argValue);
                console.log('exception' + e);
                // this.$message({
                //     message: "参数为非json格式!",
                //     type: 'warning'
                // });
            }
            console.log('here');
            this.selfDefArg.argValue = arrParam;
            Vue.set(this.tableData, this.editIndex, this.selfDefArg);

            this.selfDefArg = {
                argType: '',
                argValue: ''
            };
            this.editDialogFormVisible = false;
        }
        ,

        /**
         * 转换为json格式参数
         */
        tansferToJson: function () {
            var jsonParam = this.selfDefArg.argValue;
            if (jsonParam == "") {
                this.$message({
                    message: "json格式的参数不能为空",
                    type: 'warning'
                });
                return;
            } else {
                // 多次点击，会有重复判断
                jsonParam = jsonParam.trim();
                var res = "";
                for (var i = 0, j = 0, k = 0, ii, ele; i < jsonParam.length; i++) {//k:缩进，j:""个数
                    ele = jsonParam.charAt(i);
                    if (j % 2 == 0 && ele == "}") {
                        k--;
                        for (ii = 0; ii < k; ii++) ele = "    " + ele;
                        ele = "\n" + ele;
                    } else if (j % 2 == 0 && ele == "{") {
                        ele += "\n";
                        k++;
                        debugger;
                        for (ii = 0; ii < k; ii++) ele += "    ";
                    } else if (j % 2 == 0 && ele == ",") {
                        ele += "\n";
                        for (ii = 0; ii < k; ii++) ele += "    ";
                    } else if (ele == "\"") j++;
                    res += ele;
                }
                this.selfDefArg.argValue = res;
            }
        }
        ,


        /**
         * 编辑参数
         * @param item
         */
        editSelfArg: function (item, idx) {
            console.log(item);
            var argValJson = item.argValue;

            try {
                if (item.argType == 'java.util.List') {
                    argValJson = JSON.parse(item.argValue);
                }
            } catch (e) {
                console.log('参数转换出现了异常：' + e);
            }
            this.selfDefArg = {
                argType: item.argType,
                argValue: argValJson
            };
            this.editDialogFormVisible = true;
            this.editIndex = idx;
        }
        ,

        /**
         * 删除参数
         * @param idx
         */
        delSelfArg: function (idx) {
            this.$confirm('确认删除？')
                .then(_ => {
                    this.tableData.splice(idx, 1);
                    this.reqArg.argTypes.splice(idx, 1);
                    this.reqArg.argObjects.splice(idx, 1);
                })
                .catch(_ => {
                });
        }

    }

});


function useCurArg(val) {
    console.log(val);
}

function clearCache() {
    console.log('CLEAR CACHE');
    localStorage.removeItem('iubbox-token');
    location.reload();
}