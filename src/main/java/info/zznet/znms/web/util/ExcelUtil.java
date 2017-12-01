package info.zznet.znms.web.util;
import info.zznet.znms.base.util.DateUtil;
import info.zznet.znms.base.util.helper.UserExportHelper;
import info.zznet.znms.web.annotation.PropKey;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;

/**
 * excel操作工具类
 * @author yuanjingtao
 * @param <T> 范型
 */
@SuppressWarnings("deprecation")
public class ExcelUtil<T> {

	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 *
	 * @param title
	 *            表格标题名
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param response
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 */
	public void exportExcel(String title, Collection<T> dataset, HttpServletResponse response,HttpServletRequest request) {
		exportExcel(title,null,dataset,response,request);
	}

	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 * 
	 * @param title
	 *            表格标题名
	 * @param ignoreProperties 忽略的属性
	 *
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param response
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 */
	@SuppressWarnings({"unused", "rawtypes", "unchecked" })
	public void exportExcel(String title, String[] ignoreProperties,Collection<T> dataset, HttpServletResponse response,HttpServletRequest request) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);

		//生成标题的样式
		HSSFCellStyle styleTitle = workbook.createCellStyle();
		// 设置这些样式
		styleTitle.setFillForegroundColor(HSSFColor.WHITE.index);
		styleTitle.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
		styleTitle.setFillPattern(HSSFCellStyle.NO_FILL);
		styleTitle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleTitle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleTitle.setBorderRight(HSSFCellStyle.BORDER_THICK);
		styleTitle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		// 生成一个字体
		HSSFFont fontTitle = workbook.createFont();
		fontTitle.setColor(HSSFColor.BLUE.index);
		fontTitle.setFontHeightInPoints((short) 24);
		fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontTitle.setFontName("华文楷体");
		// 把字体应用到当前的样式
		styleTitle.setFont(fontTitle);


		// 生成一个样式   字段栏用
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 14);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("仿宋");
		// 把字体应用到当前的样式
		style.setFont(font);


		// 生成并设置另一个样式 正文栏使用 正文样式1
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.WHITE.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font2.setFontName("华文楷体");
		// 把字体应用到当前的样式
		style2.setFont(font2);


		// 生成并设置另一个样式 正文栏使用 正文样式2
		HSSFCellStyle style3 = workbook.createCellStyle();
		style3.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font4 = workbook.createFont();
		font4.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font4.setFontName("华文楷体");
		// 把字体应用到当前的样式
		style3.setFont(font4);


		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();


		HSSFRow headRow = sheet.createRow(1);
		HSSFRow bodyRow;
		// 遍历集合数据，产生数据行
		Iterator it = dataset.iterator();
		int index = 1;//起始行号
		//文字的样式
		HSSFFont font3 = workbook.createFont();
		int styleTag = 0;
		while (it.hasNext()) {
			index++;
			bodyRow = sheet.createRow(index);
			Object t = it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();
			Method[] methods = t.getClass().getDeclaredMethods();


			int j = 0;
			for (short i = 0; i < fields.length; i++) {
				Field field = fields[i];
				field.getAnnotation(PropKey.class);
				String fieldName = field.getName();
				String getMethodName = "";
				if(field.getType()==boolean.class){
					getMethodName = "is"+ fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);
					boolean isExist = false;
					for(Method method : methods){
						if(method.getName().equals(getMethodName)){
							isExist = true;
							continue;
						}
					}
					if(!isExist){
						getMethodName = "get"+ fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);
					}
				}else{
					getMethodName = "get"+ fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);
				}
				// 国际化
				String fieldNameKey;
				if(null == field.getAnnotation((PropKey.class))){
					continue;
				} else {
					fieldNameKey = field.getAnnotation(PropKey.class).value();					
				}
				
				try {
					Class tCls = t.getClass();
					Method getMethod =null;
					getMethod = tCls.getMethod(getMethodName,
								new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					if (value instanceof byte[]) {
						// 有图片时，设置行高为60px;
						bodyRow.setHeightInPoints(60);
						// 设置图片所在列宽度为80px,注意这里单位的一个换算
						sheet.setColumnWidth(i, (short) (35.7 * 80));
						// sheet.autoSizeColumn(i);
						byte[] bsValue = (byte[]) value;
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
								1023, 255, (short) 6, index, (short) 6, index);
						anchor.setAnchorType(2);
						patriarch.createPicture(anchor, workbook.addPicture(
								bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
					} else if(value instanceof Collection){
						Collection c = (Collection)value;
						StringBuffer sb = new StringBuffer(200);
						if(value != null && !c.isEmpty()){
							for(Object o:c){
								sb.append(o.toString());
								sb.append(",");
							}
							sb.deleteCharAt(sb.length() - 1);
						}
						textValue = sb.toString();
					}else if(value instanceof Date){
						Date date =(Date) value;
						if (null!=date){
							textValue = DateUtil.dateToStr(date,DateUtil.DF_yyyyMMddHHmmss);
						}else {
							textValue = "";
						}

					}else if(value instanceof Boolean){
						//boolean返回是和否
						Boolean boo =(Boolean) value;
						String convertValue = UserExportHelper.getConvertValue(t, fieldName, value);
						if(StringUtils.isNotEmpty(convertValue)){
							textValue=convertValue;
						}else{
							textValue = boo ?MessageUtil.getMessage("ischecked"):MessageUtil.getMessage("isnotchecked");
						}
					}
					else {
						// 其它数据类型都当作字符串简单处理
						String convertValue = UserExportHelper.getConvertValue(t, fieldName, value);
						if(StringUtils.isNotEmpty(convertValue)){
							textValue=convertValue;
						}else{
							textValue = value == null ? "":value.toString();
						}
					}

					HSSFCell headcell = headRow.createCell(j);
					headcell.setCellStyle(style);
					HSSFRichTextString text = new HSSFRichTextString(
							MessageUtil.getMessage(fieldNameKey));
					headcell.setCellValue(text);

					HSSFCell cell = bodyRow.createCell(j);
					if (styleTag % 2 == 0) {
						cell.setCellStyle(style2);
					} else {
						cell.setCellStyle(style3);
					}

					j++;
					if (i == fields.length - 1) {//多增加一行 处理有数字显示不对的情况
						HSSFCell emptyCell = bodyRow.createCell(j);
						emptyCell.setCellValue(" ");
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							HSSFRichTextString richString = new HSSFRichTextString(
									textValue);

							font3.setColor(HSSFColor.BLACK.index);
							richString.applyFont(font3);
							cell.setCellValue(richString);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} finally {
					// 清理资源
				}
			}
			styleTag++;

		}
		//标题栏宽度自动扩充
		int colMaxIndex = headRow.getLastCellNum();
		for (int colIndex = 0; colIndex < colMaxIndex; colIndex++) {
			HSSFCell cell = headRow.getCell(colIndex);
			String value = cell.getStringCellValue();
			if (isChineseChar(value)) {
				sheet.setColumnWidth(colIndex, Math.max(value.getBytes().length * 2 * 256, 15 * 256));
			} else {
				sheet.setColumnWidth(colIndex, Math.max(value.getBytes().length * 256, 15 * 256));
			}
		}
		HSSFRow titleRow = sheet.createRow(0);
		HSSFCell titleCell = titleRow.createCell(0);
		titleCell.setCellValue(title);
		titleCell.setCellStyle(styleTitle);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, Math.max(colMaxIndex - 1, 0)));
		//特殊处理：处理没有数据，只有title的情况
		if (colMaxIndex <= 1) {
			sheet.setColumnWidth(0, Math.max(title.getBytes().length * 3 * 256, 15 * 256));
		}

		try {
			String dateSuffix = DateUtil.dateToStr(new Date(), DateUtil.DF_yyyyMMdd);
			String fileName = encodingFileName(request,response,title+"-"+dateSuffix+".xls");

			response.addHeader("Content-Disposition", "attachment;filename="+fileName);
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			OutputStream os = response.getOutputStream();
			workbook.write(os);
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否是中文
	 *
	 * @param str
	 * @return
	 */
	public static boolean isChineseChar(String str) {
		boolean temp = false;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			temp = true;
		}
		return temp;
	}
	
	public static String encodingFileName (HttpServletRequest request, HttpServletResponse response, String fileName) {
		final String userAgent = request.getHeader("USER-AGENT");
		String encodingFileName = fileName;
		try {

			if (StringUtils.contains(userAgent, "MSIE")) {//IE浏览器
				encodingFileName = URLEncoder.encode(fileName, "UTF8");
			} else if (StringUtils.contains(userAgent, "Mozilla")) {//google,火狐浏览器
				encodingFileName = new String(fileName.getBytes(), "ISO8859-1");
			} else {
				encodingFileName = URLEncoder.encode(fileName, "UTF8");//其他浏览器
			}

		} catch (UnsupportedEncodingException e) {
		}
		return encodingFileName;
	}
	
	public static String getValue(Cell cell) {
		if(cell == null) {
			return "";
		}
		if(cell.getCellType() != Cell.CELL_TYPE_STRING) {
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}
		return cell.getStringCellValue();
	}
}
