package com.example.legendary.common.utils;

/** 
* @ClassName: BusinessException 
* @Description: 自定义异常处理
* @author 吴嘉晟
* @date 2018年11月8日
*  
*/
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BusinessException() {}
	
	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(Throwable cause) {
		super(cause);
	}
	
	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}
}
