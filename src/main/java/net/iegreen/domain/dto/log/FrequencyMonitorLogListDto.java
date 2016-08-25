package net.iegreen.domain.dto.log;

import net.iegreen.domain.dto.application.ApplicationInstanceDto;
import net.iegreen.domain.shared.paginated.DefaultPaginated;
import net.iegreen.infrastructure.grouper.GroupResult;
import net.iegreen.infrastructure.grouper.impl.FrequencyMonitorLogDtoCreateTimeGrouper;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 15-2-13
 *
 * @author Shengzhao Li
 */
public class FrequencyMonitorLogListDto extends DefaultPaginated<FrequencyMonitorLogDto> {


    private String instanceGuid;
    //1 is normal,2 is not normal, otherwise all
    private int normal = 0;
    private OrderItem orderItem = OrderItem.LOG_TIME;

    private List<ApplicationInstanceDto> instanceDtos = new ArrayList<>();

    public FrequencyMonitorLogListDto() {
    }

    @Override
    public Map<String, Object> queryMap() {
        Map<String, Object> map = super.queryMap();
        map.put("instanceGuid", instanceGuid);
        map.put("normal", normal);
        map.put("orderItem", orderItem.getField());
        return map;
    }


    public List<GroupResult<String, FrequencyMonitorLogDto>> getListGroupResults() {
        FrequencyMonitorLogDtoCreateTimeGrouper grouper = new FrequencyMonitorLogDtoCreateTimeGrouper(list);
        return grouper.group().getGroupResults();
    }

    public OrderItem[] getOrderItems() {
        return OrderItem.values();
    }

    public ApplicationInstanceDto getInstanceDto() {
        if (StringUtils.isNotEmpty(instanceGuid)) {
            for (ApplicationInstanceDto instanceDto : instanceDtos) {
                if (instanceDto.getGuid().equals(instanceGuid)) {
                    return instanceDto;
                }
            }
        }
        return null;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public List<ApplicationInstanceDto> getInstanceDtos() {
        return instanceDtos;
    }

    public int getNormal() {
        return normal;
    }

    public void setNormal(int normal) {
        this.normal = normal;
    }

    public String getInstanceGuid() {
        return instanceGuid;
    }

    public void setInstanceGuid(String instanceGuid) {
        this.instanceGuid = instanceGuid;
    }


    /**
     * Order item
     */
    public static enum OrderItem {
        LOG_TIME("createTime", "日志时间"),
        CONN_TIME("costTime", "连接用时");
        private String field;
        private String label;

        private OrderItem(String field, String label) {
            this.field = field;
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        public String getField() {
            return field;
        }

        public String getValue() {
            return name();
        }
    }

}
