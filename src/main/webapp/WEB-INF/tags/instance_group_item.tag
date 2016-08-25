<%@tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ attribute name="d" required="true" type="net.iegreen.domain.dto.application.ApplicationInstanceDto" %>
<li class="list-group-item">
    <div class="pull-right">
        <c:if test="${not d.enabled}">
            <sec:authorize ifAnyGranted="ROLE_START_STOP_INSTANCE">
                <a href="${contextPath}/instance/enable.hb?guid=${d.guid}" title="开始监控"
                   onclick="return confirm('确认启用实例[${d.instanceName}]的心跳监控 ?')"><em
                        class="fui-play"></em></a>
                &nbsp;
            </sec:authorize>
            <sec:authorize ifAnyGranted="ROLE_CREATE_EDIT_INSTANCE">
                <a href="${contextPath}/instance/instance_form.hb?guid=${d.guid}" title="编辑"><em
                        class="fui-new"></em></a>
                &nbsp;
            </sec:authorize>
            <sec:authorize ifAnyGranted="ROLE_DELETE_INSTANCE">
                <a href="${contextPath}/instance/delete.hb?guid=${d.guid}" title="删除"
                   onclick="return confirm('确认删除实例[${d.instanceName}] (包括删除心跳监控日志) ?')"><em
                        class="fui-cross"></em></a>
            </sec:authorize>
        </c:if>
        <c:if test="${d.enabled}">
            <a href="${contextPath}/monitoring/${d.guid}.hb" title="监控中"><em
                    class="fui-time"></em></a>
            &nbsp;
            <a href="${contextPath}/log/list.hb?instanceGuid=${d.guid}" title="监控日志"><em
                    class="fui-list"></em></a>
            &nbsp;
            <sec:authorize ifAnyGranted="ROLE_START_STOP_INSTANCE">
                <a href="${contextPath}/instance/stop.hb?guid=${d.guid}" title="停止"
                   onclick="return confirm('确认停止实例[${d.instanceName}]的心跳监控 ?')"><em
                        class="fui-pause"></em></a>
            </sec:authorize>
        </c:if>
    </div>

    <h4 class="list-group-item-heading">
        <c:if test="${d.enabled}"><em class="fui-time text-success" title="监控中"></em></c:if>
        <c:if test="${d.privateInstance}"><em class="fui-lock text-muted" title="私有实例"></em></c:if>
        ${d.instanceName}
        <small><a href="${d.monitorUrl}" target="_blank">${d.monitorUrl}</a></small>
    </h4>

    <div class="list-group-item-text text-muted">
        频率: <span class="text-info">${d.frequency.seconds}s</span>&nbsp;
        最大连接时间: <span class="text-info">${d.maxConnectionSeconds}s</span>&nbsp;
        请求方式: <span class="text-info">${d.requestMethod}</span>&nbsp;
        连续失败次数: <span class="text-info">${d.continueFailedTimes}</span>&nbsp;
        <c:if test="${not empty d.contentType}">ContentType: <span class="text-info">${d.contentType}</span></c:if>
        <br/>
        创建人: <span class="text-info">${d.creatorName}</span>&nbsp;
        邮箱: <a href="mailto:${d.email}" class="text-info">${d.email}</a>
        <c:if test="${not empty d.weixinUserDtos}">
            <br/>微信号: <span class="text-info"><c:forEach items="${d.weixinUserDtos}" var="wu"
                                                         varStatus="vs">${wu.nickName}${vs.last?'':','}</c:forEach></span>
        </c:if>
        <br/>
        备注: <a class="text-info">${d.remark}</a>
    </div>
</li>
