<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--path和basePath必须建，以便模板页面的引入和资源访问正确--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>订单列表</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="/webjars/bootstrap/3.3.5/css/bootstrap.min.css">
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <%--<link rel="stylesheet" href="/webjars/bootstrap/3.3.5/css/bootstrap-theme.min.css">--%>
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="/webjars/jquery/1.11.1/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="/webjars/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <!-- bootstrap-table -->
    <script type="text/javascript" src="${path}/assets/dependents/bootstrap-table/1.9.1/bootstrap-table.js"></script>
    <script type="text/javascript" src="${path}/assets/dependents/bootstrap-table/1.9.1/bootstrap-table-locale-all.js"></script>
    <link rel="stylesheet" type="text/css" href="${path}/assets/dependents/bootstrap-table/1.9.1/bootstrap-table.css"/>
    <!-- layer -->
    <script type="text/javascript" src="${path}/assets/dependents/layer/layer.js"></script>
    <!-- laydate -->
    <link rel="stylesheet" type="text/css" href="${path}/assets/dependents/laydate/need/laydate.css"/>
    <script type="text/javascript" src="${path}/assets/dependents/laydate/laydate.js"></script>

    <link rel="stylesheet" href="${path}/assets/css/basic.css">
    <link rel="stylesheet" href="${path}/assets/css/font-awesome.css">
</head>
<body>
<div id="wrapper">
    <%@include file="../common/header.jsp" %>
    <div id="page-wrapper">
        <div id="page-inner">
            <div class="row">
                <div class="col-md-12">

                    <div class="row-fluid">
                        <div class="span12">
                            <div class="control-group form-inline" style="border: 1px solid #ccc;padding: 10px; border-radius: 3px;">
                                <div class="input-group input-group-sm">
                                    <label for="phoneno">用户手机</label>
                                    <input id="phoneno" type="text">
                                </div>
                                <div class="input-group input-group-sm">
                                    <label for="address">客户地址</label>
                                    <input id="address" style="width: 300px;" type="text">
                                </div>
                                <button id="btn_search" type="button" class="btn btn-primary btn-sm">
                                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询
                                </button>
                                <button id="btn_clean_search" type="button" class="btn btn-danger btn-sm">清空条件</button>
                            </div>
                        </div>
                    </div>

                    <div id="toolbar" class="btn-group">
                        <!-- 工具栏 -->
                        <button id="btn_add" type="button" class="btn btn-default btn-sm">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
                        </button>
                        <button id="btn_delete" type="button" class="btn btn-default btn-sm">
                            <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>删除
                        </button>
                        <button id="btn_refresh" type="button" class="btn btn-default btn-sm">
                            <span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>刷新
                        </button>
                        <%--<button id="btn_clean" type="button" class="btn btn-default btn-sm">清空</button>--%>
                        <%--<button id="btn_init" type="button" class="btn btn-default btn-sm">初始化</button>--%>
                        <button id="btn_toggleview" type="button" class="btn btn-default btn-sm">切换视图</button>
                        <button id="btn_togglepage" type="button" class="btn btn-default btn-sm">显隐分页</button>
                        <button id="btn_selectpage" type="button" class="btn btn-default btn-sm">跳转</button>
                    </div>
                    <%--<div style="overflow: scroll">--%>
                    <!-- 表格 -->
                    <table id="orders" class="table table-hover" style="min-width: 1500px;"></table>
                    <%--</div>--%>


                    <!-- 新增或修改弹框 -->
                    <div class="modal fade" id="addAndUpdate" tabindex="-1" role="dialog" aria-labelledby="addAndUpdateLabel">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                            aria-hidden="true">×</span></button>
                                    <h4 class="modal-title" id="addAndUpdateLabel">新增订单信息</h4>
                                </div>
                                <div class="modal-body">
                                    <input type="hidden" name="txt_type" class="form-control" id="txt_type" placeholder="操作类型">
                                    <input type="hidden" name="txt_id" class="form-control" id="txt_id" placeholder="编号">
                                    <div class="form-group">
                                        <label for="txt_orderno">单号</label>
                                        <input type="text" name="txt_orderno" class="form-control" id="txt_orderno" placeholder="单号">
                                    </div>
                                    <div class="form-group">
                                        <label for="txt_customer">用户</label>
                                        <input type="text" name="txt_customer" class="form-control" id="txt_customer" placeholder="用户">
                                    </div>
                                    <div class="form-group">
                                        <label for="txt_address">地址</label>
                                        <input type="text" name="txt_address" class="form-control" id="txt_address" placeholder="地址">
                                    </div>
                                    <div class="form-group">
                                        <label for="txt_phoneno">电话</label>
                                        <input type="text" name="txt_phoneno" class="form-control" id="txt_phoneno" placeholder="电话">
                                    </div>
                                    <div class="form-group">
                                        <label for="txt_payable">价格</label>
                                        <input type="text" name="txt_payable" class="form-control" id="txt_payable" placeholder="价格">
                                    </div>
                                    <div class="form-group">
                                        <label for="txt_orderstate">状态</label>
                                        <input type="text" name="txt_orderstate" class="form-control" id="txt_orderstate" placeholder="状态">
                                    </div>

                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><span
                                            class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭
                                    </button>
                                    <button type="button" id="btn_add_update_submit" class="btn btn-primary btn-sm"
                                            data-dismiss="modal"><span
                                            class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <!-- /. WRAPPER  -->
    <%@include file="../common/footer.jsp" %>
</div>
<!-- METISMENU SCRIPTS -->
<script src="${path}/assets/js/jquery.metisMenu.js"></script>
<!-- CUSTOM SCRIPTS -->
<script src="${path}/assets/js/custom.js"></script>

<script type="text/javascript" src="${path}/assets/js/commToos.js"></script>
<%--时间选择器插件--%>
<script type="text/javascript" src="${path}/assets/js/laydate.js"></script>
<script type="text/javascript" src="${path}/assets/js/viewsJs/orders.js"></script>
</body>
</html>
