package net.iegreen.domain.dto.user;

import net.iegreen.domain.dto.AbstractDto;
import net.iegreen.domain.user.WeixinUser;
import net.iegreen.infrastructure.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 2016/5/17
 *
 * @author Shengzhao Li
 */
public class WeixinUserDto extends AbstractDto {
    private static final long serialVersionUID = -5456120599079765768L;

    private String createDate;
    private String openId;

    //HB 账号
    private String hbUsername;

    // 微信 用户的昵称
    private String nickName;

    public WeixinUserDto() {
    }

    public WeixinUserDto(WeixinUser weixinUser) {
        super(weixinUser.guid());
        this.openId = weixinUser.openId();
        this.createDate = DateUtils.toDateText(weixinUser.createTime());


        this.hbUsername = weixinUser.hbUsername();
        this.nickName = weixinUser.nickName();
    }


    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getHbUsername() {
        return hbUsername;
    }

    public void setHbUsername(String hbUsername) {
        this.hbUsername = hbUsername;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public static List<WeixinUserDto> toDtos(List<WeixinUser> weixinUsers) {
        List<WeixinUserDto> dtos = new ArrayList<>(weixinUsers.size());
        for (WeixinUser weixinUser : weixinUsers) {
            dtos.add(new WeixinUserDto(weixinUser));
        }
        return dtos;
    }
}
