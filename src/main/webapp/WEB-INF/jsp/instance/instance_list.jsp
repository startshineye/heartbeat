  <%--
 * 
 * @author Shengzhao Li
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dis" uri="http://displaytag.sf.net" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>实例列表</title>

    <style>
        .list-group li:hover {
            background-color: #f9f9f9;
        }
    </style>
</head>
<body>
<%--Alert--%>
<div class="row">
    <div class="col-md-12">
        <div class="alert alert-success" style="display: none;">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <div><em class="fui-check-circle"></em>
                <span id="saveInstanceOK" style="display: none;">保存实例成功</span>
                <span id="enableSuccess" style="display: none;">启用实例心跳成功</span>
                <span id="stopSuccess" style="display: none;">停止实例心跳成功</span>
                <span id="deleteSuccess" style="display: none;">删除实例成功</span>
            </div>
        </div>
        <div class="alert alert-danger" style="display: none;">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <div><em class="fui-check-circle"></em>
                <span id="enableFailed" style="display: none;">启用实例心跳监控失败; 请查看服务端控制台日志发生了什么...</span>
                <span id="stopFailed" style="display: none;">停止实例心跳监控失败; 请查看服务端控制台日志发生了什么...</span>
                <span id="deleteFailed"
                      style="display: none;">删除实例失败; 请确保实例状态为禁用</span>
            </div>
        </div>

    </div>
</div>
<div class="row">
    <div class="col-md-4">
        <form class="form-inline" role="form" action="" id="filterForm">
            <div class="input-group">
                <input type="text" name="instanceName" class="form-control" id="instanceName"
                       placeholder="实例名称" value="${listDto.instanceName}"/>
                <span class="input-group-btn">
                    <button type="submit" class="btn"><i class="fui-search"></i></button>
                </span>
            </div>
            <strong> ${listDto.totalSize}</strong>个实例
        </form>
    </div>
    <div class="col-md-2">
        &nbsp;
    </div>
    <div class="col-md-4 col-md-offset-2">
        <div class="pull-right">
            <%--<sec:authorize ifAnyGranted="ROLE_CREATE_EDIT_INSTANCE">--%>
            <a href="instance_form.hb" class="btn btn-primary"><i class="fui-plus"></i> 添加实例</a>
            <%--</sec:authorize>--%>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <br/>
        <c:if test="${empty listDto.list}">
            <div class="alert alert-info" role="alert">
                <strong class="fui-info-circle"></strong>
                尚未添加实例或搜索结果为空, 请点击'添加实例'创建第一个实例.
            </div>
        </c:if>
        <c:if test="${not empty listDto.list}">
            <ul class="list-group">
                <c:forEach items="${listDto.list}" var="d" varStatus="sta">
                    <custom:instance_group_item d="${d}"/>
                </c:forEach>
            </ul>
            <%--pagination--%>
            <dis:table list="${listDto}" id="d" form="filterForm" class="table table-striped table-hover hidden">
                <dis:column property="frequency.seconds"/>
            </dis:table>
        </c:if>
    </div>
</div>
<script>
    $(function () {
        new InstanceList('${param.alert}');
    })
</script>
</body>
</html>