<%@tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="d" required="true" type="net.iegreen.domain.dto.application.ApplicationInstanceDto" %>
 <c:set var="URL" value="<%=net.iegreen.domain.application.InstanceType.URL%>"/>
    <c:set var="DATABASE" value="<%=net.iegreen.domain.application.InstanceType.DATABASE%>"/>
   <c:set var="FTP" value="<%=net.iegreen.domain.application.InstanceType.FTP%>"/>
<div class="row">
    <br/>

    <div class="col-md-12">
        <div class="alert alert-info" role="alert">
            <ul class="list-inline">
                <li>监控地址: 
                    <c:if test="${d.instanceType eq URL}">
           <a href="${d.monitorUrl}" target="_blank">${d.monitorUrl}</a>
        </c:if>
        
        <c:if test="${d.instanceType eq DATABASE}">
          <a>${d.sqlurl}</a>
        </c:if>
        
        <c:if test="${d.instanceType eq FTP}">
          <a>${d.ftpurl}</a>
        </c:if>
                
                </li>
                <li>
                    频率: ${d.frequency.seconds}s
                </li>
                <li>
                    <abbr title='最大连接时间'>最大连接时间</abbr>: ${d.maxConnectionSeconds}s
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
