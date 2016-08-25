<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<ul class="list-group">
    <li class="list-group-item">
        正常连接时长: <span class="text-success">${statisticsDto.normalConnection}</span>
    </li>
    <li class="list-group-item">
        最后不能连接时间: <a href="../log/list.hb?instanceGuid=${statisticsDto.guid}&normal=2"><span
            class="text-danger">${statisticsDto.lastNotNormalTime}</span></a>
    </li>
    <li class="list-group-item">
        监控正常/不正常次数: <a href="../log/list.hb?instanceGuid=${statisticsDto.guid}&normal=1"><span
            class="text-info">${statisticsDto.normalAmount}</span></a> / <a
            href="../log/list.hb?instanceGuid=${statisticsDto.guid}&normal=2"><span
            class="text-danger">${statisticsDto.notNormalAmount}</span></a>
    </li>
    <li class="list-group-item">
        被中断连接次数: <a href="../log/reminder_list.hb?instanceGuid=${statisticsDto.guid}&normal=0"><span
            class="text-info">${statisticsDto.interruptTime}</span></a>
    </li>
    <li class="list-group-item">
        发送提醒次数: <a href="../log/reminder_list.hb?instanceGuid=${statisticsDto.guid}&normal=1"><span
            class="text-info">${statisticsDto.sendReminderTime}</span></a>
    </li>
</ul>