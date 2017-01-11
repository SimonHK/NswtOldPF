package com.nswt.statical;

import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.ServletUtil;

/**
 * @Author lanjun
 * @Date 2008-5-6
 * @Mail lanjun@nswt.com
 */
public class TemplateData {
	protected String FirstFileName; // 第一页名称 如index.shtml

	protected String OtherFileName; // 其他页名称 如index_1.shtml

	protected int PageSize = 20;

	protected int PageIndex = 0;

	protected int Total;

	protected int PageCount;

	protected DataTable ListTable;

	public DataTable getDataTable(String sql) {
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		return dt;
	}

	public DataTable getPagedDataTable(DataTable dt) {
		int count = PageSize;
		if ((PageIndex + 1) * PageSize > Total) {
			count = Total - PageIndex * PageSize;
		}
		Object[][] values = new Object[count][dt.getColCount()];
		for (int i = 0; i < count; i++) {
			values[i] = dt.getDataRow(PageIndex * PageSize + i).getDataValues();
		}
		return new DataTable(dt.getDataColumns(), values);
	}

	public String getPreviousPage() {
		if (PageIndex == 1) {
			return FirstFileName;
		} else if (PageIndex != 0) {
			return OtherFileName.replaceAll("@INDEX", String.valueOf(PageIndex));
		} else if (PageIndex == 0) {
			return "#";
		}

		return null;
	}

	public int getPageCount() {
		return PageCount;
	}

	public void setPageCount(int pageCount) {
		PageCount = pageCount;
	}

	public int getPageIndex() {
		return PageIndex;
	}

	public void setPageIndex(int pageIndex) {
		PageIndex = pageIndex;
	}

	public int getTotal() {
		return Total;
	}

	public void setTotal(int total) {
		Total = total;
	}

	public String getNextPage() {
		if (PageIndex + 1 != PageCount) {
			return OtherFileName.replaceAll("@INDEX", String.valueOf(PageIndex + 2));
		} else {
			return "#";
		}
	}

	public String getFirstPage() {
		return FirstFileName;
	}

	public String getLastPage() {
		if (PageCount == 1) {
			return FirstFileName;
		} else {
			return OtherFileName.replaceAll("@INDEX", String.valueOf(PageCount));
		}
	}

	public String getFirstFileName() {
		return FirstFileName;
	}

	public void setFirstFileName(String firstFileName) {
		FirstFileName = firstFileName;
	}

	public String getOtherFileName() {
		return OtherFileName;
	}

	public void setOtherFileName(String otherFileName) {
		OtherFileName = otherFileName;
	}

