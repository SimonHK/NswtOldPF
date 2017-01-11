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
	protected String FirstFileName; // ��һҳ���� ��index.shtml

	protected String OtherFileName; // ����ҳ���� ��index_1.shtml

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
		sb.append("��" + Total + "����¼��ÿҳ" + PageSize + "������ǰ��<span class='fc_ch1'>" + (PageIndex + 1) 
				+ "</span>/<span class='fc_ch1'>" + PageCount + "</span>ҳ&nbsp;&nbsp;&nbsp;&nbsp;");
		if (PageIndex > 0) {
			sb.append("<a href='" + getFirstPage() + "'><span class='fc_ch1'>��һҳ</span></a>|");
			sb.append("<a href='" + getPreviousPage() + "'><span class='fc_ch1'>��һҳ</span></a>|");
		} else {
			sb.append("<span class='fc_hui2'>��һҳ</span>|");
			sb.append("<span class='fc_hui2'>��һҳ</span>|");
		}
		if (PageIndex + 1 != PageCount && PageCount>0) {
			sb.append("<a href='" + getNextPage() + "'><span class='fc_ch1'>��һҳ</span></a>|");
			sb.append("<a href='" + getLastPage() + "'><span class='fc_ch1'>��ĩҳ</span></a>");
		} else {
			sb.append("<span class='fc_hui2'>��һҳ</span>|");
			sb.append("<span class='fc_hui2'>��ĩҳ</span>");
		}

		sb.append("&nbsp;&nbsp;ת����<input id='_PageBar_Index_"+id+"' type='text' size='4' style='width:30px' ");
		sb.append("style='' onKeyUp=\"value=value.replace(/\\D/g,'')\">ҳ");

		String listNameRule = getFirstFileName().substring(0, getFirstFileName().lastIndexOf("."));
		String ext = ServletUtil.getUrlExtension(getFirstFileName());
		sb.append("&nbsp;&nbsp;<input type='button' onclick=\"if(/[^\\d]/.test(document.getElementById('_PageBar_Index_"
				+id+"').value)){alert('�����ҳ��');$('_PageBar_Index_"+id+"').focus();}else if(document.getElementById('_PageBar_Index_"
				+id+"').value>" + PageCount + "){alert('�����ҳ��');document.getElementById('_PageBar_Index_"
				+id+"').focus();}else{var PageIndex = (document.getElementById('_PageBar_Index_"
				+id+"').value)>0?document.getElementById('_PageBar_Index_"
				+id+"').value:1;if(PageIndex==1){window.location='"+getFirstFileName()+"'}else{window.location='"+listNameRule+"_'+PageIndex+'"+ext+"';}}\" style='' value='��ת'></td>");
		sb.append("</tr></table>");
		return sb.toString();
	}
	
	//������ҳ��
	public String getPageBarBig5(int id) {
		StringBuffer sb = new StringBuffer();

		sb.append("<table width='100%' border='0' class='noBorder' align='center'><tr>");
		sb.append("<td height='18' align='center' valign='middle' style='border-width: 0px;color:#525252'>");
		sb.append("��" + Total + "�lӛ䛣�ÿ�" + PageSize + "�l����ǰ��<span class='fc_ch1'>" + (PageIndex + 1) 
				+ "</span>/<span class='fc_ch1'>" + PageCount + "</span>ҳ&nbsp;&nbsp;&nbsp;&nbsp;");
		if (PageIndex > 0) {
			sb.append("<a href='" + getFirstPage() + "'><span class='fc_ch1'>��һ�</span></a>|");
			sb.append("<a href='" + getPreviousPage() + "'><span class='fc_ch1'>��һ�</span></a>|");
		} else {
			sb.append("<span class='fc_hui2'>��һ�</span>|");
			sb.append("<span class='fc_hui2'>��һ�</span>|");
		}
		if (PageIndex + 1 != PageCount && PageCount>0) {
			sb.append("<a href='" + getNextPage() + "'><span class='fc_ch1'>��һ�</span></a>|");
			sb.append("<a href='" + getLastPage() + "'><span class='fc_ch1'>��ĩ�</span></a>");
		} else {
			sb.append("<span class='fc_hui2'>��һ�</span>|");
			sb.append("<span class='fc_hui2'>��ĩ�</span>");
		}

		sb.append("&nbsp;&nbsp;�D����<input id='_PageBar_Index_"+id+"' type='text' size='4' style='width:30px' ");
		sb.append("style='' onKeyUp=\"value=value.replace(/\\D/g,'')\">�");

		String listNameRule = getFirstFileName().substring(0, getFirstFileName().lastIndexOf("."));
		String ext = ServletUtil.getUrlExtension(getFirstFileName());
		sb.append("&nbsp;&nbsp;<input type='button' onclick=\"if(/[^\\d]/.test(document.getElementById('_PageBar_Index_"
				+id+"').value)){alert('�e�`��퓴a');$('_PageBar_Index_"+id+"').focus();}else if(document.getElementById('_PageBar_Index_"
				+id+"').value>" + PageCount + "){alert('�e�`��퓴a');document.getElementById('_PageBar_Index_"
				+id+"').focus();}else{var PageIndex = (document.getElementById('_PageBar_Index_"
				+id+"').value)>0?document.getElementById('_PageBar_Index_"
				+id+"').value:1;if(PageIndex==1){window.location='"+getFirstFileName()+"'}else{window.location='"+listNameRule+"_'+PageIndex+'"+ext+"';}}\" style='' value='���D'></td>");
		sb.append("</tr></table>");
		return sb.toString();
	}
	
	//Ӣ�İ��ҳ��
	public String getPageBarEN(int id) {
		StringBuffer sb = new StringBuffer();

		sb.append("<table width='100%' border='0' class='noBorder' align='center'><tr>");
		sb.append("<td height='18' align='center' valign='middle' style='border-width: 0px;color:#525252'>");
		sb.append("Total " + Total + " items , " + PageSize + " per page , current:<span class='fc_ch1'>" + (PageIndex + 1) 
				+ "</span>/<span class='fc_ch1'>" + PageCount + "</span>ҳ&nbsp;&nbsp;&nbsp;&nbsp;");
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
	 * ���·�ҳ
	 * @param id
	 * @return
	 */
	public String getPageBreakBar(int id) {
		StringBuffer sb = new StringBuffer();
		if(this.getTotal()>1){
			sb.append("<table width='100%' border='0' class='noBorder' align='center'><tr>");
			sb.append("<td height='18' align='center' valign='middle' style='border-width: 0px;color:#525252'>");
			//sb.append("�� " + Total + " ҳ ");
			if (PageIndex > 0) {
				sb.append("<a href='" + getPreviousPage() + "'><span class='fc_ch1'>��һҳ</span></a>&nbsp;&nbsp;");
			} else {
				sb.append("<span class='fc_hui2'>��һҳ</span>&nbsp;&nbsp;");
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
				sb.append("&nbsp;&nbsp;<a href='" + getNextPage() + "'><span class='fc_ch1'>��һҳ</span></a>&nbsp;");
			} else {
				sb.append("&nbsp;&nbsp;<span class='fc_hui2'>��һҳ</span>&nbsp;");
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
