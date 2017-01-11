package com.nswt.cms.webservice;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.codehaus.xfire.XFire;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.ServiceFactory;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.service.invoker.ObjectInvoker;
import org.codehaus.xfire.transport.http.XFireServlet;

import com.nswt.framework.utility.LogUtil;

/**
 * xfire webservice 发布的servlet 参考web.xml中的配置
 * @author lanjun 
 * 2016-5-5
 */
public class XFireConfigServlet extends XFireServlet {
	private static Log log = LogUtil.getLogger();

	public XFire createXFire() throws ServletException {
		XFire xfire = super.createXFire();
		ServiceFactory factory = new ObjectServiceFactory(xfire.getTransportManager(), null);

		Service service = factory.create(CmsService.class);
		service.setProperty(ObjectInvoker.SERVICE_IMPL_CLASS, CmsServiceImpl.class);
		xfire.getServiceRegistry().register(service);
		
		//用户同步
		Service userService = factory.create(UserOperator.class);
		userService.setProperty(ObjectInvoker.SERVICE_IMPL_CLASS, UserOperatorImpl.class);
		xfire.getServiceRegistry().register(userService);

		if (xfire == null || xfire.getServiceRegistry() == null || xfire.getServiceRegistry().getServices() == null 
				|| xfire.getServiceRegistry().getServices().size() == 0) {
			xfire = super.createXFire();
		}
		
		//新闻同步
		Service articleService = factory.create(ArticleOperator.class);
		articleService.setProperty(ObjectInvoker.SERVICE_IMPL_CLASS, ArticleOperatorImpl.class);
		xfire.getServiceRegistry().register(articleService);

		if (xfire == null || xfire.getServiceRegistry() == null || xfire.getServiceRegistry().getServices() == null 
				|| xfire.getServiceRegistry().getServices().size() == 0) {
			xfire = super.createXFire();
		}

		log.info("发布webservice");

		return xfire;
	}
}
