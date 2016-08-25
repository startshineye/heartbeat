package net.iegreen.domain.log.reminder;

import net.iegreen.domain.log.FrequencyMonitorLog;
import net.iegreen.domain.log.MonitoringReminderLog;
import net.iegreen.domain.user.WeixinUser;
import net.iegreen.infrastructure.MinixinUtils;
import net.iegreen.infrastructure.STRender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 2016/5/19
 * <p/>
 * 微信 提醒实现
 *
 * @author Shengzhao Li
 */
public class WeChatPerMonitoringReminderSender extends AbstractPerMonitoringReminderSender {


    private static final String CHANGE_NORMAL_WECHAT_CONTENT_TEMPLATE = "template/instance_change_normal_wechat.st";

    private static final String CHANGE_NOT_NORMAL_WECHAT_CONTENT_TEMPLATE = "template/instance_change_not_normal_wechat.st";

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatPerMonitoringReminderSender.class);

    @Override
    public boolean support(FrequencyMonitorLog monitorLog) {
        return !monitorLog.instance().weixinUsers().isEmpty();
    }

    @Override
    public void send(MonitoringReminderLog reminderLog, FrequencyMonitorLog monitorLog) {

        final boolean normal = monitorLog.normal();
        reminderLog.changeNormal(normal);

        String content;
        if (normal) {
            content = changeNormalContent(monitorLog);
        } else {
            content = changeUnNormalContent(monitorLog);
        }
        reminderLog.weChartContent(content);

        final List<WeixinUser> weixinUsers = monitorLog.instance().weixinUsers();
        for (WeixinUser weixinUser : weixinUsers) {
            final String openId = weixinUser.openId();
            reminderLog.appendOpenId(openId);

            boolean result = MinixinUtils.sendMsg(openId, content);
            LOGGER.debug("Send WeChat reminder to: {}, content: {}, result: {}", openId, content, result);
        }

    }

    private String changeUnNormalContent(FrequencyMonitorLog monitorLog) {

        Map<String, Object> model = contentModel(monitorLog);
        STRender stRender = new STRender(CHANGE_NOT_NORMAL_WECHAT_CONTENT_TEMPLATE, model);

        return stRender.render();
    }

    private String changeNormalContent(FrequencyMonitorLog monitorLog) {

        Map<String, Object> model = contentModel(monitorLog);
        STRender stRender = new STRender(CHANGE_NORMAL_WECHAT_CONTENT_TEMPLATE, model);

        return stRender.render();
    }
}
