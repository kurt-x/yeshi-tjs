package com.yeshi.tjs.core

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController
{
    @RequestMapping(name = "ping", path = ["/ping"], method = [RequestMethod.HEAD]) fun ping() = Unit
}
