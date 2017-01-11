package com.nswt.statical.template;

import java.io.PrintWriter;
import java.util.Date;

import com.nswt.framework.utility.CaseIgnoreMapx;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.Treex;

/**
 * 所有模板类的基类，各属性值是编译时从解析得到的TemplateConfig实例而来
 * 
 * @Author NSWT
 * @Date 2016-5-30
 * @Mail nswt@nswt.com.cn
 */
public abstract class TemplateBase {
	protected TemplateContext context;

	protected TemplatePrintWriter out;

	protected int pageSize = 0;

	protected int pageIndex = 0;

	protected int pageTotal = 0;

	protected String destination;

	public PrintWriter getWriter() {
		return out;
	}

	public String getResult() {
		return out.getResult();
	}

	public TemplateContext getContext() {
		return context;
	}

	public void setContext(TemplateContext ctx) {
		context = (TemplateContext) ctx.clone();
		out = new TemplatePrintWriter(context);
		Mapx map = (Mapx) context.getTree().getRoot().getData();
		if (map == null) {
			map = new CaseIgnoreMapx();
		}
		map.put(TemplateContext.PrintWriterInCurrentNode, out);
		context.getTree().getRoot().setData(map);
	}

	public boolean hasNextPage() {
		return pageIndex * pageSize < pageTotal;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public long getLastModified() {
		return getConfig().getLastModified();
	}

	public String eval(String holder) {
		return context.eval(holder);
	}

	public Object evalObject(String holder) {
		return context.getHolderValue(holder);
	}

	public int evalInt(String holder) {
		Object o = context.getHolderValue(holder);
		if (o instanceof Number) {
			return ((Number) o).intValue();
		} else if (o != null) {
			try {
				return Integer.parseInt(o.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public long evalLong(String holder) {
		Object o = context.getHolderValue(holder);
		if (o instanceof Number) {
			return ((Number) o).longValue();
		} else if (o != null) {
			try {
				return Long.parseLong(o.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public Date evalDate(String holder) {
		Object o = context.getHolderValue(holder);
		if (o instanceof Date) {
			return ((Date) o);
		} else if (o != null) {
			try {
				return DateUtil.parseDateTime(o.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public double evalDouble(String holder) {
		Object o = context.getHolderValue(holder);
		if (o instanceof Number) {
			return ((Number) o).doubleValue();
		} else if (o != null) {
			try {
				return Double.parseDouble(o.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public void write(Object obj) {
		if (obj == null) {
			return;
		} else {
			if (obj instanceof Date) {
				getWriter().print(DateUtil.toDateTimeString((Date) obj));
			} else {
				getWriter().print(obj);
			}
		}
	}

	public void write(double obj) {
		getWriter().print(obj);
	}

	public void write(int obj) {
		getWriter().print(obj);
	}

	public void write(long obj) {
		getWriter().print(obj);
	}

	public void write(float obj) {
		getWriter().print(obj);
	}

	/**
	 * 返回相对于应用根目录的模板文件路径
	 */
	public abstract String getTemplateFilePath();

	public abstract void execute() throws Exception;

	public abstract TemplateConfig getConfig();

	public abstract Treex getTagTree() throws Exception;
}
