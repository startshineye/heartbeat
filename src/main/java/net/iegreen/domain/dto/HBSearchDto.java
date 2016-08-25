package net.iegreen.domain.dto;

import net.iegreen.domain.shared.paginated.DefaultPaginated;
import net.iegreen.service.operation.search.ApplicationInstanceSearcher;
import net.iegreen.service.operation.search.HBSearcher;
import net.iegreen.service.operation.search.MonitorLogSearcher;

import java.util.Map;

/**
 * 15-3-13
 *
 * @author Shengzhao Li
 */
public class HBSearchDto extends DefaultPaginated<HBSearchResultDto> {

    private static final HBSearchType DEFAULT_SEARCH_TYPE = HBSearchType.INSTANCE;

    private String key;
    private HBSearchType searchType = DEFAULT_SEARCH_TYPE;

    public HBSearchDto() {
    }

    @Override
    public Map<String, Object> queryMap() {
        final Map<String, Object> map = super.queryMap();
        map.put("key", key);
        return map;
    }

    public HBSearchType[] getSearchTypes() {
        return HBSearchType.values();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public HBSearchType getSearchType() {
        if (searchType == null) {
            return DEFAULT_SEARCH_TYPE;
        }
        return searchType;
    }

    public void setSearchType(HBSearchType searchType) {
        this.searchType = searchType;
    }

    public static enum HBSearchType {
        INSTANCE("实例") {
            @Override
            public HBSearcher resolveSearcher() {
                return new ApplicationInstanceSearcher();
            }
        },
        MONITOR_LOG("监控日志") {
            @Override
            public HBSearcher resolveSearcher() {
                return new MonitorLogSearcher();
            }
        };

        private String label;

        private HBSearchType(String label) {
            this.label = label;
        }

        public abstract HBSearcher resolveSearcher();


        public boolean isInstance() {
            return INSTANCE.equals(this);
        }

        public boolean isMonitorLog() {
            return MONITOR_LOG.equals(this);
        }

        public String getLabel() {
            return label;
        }
    }
}
