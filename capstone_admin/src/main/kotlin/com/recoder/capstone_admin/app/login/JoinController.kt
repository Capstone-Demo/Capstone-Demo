package com.recoder.capstone_admin.app.login

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class JoinController{

    @RequestMapping("/join")
    fun getToJoin(): String = "/join/join.html"
}