package com.recoder.capstone_admin.app.login

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class LoginController{
    @RequestMapping("/")
    fun getToLogin(model: Model): String {
        return "login/login.html"
    }
}