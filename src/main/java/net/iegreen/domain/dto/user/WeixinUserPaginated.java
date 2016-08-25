package net.iegreen.domain.dto.user;

import net.iegreen.domain.shared.paginated.DefaultPaginated;

import java.util.Map;

/**
 * 2016/5/20
 *
 * @author Shengzhao Li
 */
public class WeixinUserPaginated extends DefaultPaginated<WeixinUserDto> {

    private String nickName;

    public WeixinUserPaginated() {
    }

    @Override
    public Map<String, Object> queryMap() {
        final Map<String, Object> map = super.queryMap();
        map.put("nickName", nickName);
        return map;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
