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
    <title>微信用户</title>
</head>
<body>
<%--Alert--%>
<%--<div class="row">--%>
<%--<div class="col-md-12">--%>
<%--<div class="alert alert-success" style="display: none;">--%>
<%--<button type="button" class="close" data-dismiss="alert">&times;</button>--%>
<%--<div><em class="fui-check-circle"></em>--%>
<%--<span id="saveUserOK" style="display: none;">Save user successful</span>--%>
<%--<span id="deleteSuccess" style="display: none;">Delete user successful</span>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>

<div class="row">
    <div class="col-md-8">
        <form class="form-inline" role="form" action="" id="filterForm">
            <div class="input-group">
                <input type="text" name="nickName" value="${listDto.nickName}" placeholder="输入昵称"
                       class="form-control"/>
                <span class="input-group-btn">
                    <button type="submit" class="btn"><i class="fui-search"></i></button>
                </span>
            </div>
            <strong>${listDto.totalSize}</strong>个微信用户
        </form>
    </div>
    <div class="col-md-4">
    </div>
</div>


<div class="row">
    <div class="col-md-12">
        <dis:table list="${listDto}" id="d" form="filterForm" class="table table-striped table-hover">
            <dis:column title="OpenId" property="openId"/>
            <dis:column title="昵称" property="nickName"/>
            <dis:column title="HB账号" property="hbUsername"/>
            <dis:column title="添加日期" property="createDate"/>
        </dis:table>
    </div>
</div>


</body>
</html>