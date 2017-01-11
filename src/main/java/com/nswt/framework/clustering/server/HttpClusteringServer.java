package com.nswt.framework.clustering.server;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nswt.framework.clustering.Clustering;
import com.nswt.framework.utility.LogUtil;

/**
 * @Author NSWT
 * @Date 2016-4-22
 * @Mail nswt@nswt.com.cn
 */
public class HttpClusteringServer extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		if (!Clustering.isClusteringServer()) {
			try {
				LogUtil.warn("��Ӧ��δ�����óɼ�Ⱥ������!");
				response.getWriter().print("��Ӧ��δ�����óɼ�Ⱥ������!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String type = request.getParameter("Type");
		String key = request.getParameter("Key");
		String action = request.getParameter("Action");
		String value = request.getParameter("Value");
		String result = ClusteringData.dealRequest(type, key, action, value);
		try {
			response.getWriter().print(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
