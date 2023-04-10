package com.ACGN.controller;

import com.ACGN.Service.DiscussPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class DiscussPostController {
    @Autowired
    private DiscussPostService discussPostService;


}