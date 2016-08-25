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
    <title>提醒日志</title>
</head>
<body>

<div class="row">
    <div class="col-md-10">
        <form class="form-inline" role="form" action="" id="filterForm">
            <div class="form-group">
                <select name="instanceGuid" class="form-control">
                    <option value="">所有实例</option>
                    <c:forEach items="${listDto.instanceDtos}" var="st">
                        <option value="${st.guid}" ${st.guid eq listDto.instanceGuid?'selected':''}>${st.instanceName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <select name="normal" class="form-control">
                    <option value="-1">所有状态变化</option>
                    <option value="0" ${listDto.normal eq 0?'selected':''}>正常 -> 不正常</option>
                    <option value="1" ${listDto.normal eq 1?'selected':''}>不正常 -> 正常</option>
                </select>
            </div>
            <button type="submit" class="btn"><i class="fui-search"></i></button>
            <strong>${listDto.totalSize}</strong>条提醒日志
        </form>
    </div>
    <div class="col-md-2">
        &nbsp;
    </div>
</div>

<c:if test="${not empty listDto.instanceDto}">
    <custom:log_instance_details d="${listDto.instanceDto}"/>
</c:if>

<div class="row">
    <div class="col-md-12">
        <dis:table list="${listDto}" id="d" form="filterForm" class="table table-striped table-hover">
            <dis:column title="实例名称">
                <a href="${contextPath}/monitoring/${d.instanceGuid}.hb">${d.instanceName}</a>
            </dis:column>
            <dis:column title="提醒时间" property="createTime"/>
            <dis:column title="状态">
                <c:if test="${d.changeNormal}">
                    <em class="fui-checkbox-checked text-success" title="返回正常"></em>
                </c:if>
                <c:if test="${not d.changeNormal}">
                    <em class="fui-stumbleupon text-warning" title="变为不正常"></em>
                </c:if>
            </dis:column>
            <dis:column title="提醒邮箱">
                <a href="mailto:${d.receiveEmail}" class="text-info">${d.receiveEmail}</a>
                &nbsp;
                <a title="显示邮件内容" class="showMailContent" href="javascript:void(0)"><em
                        class="fui-mail"></em></a>

                <div class="hidden">
                        ${d.emailContent}
                </div>
            </dis:column>
            <dis:column title="微信号">
                <c:if test="${not empty d.openId}">
                    <span class="text-info">${d.openId}</span>
                    &nbsp;
                    <a title="Show WeChat Content" class="showWeChatContent" href="javascript:void(0)"><img
                            src="${contextPath}/resources/images/wechat.jpg" class="img-rounded"
                            style="max-height: 20px;"/></a>

                    <div class="hidden">${d.weChartContent}</div>
                </c:if>
            </dis:column>
        </dis:table>

    </div>
</div>

<script>
    $(function () {
        new ReminderLogs();
    })
</script>
</body>
</html>