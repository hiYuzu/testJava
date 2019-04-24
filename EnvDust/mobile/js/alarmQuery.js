/**
 * @author hiYuzu
 * @description
 * @version V1.0 报警查询页面
 * @date 2019/4/17 16:11
 */
var updateType;

function drawAlarmDetailsTable(deviceCode, thingCode, updateType, beginTime, endTime) {//绘制报警详情表
    var url = "../../MonitorStorageController/preGetOriginalData";
    var column = [];    //表单列内容
    var rtdColumn = [];    //实时数据列内容
    rtdColumn.push({
        field: 'num',
        title: '序号',
        align: "center",
        width: 50,
        formatter: function (value, row, index) {
            return index + 1;
        }
    }, {
        field: 'rtdTime',
        title: '发布时间',
        align: 'center'
    }, {
        field: 'thingRtd',
        title: '监测值',
        align: 'center'
    }, {
        field: 'thingUnit',
        title: '物质单位',
        align: 'center'
    });
    var otherColumn = [];    //其他数据类型列内容
    otherColumn.push({
        field: 'num',
        title: '序号',
        align: "center",
        width: 50,
        formatter: function (value, row, index) {
            return index + 1;
        }
    }, {
        field: 'beginTime',
        title: '发布时间',
        align: 'center'
    }, {
        field: 'thingAvg',
        title: '监测值',
        align: 'center'
    }, {
        field: 'thingUnit',
        title: '物质单位',
        align: 'center'
    });
    if(updateType == 2011) {
        column = rtdColumn;
    } else {
        column = otherColumn;
    }
    //初始化monitorTable
    $("#alarmDetailsTable").bootstrapTable("destroy");
    $("#alarmDetailsTable").bootstrapTable({
        url: url,
        method: "post",
        striped: true,        //是否显示行间隔色
        showColumns: false,      //列显示控制
        pagination: true,     //是否显示分页（*）
        sidePagination: "server",     //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                //初始化加载第一页，默认第一页
        pageSize: 10,                 //每页的记录行数（*）
        // pageList: [5, 10, 15, 20],  //可供选择的每页的行数（*）
        columns: column,
        paramsType: "",
        queryParams: function (params) {
            return {
                rows: params.limit,
                page: (params.offset / params.limit) + 1,
                deviceCode: deviceCode,
                updateType: updateType,
                select: deviceCode,
                thingCode: thingCode,
                beginTime: beginTime,
                endTime: endTime
            };
        },
        formatNoMatches: function () {
            return "无数据";
        }
    });
    //隐藏正在加载
    $("#alarmDetailsTable").bootstrapTable("hideLoading");
}

$(function () {
    var currentDate = Date.parse(new Date());
    //初始化为查询一天
    $("#timePicker1").val(longToDay(currentDate));
    $("#timePicker2").val(longToDay(currentDate));
    btnClick();
});

function btnClick() {//    点击查询
    $("#myModal").modal('hide');
    //获取时间值,精确到日yyyy-mm-dd
    var beginDate = $("#timePicker1").val();
    var endDate = $("#timePicker2").val();
    //转成long,开始时间为当天0点
    beginDate = new Date(beginDate).getTime();
    //结束时间为第二天0点
    endDate = new Date(endDate).getTime()  - 1000;
    var currentDate = Date.parse(new Date());
    if (beginDate > currentDate) {
        alert("开始时间不能晚于当前时间!");
        return false;
    }
    var beginTime = longToSec(beginDate);
    var endTime = longToSec(endDate);
    if (endTime > beginTime) {
        checkType();
        var deviceCode = $("#device").val();
        var thingCode = $("#thingName").val();
        drawAlarmDetailsTable(deviceCode, thingCode, updateType, beginTime, endTime);
    } else {
        alert("结束时间必须晚于开始时间");
    }
}

$('#manufactor,#device').selectpicker({
    'width': '100%'
});

