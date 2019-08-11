package com.example.legendary.common.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * poi的Excel工具类
 * @Author：吴嘉晟
 * @CreateTime：2017年12月26日 上午10:49:32
 * @Modifier：吴嘉晟
 * @UpdateTime：2017年12月26日 上午10:49:32
 * @Version：1.0
 * @Remark：目前只支持读取Excel
 *
 */
public class PoiUtil {
	
	private PoiUtil() {
	}
	
	/**
	 * 读取excel(xls)
	 * @param file 文件
	 * @return List<Object>
	 */
	public static ArrayList readOldExcel(File file) {
		ArrayList objList = new ArrayList();
		
		InputStream is = null;
		HSSFWorkbook hssfWorkbook;
		try {
			is = new FileInputStream(file);
			hssfWorkbook  = new HSSFWorkbook(is);
			// 获取表单数
			for (int numSheet = 0; numSheet < hssfWorkbook .getNumberOfSheets(); numSheet++) {
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
	             if (hssfSheet == null) {
	                  continue;
	              }
	             // 获取行数
	              for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
	            	  HSSFRow hssfRow = hssfSheet.getRow(rowNum);
	                  if (hssfRow != null) {
	                	  List<String> obj = new ArrayList<String>();
	                	  // 获取这行列数
	                	  for(int i = 0; i < hssfRow.getLastCellNum(); i++) {
	                		  String xscell = hssfRow.getCell(i).toString();
	                		  obj.add(xscell);
	                	  }
						  objList.add(obj);
					  }
	              }
	          }
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				Objects.requireNonNull(is).close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return objList;
	}
	
	/**
	 * 读取Excel(xlsx)
	 * @param file 文件
	 * @return List<Object>
	 */
	public static ArrayList readNewExcel(File file) {
		ArrayList objList = new ArrayList<>();
		
		InputStream is = null;
		XSSFWorkbook xssfWorkbook;
		try {
			is = new FileInputStream(file);
			xssfWorkbook  = new XSSFWorkbook(is);
			// 获取表单数
			for (int numSheet = 0; numSheet < xssfWorkbook .getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
	             if (xssfSheet == null) {
	                  continue;
	              }
	              // 获取行数
	              for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
	            	  XSSFRow xssfRow = xssfSheet.getRow(rowNum);
	                  if (xssfRow != null) {
	                	  List<Object> obj = new ArrayList<>();
	                	  // 获取这行的列数
	                	  for(int i = 0; i < xssfRow.getLastCellNum(); i++) {
	                		  XSSFCell xscell = xssfRow.getCell(i);
	                		  if(xscell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
								  BigDecimal bd = new BigDecimal(xscell.getNumericCellValue());
	                		  	obj.add(bd.toString());
							  }else{
								  obj.add(xscell);
							  }
	                	  }
	                	  objList.add(obj);
	                  }
	              }
	          }
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				Objects.requireNonNull(is).close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return objList;
	}
}
