package com.zdf.util;


public class MjWebsiteConstant {
	public static final String CN163 = "cn163";
	
	public static final String MEIJUHUI = "meijuhui";
	
	public static final String ZIMUZU = "zimuzu";
	
	public static final String MEIJUTT = "meijutt";
	
	public static final String M77DS = "m77ds";
	
	public static final String M77DS_NEW = "m77ds_new";
	
	public static final String J941K = "941k";

	public static final String KKKK = "kkkk";
	
	
	public static final String M77DS_VIDEO_DOMAIN = "http://go.niubaitu.com/sj.php";
	
	
	
	
	
	
	
	
	
	public enum WebsiteM77ds {
		// 利用构造函数传参
		M77DS_YOUKU("https://api.niubaitu.com/youkuvip.php","https://api.niubaitu.com/url.php",
				"'优酷',\\[(.+?)\\]\\],","'优酷',\\[(.+?)\\]\\]\\],","youku"),
		M77DS_YKYUN("https://api.niubaitu.com/acfun.php","https://api.niubaitu.com/url.php",
				"'云视频',\\[(.+?)\\]\\],","'云视频',\\[(.+?)\\]\\]\\],","ykyun"),
		M77DS_IQIYI("http://vip.niubaitu.com:778/iqiyi.php","http://vip.niubaitu.com:778/url.php",
				"'爱奇艺',\\[(.+?)\\]\\],","'爱奇艺',\\[(.+?)\\]\\]\\],","qiyi"),
		M77DS_SOHU("http://vip.niubaitu.com:778/sohu.php","http://vip.niubaitu.com:778/url.php",
				"'搜狐',\\[(.+?)\\]\\],","'搜狐',\\[(.+?)\\]\\]\\],","sohu"),
		M77DS_LETV("http://vip.niubaitu.com:778/letv.php","http://vip.niubaitu.com:778/url.php",
				"'乐视',\\[(.+?)\\]\\],","'乐视',\\[(.+?)\\]\\]\\],","letv"),
        M77DS_MGTV("http://vip.niubaitu.com:778/mg.php","http://vip.niubaitu.com:778/url.php",
        		"'芒果TV',\\[(.+?)\\]\\],","'芒果TV',\\[(.+?)\\]\\]\\],","hunantv"),
		M77DS_QQLIVE("http://vip.niubaitu.com:778/qqvip.php","http://vip.niubaitu.com:778/url.php",
				"'腾讯视频',\\[(.+?)\\]\\],","'腾讯视频',\\[(.+?)\\]\\]\\],","qqlive");
		
		// 定义私有变量
		private String firstUrl;
		private String secondUrl;
		private String regular1;
		private String regular2;
		private String code;
		// 构造函数，枚举类型只能为私有
		private WebsiteM77ds(String firstUrl, String secondUrl, 
				String regular1, String regular2, String code) {
			this.firstUrl = firstUrl;
			this.secondUrl = secondUrl;
			this.regular1 = regular1;
			this.regular2 = regular2;
			this.code = code;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getFirstUrl() {
			return firstUrl;
		}
		public void setFirstUrl(String firstUrl) {
			this.firstUrl = firstUrl;
		}
		public String getSecondUrl() {
			return secondUrl;
		}
		public void setSecondUrl(String secondUrl) {
			this.secondUrl = secondUrl;
		}
		public String getRegular1() {
			return regular1;
		}
		public void setRegular1(String regular1) {
			this.regular1 = regular1;
		}
		public String getRegular2() {
			return regular2;
		}
		public void setRegular2(String regular2) {
			this.regular2 = regular2;
		}
		

	}
	public static String get77dsFirstUrlByCode(String code) {
		for (WebsiteM77ds c : WebsiteM77ds.values()) {
			if (c.getCode().equals(code)) {
				return c.firstUrl;
			}
		}
		return null;
	}
	public static String get77dsSecondUrlByCode(String code) {
		for (WebsiteM77ds c : WebsiteM77ds.values()) {
			if (c.getCode().equals(code)) {
				return c.secondUrl;
			}
		}
		return null;
	}
	public static String get77dsRegular1ByCode(String code) {
		for (WebsiteM77ds c : WebsiteM77ds.values()) {
			if (c.getCode().equals(code)) {
				return c.regular1;
			}
		}
		return null;
	}
	public static String get77dsRegular2ByCode(String code) {
		for (WebsiteM77ds c : WebsiteM77ds.values()) {
			if (c.getCode().equals(code)) {
				return c.regular2;
			}
		}
		return null;
	}
	
	
	
	
	public enum Website4kba {
		// 利用构造函数传参
		KKKK_YOUYUN("&?Next","4k_3_5","youyun"),
		KKKK_YOUKU("&m3u8","4k_3_5","youku"),
		KKKK_LEV("&m3u8","4k_3_5","lev"),
		KKKK_ACFUN("&m3u8","4k_3_5","acfun"),
		KKKK_PYUN("&?Next","4k_3_5","pyun"),
		KKKK_QIYI("&?Next","4k_3_5","qiyi"),
		KKKK_IQY("&?Next","4k_3_5","iqy"),
		KKKK_QQ("&?Next","4k_3_5","qq");
		
		// 定义私有变量
		private String substring;
		private String type;
		private String code;
		// 构造函数，枚举类型只能为私有
		private Website4kba(String substring,String type,String code) {
			this.substring = substring;
			this.type = type;
			this.code = code;
		}
		
		public String getSubstring() {
			return substring;
		}

		public void setSubstring(String substring) {
			this.substring = substring;
		}

		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}

	}
	public static String get4kbaTypeByCode(String code) {
		for (Website4kba c : Website4kba.values()) {
			if (c.getCode().equals(code)) {
				return c.type;
			}
		}
		return null;
	}
	public static String get4kbaSubByCode(String code) {
		for (Website4kba c : Website4kba.values()) {
			if (c.getCode().equals(code)) {
				return c.substring;
			}
		}
		return null;
	}
	public static void main(String[] args) {
		System.out.println(MjWebsiteConstant.get4kbaSubByCode("youyun"));
	}
}
