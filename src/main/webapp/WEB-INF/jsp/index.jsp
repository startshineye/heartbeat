<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 <c:set var="URL" value="<%=net.iegreen.domain.application.InstanceType.URL%>"/>
 <c:set var="DATABASE" value="<%=net.iegreen.domain.application.InstanceType.DATABASE%>"/>
 <c:set var="FTP" value="<%=net.iegreen.domain.application.InstanceType.FTP%>"/>
  
<!DOCTYPE HTML>
<html>
<head>
    <title><spring:message code="index.jsp.title" text="Monitoring"/></title>
    <c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/>
</head>
<body>
<c:if test="${indexDto.employInstances}" var="emptyInstances">
    <div class="row">
        <div class="col-md-12">
            <div class="alert alert-info" role="alert">
                <strong class="fui-info-circle"></strong>
                <spring:message code="index.jsp.empty.instances"
                                text="Not enabled any instance to monitoring yet. Now create the first"/> <a
                    href="${contextPath}/instance/instance_form.hb"><spring:message code="index.jsp.new.instance"
                                                                                    text="new instance"/></a>
                <spring:message code="index.jsp.or.go.to" text="or go to"/> <a
                    href="${contextPath}/instance/list.hb"><spring:message code="index.jsp.instances"
                                                                           text="instances"/></a> <spring:message
                    code="index.jsp.enable.it" text="enable it."/>
            </div>
        </div>
    </div>
</c:if>

<c:if test="${not emptyInstances}">
    <div class="row">
        <div class="col-md-10">
            <form class="form-inline" role="form" action="" id="filterForm">
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon"><spring:message code="index.jsp.per.show" text="Per show"/></div>
                        <select name="maxResult" class="form-control">
                            <c:forEach begin="10" end="50" step="10" var="i">
                                <option value="${i}" ${i == indexDto.maxResult?'selected':''}>${i}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" id="enabled" ${indexDto.enabled?'checked':''}/><spring:message
                                code="index.jsp.monitoring" text="Monitoring"/>
                            <input type="hidden" name="enabled" value="${indexDto.enabled}"/>
                        </label>
                    </div>
                </div>
            </form>
        </div>
        <div class="col-md-2">
            <div class="pull-right">
                <strong>${indexDto.instanceDtos.size()}</strong> <spring:message code="index.jsp.instances.total"
                                                                                 text="instance(s)"/>
            </div>
        </div>
    </div>

    <c:forEach items="${indexDto.instanceDtos}" var="ins" varStatus="s">
        <div class="row">
            <hr/>
            <div class="col-md-12">
                <h4><c:if test="${ins.enabled}"><em class="fui-time text-success" title="监控中"></em></c:if>
                    <c:if test="${ins.privateInstance}"><em class="fui-lock text-muted" title="私有实例"></em></c:if>
                    <a href="${contextPath}/monitoring/${ins.guid}.hb">${ins.instanceName}</a>
                   
                    <c:if test="${ins.instanceType eq URL}">
			            <small><a href="${ins.monitorUrl}" target="_blank" class="text-muted">${ins.monitorUrl}</a> </small>
			        </c:if>
			        
			        <c:if test="${ins.instanceType eq DATABASE}">
			                 <small><a >${ins.sqlurl}</a> </small>
			    
			        </c:if>
			        
			        <c:if test="${ins.instanceType eq FTP}">
			              <small><a >${ins.ftpurl}</a> </small>
			        </c:if>
                   
                   
                </h4>

                <div id="chart${s.index}" style="height:300px"></div>
            </div>
        </div>

    </c:forEach>

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
                    <c:forEach items="${indexDto.instanceDtos}" var="ins" varStatus="s">
                    var option${s.index} = {
                        title: {
                            text: '${ins.instanceName}',
                            subtext: '<spring:message code="index.jsp.frequency" text="Frequency"/>: ${ins.frequency.seconds}s  <spring:message code="index.jsp.max.conn" text="Max-Conn"/>: ${ins.maxConnectionSeconds}s '
                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data: ['<spring:message code="index.jsp.conn.time" text="Conn Time(ms)"/>']
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
                                data:${ins.categoryData}
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value'
                            }
                        ],
                        series: [
                            {
                                name: '<spring:message code="index.jsp.conn.time" text="Conn Time(ms)"/>',
                                type: 'line',
                                data:${ins.seriesData},
                                markLine: {
                                    data: [
                                        {
                                            type: 'average',
                                            name: '<spring:message code="index.jsp.average.conn.time" text="Average Conn Time"/>'
                                        }
                                    ]
                                }
                            }
                        ]
                    };

                    var myChart${s.index} = ec.init(document.getElementById('chart${s.index}'));
                    myChart${s.index}.setOption(option${s.index});
                    myChart${s.index}.setTheme('macarons');

                    var lastLogDate${s.index} = '${ins.lastLogDate}';
                    setInterval(function () {
                        $.get("load_addition_monitor_logs.hb", {
                            lastLogDate: lastLogDate${s.index},
                            guid: '${ins.guid}'
                        }, function (data) {
                            myChart${s.index}.addData(eval(data.additionData));
                            lastLogDate${s.index} = data.lastLogDate;
                        });

                    }, ${ins.intervalTime});
                    </c:forEach>
                }
        );

    </script>
</c:if>

<script>
    $(function () {
        new Index('${param.alert}');
    });
</script>
</body>
</html>