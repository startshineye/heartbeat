<%--
 * 
 * @author Shengzhao Li
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>用户设置</title>
</head>
<body>
<%--Alert--%>
<div class="row">
    <div class="col-md-12">
        <div class="alert alert-success" style="display: none;">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <div><em class="fui-check-circle"></em>
                <span id="updateProfileOK" style="display: none;">更新用户设置成功</span>
            </div>
        </div>
    </div>
</div>

<div>
    <div class="row">
        <div class="col-md-12">
            <h4>用户设置
                <small>修改密码</small>
            </h4>
            <form:form commandName="formDto" cssClass="form-horizontal">
                <div class="form-group">
                    <label class="col-sm-2 control-label">账号</label>

                    <div class="col-sm-8">
                        <p class="form-control-static">${SPRING_SECURITY_CONTEXT.authentication.principal.username}</p>
                    </div>
                </div>

                <div class="form-group">
                    <label for="oldPassword" class="col-sm-2 control-label">旧密码</label>

                    <div class="col-sm-8">
                        <form:password path="oldPassword" id="oldPassword" cssClass="form-control"
                                       placeholder="请输入旧密码" required="true" maxlength="255"/>
                        <p class="help-block">当前登录用户的密码</p>
                        <form:errors path="oldPassword" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="password" class="col-sm-2 control-label">新密码</label>

                    <div class="col-sm-8">
                        <form:password path="password" id="password" cssClass="form-control"
                                       placeholder="请输入新密码" required="true" maxlength="255"/>
                        <p class="help-block">新密码长度 >= 6</p>
                        <form:errors path="password" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="rePassword" class="col-sm-2 control-label">重复新密码</label>

                    <div class="col-sm-8">
                        <form:password path="rePassword" id="rePassword" cssClass="form-control"
                                       placeholder="请再次输入新密码" required="true" maxlength="255"/>
                        <p class="help-block">再次输入新密码</p>
                        <form:errors path="rePassword" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-primary"><em class="fui-check-circle"></em> 更新
                        </button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>

<script>
    $(function () {
        new UserProfile('${param.alert}');
    });
</script>
</body>
</html>