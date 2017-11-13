//package com.zdf.webspider.processor.donghua;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.zdf.util.JuTypeUtil.JuType;
//import com.zdf.util.MjWebsiteConstant;
//import com.zdf.webspider.processor.KkkkbaProcessor;
//import com.zdf.webspider.processor.gj.GjM77dsNewProcessor;
//
//@Component
//public class DhHandlerProcessor {
//	@Autowired
//	private GjM77dsNewProcessor m77dsNewProcessor;
//
//	@Autowired
//	private KkkkbaProcessor kkkkbaProcessor;
//
//
//	public void exe(String type){
//		switch (type){
//			case MjWebsiteConstant.M77DS_NEW:m77dsNewProcessor.handleLinks(m77dsNewProcessor,JuType.DONGHUA.getCode()); break;
//			case MjWebsiteConstant.KKKK:kkkkbaProcessor.handleLinks(kkkkbaProcessor,JuType.DONGHUA.getCode()); break;
//		}
//	}
//
//
//
//}
