//package com.zdf.controller;
//
//import org.apache.log4j.Logger;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.zdf.util.MjWebsiteConstant;
//import com.zdf.webspider.processor.donghua.DhHandlerProcessor;
//import com.zdf.webspider.processor.gj.GjHandlerProcessor;
//import com.zdf.webspider.processor.gj.GjM77dsProcessor;
//import com.zdf.webspider.processor.hj.HjHandlerProcessor;
//import com.zdf.webspider.processor.mj.HandleProcessor;
//
//
//@Controller
//@RequestMapping(value = "/spider")
//public class SpiderController {
//	private final Logger             logger = Logger.getLogger(SpiderController.class);
//
//	@Autowired
//	private DhHandlerProcessor dhHandlerProcessor;
//
//	@Autowired
//	private GjHandlerProcessor gjHandlerProcessor;
//
//	@Autowired
//	private HjHandlerProcessor hjHandlerProcessor;
//
//	@Autowired
//	private HandleProcessor mjHandleProcessor;
//
//
//	// ============================== 动画  ==============================
//	@RequestMapping(value = "/donghua")
//	@ResponseBody
//    public String donghua(Model model) throws Exception {
//		logger.info("donghua M77DS_NEW" + " Spider begin !!!!!!!!!!!!!!!!!!!!!!!!!");
//		dhHandlerProcessor.exe(MjWebsiteConstant.M77DS_NEW);
//		logger.info("donghua M77DS_NEW" + " Spider end !!!!!!!!!!!!!!!!!!!!!!!!!");
//
//		logger.info("donghua KKKK" + " Spider begin !!!!!!!!!!!!!!!!!!!!!!!!!");
//		dhHandlerProcessor.exe(MjWebsiteConstant.KKKK);
//		logger.info("donghua KKKK" + " Spider end !!!!!!!!!!!!!!!!!!!!!!!!!");
//        return "success";
//    }
//
//	// ============================== 国剧  ==============================
//	@RequestMapping(value = "/guoju")
//	@ResponseBody
//    public String guoju(Model model) throws Exception {
//		logger.info("guoju M77DS" + " Spider begin !!!!!!!!!!!!!!!!!!!!!!!!!");
//		gjHandlerProcessor.exe(MjWebsiteConstant.M77DS);
//		logger.info("guoju M77DS" + " Spider end !!!!!!!!!!!!!!!!!!!!!!!!!");
//
//		logger.info("guoju M77DS_NEW" + " Spider begin !!!!!!!!!!!!!!!!!!!!!!!!!");
//		gjHandlerProcessor.exe(MjWebsiteConstant.M77DS_NEW);
//		logger.info("guoju M77DS_NEW" + " Spider end !!!!!!!!!!!!!!!!!!!!!!!!!");
//        return "success";
//    }
//
//	// ============================== 韩剧  ==============================
//	@RequestMapping(value = "/hanju")
//	@ResponseBody
//    public String hanju(Model model) throws Exception {
//		logger.info("hanju M77DS" + " Spider begin !!!!!!!!!!!!!!!!!!!!!!!!!");
//		hjHandlerProcessor.exe(MjWebsiteConstant.M77DS);
//		logger.info("hanju M77DS" + " Spider end !!!!!!!!!!!!!!!!!!!!!!!!!");
//
//		logger.info("hanju M77DS_NEW" + " Spider begin !!!!!!!!!!!!!!!!!!!!!!!!!");
//		hjHandlerProcessor.exe(MjWebsiteConstant.M77DS_NEW);
//		logger.info("hanju M77DS_NEW" + " Spider end !!!!!!!!!!!!!!!!!!!!!!!!!");
//        return "success";
//    }
//
//
//
//
//
//	// ============================== 美剧 都单独调用 ==============================
//
//	@RequestMapping(value = "/meijuLinks")
//	@ResponseBody
//    public String meijuLinks(Model model) throws Exception {
//		logger.info("meijuLinks 163" + " Spider begin !!!!!!!!!!!!!!!!!!!!!!!!!");
//		mjHandleProcessor.exe(MjWebsiteConstant.CN163);
//		logger.info("meijuLinks 163" + " Spider end !!!!!!!!!!!!!!!!!!!!!!!!!");
//        return "success";
//    }
//
//	@RequestMapping(value = "/meijuVideo1")
//	@ResponseBody
//    public String meijuVideo1(Model model) throws Exception {
//		logger.info("meijuVideo1 M77DS_NEW" + " Spider begin !!!!!!!!!!!!!!!!!!!!!!!!!");
//		mjHandleProcessor.exe(MjWebsiteConstant.M77DS_NEW);
//		logger.info("meijuVideo1 M77DS_NEW" + " Spider end !!!!!!!!!!!!!!!!!!!!!!!!!");
//        return "success";
//    }
//
//	@RequestMapping(value = "/meijuVideo2")
//	@ResponseBody
//	public String meijuVideo2(Model model) throws Exception {
//		logger.info("meijuVideo2 M77DS" + " Spider begin !!!!!!!!!!!!!!!!!!!!!!!!!");
//		mjHandleProcessor.exe(MjWebsiteConstant.M77DS);
//		logger.info("meijuVideo2 M77DS" + " Spider end !!!!!!!!!!!!!!!!!!!!!!!!!");
//		return "success";
//	}
//
//	@RequestMapping(value = "/meijuVideo3")
//	@ResponseBody
//	public String meijuVideo3(Model model) throws Exception {
//		logger.info("meijuVideo3 MEIJUHUI" + " Spider begin !!!!!!!!!!!!!!!!!!!!!!!!!");
//		mjHandleProcessor.exe(MjWebsiteConstant.MEIJUHUI);
//		logger.info("meijuVideo3 MEIJUHUI" + " Spider end !!!!!!!!!!!!!!!!!!!!!!!!!");
//		return "success";
//	}
//
//
//	@RequestMapping(value = "/newk")
//	@ResponseBody
//	public String newk(Model model) throws Exception {
//		logger.info("KKKK " + " Spider begin !!!!!!!!!!!!!!!!!!!!!!!!!");
//		mjHandleProcessor.exe(MjWebsiteConstant.KKKK);
//		logger.info("KKKK " + " Spider end !!!!!!!!!!!!!!!!!!!!!!!!!");
//		return "success";
//	}
//}
