package net.iegreen.domain.application;

import net.iegreen.domain.AbstractDomain;
import net.iegreen.domain.user.WeixinUser;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 2016/5/17
 *
 * @author Shengzhao Li
 */
@Entity
@Table(name = "application_instance_weixin_user")
public class ApplicationInstanceWeixinUser extends AbstractDomain {


    private static final long serialVersionUID = -1967036436756859809L;

    @ManyToOne
    @JoinColumn(name = "instance_id")
    private ApplicationInstance applicationInstance;

    @ManyToOne
    @JoinColumn(name = "weixin_user_id")
    private WeixinUser weixinUser;


    public ApplicationInstanceWeixinUser() {
    }

    public ApplicationInstanceWeixinUser(ApplicationInstance instance, WeixinUser weixinUser) {
        this.applicationInstance = instance;
        this.weixinUser = weixinUser;
    }

    public ApplicationInstance applicationInstance() {
        return applicationInstance;
    }

    public ApplicationInstanceWeixinUser applicationInstance(ApplicationInstance applicationInstance) {
        this.applicationInstance = applicationInstance;
        return this;
    }

    public WeixinUser weixinUser() {
        return weixinUser;
    }

    public ApplicationInstanceWeixinUser weixinUser(WeixinUser weixinUser) {
        this.weixinUser = weixinUser;
        return this;
    }
}
