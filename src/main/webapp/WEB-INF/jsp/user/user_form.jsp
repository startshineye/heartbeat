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
    <title>${formDto.newly?'添加':'编辑'}用户</title>
</head>
<body>
<div>
    <div class="row">
        <div class="col-md-12">
            <h4>${formDto.newly?'添加':'编辑'}用户</h4>
            <form:form commandName="formDto" cssClass="form-horizontal">
                <form:hidden path="existUsername"/>
                <div class="form-group">
                    <label for="username" class="col-sm-2 control-label">账号</label>

                    <div class="col-sm-8">
                        <form:input path="username" id="username" cssClass="form-control" autocomplete="off"
                                    placeholder="请输入账号" required="true" maxlength="255"/>
                        <p class="help-block">账号必须唯一</p>
                        <form:errors path="username" cssClass="text-danger"/>
                    </div>
                </div>
                <c:if test="${formDto.newly}">
                    <div class="form-group">
                        <label for="password" class="col-sm-2 control-label">密码</label>

                        <div class="col-sm-8">
                            <form:password path="password" id="password" cssClass="form-control"
                                           placeholder="请输入密码" required="true" maxlength="255"/>
                            <p class="help-block">密码长度 >= 6</p>
                            <form:errors path="password" cssClass="text-danger"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="rePassword" class="col-sm-2 control-label">重复密码</label>

                        <div class="col-sm-8">
                            <form:password path="rePassword" id="rePassword" cssClass="form-control"
                                           placeholder="请再次输入密码" required="true" maxlength="255"/>
                            <p class="help-block">再次输入相同的密码</p>
                            <form:errors path="rePassword" cssClass="text-danger"/>
                        </div>
                    </div>
                </c:if>
                <div class="form-group">
                    <label for="email" class="col-sm-2 control-label">邮箱</label>

                    <div class="col-sm-8">
                        <form:input path="email" id="email" cssClass="form-control"
                                    placeholder="请输入邮箱" maxlength="255"/>
                        <p class="help-block">用户的邮箱, 可选.</p>
                        <form:errors path="email" cssClass="text-danger"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="phone" class="col-sm-2 control-label">电话</label>

                    <div class="col-sm-8">
                        <form:input path="phone" id="phone" cssClass="form-control"
                                    placeholder="请输入电话" maxlength="255"/>
                        <p class="help-block">用户的电话号码, 可选.</p>
                        <form:errors path="phone" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">权限</label>

                    <div class="col-sm-8">
                        <c:forEach items="${formDto.allPrivileges}" var="p">
                            <label class="toggle-checkbox">
                                <input type="checkbox" name="privileges"
                                       value="${p.value}" ${fun:contains(formDto.privileges, p)?'checked':''}/> ${p.label}
                            </label>
                        </c:forEach>
                        <p class="help-block">用户的权限, 至少勾选一个.</p>
                        <form:errors path="privileges" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-primary"><em class="fui-check-circle"></em> 保存
                        </button>
                        &nbsp;<a href="../list.hb">取消</a>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>

</body>
</html>