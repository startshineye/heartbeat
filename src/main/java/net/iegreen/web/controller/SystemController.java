package net.iegreen.web.controller;

import net.iegreen.domain.dto.user.SystemSettingDto;
import net.iegreen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * @author Shengzhao Li
 */
@Controller
@RequestMapping("/system/")
public class SystemController {


    @Autowired
    private UserService userService;


    @RequestMapping(value = "setting.hb", method = RequestMethod.GET)
    public String loadSetting(Model model) {
        SystemSettingDto formDto = userService.loadSystemSettingDto();
        model.addAttribute("formDto", formDto);
        return "user/system_setting";
    }

    @RequestMapping(value = "setting.hb", method = RequestMethod.POST)
    public String submitSetting(@ModelAttribute("formDto") @Valid SystemSettingDto formDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/system_setting";
        }
        userService.updateSystemSetting(formDto);
        model.addAttribute("alert", "updateSettingOK");
        return "redirect:setting.hb";
    }


}