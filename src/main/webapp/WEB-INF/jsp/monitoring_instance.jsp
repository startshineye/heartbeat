<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <title><spring:message code="monitoring.instance.jsp.title" text="Monitoring"/>
        [${instanceDto.instanceName}]</title>
</head>
<body>

<div class="row">
    <h4><c:if test="${instanceDto.enabled}"><em class="fui-time text-success"
                                                title="<spring:message code="monitoring.instance.jsp.title" text="Monitoring"/>"></em></c:if>
        <c:if test="${instanceDto.privateInstance}"><em class="fui-lock text-muted" title="私有实例"></em></c:if>
        ${instanceDto.instanceName}</h4>

    <div class="col-md-12">
        <div id="chart" style="height:400px"></div>
    </div>
</div>

<div class="row">
    <div class="col-md-6">
        <ul class="list-group">
            <li class="list-group-item">
                <spring:message code="monitoring.instance.jsp.monitor.url" text="Monitor URL"/>: <a
                    href="${instanceDto.monitorUrl}"
                    target="_blank">${instanceDto.monitorUrl}</a> <span
                    class="text-info">[${instanceDto.requestMethod}]</span>
            </li>
            <c:if test="${not empty instanceDto.contentType}">
                <li class="list-group-item">
                    <spring:message code="monitoring.instance.jsp.contenttype" text="ContentType"/>: <span
                        class="text-info">${instanceDto.contentType}</span>
                </li>
            </c:if>
            <li class="list-group-item">
                <spring:message code="monitoring.instance.jsp.frequency" text="Frequency"/>: <span
                    class="text-info">${instanceDto.frequency.seconds}s</span>
            </li>
            <li class="list-group-item">
                <abbr title='<spring:message code="monitoring.instance.jsp.max.connection.time" text="Max Connection Time(s)"/>'><spring:message
                        code="monitoring.instance.jsp.max.conn"
                        text="Max Conn(s)"/></abbr>: <span
                    class="text-info">${instanceDto.maxConnectionSeconds}s</span>
            </li>
            <li class="list-group-item">
                <spring:message code="monitoring.instance.jsp.email" text="Email"/>: <span
                    class="text-info">${instanceDto.email}</span>
            </li>
            <c:if test="${not empty instanceDto.weixinUserDtos}">
                <li class="list-group-item">
                    WeChat: <span class="text-info"><c:forEach items="${instanceDto.weixinUserDtos}" var="wu"
                                                               varStatus="vs">${wu.nickName}${vs.last?'':','}</c:forEach></span>
                </li>
            </c:if>
            <li class="list-group-item">
                <spring:message code="monitoring.instance.jsp.remark" text="Remark"/>: <span
                    class="text-info">${instanceDto.remark}</span>
            </li>
        </ul>
    </div>
    <div class="col-md-6">
        <img src="${contextPath}/resources/images/loading_64.gif" class="img-responsive"/>
        <%--Ajax load data--%>
        <div id="staticsDiv"></div>
    </div>
</div>


<div class="row">
    <div class="col-md-12">
        <div class="text-center">
            <a href="javascript:history.back();" class="btn btn-inverse"><spring:message
                    code="monitoring.instance.jsp.back" text="Back"/></a>
        </div>
    </div>
</div>


<script src="${contextPath}/resources/js/echarts/echarts.js"></script>
<script>
    require.config({
        paths: {
            echarts: '${contextPath}/resources/js/echarts'
        }
    });
    require(
            [
                'echarts',
                'echarts/chart/bar',
                'echarts/chart/line'
            ],
            function (ec) {

                var option0 = {
                    title: {
                        text: '${instanceDto.instanceName}',
                        subtext: '<spring:message code="monitoring.instance.jsp.frequency" text="Frequency"/>: ${instanceDto.frequency.seconds}s  <spring:message code="monitoring.instance.jsp.max.conn2" text="Max-Conn"/>: ${instanceDto.maxConnectionSeconds}s <spring:message code="monitoring.instance.jsp.request.method" text="Request-Method"/>: ${instanceDto.requestMethod}'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data: ['<spring:message code="monitoring.instance.jsp.conn.time" text="Conn Time(ms)"/>']
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            mark: {show: false},
                            dataView: {show: false, readOnly: false},
                            magicType: {show: true, type: ['line', 'bar']},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    xAxis: [
                        {
                            type: 'category',
                            data:${instanceDto.categoryData}
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            name: '<spring:message code="monitoring.instance.jsp.conn.time" text="Conn Time(ms)"/>',
                            type: 'line',
                            data:${instanceDto.seriesData},
                            markLine: {
                                data: [
                                    {
                                        type: 'average',
                                        name: '<spring:message code="monitoring.instance.jsp.average.conn.time" text="Average Conn Time"/>'
                                    }
                                ]
                            }
                        }
                    ]
                };

                var myChart0 = ec.init(document.getElementById('chart'));
                myChart0.setOption(option0);
                myChart0.setTheme('macarons');

                var lastLogDate0 = '${instanceDto.lastLogDate}';
                setInterval(function () {
                    $.get("../load_addition_monitor_logs.hb", {
                        lastLogDate: lastLogDate0,
                        guid: '${instanceDto.guid}'
                    }, function (data) {
                        myChart0.addData(eval(data.additionData));
                        lastLogDate0 = data.lastLogDate;
                    });

                }, ${instanceDto.intervalTime});
            }
    );

    new MonitoringInstance('${instanceDto.guid}');
</script>

</body>
</html>