package info.zznet.znms.base.rrd;

import info.zznet.znms.web.util.ExcelUtil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jrobin.core.RrdException;



public class Test {
	public static void main(String...strings) throws IOException, RrdException{
//		new Thread(){
//			public void run(){
//				try {
//					RrdDb rrdDb = new RrdDb("D:\\RMU_782aec2a9cd72baa3260c1c805489bee.rrd", false);
//					while(true){
//						Sample sample = rrdDb.createSample();
//						long now = System.currentTimeMillis()/1000;
//						sample.setTime(now);
//						sample.setValues(2d);
//						sample.update();	
//						try {
//							Thread.sleep(50000);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}.start();
//System.out.println(StringUtils.substringBetween("<78>Oct 17 14:45:01 z-log CROND[30848]: (root) CMD (/usr/sbin/ntpdate 121.43.182.79 &> /dev/null)", "<", ">"));

//		new Thread(){
//			public void run(){
////				while(true){
//					try {
//						RrdDb rrdDb = new RrdDb("D:\\netStream_5397d603fe42927e01f52ae14a7285d8.rrd", true);
//						FetchRequest request = rrdDb.createFetchRequest(ConsolFun.AVERAGE,
//								System.currentTimeMillis()/1000-350,
//								System.currentTimeMillis()/1000);
//						FetchData fetchData = request.fetchData();
////						System.out.println(fetchData.dump());
//						System.out.println(rrdDb.dump());
//						Thread.sleep(1000);
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
////				}
//				
//			}
//		}.start();
//		
		
		XSSFWorkbook xssfWorkbook = null;
		try {
			xssfWorkbook = new XSSFWorkbook(new FileInputStream("D://主机导入模板xx.xlsx"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
		// 循环行Row
        for (int rowNum = 2; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
            XSSFRow hssfRow = xssfSheet.getRow(rowNum);
            if (hssfRow == null) {
                continue;
            }
            Iterator<Cell> itr = hssfRow.cellIterator();
            while(itr.hasNext()){
            	getValue(itr.next());
            }
            String hostName = ExcelUtil.getValue(hssfRow.getCell(0));
            String hostIp = ExcelUtil.getValue(hssfRow.getCell(1));
            String activate = ExcelUtil.getValue(hssfRow.getCell(2));
            String availabilityMethod = ExcelUtil.getValue(hssfRow.getCell(3));
            String snmpVersion = ExcelUtil.getValue(hssfRow.getCell(4));
            String snmpCommunity = ExcelUtil.getValue(hssfRow.getCell(5));
            String snmpUserName = ExcelUtil.getValue(hssfRow.getCell(6));
            String snmpPassword = ExcelUtil.getValue(hssfRow.getCell(7));
            String snmpAuthProtocol = ExcelUtil.getValue(hssfRow.getCell(8));
            String snmpPrivPassphrase = ExcelUtil.getValue(hssfRow.getCell(9));
            String snmpPrivProtocol = ExcelUtil.getValue(hssfRow.getCell(10));
            String snmpContext = ExcelUtil.getValue(hssfRow.getCell(11));
            String notes = ExcelUtil.getValue(hssfRow.getCell(12));
            hssfRow.createCell(13, Cell.CELL_TYPE_STRING).setCellValue("xsx");
            
        }
        
     // 创建文件输出流，准备输出电子表格
        OutputStream out = new FileOutputStream("D://主机导入模板xx.xlsx");
        xssfWorkbook.write(out);
        out.close();
	}
	
	 @SuppressWarnings("static-access")
    private static void getValue(Cell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
        	hssfCell.setCellType(Cell.CELL_TYPE_STRING);
            // 返回布尔类型的值
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
        	hssfCell.setCellType(Cell.CELL_TYPE_STRING);
        }
    }
	 
	 public static void addValue(String x, int col) {
		 
	 }
}