	public int getPageSize() {
		return PageSize;
	}

	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}

	public String getPageBar(int id) {
		StringBuffer sb = new StringBuffer();

		sb.append("<table width='100%' border='0' class='noBorder' align='center'><tr>");
		sb.append("<td height='18' align='center' valign='middle' style='border-width: 0px;color:#525252'>");
		sb.append("共" + Total + "条记录，每页" + PageSize + "条，当前第<span class='fc_ch1'>" + (PageIndex + 1) 
				+ "</span>/<span class='fc_ch1'>" + PageCount + "</span>页&nbsp;&nbsp;&nbsp;&nbsp;");
		if (PageIndex > 0) {
			sb.append("<a href='" + getFirstPage() + "'><span class='fc_ch1'>第一页</span></a>|");
			sb.append("<a href='" + getPreviousPage() + "'><span class='fc_ch1'>上一页</span></a>|");
		} else {
			sb.append("<span class='fc_hui2'>第一页</span>|");
			sb.append("<span class='fc_hui2'>上一页</span>|");
		}
		if (PageIndex + 1 != PageCount && PageCount>0) {
			sb.append("<a href='" + getNextPage() + "'><span class='fc_ch1'>下一页</span></a>|");
			sb.append("<a href='" + getLastPage() + "'><span class='fc_ch1'>最末页</span></a>");
		} else {
			sb.append("<span class='fc_hui2'>下一页</span>|");
			sb.append("<span class='fc_hui2'>最末页</span>");
		}

		sb.append("&nbsp;&nbsp;转到第<input id='_PageBar_Index_"+id+"' type='text' size='4' style='width:30px' ");
		sb.append("style='' onKeyUp=\"value=value.replace(/\\D/g,'')\">页");

		String listNameRule = getFirstFileName().substring(0, getFirstFileName().lastIndexOf("."));
		String ext = ServletUtil.getUrlExtension(getFirstFileName());
		sb.append("&nbsp;&nbsp;<input type='button' onclick=\"if(/[^\\d]/.test(document.getElementById('_PageBar_Index_"
				+id+"').value)){alert('错误的页码');$('_PageBar_Index_"+id+"').focus();}else if(document.getElementById('_PageBar_Index_"
				+id+"').value>" + PageCount + "){alert('错误的页码');document.getElementById('_PageBar_Index_"
				+id+"').focus();}else{var PageIndex = (document.getElementById('_PageBar_Index_"
				+id+"').value)>0?document.getElementById('_PageBar_Index_"
				+id+"').value:1;if(PageIndex==1){window.location='"+getFirstFileName()+"'}else{window.location='"+listNameRule+"_'+PageIndex+'"+ext+"';}}\" style='' value='跳转'></td>");
		sb.append("</tr></table>");
		return sb.toString();
	}
	
	//繁体版分页条
	public String getPageBarBig5(int id) {
		StringBuffer sb = new StringBuffer();

		sb.append("<table width='100%' border='0' class='noBorder' align='center'><tr>");
		sb.append("<td height='18' align='center' valign='middle' style='border-width: 0px;color:#525252'>");
		sb.append("共" + Total + "條記錄，每頁" + PageSize + "條，當前第<span class='fc_ch1'>" + (PageIndex + 1) 
				+ "</span>/<span class='fc_ch1'>" + PageCount + "</span>页&nbsp;&nbsp;&nbsp;&nbsp;");
		if (PageIndex > 0) {
			sb.append("<a href='" + getFirstPage() + "'><span class='fc_ch1'>第一頁</span></a>|");
			sb.append("<a href='" + getPreviousPage() + "'><span class='fc_ch1'>上一頁</span></a>|");
		} else {
			sb.append("<span class='fc_hui2'>第一頁</span>|");
			sb.append("<span class='fc_hui2'>上一頁</span>|");
		}
		if (PageIndex + 1 != PageCount && PageCount>0) {
			sb.append("<a href='" + getNextPage() + "'><span class='fc_ch1'>下一頁</span></a>|");
			sb.append("<a href='" + getLastPage() + "'><span class='fc_ch1'>最末頁</span></a>");
		} else {
			sb.append("<span class='fc_hui2'>下一頁</span>|");
			sb.append("<span class='fc_hui2'>最末頁</span>");
		}

		sb.append("&nbsp;&nbsp;轉到第<input id='_PageBar_Index_"+id+"' type='text' size='4' style='width:30px' ");
		sb.append("style='' onKeyUp=\"value=value.replace(/\\D/g,'')\">頁");

		String listNameRule = getFirstFileName().substring(0, getFirstFileName().lastIndexOf("."));
		String ext = ServletUtil.getUrlExtension(getFirstFileName());
		sb.append("&nbsp;&nbsp;<input type='button' onclick=\"if(/[^\\d]/.test(document.getElementById('_PageBar_Index_"
				+id+"').value)){alert('錯誤的頁碼');$('_PageBar_Index_"+id+"').focus();}else if(document.getElementById('_PageBar_Index_"
				+id+"').value>" + PageCount + "){alert('錯誤的頁碼');document.getElementById('_PageBar_Index_"
				+id+"').focus();}else{var PageIndex = (document.getElementById('_PageBar_Index_"
				+id+"').value)>0?document.getElementById('_PageBar_Index_"
				+id+"').value:1;if(PageIndex==1){window.location='"+getFirstFileName()+"'}else{window.location='"+listNameRule+"_'+PageIndex+'"+ext+"';}}\" style='' value='跳轉'></td>");
		sb.append("</tr></table>");
		return sb.toString();
	}
	
	//英文版分页条
	public String getPageBarEN(int id) {
		StringBuffer sb = new StringBuffer();

		sb.append("<table width='100%' border='0' class='noBorder' align='center'><tr>");
		sb.append("<td height='18' align='center' valign='middle' style='border-width: 0px;color:#525252'>");
		sb.append("Total " + Total + " items , " + PageSize + " per page , current:<span class='fc_ch1'>" + (PageIndex + 1) 
				+ "</span>/<span class='fc_ch1'>" + PageCount + "</span>页&nbsp;&nbsp;&nbsp;&nbsp;");
		if (PageIndex > 0) {
			sb.append("<a href='" + getFirstPage() + "'><span class='fc_ch1'>Home</span></a>|");
			sb.append("<a href='" + getPreviousPage() + "'><span class='fc_ch1'>Previous</span></a>|");
		} else {
			sb.append("<span class='fc_hui2'>Home</span>|");
			sb.append("<span class='fc_hui2'>Previous</span>|");
		}
		if (PageIndex + 1 != PageCount && PageCount>0) {
			sb.append("<a href='" + getNextPage() + "'><span class='fc_ch1'>Next</span></a>|");
			sb.append("<a href='" + getLastPage() + "'><span class='fc_ch1'>End</span></a>");
		} else {
			sb.append("<span class='fc_hui2'>Next</span>|");
			sb.append("<span class='fc_hui2'>End</span>");
		}

		sb.append("&nbsp;&nbsp;Jump to <input id='_PageBar_Index_"+id+"' type='text' size='4' style='width:30px' ");
		sb.append("style='' onKeyUp=\"value=value.replace(/\\D/g,'')\"> page");

		String listNameRule = getFirstFileName().substring(0, getFirstFileName().lastIndexOf("."));
		String ext = ServletUtil.getUrlExtension(getFirstFileName());
		sb.append("&nbsp;&nbsp;<input type='button' onclick=\"if(/[^\\d]/.test(document.getElementById('_PageBar_Index_"
				+id+"').value)){alert('Error Page');$('_PageBar_Index_"+id+"').focus();}else if(document.getElementById('_PageBar_Index_"
				+id+"').value>" + PageCount + "){alert('Error Page');document.getElementById('_PageBar_Index_"
				+id+"').focus();}else{var PageIndex = (document.getElementById('_PageBar_Index_"
				+id+"').value)>0?document.getElementById('_PageBar_Index_"
				+id+"').value:1;if(PageIndex==1){window.location='"+getFirstFileName()+"'}else{window.location='"+listNameRule+"_'+PageIndex+'"+ext+"';}}\" style='' value='jump'></td>");
		sb.append("</tr></table>");
		return sb.toString();
	}
	
	
	/**
	 * 文章分页
	 * @param id
	 * @return
	 */
	public String getPageBreakBar(int id) {
		StringBuffer sb = new StringBuffer();
		if(this.getTotal()>1){
			sb.append("<table width='100%' border='0' class='noBorder' align='center'><tr>");
			sb.append("<td height='18' align='center' valign='middle' style='border-width: 0px;color:#525252'>");
			//sb.append("共 " + Total + " 页 ");
			if (PageIndex > 0) {
				sb.append("<a href='" + getPreviousPage() + "'><span class='fc_ch1'>上一页</span></a>&nbsp;&nbsp;");
			} else {
				sb.append("<span class='fc_hui2'>上一页</span>&nbsp;&nbsp;");
			}
			
			StringBuffer pageList = new StringBuffer();
			for (int i = 0; i < PageCount; i++) {
				String href = null;
				if(i==0){
					href = this.FirstFileName;
				}else{
					href = OtherFileName.replaceAll("@INDEX", String.valueOf(i + 1));
				}
				
				if(i==this.PageIndex){
					pageList.append(" "+(i + 1));
				}else{
					pageList.append("  <a href=");
					pageList.append(href);
					pageList.append(">" + (i + 1) + "</a>");
				}
			}
			
		    sb.append(pageList);

			if (PageIndex + 1 != PageCount) {
				sb.append("&nbsp;&nbsp;<a href='" + getNextPage() + "'><span class='fc_ch1'>下一页</span></a>&nbsp;");
			} else {
				sb.append("&nbsp;&nbsp;<span class='fc_hui2'>下一页</span>&nbsp;");
			}

			sb.append("&nbsp;&nbsp;</td>");
			sb.append("</tr></table>");
		}
		return sb.toString();
	}

	public DataRow getPageRow() {
		StringBuffer pageList = new StringBuffer();
		for (int i = 0; i < PageCount; i++) {
			String href = null;
			if(i==0){
				href = this.FirstFileName;
			}else{
				href = OtherFileName.replaceAll("@INDEX", String.valueOf(i + 1));
			}
			
			if(i==this.PageIndex){
				pageList.append(" "+(i + 1));
			}else{
				pageList.append("  <a href=");
				pageList.append(href);
				pageList.append(">" + (i + 1) + "</a>");
			}
		}

		DataTable dataPage = new DataTable();
		String[] cols = { "Total", "PageCount", "PageSize", "FirstPage", "PrevPage", "NextPage", "LastPage", "PageIndex",
				"PageList" };
		String[] values = { Total + "", PageCount + "", PageSize + "", getFirstPage(), getPreviousPage(), getNextPage(),
				getLastPage(), (PageIndex + 1) + "", pageList.toString() };
		dataPage.insertColumns(cols);
		dataPage.insertRow(values);

		return dataPage.getDataRow(0);
	}

	public DataTable getListTable() {
		return ListTable;
	}

	public void setListTable(DataTable listTable) {
		ListTable = listTable;
	}
}
