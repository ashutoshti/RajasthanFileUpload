package Utilties;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelApi 
{
	public static FileInputStream fis=null;
	public static FileOutputStream fos=null;
	public static XSSFWorkbook workbook=null;
	public static XSSFSheet worksheet=null;
	public static XSSFRow row=null;
	public static XSSFCell cell=null; //Column No
	static File excelfilepath;
	XSSFFont font=null;
	XSSFCellStyle style=null;
	
	public ExcelApi(File excelfilepath) throws Exception
	{
		ExcelApi.excelfilepath =excelfilepath;
		fis=new FileInputStream(excelfilepath);
		workbook =new XSSFWorkbook(fis);
		fis.close();
	}
	
	public String getCellData(String sheetName, int colNum, int rowNum)
	{
		try 
		{
		worksheet=workbook.getSheet(sheetName);
		row=worksheet.getRow(rowNum);
		cell=row.getCell(colNum);
				if (cell.getCellTypeEnum()==CellType.STRING) 
				{
					return cell.getStringCellValue();
				} 
				else if(cell.getCellTypeEnum()==CellType.NUMERIC||cell.getCellTypeEnum()==CellType.FORMULA)
				{
					String cellValue=String.valueOf(cell.getNumericCellValue());
					if(HSSFDateUtil.isCellDateFormatted(cell))
					{
						DateFormat dt=new SimpleDateFormat("dd/MM/yyy");
						Date date=cell.getDateCellValue();
						cellValue=dt.format(date);
					}
					return cellValue;
				}
				else if (cell.getCellTypeEnum()==CellType.BLANK) 
				return "";
				
				else
					return String.valueOf(cell.getBooleanCellValue());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "Data Not Found";
		}
	}

	public String getCellData(String sheetName, String colName, int rowNum)
	{
		try 
		{
			int colNum=-1;
			worksheet=workbook.getSheet(sheetName);
			row=worksheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) 
			{
				if (row.getCell(i).getStringCellValue().trim().equals(colName)) 
				colNum=i;
			}
			
			row=worksheet.getRow(rowNum-1);
			cell=row.getCell(colNum);

			if (cell.getCellTypeEnum()==CellType.STRING) 
			{
				return cell.getStringCellValue();
			} 
			else if(cell.getCellTypeEnum()==CellType.NUMERIC||cell.getCellTypeEnum()==CellType.FORMULA)
			{
				String cellValue=String.valueOf(cell.getNumericCellValue());
				if(HSSFDateUtil.isCellDateFormatted(cell))
				{
					DateFormat dt=new SimpleDateFormat("dd/MM/yyy");
					Date date=cell.getDateCellValue();
					cellValue=dt.format(date);
				}
				return cellValue;
			}
			else if (cell.getCellTypeEnum()==CellType.BLANK) 
			return "";
			
			else
				return String.valueOf(cell.getBooleanCellValue());
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Data Not Found");
		}
		return null;
	}
	
	public boolean setCellData(String sheetName, int colNum, int rowNum, String value)
	{
		try 
		{
			worksheet=workbook.getSheet(sheetName);
			row=worksheet.getRow(rowNum);
			if(row==null)
				row=worksheet.createRow(rowNum);
			
			cell=row.getCell(colNum);
			
			if(cell==null)
				cell=row.createCell(colNum);
			cell.setCellValue(value);

			fos=new FileOutputStream(excelfilepath);
			workbook.write(fos);
			fos.close();
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Data Not Found");
			return false;
		}
	
		
	}

	public boolean setCellData(String sheetName, String colName, int rowNum, String value)
	{
		try 
		{
			int colNum=-1;
			worksheet=workbook.getSheet(sheetName);
			row=worksheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) 
			{
				if(row.getCell(i).getStringCellValue().trim().equals(colName))
				{
					colNum=i;
				}
			}
				row=worksheet.getRow(rowNum-1);

				if(row==null)
					row=worksheet.createRow(rowNum-1);

				cell=row.createCell(colNum);
					
				if(cell==null)
					cell=row.createCell(colNum);
				cell.setCellValue(value);
				
				fos=new FileOutputStream(excelfilepath);
				workbook.write(fos);
				fos.close();
				
			
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Data Not Found");
			return false;
		}
	
		
	}

	public int getRowCount(String sheetName)
	{
		worksheet=workbook.getSheet(sheetName);
				int rowCount=worksheet.getLastRowNum()+1;
		return rowCount;
	}

	public int getColumnCount(String sheetName)
	{
		worksheet=workbook.getSheet(sheetName);
		row=worksheet.getRow(0);
		int columnCount=row.getLastCellNum();
		return columnCount;
	}

	public int getlastrowcount(String SheetName, int rownumber)
	{
		worksheet=workbook.getSheet(SheetName);
		int rowlastcount=worksheet.getRow(rownumber).getLastCellNum();
		return rowlastcount;
	}

	public static String[][] ReadExcel(String testdata_path,String sheet) throws Exception{
		
		excelfilepath = new File(testdata_path);
		fis = new FileInputStream(excelfilepath);
		workbook = new XSSFWorkbook(fis);
		worksheet = workbook.getSheet(sheet);
		
		int rowcount = worksheet.getLastRowNum()+1;
		int colcount = worksheet.getRow(0).getLastCellNum();
		System.out.println(rowcount);
		System.out.println(colcount);
				
		String[][] exlarr = new String[rowcount][colcount];
		
		for(int i=0;i<=rowcount-1;i++){
			row = worksheet.getRow(i);
			for(int j=0;j<=colcount-1;j++){
				cell = row.getCell(j);
				try{
					String cellvalue = new DataFormatter().formatCellValue(cell);
					exlarr[i][j] = cellvalue;
				}catch(Exception e){
					exlarr[i][j]= "NaN";
				}	
				//System.out.println(cellvalue);
			}
		}
		
		return exlarr;
	}
	
	public static void csvToXLSX(String CSVFileURL, String convertExcellocation) {
		try {
			String csvFileAddress = "E:\\eclipse-workspace\\GETCO\\TestData\\winddataawk_23_07_2019_Wind.csv"; // csv file address
			String xlsxFileAddress = "E:\\eclipse-workspace\\GETCO\\TestData\\workbook.xlsx"; // xlsx file address
			XSSFWorkbook workBook = new XSSFWorkbook();
			XSSFSheet sheet = workBook.createSheet("Sheet1");
			String currentLine = null;
			int RowNum = 0;
			BufferedReader br = new BufferedReader(new FileReader(csvFileAddress));
			while ((currentLine = br.readLine()) != null) {
				//String str[] = currentLine.split(",");
				String[] str = currentLine.split("\\t|,|;|\\.|\\?|!|-|:|@|\\[|\\]|\\(|\\)|\\{|\\}|_|\\*|/");
				RowNum++;
				XSSFRow currentRow = sheet.createRow(RowNum);
				for (int i = 0; i < str.length; i++) {
					currentRow.createCell(i).setCellValue(str[i]);
				}
			}

			FileOutputStream fileOutputStream = new FileOutputStream(xlsxFileAddress);
			workBook.write(fileOutputStream);
			fileOutputStream.close();
			System.out.println("Done");
		} catch (Exception ex) {
			System.out.println(ex.getMessage() + "Exception in try");
		}
	}

	public int getColumnCount(String sheetName, int columnStartingIndex) {
		worksheet = workbook.getSheet(sheetName);
		row = worksheet.getRow(columnStartingIndex);
		int columnCount = row.getLastCellNum();
		return columnCount;
	}
}
