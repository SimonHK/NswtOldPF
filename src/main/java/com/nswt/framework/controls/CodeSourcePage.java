package com.nswt.framework.controls;

import com.nswt.framework.Ajax;
import com.nswt.framework.Current;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.utility.StringUtil;

/**
 * ��ӦSelect.js�е�loadData����
 * 
 */
public class CodeSourcePage extends Ajax {
	public void getData() {
		String codeType = $V("CodeType");
		if (StringUtil.isEmpty($V("ConditionField"))) {
			Request.put("ConditionField", "1");
			Request.put("ConditionValue", "1");
		}
		DataTable dt = null;
		String method = $V("Method");
		if (StringUtil.isEmpty(method) && codeType.startsWith("#")) {
			method = codeType.substring(1);
		}
		if (StringUtil.isNotEmpty(method)) {			
			String className = method.substring(0, method.lastIndexOf("."));
			String methodName = method.substring(method.lastIndexOf(".") + 1);
			try {
				Object o = Current.invokeMethod(method, new Object[] { Request });
				dt = (DataTable) o;
			} catch (Exception e) {
				throw new RuntimeException("ȷ����" + className + "�ķ���" + methodName + "����ֵ��DataTable����!");
			}
		} else {
			CodeSource cs = SelectTag.getCodeSourceInstance();
			dt = cs.getCodeData(codeType, Request);
		}
		$S("DT", dt);
	}
}
