/**
 * Created by Xujp on 2018/1/15.
 */

var $table = $('#orders');

//bootstrapTable使用中文
$.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales['zh-CN']);

//防止表头与表格不对齐
$(window).resize(function () {
    $table.bootstrapTable('resetView');
});

$(function () {
    //使用严格模式
    "use strict";

    tableInit();
    $('#Ordess').bootstrapTable('hideLoading');
    //清空表单数据
    clearForm();
})

//初始化Table
function tableInit() {
    //先销毁表格
    $table.bootstrapTable('destroy');

    $table.bootstrapTable({
        //请求地址
        url: '../order/showOrder',
        //请求方式
        method: 'post',
        //请求内容类型
        contentType: "application/x-www-form-urlencoded",
        //数据类型
        dataType: "json",
        //table高度，
        //如果没有设置height属性，
        //表格自动根据记录条数觉得表格高度
        height: '500',
        //是否显示行间隔色
        striped: true,
        //是否启用排序
        sortable: true,
        //排序方式
        sortOrder: "asc",
        //是否使用缓存
        cache: false,
        //每行的唯一标识
        uniqueId: "id",
        //指定工具栏
        toolbar: "#toolbar",
        //显示隐藏列
        showColumns: true,
        //显示刷新按钮
        showRefresh: false,
        //切换显示样式
        showToggle: false,
        //显示Table脚部
        showFooter: false,
        //是否显示详细视图
        cardView: false,
        //是否显示父子表
        detailView: true,
        //detail格式化
        detailFormatter: genderDetail,
        //是否显示分页
        pagination: true,
        //是否显示分页按钮
        showPaginationSwitch: false,
        //是否启用点击选中行
        clickToSelect: false,
        //最少要显示的列数
        minimumCountColumns: 2,
        //cell没有值时显示
        undefinedText: '-',
        //分页方式：client客户端分页，server服务端分页
        sidePagination: "server",
        //每页的记录行数
        pageSize: 10,
        //初始化加载第1页，默认第1页
        pageNumber: 1,
        //可供选择的每页的行数
        pageList: "[10, 25, 50, 80, 100]",
        paginationFirstText: "首页",
        paginationPreText: "上一页",
        paginationNextText: "下一页",
        paginationLastText: "末页",
        buttonsClass: 'default',
        iconsPrefix: 'glyphicon',
        queryParams: queryParams,
        icons: {
            paginationSwitchDown: 'glyphicon-collapse-down icon-chevron-down',
            paginationSwitchUp: 'glyphicon-collapse-up icon-chevron-up',
            refresh: 'glyphicon-refresh icon-refresh',
            toggle: 'glyphicon-list-alt icon-list-alt',
            columns: 'glyphicon-th icon-th',
            detailOpen: 'glyphicon-plus icon-plus',
            detailClose: 'glyphicon-minus icon-minus'
        }, columns: [{
            field: 'state',
            checkbox: true,
            align: 'center',
            valign: 'middle'
        }, {
            title: '序号',
            field: 'index',
            align: 'center',
            valign: 'middle',
            formatter: genderIndex
        }, {
            title: '编号',
            field: 'id',
            align: 'center',
            valign: 'middle'
        }, {
            title: '单号',
            field: 'orderno',
            align: 'center',
            valign: 'middle'
        }, {
            title: '订单名',
            field: 'customer',
            align: 'center',
            valign: 'middle'
        }, {
            title: '订单手机',
            field: 'phoneno',
            align: 'center',
            valign: 'middle'
        }, {
            title: '订单地址',
            field: 'address',
            align: 'center',
            valign: 'middle'
        }, {
            title: '下单时间',
            field: 'ordertime',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                if (value == null) {
                    return "";
                }else {
                    var offlineTimeStr = formatDateTime(value);
                    return offlineTimeStr;
                }

            }
        }, {
            title: '接单时间',
            field: 'createtime',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                if (value == null) {
                    return "";
                }else {
                    var offlineTimeStr = formatDateTime(value);
                    return offlineTimeStr;
                }

            }
        }, {
            title: '订单价格',
            field: 'payable',
            align: 'center',
            valign: 'middle'
        }, {
            title: '状态',
            field: 'orderstate',
            align: 'center',
            valign: 'middle'
        }, {
            title: '操作',
            field: 'operate',
            align: 'center',
            events: operateEvents,
            formatter: genderOpt
        }],
        responseHandler: function (res) {
            if (res.status == 0) {
                var obj = {
                    "total": res.total,
                    "rows": res.records,
                };
            } else {
                var obj = {
                    "total": 0,
                    "rows": [],
                }
            }
            return obj;
        }, onLoadSuccess: function () {
            //加载成功时执行
            console.log("加载成功!");
        }, onLoadError: function () {
            //加载失败时执行
            layer.msg("加载失败!", {icon: 2, time: 2000});
        }, formatLoadingMessage: function () {
            //正在加载
            return "请稍等，正在加载中...";
        }, formatNoMatches: function () {
            //没有匹配的结果
            return '无符合条件的记录';
        }
    })
}

