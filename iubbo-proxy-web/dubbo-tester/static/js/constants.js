let server_addr="http://localhost:7089/";

let reqDubbo="dubboInvoker/index";
let reqZkAddr="dubboInvoker/getServiceNameList?zkHost=";
let selectArgRecord="dubbo-invoke-req-record/select-in-page";
let saveArg="dubbo-invoke-req-record/save-one";
let userLogin="dubboInvoker/login";


function getZkAddr(){
    return server_addr+reqZkAddr;
}

function getReqDubboUrl() {
    return server_addr + reqDubbo;
}

function getSelectArgRecordUrl(){
    return server_addr+selectArgRecord;
}

function getSaveArgRecordUrl() {
    return server_addr+saveArg;
}

function getUserLoginUrl(){
    return server_addr+userLogin;
}