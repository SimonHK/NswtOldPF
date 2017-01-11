package com.nswt.cms.stat;

import java.security.SecureRandom;

import javax.servlet.http.HttpServletRequest;

import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;

/**
 * @Author ������
 * @Date 2008-11-20
 * @Mail nswt@nswt.com.cn
 */
public class StatUtil {

	public static String getDomain(String url) {
		int i1 = url.indexOf("//") + 2;
		int i2 = url.indexOf("/", i1 + 2);
		if (i2 == -1) {
			i2 = url.length();
		}
		return url.substring(i1, i2).toLowerCase();
	}

	private static String[] DistrictCodes;

	private static Object[] IPRanges;

	private static Object mutex = new Object();

	private static void initIPRanges() {
		if (DistrictCodes == null) {
			synchronized (mutex) {
				if (DistrictCodes == null) {
					DataTable dt = new QueryBuilder("select IPRanges,DistrictCode from ZDIPRange").executeDataTable();
					String[] codes = new String[dt.getRowCount()];
					IPRanges = new Object[dt.getRowCount()];
					for (int i = 0; i < dt.getRowCount(); i++) {
						String code = dt.getString(i, 1);
						String ranges = dt.getString(i, 0);
						codes[i] = code;
						String[] arr = StringUtil.splitEx(ranges, ",");
						long[] r = new long[arr.length * 2];
						for (int j = 0; j < arr.length; j++) {
							String[] arr2 = StringUtil.splitEx(arr[j], "+");
							r[2 * j] = Long.parseLong(arr2[0]);
							r[2 * j + 1] = r[2 * j] + Long.parseLong(arr2[1]);
						}
						IPRanges[i] = r;
					}
					DistrictCodes = codes;
				}
			}
		}
	}

	public static long convertIP(String ip) {
		String[] arr1 = StringUtil.splitEx(ip, ".");
		try {
			long t = Long.parseLong(arr1[0]) * 16777216L + Long.parseLong(arr1[1]) * 65536L + Long.parseLong(arr1[2])
					* 256L + Long.parseLong(arr1[3]) - 1L;
			return t;
		} catch (Exception e) {
			LogUtil.warn("�����IP��ַ:" + ip);
		}
		return 0;
	}

	public static String getDistrictCode(String strIP) {
		initIPRanges();
		long ip = convertIP(strIP);
		for (int j = 0; j < DistrictCodes.length; j++) {
			long[] arr = (long[]) IPRanges[j];
			if (ip < arr[0] || ip > arr[arr.length - 1]) {
				continue;
			}
			for (int k = 0; k < arr.length; k += 2) {
				long i1 = arr[k];
				long i2 = arr[k + 1];
				if (ip >= i1 && ip <= i2) {
					return DistrictCodes[j];
				}
			}
		}
		return "000999";// ����
	}

	public static String getOS(String useragent) {
		if (StringUtil.isEmpty(useragent)) {
			return "����";
		}
		if (useragent.indexOf("Windows NT 6.1") > 0) {
			return "Windows 7";
		} else if (useragent.indexOf("Windows NT 6.0") > 0) {
			return "Windows Vista";
		} else if (useragent.indexOf("Windows NT 5.2") > 0) {
			return "Windows 2003";
		} else if (useragent.indexOf("Windows NT 5.1") > 0) {
			return "Windows XP";
		} else if (useragent.indexOf("Windows NT 5.0") > 0) {
			return "Windows 2000";
		} else if (useragent.indexOf("Windows NT") > 0) {
			return "Windows NT";
		} else if (useragent.indexOf("Windows 9") > 0 || useragent.indexOf("Windows 4") > 0) {
			return "Windows 9x";
		} else if (useragent.indexOf("Unix") > 0 || useragent.indexOf("SunOS") > 0 || useragent.indexOf("BSD") > 0) {
			return "Unix";
		} else if (useragent.indexOf("Linux") > 0) {
			return "Linux";
		} else if (useragent.indexOf("Mac") > 0) {
			return "Mac";
		} else if (useragent.indexOf("Windows CE") > 0) {
			return "Windows CE";
		} else if (useragent.indexOf("iPhone") > 0) {
			return "iPhone";
		} else if (useragent.indexOf("BlackBerry") > 0) {
			return "BlackBerry";
		} else if (useragent.indexOf("SymbianOS") > 0 || useragent.indexOf("Series") > 0) {
			return "Symbian";
		} else {
			return "����";
		}
	}

