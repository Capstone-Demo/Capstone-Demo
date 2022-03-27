package com.recoder.capstone_admin.app.user

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
@RequestMapping("user")
class UserController{
    @RequestMapping(method=arrayOf(RequestMethod.GET))
    fun getToUser() = "user/user.html"
}