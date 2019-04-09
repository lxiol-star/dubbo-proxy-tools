/**
 * 获取service的信息
 */
function listService() {
    var ip = $("#zk_server").val();
    if (ip == null || ip == '') {
        alert('ip地址不能为空！');
        return;
    }
    var param = {
        "address": ip
    };

    $.ajax({
        url: "/zk/add/zkServer",
        type: 'get',
        timeout : 3000,
        data: param,
        async: true,
        dataType: 'json',
        success: function (msg) {
            var result = msg;
            var dataList = result.data;
            // $("#rpc_service_list").children().filter("option").remove();
            $("#rpc_service_list").empty();
            for (var i = 0; i < dataList.length; i++) {
                $("#rpc_service_list").append("<option value='" + dataList[i] + "'>" + dataList[i] + "</option>");
            }
        },
        error: function () {
            alert('服务器未知异常！');
        }
    });
}

/**
 * 服务详情列表展示
 */
function showMethodList() {
    var ip = $("#zk_server").val();
    if (ip == null || ip == '') {
        alert('ip地址不能为空！');
        return;
    }
    var serviceName = $("#rpc_service_list").val();
    var param = {
        "serviceName": serviceName
    };
    var result = Idea.getAjax("/zk/getServiceDetail", param);

    var dataList = result.data;
    if (dataList.length == 0) {
        $("#server_detail_list").children().filter("div").remove();
        return;
    }
    $("#server_detail_list").children().filter("div").remove();
    for (var i = 0; i < dataList.length; i++) {
        $("#server_detail_list").append(" <div class=\"radio_item\">\n" +
            "                <div style=\"display: inline-block;\">\n" +
            "                    <input type=\"radio\" value=" + dataList[i].id + " onclick=paramShow(" + dataList[i].id + ") class=\"radio\" name=\"server_function\">\n" +
            "                </div>\n" +
            "                <div class=\"function_label\">\n" +
            "               " + dataList[i].des +
            "                </div>\n" +
            "            </div>");
    }
}

/**
 * 参数展示
 */
function paramShow(id) {
    var serviceName = $("#rpc_service_list").val();
    var param = {
        "serviceName": serviceName,
        "id": id
    };
    var result = Idea.getAjax("/dubbo/getParameters", param);
    $("#param_show").val('');
    $("#param_show").val(result.data);
    console.log(result);
}


/**
 * 发送rpc请求
 */
function send() {
    var paramJson = $("#param_show").val();
    var id = $("input[name='server_function']:checked").val();
    if (id == null || id == '') {
        alert('请先选择服务！');
        return;
    }
    var param = {
        "id": id,
        "address": $("#zk_server").val(),
        "serviceName": $("#rpc_service_list").val(),
        "paramJson": $("#param_show").val()
    };
    console.log(param);
    var result = Idea.getAjax("/dubbo/sendReq", param);
    $("#rpc_result").val(result.data);
}


/**
 * 获取get类型的ajax请求的封装工具类
 *
 * @param url
 * @param data
 * @returns {*}
 */
var Idea = window.Idea || {

    "getAjax": function (url, data) {
        var result;
        $.ajax({
            url: url,
            type: 'get',
            timeout : 3000,
            data: data,
            async: false,
            dataType: 'json',
            success: function (msg) {
                result = msg;
            },
            error: function () {
                alert('服务器未知异常！');
            }
        });
        return result;
    }
};
