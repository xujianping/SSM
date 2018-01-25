<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--path和basePath必须建，以便模板页面的引入和资源访问正确--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html lang="zh-CN">
<head>
    <title>订单列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="/webjars/bootstrap/3.3.5/css/bootstrap.min.css">
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <link rel="stylesheet" href="/webjars/bootstrap/3.3.5/css/bootstrap-theme.min.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="/webjars/jquery/1.11.1/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="/webjars/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="assets/css/basic.css">
    <link rel="stylesheet" href="assets/css/font-awesome.css">
</head>
<body>
<div id="wrapper">

    <%@include file="WEB-INF/views/common/header.jsp" %>

    <div id="page-wrapper">
        <div id="page-inner">
            <div class="row">
                <div class="col-md-12">
                    <h1 class="page-head-line">大标题</h1>
                    <h1 class="page-subhead-line">小标题 </h1>

                </div>
            </div>
            <!-- /. ROW  -->
        </div>
    </div>
    <!-- /. WRAPPER  -->
    <%@include file="WEB-INF/views/common/footer.jsp" %>

</div>
<!-- METISMENU SCRIPTS -->
<script src="${path}/js/jquery.metisMenu.js"></script>
<!-- CUSTOM SCRIPTS -->
<script src="${path}/js/custom.js"></script>
</body>
</html>
