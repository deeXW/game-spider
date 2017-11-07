package com.zdf.webspider.processor.gj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zdf.util.MjWebsiteConstant;
import com.zdf.util.JuTypeUtil.JuType;
import com.zdf.webspider.processor.KkkkbaProcessor;

@Component
public class GjHandlerProcessor {
	@Autowired
	private GjM77dsProcessor gjM77dsProcessor;
	@Autowired
	private GjM77dsNewProcessor gjM77dsNewProcessor;
	@Autowired
	private KkkkbaProcessor kkkkbaProcessor;
	
	public void exe(String type){
		switch (type){
			case MjWebsiteConstant.M77DS:gjM77dsProcessor.handleLinks(gjM77dsProcessor); break;
			case MjWebsiteConstant.M77DS_NEW:gjM77dsNewProcessor.handleLinks(gjM77dsNewProcessor,JuType.GUOJU.getCode()); break;
			case MjWebsiteConstant.KKKK:kkkkbaProcessor.handleLinks(kkkkbaProcessor,JuType.GUOJU.getCode()); break;
		}
	}
	


}
