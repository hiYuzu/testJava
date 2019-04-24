var myChart1 = null;
function mainlogOutFunc(){
    $.ajax({
        url : "./../../../UserController/toLogout",
        type : "post",
        dataType : "json",
        error : function(json) {
            $.messager.confirm("提示", '退出请求未响应，是否强制退出？', function(r) {
                if (r) {
                    window.location.href = "../index.html";
                }
            });
        },
        success : function(json) {
            if (json.result) {
                window.location.href = "../index.html";
            } else {
                $.messager.alert("错误", json.detail, "error");
            }
        }
    });
}
function initLayout() {
    var h = document.documentElement.clientHeight - 90;
    $('#mapDiv').height(h);
}
