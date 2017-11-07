package com.zdf.webspider.processor.hj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zdf.util.MjWebsiteConstant;
import com.zdf.util.JuTypeUtil.JuType;
import com.zdf.webspider.processor.gj.GjM77dsNewProcessor;

@Component
public class HjHandlerProcessor {
	@Autowired
	private HjM77dsProcessor hjM77dsProcessor;
	@Autowired
	private GjM77dsNewProcessor m77dsNewProcessor;
	
	public void exe(String type){
		switch (type){
			case MjWebsiteConstant.M77DS:hjM77dsProcessor.handleLinks(hjM77dsProcessor); break;
			case MjWebsiteConstant.M77DS_NEW:m77dsNewProcessor.handleLinks(m77dsNewProcessor,JuType.HANJU.getCode()); break;
		}
	}
	


}
