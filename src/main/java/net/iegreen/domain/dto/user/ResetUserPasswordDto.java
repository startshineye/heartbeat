package net.iegreen.domain.dto.user;

import net.iegreen.domain.dto.AbstractDto;
import net.iegreen.domain.user.User;

/**
 * @author Shengzhao Li
 */
public class ResetUserPasswordDto extends AbstractDto {

    private String newPassword;
    private String username;

    public ResetUserPasswordDto() {
    }

    public ResetUserPasswordDto(User user, String newPassword) {
        super(user.guid());
        this.newPassword = newPassword;
        this.username = user.username();
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}