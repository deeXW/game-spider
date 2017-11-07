package com.zdf.webspider.processor.mj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zdf.util.MjWebsiteConstant;
import com.zdf.util.JuTypeUtil.JuType;
import com.zdf.webspider.processor.KkkkbaProcessor;
import com.zdf.webspider.processor.gj.GjM77dsNewProcessor;

@Component
public class HandleProcessor {
	@Autowired
	private Cn163Processor cn163Processor;
	
	@Autowired
	private MeijuHuiProcessor meijuHuiProcessor;
	
	@Autowired
	private ZimuzuProcessor zimuzuProcessor;
	
	@Autowired
	private MeijuttProcessor meijuttProcessor;
	
	@Autowired
	private M77dsProcessor m77dsProcessor;
	
	@Autowired
	private Mj941kProcessor mj941kProcessor;
	
	@Autowired
	private GjM77dsNewProcessor m77dsNewProcessor;
	
	@Autowired
	private KkkkbaProcessor kkkkbaProcessor;
	
	public void exe(String type){
		switch (type){
			case MjWebsiteConstant.CN163:cn163Processor.handleLinks(cn163Processor); break;
			case MjWebsiteConstant.MEIJUHUI:meijuHuiProcessor.handleLinks(meijuHuiProcessor); break;
			case MjWebsiteConstant.ZIMUZU:zimuzuProcessor.handleLinks(zimuzuProcessor); break;
			case MjWebsiteConstant.MEIJUTT:meijuttProcessor.handleLinks(meijuttProcessor); break;
			case MjWebsiteConstant.M77DS:m77dsProcessor.handleLinks(m77dsProcessor); break;
			case MjWebsiteConstant.J941K:mj941kProcessor.handleLinks(mj941kProcessor); break;
			case MjWebsiteConstant.M77DS_NEW:m77dsNewProcessor.handleLinks(m77dsNewProcessor,JuType.MEIJU.getCode()); break;
			case MjWebsiteConstant.KKKK:kkkkbaProcessor.handleLinks(kkkkbaProcessor,JuType.MEIJU.getCode()); break;
		}
		
		
	}
	
}
