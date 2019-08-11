package com.example.legendary.common.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Bigdecimal工具类(用于得到生活计数法)
 * 使用规则---使用BigDecimal对象进行算术运算需要将数字转化为字符串
 * @author 吴嘉晟
 */
public class BigdecimalUtil {

	private BigdecimalUtil(){
	}

	/**
	 * 加
	 * @param d1 加数
	 * @param d2 被加数
	 * @return 和
	 */
	public static BigDecimal add(double d1,double d2){
		BigDecimal big1 = new BigDecimal(Double.toString(d1));
		BigDecimal big2 = new BigDecimal(Double.toString(d2));
		return big1.add(big2);
	}

	/**
	 * 减
	 * @param d1 减数
	 * @param d2 被减数
	 * @return 差
	 */
	public static BigDecimal sub(double d1,double d2){
		BigDecimal big1 = new BigDecimal(Double.toString(d1));
		BigDecimal big2 = new BigDecimal(Double.toString(d2));
		return big1.subtract(big2);
	}

	/**
	 * 乘
	 * @param d1 乘数
	 * @param d2 被乘数
	 * @return 积
	 */
	public static BigDecimal mul(double d1,double d2){
		BigDecimal big1 = new BigDecimal(Double.toString(d1));
		BigDecimal big2 = new BigDecimal(Double.toString(d2));
		return big1.multiply(big2);
	}

	/**
	 * 除
	 * @param d1 除数
	 * @param d2 被除数
	 * @return 商
	 */
	public static BigDecimal div(double d1,double d2){
		//MathContext对象会将一个数保留几位小数后一位将进行四舍五入
		MathContext mc = new MathContext(2, RoundingMode.HALF_DOWN);
		BigDecimal big1 = new BigDecimal(Double.toString(d1));
		BigDecimal big2 = new BigDecimal(Double.toString(d2));
		//四舍五入保留两位小数
		return big1.divide(big2, mc);
	}

}