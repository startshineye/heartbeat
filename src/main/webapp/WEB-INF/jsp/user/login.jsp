<%--
 * 
 * @author Shengzhao Li
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8"/>
    <c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/>

    <meta name="viewport" content="width=device-width,user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="keywords" content="HeartBeat "/>
    <meta name="description" content="HeartBeat "/>
    <meta name="author" content="andaily.com"/>


    <title><spring:message code="login.jsp.title" text="Sign in"/> - HeartBeat</title>


    <link href="${contextPath}/resources/flat_ui/css/vendor/bootstrap.min.css" rel="stylesheet"/>
    <link href="${contextPath}/resources/flat_ui/css/flat-ui.css" rel="stylesheet"/>
    <link rel="shortcut icon" href="${contextPath}/favicon.ico"/>

</head>
<body>


<div class="container" style="margin-top: 1%;">
    <div class="row">
        <div class="col-md-10 col-md-offset-1">

            <div class="login">
                <div class="login-screen">
                    <div class="login-icon">
                        <a href="${contextPath}/" class="text-warning"><h3>HB</h3></a>
                        <a href="${contextPath}/resources/images/qiuchang8_qrcode.jpg" target="_blank"><img
                                src="${contextPath}/resources/images/iegreen-wx.jpg"
                                class="img-rounded img-responsive"/></a>
                    </div>

                    <div class="login-form">
                        <form action="${contextPath}/login" method="post">
                            <div class="control-group">
                                <input type="text" name="username" class="login-field form-control" id="username"
                                       placeholder="<spring:message code="login.jsp.username" text="Username"/>"
                                       required="required" maxlength="255" autocomplete="off"/>
                                <label class="login-field-icon fui-user" for="username"></label>
                            </div>
                            <div class="control-group">
                                <input type="password" name="password" class="login-field form-control" id="password"
                                       placeholder="<spring:message code="login.jsp.password" text="Password"/>"
                                       required="required" maxlength="255"/>
                                <label class="login-field-icon fui-lock" for="password"></label>
                            </div>

                            <button class="btn btn-primary btn-large btn-block" type="submit"><spring:message
                                    code="login.jsp.sign.in" text="Sign in"/></button>

                            <span class="text-danger pull-right" style="font-size: 13px;margin-top: 15px;">
                                <c:if test="${param.error eq '1'}"><em
                                        class="fui-alert-circle"></em> <spring:message code="login.jsp.failed"
                                                                                       text="Sign in failed"/></c:if>
                                <c:if test="${param.error eq '2'}"><em
                                        class="fui-alert-circle"></em> <spring:message code="login.jsp.access.denied"
                                                                                       text="Access denied"/></c:if>
                            </span>
                            <c:if test="${allowRegister}">
                                <a class="login-link" href="${contextPath}/register.hb">&raquo; <spring:message
                                        code="login.jsp.register" text="Register"/></a>
                            </c:if>
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
</body>
</html>