function loadManufactor() {
    $.ajax({
        type: 'post',
        async: false,
        url: '../../AreaController/queryBottomAreas',
        dataType: "json",
        success: function (data) {
            $("#manufactor").empty();
            for (var i = 0; i < data.rows.length; i++) {
                if (i == 0) {
                    $("#manufactor").append("<option value='" + data.rows[i].areaId + "' selected>" + data.rows[i].areaName + "</option>");
                    $("#pointName").html(data.rows[i].areaName);
                } else {
                    $("#manufactor").append("<option value='" + data.rows[i].areaId + "'>" + data.rows[i].areaName + "</option>");
                }
            }
            $("#manufactor").selectpicker("refresh");
            loadDevice(data.rows[0].areaId);
        }
    });
}

function loadDevice(areaId) {
    $.ajax({
        type: 'post',
        url: '../../DeviceController/queryAreaAuthDevice',
        data: {areaId: areaId},
        dataType: "json",
        success: function (data) {
            $("#device").empty();
            for (var i = 0; i < data.rows.length; i++) {
                if (i == 0) {
                    $("#device").append("<option value='" + data.rows[i].deviceCode + "' selected>" + data.rows[i].deviceName + "</option>");
                    $("#deviceName").html(data.rows[i].deviceName);
                } else {
                    $("#device").append("<option value='" + data.rows[i].deviceCode + "'>" + data.rows[i].deviceName + "</option>");
                }
            }
            $("#device").selectpicker("refresh");
            btnClick();
        }
    });
}

$("#manufactor").change(function () {
    loadDevice($("#manufactor").val());
    $("#pointName").html("");
    $("#pointName").html($("#manufactor").find("option:selected").text());
});
$("#device").change(function () {
    $("#deviceName").html("");
    $("#deviceName").html($("#device").find("option:selected").text());
});
$("#thingName").change(function () {
    $("#currThingName").html("");
    $("#currThingName").html("污染物：" + $("#thingName").find("option:selected").text());
});
$("#updateType").change(function () {
    $("#currUpdateType").html("");
    $("#currUpdateType").html("数据类型：" + $("#updateType").val());
});

function drawThingSelect() {//获取绘制报警物质（指标）
    var url = "../../MonitorStorageController/getAthorityMonitors";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        success: function (json) {
            $("#thingName").empty();
            var num = json.length;
            for (var t = 0; t < num; t++) {
                if (t == 0) {
                    $("#thingName").append("<option value='" + json[t].code + "' selected>" + json[t].name + "</option>");
                    $("#currThingName").html("污染物:" + json[t].name);
                } else {
                    $("#thingName").append("<option value='" + json[t].code + "'>" + json[t].name + "</option>");
                }
            }
            $("#thingName").selectpicker("refresh");
        }
    });
}

function checkType() {
    //获取下拉框中的内容
    updateType = $("#updateType").val();
    if (updateType == "实时数据") {
        updateType = 2011;
    } else if (updateType == "分钟数据") {
        updateType = 2051;
    } else if (updateType == "小时数据") {
        updateType = 2061;
    } else if (updateType == "每日数据") {
        updateType = 2031;
    }
}

function longToDay(longDate) {//   long型转换为日期,格式:"yyyy-mm-dd"
    var d = new Date();
    d.setTime(longDate);
    var year = d.getFullYear();
    var month = (d.getMonth() + 1);
    if (d.getMonth() + 1 < 10) {
        month = "0" + month;
    }
    var day = d.getDate();
    if (d.getDate() < 10) {
        day = "0" + day;
    }
    return year + "-" + month + "-" + day;
}

function longToSec(longDate) {//   long型转换为日期,格式:"yyyy-mm-dd"
    var d = new Date();
    d.setTime(longDate);
    var year = d.getFullYear();
    var month = (d.getMonth() + 1);
    if (d.getMonth() + 1 < 10) {
        month = "0" + month;
    }
    var day = d.getDate();
    if (d.getDate() < 10) {
        day = "0" + day;
    }
    var hour = d.getUTCHours();
    if (d.getUTCHours() < 10) {
        hour = "0" + hour;
    }
    var minute = d.getUTCMinutes();
    if (d.getUTCMinutes() < 10) {
        minute = "0" + minute;
    }
    var second = d.getUTCSeconds();
    if (d.getUTCSeconds() < 10) {
        second = "0" + second;
    }
    return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
}