	static String[] langCodeArr;

	static String[] langNameArr;

	public static String getLanguage(String lang) {
		if (langCodeArr == null) {
			synchronized (mutex) {
				if (langCodeArr == null) {
					Mapx map = new Mapx();
					map.put("en_us", "Ӣ��(����)");
					map.put("ar", "��������");
					map.put("ar_ae", "��������(����������������)");
					map.put("ar_bh", "��������(����)");
					map.put("ar_dz", "��������(����������)");
					map.put("ar_eg", "��������(����)");
					map.put("ar_iq", "��������(������)");
					map.put("ar_jo", "��������(Լ��)");
					map.put("ar_kw", "��������(������)");
					map.put("ar_lb", "��������(�����)");
					map.put("ar_ly", "��������(������)");
					map.put("ar_ma", "��������(Ħ���)");
					map.put("ar_om", "��������(����)");
					map.put("ar_qa", "��������(������)");
					map.put("ar_sa", "��������(ɳ�ذ�����)");
					map.put("ar_sd", "��������(�յ�)");
					map.put("ar_sy", "��������(������)");
					map.put("ar_tn", "��������(ͻ��˹)");
					map.put("ar_ye", "��������(Ҳ��)");
					map.put("be", "�׶���˹��");
					map.put("be_by", "�׶���˹��(�׶���˹)");
					map.put("bg", "����������");
					map.put("bg_bg", "����������(��������)");
					map.put("ca", "��̩��������");
					map.put("ca_es", "��̩��������(������)");
					map.put("ca_es_euro", "��̩��������(������,Euro)");
					map.put("cs", "�ݿ���");
					map.put("cs_cz", "�ݿ���(�ݿ˹��͹�)");
					map.put("da", "������");
					map.put("da_dk", "������(����)");
					map.put("de", "����");
					map.put("de_at", "����(�µ���)");
					map.put("de_at_euro", "����(�µ���,Euro)");
					map.put("de_ch", "����(��ʿ)");
					map.put("de_de", "����(�¹�)");
					map.put("de_de_euro", "����(�¹�,Euro)");
					map.put("de_lu", "����(¬ɭ��)");
					map.put("de_lu_euro", "����(¬ɭ��,Euro)");
					map.put("el", "ϣ����");
					map.put("el_gr", "ϣ����(ϣ��)");
					map.put("en_au", "Ӣ��(�Ĵ�����)");
					map.put("en_ca", "Ӣ��(���ô�)");
					map.put("en_gb", "Ӣ��(Ӣ��)");
					map.put("en_ie", "Ӣ��(������)");
					map.put("en_ie_euro", "Ӣ��(������,Euro)");
					map.put("en_nz", "Ӣ��(������)");
					map.put("en_za", "Ӣ��(�Ϸ�)");
					map.put("es", "��������");
					map.put("es_bo", "��������(����ά��)");
					map.put("es_ar", "��������(����͢)");
					map.put("es_cl", "��������(����)");
					map.put("es_co", "��������(���ױ���)");
					map.put("es_cr", "��������(��˹�����)");
					map.put("es_do", "��������(������ӹ��͹�)");
					map.put("es_ec", "��������(��϶��)");
					map.put("es_es", "��������(������)");
					map.put("es_es_euro", "��������(������,Euro)");
					map.put("es_gt", "��������(Σ������)");
					map.put("es_hn", "��������(�鶼��˹)");
					map.put("es_mx", "��������(ī����)");
					map.put("es_ni", "��������(�������)");
					map.put("et", "��ɳ������");
					map.put("es_pa", "��������(������)");
					map.put("es_pe", "��������(��³)");
					map.put("es_pr", "��������(�������)");
					map.put("es_py", "��������(������)");
					map.put("es_sv", "��������(�����߶�)");
					map.put("es_uy", "��������(������)");
					map.put("es_ve", "��������(ί������)");
					map.put("et_ee", "��ɳ������(��ɳ����)");
					map.put("fi", "������");
					map.put("fi_fi", "������(����)");
					map.put("fi_fi_euro", "������(����,Euro)");
					map.put("fr", "����");
					map.put("fr_be", "����(����ʱ)");
					map.put("fr_be_euro", "����(����ʱ,Euro)");
					map.put("fr_ca", "����(���ô�)");
					map.put("fr_ch", "����(��ʿ)");
					map.put("fr_fr", "����(����)");
					map.put("fr_fr_euro", "����(����,Euro)");
					map.put("fr_lu", "����(¬ɭ��)");
					map.put("fr_lu_euro", "����(¬ɭ��,Euro)");
					map.put("hr", "���޵�����");
					map.put("hr_hr", "���޵�����(���޵���)");
					map.put("hu", "��������");
					map.put("hu_hu", "��������(������)");
					map.put("is", "������");
					map.put("is_is", "������(����)");
					map.put("it", "�������");
					map.put("it_ch", "�������(��ʿ)");
					map.put("it_it", "�������(�����)");
					map.put("it_it_euro", "�������(�����,Euro)");
					map.put("iw", "ϣ������");
					map.put("iw_il", "ϣ������(��ɫ��)");
					map.put("ja", "����");
					map.put("ja_jp", "����(�ձ�)");
					map.put("ko", "������");
					map.put("ko_kr", "������(�ϳ���)");
					map.put("lt", "��������");
					map.put("lt_lt", "��������(������)");
					map.put("lv", "����ά����(����)");
					map.put("lv_lv", "����ά����(����)(����ά��)");
					map.put("mk", "�������");
					map.put("mk_mk", "�������(���������)");
					map.put("nl", "������");
					map.put("nl_be", "������(����ʱ)");
					map.put("nl_be_euro", "������(����ʱ,Euro)");
					map.put("nl_nl", "������(����)");
					map.put("nl_nl_euro", "������(����,Euro)");
					map.put("no", "Ų����");
					map.put("no_no", "Ų����(Ų��)");
					map.put("no_no_ny", "Ų����(Ų��,Nynorsk)");
					map.put("pl", "������");
					map.put("pl_pl", "������(����)");
					map.put("pt", "��������");
					map.put("pt_br", "��������(����)");
					map.put("pt_pt", "��������(������)");
					map.put("pt_pt_euro", "��������(������,Euro)");
					map.put("ro", "����������");
					map.put("ro_ro", "����������(��������)");
					map.put("ru", "����");
					map.put("ru_ru", "����(����˹)");
					map.put("sh", "������˹-���޵�����");
					map.put("sh_yu", "������˹-���޵�����(��˹����)");
					map.put("sk", "˹�工����");
					map.put("sk_sk", "˹�工����(˹�工��)");
					map.put("sl", "˹����������");
					map.put("sl_si", "˹����������(˹��������)");
					map.put("sq", "������������");
					map.put("sq_al", "������������(����������)");
					map.put("sr", "����ά����");
					map.put("sr_yu", "����ά����(��˹����)");
					map.put("sv", "�����");
					map.put("sv_se", "�����(���)");
					map.put("th", "̩��");
					map.put("th_th", "̩��(̩��)");
					map.put("tr", "��������");
					map.put("tr_tr", "��������(������)");
					map.put("uk", "�ڿ�����");
					map.put("uk_ua", "�ڿ�����(�ڿ���)");
					map.put("zh", "����");
					map.put("zh_cn", "����(��½)");
					map.put("zh_hk", "����(���)");
					map.put("zh_tw", "����(̨��)");
					map.put("zh_sg", "����(�¼���)");
					map.put("en-us", "Ӣ��(����)");
					map.put("ar-ae", "��������(����������������)");
					map.put("ar-bh", "��������(����)");
					map.put("ar-dz", "��������(����������)");
					map.put("ar-eg", "��������(����)");
					map.put("ar-iq", "��������(������)");
					map.put("ar-jo", "��������(Լ��)");
					map.put("ar-kw", "��������(������)");
					map.put("ar-lb", "��������(�����)");
					map.put("ar-ly", "��������(������)");
					map.put("ar-ma", "��������(Ħ���)");
					map.put("ar-om", "��������(����)");
					map.put("ar-qa", "��������(������)");
					map.put("ar-sa", "��������(ɳ�ذ�����)");
					map.put("ar-sd", "��������(�յ�)");
					map.put("ar-sy", "��������(������)");
					map.put("ar-tn", "��������(ͻ��˹)");
					map.put("ar-ye", "��������(Ҳ��)");
					map.put("be-by", "�׶���˹��(�׶���˹)");
					map.put("bg-bg", "����������(��������)");
					map.put("ca-es", "��̩��������(������)");
					map.put("ca-es-euro", "��̩��������(������,Euro)");
					map.put("cs-cz", "�ݿ���(�ݿ˹��͹�)");
					map.put("da-dk", "������(����)");
					map.put("de-at", "����(�µ���)");
					map.put("de-at-euro", "����(�µ���,Euro)");
					map.put("de-ch", "����(��ʿ)");
					map.put("de-de", "����(�¹�)");
					map.put("de-de-euro", "����(�¹�,Euro)");
					map.put("de-lu", "����(¬ɭ��)");
					map.put("de-lu-euro", "����(¬ɭ��,Euro)");
					map.put("el-gr", "ϣ����(ϣ��)");
					map.put("en-au", "Ӣ��(�Ĵ�����)");
					map.put("en-ca", "Ӣ��(���ô�)");
					map.put("en-gb", "Ӣ��(Ӣ��)");
					map.put("en-ie", "Ӣ��(������)");
					map.put("en-ie-euro", "Ӣ��(������,Euro)");
					map.put("en-nz", "Ӣ��(������)");
					map.put("en-za", "Ӣ��(�Ϸ�)");
					map.put("es-bo", "��������(����ά��)");
					map.put("es-ar", "��������(����͢)");
					map.put("es-cl", "��������(����)");
					map.put("es-co", "��������(���ױ���)");
					map.put("es-cr", "��������(��˹�����)");
					map.put("es-do", "��������(������ӹ��͹�)");
					map.put("es-ec", "��������(��϶��)");
					map.put("es-es", "��������(������)");
					map.put("es-es-euro", "��������(������,Euro)");
					map.put("es-gt", "��������(Σ������)");
					map.put("es-hn", "��������(�鶼��˹)");
					map.put("es-mx", "��������(ī����)");
					map.put("es-ni", "��������(�������)");
					map.put("es-pa", "��������(������)");
					map.put("es-pe", "��������(��³)");
					map.put("es-pr", "��������(�������)");
					map.put("es-py", "��������(������)");
					map.put("es-sv", "��������(�����߶�)");
					map.put("es-uy", "��������(������)");
					map.put("es-ve", "��������(ί������)");
					map.put("et-ee", "��ɳ������(��ɳ����)");
					map.put("fi-fi", "������(����)");
					map.put("fi-fi-euro", "������(����,Euro)");
					map.put("fr-be", "����(����ʱ)");
					map.put("fr-be-euro", "����(����ʱ,Euro)");
					map.put("fr-ca", "����(���ô�)");
					map.put("fr-ch", "����(��ʿ)");
					map.put("fr-fr", "����(����)");
					map.put("fr-fr-euro", "����(����,Euro)");
					map.put("fr-lu", "����(¬ɭ��)");
					map.put("fr-lu-euro", "����(¬ɭ��,Euro)");
					map.put("hr-hr", "���޵�����(���޵���)");
					map.put("hu-hu", "��������(������)");
					map.put("is-is", "������(����)");
					map.put("it-ch", "�������(��ʿ)");
					map.put("it-it", "�������(�����)");
					map.put("it-it-euro", "�������(�����,Euro)");
					map.put("iw-il", "ϣ������(��ɫ��)");
					map.put("ja-jp", "����(�ձ�)");
					map.put("ko-kr", "������(�ϳ���)");
					map.put("lt-lt", "��������(������)");
					map.put("lv-lv", "����ά����(����)(����ά��)");
					map.put("mk-mk", "�������(���������)");
					map.put("nl-be", "������(����ʱ)");
					map.put("nl-be-euro", "������(����ʱ,Euro)");
					map.put("nl-nl", "������(����)");
					map.put("nl-nl-euro", "������(����,Euro)");
					map.put("no-no", "Ų����(Ų��)");
					map.put("no-no-ny", "Ų����(Ų��,Nynorsk)");
					map.put("pl-pl", "������(����)");
					map.put("pt-br", "��������(����)");
					map.put("pt-pt", "��������(������)");
					map.put("pt-pt-euro", "��������(������,Euro)");
					map.put("ro-ro", "����������(��������)");
					map.put("ru-ru", "����(����˹)");
					map.put("sh-yu", "������˹-���޵�����(��˹����)");
					map.put("sk-sk", "˹�工����(˹�工��)");
					map.put("sl-si", "˹����������(˹��������)");
					map.put("sq-al", "������������(����������)");
					map.put("sr-yu", "����ά����(��˹����)");
					map.put("sv-se", "�����(���)");
					map.put("th-th", "̩��(̩��)");
					map.put("tr-tr", "��������(������)");
					map.put("uk-ua", "�ڿ�����(�ڿ���)");
					map.put("zh-cn", "����(��½)");
					map.put("zh-hk", "����(���)");
					map.put("zh-tw", "����(̨��)");
					map.put("zh-sg", "����(�¼���)");
					Object[] ks = map.keyArray();
					Object[] vs = map.valueArray();
					String[] arr = new String[ks.length];
					langNameArr = new String[ks.length];
					for (int i = 0; i < map.size(); i++) {
						arr[i] = ks[i].toString();
						langNameArr[i] = vs[i].toString();
					}
					langCodeArr = arr;
				}
			}
		}
		if (StringUtil.isEmpty(lang)) {
			return "����";
		}
		lang = lang.trim();
		for (int i = 0; i < langCodeArr.length; i++) {
			if (lang.equalsIgnoreCase(langCodeArr[i])) {
				return langNameArr[i];
			}
		}
		return "����";
	}

