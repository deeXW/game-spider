//package com.zdf.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.zdf.util.MjWebsiteConstant;
//import com.zdf.webspider.processor.donghua.DhHandlerProcessor;
//
//
//@Controller
//public class TestController {
//	@Autowired
//	private DhHandlerProcessor dhHandlerProcessor;
//
//
//	@RequestMapping(value = "/donghua")
//	@ResponseBody
//    public String index(Model model) throws Exception {
//		dhHandlerProcessor.exe(MjWebsiteConstant.M77DS_NEW);
//        return "success";
//    }
//}