//生成详细视图
function genderDetail(index, row) {
    var html = [];
    $.each(row, function (key, value) {
        html.push('<p><b>' + key + ':</b> ' + value + '</p>');
    });
    return html.join('');
}

//生成序号
function genderIndex(value, row, index) {
    return index + 1;
}

//自定义列内容
function genderOpt() {
    return [
        '<a id="edit" href="javascript:void(0)" title="编辑">',
        '<i class="glyphicon glyphicon-pencil"></i>',
        '</a>  ',
        '<a id="remove" href="javascript:void(0)" title="删除">',
        '<i class="glyphicon glyphicon-trash"></i>',
        '</a>'
    ].join('');
}

//自定义列内容事件
window.operateEvents = {
    'click #edit': function (e, value, row, index) {
        editData(row);
    },
    'click #remove': function (e, value, row, index) {
        delData(row.id, "one");
    }
};

//查询条件与分页数据
function queryParams(params) {
    //排序方式
    params.order = "address desc";
    //第几页
    params.nowPage = this.pageNumber;
    //phoneno
    params.phoneno = $("#phoneno").val();
    //address
    params.address = $('#address').val();
    return params;
}

//清空表单内容
function clearForm() {
    $('#txt_type').val("");

    //向模态框中传值
    $('#txt_id').val("");
    $('#txt_orderno').val("");
    $('#txt_customer').val("");
    $('#txt_address').val("");
    $('#txt_phoneno').val("");
    $('#txt_payable').val("");
    $('#txt_orderstate').val("");

}
//刷新页面
function refresh() {
    $table.bootstrapTable('refresh');
}

//查询按钮点击事件
$("#btn_search").on("click", function () {
    refresh();
});

//清空条件按钮点击事件
$("#btn_clean_search").on("click", function () {
    $('#phoneno').val("");
    $('#address').val("");
    refresh();
});

//新增按钮点击事件
$("#btn_add").on("click", function () {
    //清空表单数据
    clearForm();
    $('#addAndUpdateLabel').text("新增订单信息");
    $('#txt_type').val("add");
    $('#addAndUpdate').modal({
        //点击ESC键,模态窗口即会退出。
        keyboard: true
    });
});

//删除按钮点击事件
$("#btn_delete").on("click", function () {
    var datas = $table.bootstrapTable('getSelections');
    if (datas.length < 1) {
        layer.alert('请选择一条或多条数据进行删除！', {icon: 2});
    } else {
        var id = [];
        for (var i = 0; i < datas.length; i++) {
            id.push(datas[i].id);
        }
        delData(id, "batch");
    }
});

//刷新按钮点击事件
$("#btn_refresh").on("click", function () {
    refresh();
});

//清空按钮点击事件
$("#btn_clean").on("click", function () {
    var id = [];
    $.ajax({
        url: '../order/deleteOrde/clean',
        method: 'post',
        contentType: "application/x-www-form-urlencoded",
        data: {id: id},
        success: function (msg) {
            if (msg.success) {
                layer.msg(msg.msg, {icon: 1, time: 1500});
            } else {
                layer.msg(msg.msg, {icon: 2, time: 1500});
            }
            refresh();
        }
    })
});

