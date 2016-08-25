<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>

<div>
    <em class="fui-check-circle"></em>
    重置密码功能!<br/>
    该用户[${resetUserPasswordDto.username}] 的新密码为 <strong
        class="label label-info">${resetUserPasswordDto.newPassword}</strong>.
</div>