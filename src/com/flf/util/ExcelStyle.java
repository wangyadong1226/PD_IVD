package com.flf.util;

public class ExcelStyle {
	
	public ExcelStyle(int rowHeight, int colWidth, String fontName,
			int fontSize, short foregroundColor, short fontColor, boolean border) {
		this.rowHeight = rowHeight;
		this.colWidth = colWidth;
		this.fontName = fontName;
		this.fontSize = fontSize;
		this.fontColor = fontColor;
		this.foregroundColor = foregroundColor;
		this.border = border;
	}
	
	public ExcelStyle(){}
	
	private int rowHeight;
	
	private int colWidth;
	
	private String fontName;
	
	private int fontSize;
	
	private short fontColor;
	
	private short foregroundColor;
	
	public short getForegroundColor() {
		return foregroundColor;
	}

	public void setForegroundColor(short foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	private boolean border = false;

	public boolean isBorder() {
		return border;
	}

	public void setBorder(boolean border) {
		this.border = border;
	}

	public int getRowHeight() {
		return rowHeight;
	}

	public void setRowHeight(int rowHeight) {
		this.rowHeight = rowHeight;
	}

	public int getColWidth() {
		return colWidth;
	}

	public void setColWidth(int colWidth) {
		this.colWidth = colWidth;
	}

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public short getFontColor() {
		return fontColor;
	}

	public void setFontColor(short fontColor) {
		this.fontColor = fontColor;
	}
}