//初始化按钮点击事件
$("#btn_init").on("click", function () {
    $.ajax({
        url: '../order/initTable/' + 1000,
        method: 'post',
        contentType: "application/x-www-form-urlencoded",
        //阻止深度序列化，向后台传送数组
        traditional: true,
        success: function (msg) {
            if (msg.success) {
                layer.msg(msg.msg, {icon: 1, time: 1500});
            } else {
                layer.msg(msg.msg, {icon: 2, time: 1500});
            }
            refresh();
        }
    })
});

//切换视图按钮点击事件
$("#btn_toggleview").on("click", function () {
    $table.bootstrapTable('toggleView');
});

//显隐分页按钮点击事件
$("#btn_togglepage").on("click", function () {
    $table.bootstrapTable('togglePagination');
});

//跳转按钮点击事件
$("#btn_selectpage").on("click", function () {
    var pageNum = 3;
    $table.bootstrapTable('selectPage', pageNum);
});

//清除弹窗原数据
$("#addAndUpdate").on("hidden.bs.modal", function () {
    $('#txt_name').val("");
    document.getElementById('p_man').checked = true;
    $('#txt_pwd').val("");
    $('#txt_email').val("");
    $('#txt_phone').val("");
});

//弹框保存按钮点击事件
$("#btn_add_update_submit").off().on('click', function () {
    var type = $('#txt_type').val(),
        id = $('#txt_id').val(),
        orderno = $('#txt_orderno').val(),
        customer = $('#txt_customer').val(),
        address = $('#txt_address').val(),
        phoneno = $('#txt_phoneno').val(),
        payable = $('#txt_payable').val(),
        orderstate = $('#txt_orderstate').val()


    //验证数据
    if (!orderno) {
        layer.msg('请填写单号!', {icon: 2, time: 1500});
        return false;
    }
    if (!customer) {
        layer.msg('请填写客户名称!', {icon: 2, time: 1500});
        return false;
    }
    if (!address) {
        layer.msg('请填写客户地址!', {icon: 2, time: 1500});
        return false;
    }
    if (!phoneno) {
        layer.msg('请填写手机!', {icon: 2, time: 1500});
        return false;
    }
    if (!payable) {
        layer.msg('请填写订单价格!', {icon: 2, time: 1500});
        return false;
    }
    if (!orderstate) {
        layer.msg('请填订单状态!', {icon: 2, time: 1500});
        return false;
    }
    $.ajax({
        url: '../order/addAndUpdateOrders/' + type,
        method: 'post',
        contentType: "application/x-www-form-urlencoded",
        data: {
            id: id,
            orderno: orderno,
            customer: customer,
            address: address,
            phoneno: phoneno,
            payable: payable,
            orderstate: orderstate
        },
        //阻止深度序列化，向后台传送数组
        traditional: true,
        success: function (msg) {
            if (msg.success) {
                layer.msg(msg.msg, {icon: 1, time: 1500});
            } else {
                layer.msg(msg.msg, {icon: 2, time: 1500});
            }
            refresh();
            clearForm();
        }
    })
});

//tr中编辑按钮点击事件
function editData(row) {
    $('#txt_type').val("update");

    //向模态框中传值
    $('#txt_id').val(row.id);
    $('#txt_orderno').val(row.orderno);
    $('#txt_customer').val(row.customer);
    $('#txt_address').val(row.address);
    $('#txt_phoneno').val(row.phoneno);
    $('#txt_payable').val(row.payable);
    $('#txt_orderstate').val(row.orderstate);

    $('#addAndUpdateLabel').text("修改订单信息");

    //显示模态窗口
    $('#addAndUpdate').modal({
        //点击ESC键,模态窗口即会退出。
        keyboard: true
    });
}

//tr中删除按钮点击事件
function delData(id, type) {
    layer.confirm('确定要删除订单编号为' + id + '数据?', {icon: 3, title: '提示'}, function () {
        $.ajax({
            url: '../order/deleteOrder/' + type,
            method: 'post',
            contentType: "application/x-www-form-urlencoded",
            //阻止深度序列化，向后台传送数组
            traditional: true,
            data: {id: id.toString()},
            success: function (msg) {
                if (msg.success) {
                    layer.msg(msg.msg, {icon: 1, time: 1500});
                } else {
                    layer.msg(msg.msg, {icon: 2, time: 1500});
                }
                refresh();
            }
        })
    });
}