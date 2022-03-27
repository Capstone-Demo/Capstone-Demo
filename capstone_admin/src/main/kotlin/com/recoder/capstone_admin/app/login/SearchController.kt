package com.recoder.capstone_admin.app.login

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
@RequestMapping("search")
class SearchController {
    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun getToSearch(): String = "search/search.html"

    @RequestMapping(value= arrayOf("{lot_no}"), method=arrayOf(RequestMethod.GET))
    fun getToLotDetails() = "search/search_info.html"
}