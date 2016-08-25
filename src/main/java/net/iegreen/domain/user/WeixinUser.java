package net.iegreen.domain.user;


import net.iegreen.domain.AbstractDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 2016/5/1
 * <p/>
 * 微信账号与 HeartBeat 账号关联
 *
 * @author Shengzhao Li
 */
@Entity
@Table(name = "weixin_user")
public class WeixinUser extends AbstractDomain {
    private static final long serialVersionUID = 4472810301041659782L;

    //weixin openId
    @Column(name = "openid", unique = true)
    private String openId;

    //HB 账号
    @Column(name = "hb_username")
    private String hbUsername;

    // 微信 用户的昵称
    @Column(name = "nick_name")
    private String nickName;

    public WeixinUser() {
    }


    public String nickName() {
        return nickName;
    }

    public WeixinUser nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public String openId() {
        return openId;
    }

    public WeixinUser openId(String openId) {
        this.openId = openId;
        return this;
    }

    public String hbUsername() {
        return hbUsername;
    }

    public WeixinUser hbUsername(String hbUsername) {
        this.hbUsername = hbUsername;
        return this;
    }


}