	public static String getScreen(String screen) {
		if (screen == null) {
			return "����";
		}
		if (!screen.equals("1024x768") && !screen.equals("1280x800") && !screen.equals("1440x900")
				&& !screen.equals("1366x768") && !screen.equals("1280x1024") && !screen.equals("1280x768")
				&& !screen.equals("1152x864") && !screen.equals("1600x900") && !screen.equals("1680x1050")
				&& !screen.equals("800x600") && !screen.equals("1280x960") && !screen.equals("1920x1080")
				&& !screen.equals("1280x720")) {
			return "����";
		}
		return screen;
	}

	public static String getBrowser(String useragent) {
		if (useragent.indexOf("Netscape") > 0) {
			return "Netscape";
		} else if (useragent.indexOf("Firefox/1.") > 0) {
			return "Firefox 1.x";
		} else if (useragent.indexOf("Firefox/2.") > 0) {
			return "Firefox 2.x";
		} else if (useragent.indexOf("Firefox/3.") > 0) {
			return "Firefox 3.x";
		} else if (useragent.indexOf("Safari") > 0) {
			return "Safari";
		} else if (useragent.indexOf("Opera") > 0) {
			return "Opera";
		} else if (useragent.indexOf("Chrome") > 0) {
			return "Chrome";
		} else if (useragent.indexOf("MSIE 8") > 0) {
			return "IE8";
		} else if (useragent.indexOf("MSIE 7") > 0) {
			return "IE7";
		} else if (useragent.indexOf("MSIE 6") > 0) {
			return "IE6";
		} else if (useragent.indexOf("MSIE 5") > 0) {
			return "IE5";
		} else if (useragent.indexOf("MSIE 4") > 0) {
			return "IE4";
		} else if (useragent.indexOf("MSIE 3") > 0) {
			return "IE3";
		} else {
			return "����";
		}
	}

	private static int TRANSACTION_ID_LENGTH = 32;

	private static char[] cs = ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890").toCharArray();

	private static String toPrintable(byte[] b) {
		char[] out = new char[b.length];
		for (int i = 0; i < b.length; i++) {
			int index = b[i] % cs.length;
			if (index < 0) {
				index += cs.length;
			}
			out[i] = cs[index];
		}
		return new String(out);
	}

	public static String getUniqueID() {
		byte[] b = new byte[TRANSACTION_ID_LENGTH];
		SecureRandom sr = new SecureRandom();
		sr.nextBytes(b);
		return toPrintable(b);
	}

	public static String getIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
