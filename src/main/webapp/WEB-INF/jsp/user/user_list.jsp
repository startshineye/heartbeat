<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="dis" uri="http://displaytag.sf.net" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>用户</title>
</head>
<body>
<%--Alert--%>
<div class="row">
    <div class="col-md-12">
        <div class="alert alert-success" style="display: none;">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <div><em class="fui-check-circle"></em>
                <span id="saveUserOK" style="display: none;">保存用户成功</span>
                <span id="deleteSuccess" style="display: none;">删除用户成功</span>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-8">
        <form class="form-inline" role="form" action="" id="filterForm">
            <div class="input-group">
                <input type="text" name="username" value="${listDto.username}" placeholder="输入账号"
                       class="form-control"/>
                <span class="input-group-btn">
                    <button type="submit" class="btn"><i class="fui-search"></i></button>
                </span>
            </div>
            <strong>${listDto.totalSize}</strong>个用户
        </form>
    </div>
    <div class="col-md-4">
        <div class="pull-right">
            <a href="form/create.hb" class="btn btn-primary"><i class="fui-plus"></i> 添加用户</a>
        </div>
    </div>
</div>


<div class="row">
    <div class="col-md-12">
        <dis:table list="${listDto}" id="d" form="filterForm" class="table table-striped table-hover">
            <dis:column title="账号" property="username" class="${d.registerUser?'text-success':''}"/>
            <dis:column title="电话" property="phone"/>
            <dis:column title="邮箱" property="email"/>
            <dis:column title="添加日期" property="createDate"/>
            <dis:column title="权限">
                <ul>
                    <c:forEach items="${d.privileges}" var="p">
                        <li>${p.label}</li>
                    </c:forEach>
                    <c:if test="${d.defaultUser}">
                        <li class="text-warning">所有权限</li>
                    </c:if>
                </ul>
            </dis:column>
            <dis:column title="">
                <a href="javascript:void(0);" guid="${d.guid}" title="重置密码" class="resetPass"
                   confirmText="确认重置用户[${d.username}] 密码 ?"><em
                        class="fui-clip"></em></a>&nbsp;
                <c:if test="${d.defaultUser}" var="du">
                    <em class="fui-lock" title="默认用户"></em>
                </c:if>
                <c:if test="${not du}">
                    <a href="form/${d.guid}.hb" title="编辑"><em class="fui-new"></em></a>&nbsp;
                    <a href="javascript:void(0);" guid="${d.guid}" class="deleteUser" title="删除"
                       confirmText="确认删除用户[${d.username}] ?"><em
                            class="fui-cross"></em></a>
                </c:if>
            </dis:column>
        </dis:table>

        <p class="help-block">
            * 蓝绿色用户是注册的
        </p>
    </div>
</div>

<%--Reset password modal--%>
<div class="modal fade" tabindex="-1" role="dialog" id="resetPassModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">重置密码</h4>
            </div>
            <div class="modal-body">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>


<script>
    $(function () {
        new UserList('${param.alert}');
    })
</script>

</body>
</html>