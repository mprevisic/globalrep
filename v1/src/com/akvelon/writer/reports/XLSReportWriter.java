package com.akvelon.writer.reports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import com.akvelon.report.Report;
import com.akvelon.test.CMDOptionReader;

public class XLSReportWriter extends ReportWriter {

	private static final Logger log = Logger.getLogger(XLSReportWriter.class);
	
	private HSSFWorkbook workbook;

	private String EXTENSION = ".xls";
	
	private CellStyle style;

	private int rowNum = 0;

	public XLSReportWriter() {
		super();
		workbook = new HSSFWorkbook();
		style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	}

	@Override
	public void writeReport() throws IOException {
		if (reports.isEmpty()) {
			log.info("No reports to write");
			return;
		}
		for (List<Report> reps : reports) {
			HSSFSheet sheet = workbook.createSheet(reps.get(0).getReportName());
			rowNum = 0;
			createReportHead(sheet);
			for (Report rep : reps) {
				createReportRow(sheet, rep);
			}
			autofitColumsSize(sheet);
		}
		FileOutputStream out = new FileOutputStream(new File(fileName + EXTENSION));
		workbook.write(out);
		out.close();
	}

	private void createReportRow(HSSFSheet sheet, Report report) {
		int cellNum = 0;
		Row row = sheet.createRow(rowNum++);
		row.createCell(cellNum++).setCellValue(report.getBliID());
		row.createCell(cellNum++).setCellValue(report.getBliName());
		row.createCell(cellNum++).setCellValue(report.getBliOwner());
		row.createCell(cellNum++).setCellValue(report.getTaskName());
		row.createCell(cellNum++).setCellValue(report.getTaskOwner());
		row.createCell(cellNum++).setCellValue(report.getReportName());
	}

	private void createReportHead(HSSFSheet sheet) {
		int cellNum = 0;
		Row row = sheet.createRow(rowNum++);
		StringTokenizer st = new StringTokenizer(HEAD, ",");
		while (st.hasMoreElements()) {
			row.createCell(cellNum).setCellValue(st.nextToken());
			row.getCell(cellNum++).setCellStyle(style);
		}
	}

	private void autofitColumsSize(HSSFSheet sheet) {
		Iterator<Row> rowIterator = sheet.iterator();
		Iterator<Cell> cellIterator = rowIterator.next().cellIterator();
		while (cellIterator.hasNext()) {
			sheet.autoSizeColumn(cellIterator.next().getColumnIndex());
		}
	}
}
