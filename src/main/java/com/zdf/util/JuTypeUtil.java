package com.zdf.util;


/**
 * @author zhongdf
 *
 */
public class JuTypeUtil {
	// 1. 定义枚举类型
	public enum JuType {
		// 利用构造函数传参
		MEIJU("美剧","meiju"),
		HANJU("韩剧","hanju"),
		DIANYING("电影","dianying"),
		DONGHUA("动画","donghua"),
		GUOJU("国剧","guoju");
		
		// 定义私有变量
		private String code;
		private String name;
		// 构造函数，枚举类型只能为私有
		private JuType(String name, String code) {
			this.name = name;
			this.code = code;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}

	}
	public static String getJuNameByCode(String code) {
		for (JuType c : JuType.values()) {
			if (c.getCode().equals(code)) {
				return c.name;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(JuTypeUtil.getJuNameByCode("1"));
	}
}
