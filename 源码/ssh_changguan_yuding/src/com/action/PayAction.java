package com.action;

import com.dao.TQchDAO;
import com.dao.TUserDAO;
import com.dao.TYudingDAO;
import com.dao.TYudingshijianDao;
import com.model.TUser;
import com.model.TYuding;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.util.MerchantApiUtil;
import com.util.PayConfigUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PayAction  extends ActionSupport{
	private String yuDingID;

	private TYudingDAO yudingDAO;

	private TYudingshijianDao yudingshijianDAO;
	private TQchDAO qchDAO;
	private TUserDAO userDAO;
	public String getYuDingID() {
		return yuDingID;
	}

	public void setYuDingID(String yuDingID) {
		this.yuDingID = yuDingID;
	}

	public TYudingDAO getYudingDAO() {
		return yudingDAO;
	}

	public void setYudingDAO(TYudingDAO yudingDAO) {
		this.yudingDAO = yudingDAO;
	}

	public TYudingshijianDao getYudingshijianDAO() {
		return yudingshijianDAO;
	}

	public void setYudingshijianDAO(TYudingshijianDao yudingshijianDAO) {
		this.yudingshijianDAO = yudingshijianDAO;
	}

	public TQchDAO getQchDAO() {
		return qchDAO;
	}

	public void setQchDAO(TQchDAO qchDAO) {
		this.qchDAO = qchDAO;
	}

	public TUserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(TUserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public static String getUtf8() {
		return UTF_8;
	}

	public static String getGbk() {
		return GBK;
	}

	public void scanPay() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();	
		try {
			//			Map<String, Object> paramMap = new HashMap<String, Object>();
			//
			//			String orderPriceStr = "0.5"; // ������� , ��λ:Ԫ
			//			paramMap.put("orderPrice", orderPriceStr);
			//			String payWayCode = "ALIPAY"; // ֧����ʽ���� ֧����: ALIPAY  ΢��:WEIXIN
			//			paramMap.put("payWayCode", payWayCode);
			//			long orderNoLong = System.currentTimeMillis();
			//			String orderNo = String.valueOf(orderNoLong);    // �������
			//			paramMap.put("orderNo", orderNo);
			//
			//			Date orderDate = new Date();//��������
			//			String orderDateStr = new SimpleDateFormat("yyyyMMdd").format(orderDate);// ��������
			//			paramMap.put("orderDate", orderDateStr);
			//
			//			Date orderTime = new Date();//����ʱ��
			//			String orderTimeStr = new SimpleDateFormat("yyyyMMddHHmmss").format(orderTime);// ����ʱ��
			//			paramMap.put("orderTime", orderTimeStr);
			//
			//			paramMap.put("payKey", PayConfigUtil.readConfig("payKey"));
			//			String productName = ""; // ��Ʒ����
			//			try {
			//			    productName = URLDecoder.decode("sport palces book", UTF_8);
			//			} catch (UnsupportedEncodingException e) {
			//			    e.printStackTrace();
			//			}
			//			paramMap.put("productName", productName);
			//
			//			String orderIp = PayConfigUtil.readConfig("orderIp"); // �µ�IP
			//			paramMap.put("orderIp", orderIp);
			//
			//			String orderPeriodStr = PayConfigUtil.readConfig("orderPeriod"); // ������Ч��
			//			paramMap.put("orderPeriod", orderPeriodStr);
			//			String returnUrl = PayConfigUtil.readConfig("returnUrl"); // ҳ��֪ͨ����url
			//			paramMap.put("returnUrl", returnUrl);
			//			String notifyUrl = PayConfigUtil.readConfig("notifyUrl"); // ��̨��Ϣ֪ͨUrl
			//			paramMap.put("notifyUrl", notifyUrl);
			//			String remark = getString_UrlDecode_UTF8("remark"); // ֧����ע
			//			paramMap.put("remark", remark);
			//
			//			////////////��չ�ֶ�,ѡ��,ԭֵ����///////////
			//			Map session = (Map) ActionContext.getContext().getSession();
			//			TUser user = (TUser) session.get("user");
			//			paramMap.put("field1", user.getUserId()+"");
			//			paramMap.put("field2", yuDingID);
			//			paramMap.put("field3", orderNo);
			//			String field4 = ""; // ��չ�ֶ�4
			//			paramMap.put("field4", field4);
			//			String field5 = ""; // ��չ�ֶ�5
			//			paramMap.put("field5", field5);
			//
			//			/////ǩ������������API�ķ���///
			//			String sign = MerchantApiUtil.getSign(paramMap, PayConfigUtil.readConfig("paySecret"));
			//			paramMap.put("sign", sign);
			//
			//			String buildRequest = MerchantApiUtil.buildRequest(paramMap, "get", "ȷ��", PayConfigUtil.readConfig("scanPayUrl"));
			//			Map request = (Map) ServletActionContext.getContext().get("request");
			//			request.put("payMessage", buildRequest);

			TYuding yuding = yudingDAO.findById(Integer.parseInt(yuDingID));
			yuding.setPaystatus("1");
			yudingDAO.attachDirty(yuding);
			Map request = (Map) ServletActionContext.getContext().get("request");
			Map session = (Map) ActionContext.getContext().getSession();
			TUser user;

			user = (TUser) session.get("user");
			int userId = user.getUserId();
			String sql = "from TYuding where del='no' and userId=" + userId;
			List yudingList = yudingDAO.getHibernateTemplate().find(sql);
			for (int i = 0; i < yudingList.size(); i++) {
				yuding = (TYuding) yudingList.get(i);
				yuding.setUser(userDAO.findById(yuding.getUserId()));
				yuding.setQch(qchDAO.findById(yuding.getQchId()));
			}
			request.put("yudingList", yudingList);
		} catch (Exception e) {
			e.printStackTrace();
			out.write("success");
		}
		out.write("success");
	}


	private static final String UTF_8 = "utf-8";

	private static final String GBK = "GBK";

	/**
	 * ��ȡrequest
	 *
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * ��ȡsession
	 *
	 * @return
	 */
	protected HttpSession getSession() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
	}

	/**
	 * ��ȡapplication
	 *
	 * @return
	 */
	protected ServletContext getApplication() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getServletContext();
	}




	public String getString(String name) {
		return getString(name, null);
	}

	public String getString(String name, String defaultValue) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr == null || "".equals(resultStr) || "null".equals(resultStr) || "undefined".equals(resultStr)) {
			return defaultValue;
		} else {
			return resultStr;
		}
	}

	/**
	 * ��ȡ�����еĲ���ֵ���������ֵΪnull��תΪ���ַ���""
	 *
	 * @return
	 */
	public Map<String, Object> getParamMap_NullStr(Map map) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		Set keys = map.keySet();
		for (Object key : keys) {
			String value = this.getString(key.toString());
			if (value == null){
				value = "";
			}
			parameters.put(key.toString(), value);
		}
		return parameters;
	}



	public int getInt(String name) {
		return getInt(name, 0);
	}

	public int getInt(String name, int defaultValue) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr != null) {
			try {
				return Integer.parseInt(resultStr);
			} catch (Exception e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	public BigDecimal getBigDecimal(String name) {
		return getBigDecimal(name, null);
	}

	public BigDecimal getBigDecimal(String name, BigDecimal defaultValue) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr != null) {
			try {
				return BigDecimal.valueOf(Double.parseDouble(resultStr));
			} catch (Exception e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	/**
	 * ���ݲ�������HttpRequest�л�ȡString���͵Ĳ���ֵ����ֵ�򷵻�"" .
	 *
	 * @param key
	 *            .
	 * @return String .
	 */
	public String getString_UrlDecode_UTF8(String key) {
		try {
			return URLDecoder.decode(this.getString(key), UTF_8);
		} catch (Exception e) {
			return "";
		}

	}

	public String getString_UrlDecode_GBK(String key) {
		try {
			return new String(getString(key.toString()).getBytes("GBK"), "UTF-8");
		} catch (Exception e) {
			return "";
		}

	}

	/**
	 * ��ȡ�ͻ��˵�IP��ַ
	 *
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ipAddress = null;
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// ��������ȡ�������õ�IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}

		// ����ͨ�����������������һ��IPΪ�ͻ�����ʵIP,���IP����','�ָ�
		if (ipAddress != null && ipAddress.length() > 15) {
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	/**
	 * ��ȡrefererUrl
	 */
	public String getRefererUrl(HttpServletRequest request) {
		return request.getHeader("referer");
	}

	public String readRequest(HttpServletRequest request) throws IOException {
		StringBuilder sb = new StringBuilder();
		try {
			String line;
			while ((line = request.getReader().readLine()) != null) {
				sb.append(line);
			}
		} finally {
			request.getReader().close();
		}
		return sb.toString();
	}

	public void write(HttpServletResponse response, String s) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(s);
		} catch (IOException e) {
		} finally {
			out.close();
		}
	}
}
