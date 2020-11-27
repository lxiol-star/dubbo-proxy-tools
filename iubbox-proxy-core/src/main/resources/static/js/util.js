/**
 * 转换为json格式参数
 * @param json
 * @returns {*}
 */
function tansferToJson(json) {
    var text_value = json;
    if (text_value == "") {
        alert("不能为空");
        return false;
    } else {
        var res = "";
        for (var i = 0, j = 0, k = 0, ii, ele; i < text_value.length; i++) {//k:缩进，j:""个数
            ele = text_value.charAt(i);
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
        return res;
    }
}

/**
 * 创建一个map进行存储
 */
var reqHistory = [];

function getReqHistory() {
    if (localStorage.getItem('argArr') != null) {
        reqHistory = JSON.parse(localStorage.getItem('argArr'));
    }
    return reqHistory;
}

function getToken() {
    return localStorage.getItem('iubbox-token');
}


function getPageList(page) {
    var pageSize = 5;
    var totalArr = [];
    totalArr = getReqHistory();
    console.log('this is util'+totalArr.length);
    var pageResult = [];
    for (var i = 0; i < pageSize; i++) {
        var j=(pageSize*(page-1))+i;
        if(j>=totalArr.length){
            break;
        }
        pageResult[i] = totalArr[j];
    }
    console.log(pageResult);
    return pageResult;
}