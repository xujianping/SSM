/**
 * Created by Xujp on 2018/1/15.
 */

//替换sex数据为文字
function genderSex(value, row, index) {
    if (value == null || value == undefined) {
        return "-";
    } else if (value == 1) {
        return "男";
    } else if (value == 0) {
        return "女";
    }
}

//替换delete数据为文字
function genderDel(value, row, index) {
    if (value == null || value == undefined) {
        return "-";
    } else if (value == 1) {
        return "已删除";
    } else if (value == 0) {
        return "正常";
    }
}
//格式化时间
function formatDateTime(inputTime) {
    var date = new Date(inputTime);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    h = h < 10 ? ('0' + h) : h;
    var minute = date.getMinutes();
    var second = date.getSeconds();
    minute = minute < 10 ? ('0' + minute) : minute;
    second = second < 10 ? ('0' + second) : second;
    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
};
