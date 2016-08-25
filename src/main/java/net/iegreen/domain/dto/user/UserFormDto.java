package net.iegreen.domain.dto.user;

import net.iegreen.domain.user.Privilege;
import net.iegreen.domain.user.User;
import net.iegreen.infrastructure.MatchUtils;
import net.iegreen.infrastructure.PasswordHandler;

import java.util.List;

/**
 * @author Shengzhao Li
 */
public class UserFormDto extends UserDto {

    private String password;
    private String rePassword;

    private String existUsername;

    public UserFormDto() {
    }

    public UserFormDto(User user) {
        super(user);
        this.existUsername = user.username();
    }

    @Override
    public boolean isNewly() {
        return super.isNewly() || MatchUtils.isCreate(guid);
    }

    public List<Privilege> getAllPrivileges() {
        return Privilege.authPrivileges();
    }


    public User toDomain() {
        String encryptPass = PasswordHandler.encryptPassword(password);
        return new User(username, encryptPass, phone, email);
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getExistUsername() {
        return existUsername;
    }

    public void setExistUsername(String existUsername) {
        this.existUsername = existUsername;
    }
}