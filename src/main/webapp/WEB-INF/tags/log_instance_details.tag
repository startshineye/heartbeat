<%@tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="d" required="true" type="net.iegreen.domain.dto.application.ApplicationInstanceDto" %>
<div class="row">
    <br/>

    <div class="col-md-12">
        <div class="alert alert-info" role="alert">
            <ul class="list-inline">
                <li>监控URL: <a href="${d.monitorUrl}"
                              target="_blank">${d.monitorUrl}</a></li>
                <li>
                    频率: ${d.frequency.seconds}s
                </li>
                <li>
                    <abbr title='最大连接时间'>最大连接时间</abbr>: ${d.maxConnectionSeconds}s
                </li>
                <li>
                    请求方式: ${d.requestMethod}
                </li>
                <c:if test="${not empty d.contentType}">
                    <li>
                        ContentType: ${d.contentType}
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</div>
