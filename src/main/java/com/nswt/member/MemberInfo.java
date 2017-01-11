package com.nswt.member;

import java.util.Date;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import com.nswt.cms.dataservice.MemberField;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.framework.Ajax;
import com.nswt.framework.User;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCForumMemberSchema;
import com.nswt.schema.ZDMemberCompanyInfoSchema;
import com.nswt.schema.ZDMemberPersonInfoSchema;

public class MemberInfo extends Ajax {
	
	public static Mapx init(Mapx params) {
		String UserName = User.getUserName();
		if(StringUtil.isNotEmpty(UserName)){
			Member m = new Member(UserName);
			m.fill();
			if(StringUtil.isEmpty(m.getType())){
				m.setType("Person");
			}
			if(m.getType().equals("Person")){
				params.put("MemberType","����");
			}else{
				params.put("MemberType","��ҵ");
			}
			params.putAll(m.toMapx());
			params.put("Gender",HtmlUtil.codeToRadios("Gender","Gender",m.getGender()));
			params.put("MemberLevelName",new QueryBuilder("select Name from ZDMemberLevel where ID = ?",Long.parseLong(m.getMemberLevel())).executeString());
			if(m.getStatus().equals("Y")){
				params.put("StatusName","ͨ�����");
			}else if(m.getStatus().equals("X")){
				params.put("StatusName","�ȴ����");
			}else{
				params.put("StatusName","���δͨ��");
			}
			params.put("Columns",MemberField.getColumnAndValue(m));
			if(StringUtil.isEmpty(params.getString("Logo"))){
				params.put("Logo","../Images/nophoto.jpg");
			}
		}
		return params;
	}
	
	public static Mapx initDetail(Mapx params){
		String UserName = User.getUserName();
		if(StringUtil.isNotEmpty(UserName)){
			Member m = new Member(UserName);
			m.fill();
			if(StringUtil.isEmpty(m.getType())){
				m.setType("Person");
			}
			if(m.getType().equals("Person")){
				params.put("MemberType","����");
				ZDMemberPersonInfoSchema person = new ZDMemberPersonInfoSchema();
				person.setUserName(m.getUserName());
				person.fill();
				params.putAll(person.toMapx());
			}else{
				params.put("MemberType","��ҵ");
				ZDMemberCompanyInfoSchema company = new ZDMemberCompanyInfoSchema();
				company.setUserName(m.getUserName());
				company.fill();
				params.putAll(company.toMapx());
			}
			params.putAll(m.toMapx());
			if(StringUtil.isEmpty(params.getString("Pic"))){
				params.put("PicPath","../Images/nopicture.jpg");
			}else{
				params.put("PicPath",params.getString("Pic"));
			}
		}
		return params;
	}
	
	public void doSave(){
		String UserName = $V("UserName");
		Member member = new Member(UserName);
		member.fill();
		member = MemberField.setPropValues(member,Request);
		member.setValue(Request);
		if(StringUtil.isEmpty(member.getName())){
			member.setName("ע���û�");
		}
		if(member.update()){
			if(member.getType().equalsIgnoreCase("Person")){
				ZDMemberPersonInfoSchema person = new ZDMemberPersonInfoSchema();
				person.setUserName(member.getUserName());
				if(person.fill()){
					person.setNickName(member.getName());
					person.update();
				}
			}else{
				ZDMemberCompanyInfoSchema company = new ZDMemberCompanyInfoSchema();
				company.setUserName(member.getUserName());
				if(company.fill()){
					company.setCompanyName(member.getName());
					company.setEmail(member.getEmail());
					company.update();
				}
			}
			ZCForumMemberSchema forumMember = new ZCForumMemberSchema();
			forumMember.setUserName(UserName);
			if(forumMember.fill()){
				if(StringUtil.isEmpty(forumMember.getHeadImage())&&StringUtil.isNotEmpty(member.getLogo())){
					forumMember.setHeadImage(member.getLogo());
				}
				if(StringUtil.isEmpty(forumMember.getNickName())){
					forumMember.setNickName($V("Name"));
					forumMember.setModifyUser(UserName);
					forumMember.setModifyTime(new Date());
				}
				forumMember.update();
			}
			Response.setLogInfo(1,"����ɹ�");
		}else{
			Response.setLogInfo(0,"����ʧ��");
		}
	}
	
	public void doDetailSave(){
		String UserName = $V("UserName");
		Member member = new Member(UserName);
		member.fill();
		member.setValue(Request);
		if(member.getType().equalsIgnoreCase("Person")){
			ZDMemberPersonInfoSchema person = new ZDMemberPersonInfoSchema();
			person.setUserName(member.getUserName());
			if(person.fill()){
				person.setValue(Request);
				person.update();
			}else{
				person.setValue(Request);
				person.insert();
			}
		}else{
			ZDMemberCompanyInfoSchema company = new ZDMemberCompanyInfoSchema();
			company.setUserName(member.getUserName());
			if(company.fill()){
				company.setValue(Request);
				company.update();
			}else{
				company.setValue(Request);
				company.insert();
			}
		}
		if(member.update()){
			Response.setLogInfo(1,"����ɹ�");
		}else{
			Response.setLogInfo(0,"����ʧ��");
		}
	}
	
	public void modifyPassword(){
		String UserName = $V("UserName");
		String OldPassWord = $V("OldPassWord").trim();
		String NewPassWord = $V("NewPassWord").trim();
		String ConfirmPassword = $V("ConfirmPassword").trim();
		Member member = new Member();
		member.setUserName(UserName);
		member.fill();
		String password = member.getPassword();
		if(StringUtil.md5Hex(OldPassWord).equals(password)){
			if(NewPassWord.equals(ConfirmPassword)){
				member.setPassword(NewPassWord);
				if(member.update()){
					Response.setLogInfo(1,"�޸�����ɹ�");
				}else{
					Response.setLogInfo(0,"�޸�����ʧ��");
				}
			}else{
				Response.setLogInfo(0,"������������벻һ��");
			}
		}else{
			Response.setLogInfo(0,"ԭ�������");
		}
	}
	
	public void resetPassword(){
		String UserName = $V("UserName");
		String NewPassWord = $V("NewPassWord").trim();
		String ConfirmPassword = $V("ConfirmPassword").trim();
		Member member = new Member(UserName);
		member.fill();
		if(StringUtil.isNotEmpty(member.getPWQuestion())){
			if(NewPassWord.equals(ConfirmPassword)){
				member.setPassword(NewPassWord);
				member.setPWQuestion("");
				if(member.update()){
					Response.setLogInfo(1,"��������ɹ�");
					Response.put("SiteID",member.getSiteID());
				}else{
					Response.setLogInfo(0,"��������ʧ��");
				}
			}else{
				Response.setLogInfo(0,"������������벻һ��");
			}
		}else{
			Response.setLogInfo(0,"�޸������ѹ���");
		}
	}
	
	public void getPassWord(){
		String UserName = $V("UserName");
		String URL = $V("URL");
		URL = URL.substring(0,URL.lastIndexOf("/")+1);
		if(StringUtil.isNotEmpty(UserName)){
			Member member = new Member(UserName);
			if(member.fill()){
				String SiteName = SiteUtil.getName(member.getSiteID());
				String to = member.getEmail();
				if(StringUtil.isNotEmpty(to)){
					SimpleEmail email = new SimpleEmail();
					email.setHostName("smtp.163.com");
					try {
						String pwq = StringUtil.md5Hex(member.getUserName()+System.currentTimeMillis());
						StringBuffer sb = new StringBuffer();
						sb.append("�𾴵�"+SiteName+"�û���<br/>");
						sb.append("��ã�<br/>");
						sb.append("<a href='"+URL+"GetPassword.jsp?User="+member.getUserName()+"&pwq="+pwq+"&SiteID="+member.getSiteID()+"'>�����˴��޸���������</a><br/>");
						sb.append("�������������޷�����������������Դ��ı�ģʽ�鿴�ʼ����븴�����µ�ַ��ճ�����������ַ��Ȼ�󰴡��س�����ֱ�ӷ���<br/>");
						sb.append(URL+"GetPassword.jsp?User="+member.getUserName()+"&pwq="+pwq);
						sb.append("<br/><br/>ע�����ʼ�Ϊϵͳ�Զ����ͣ�����ظ���<br/>");
						sb.append("������������������������������������������������������"+SiteName);
						member.setLoginMD5(pwq);
						email.setAuthentication("0871huhu@163.com","08715121182");
						email.addTo(to,member.getUserName());
						email.setFrom("0871huhu@163.com", SiteName);
						email.setSubject(SiteName+"���һ���������");
						email.setContent(sb.toString(), "text/html;charset=utf-8");
						if(member.update()){
							Response.setLogInfo(1,"ϵͳ�Ѿ����������������ӵ���ע��ʱ��д�ĵ������䣬�����");
							Response.put("SiteID",member.getSiteID());
						}else{
							Response.setLogInfo(0,"ϵͳ����");
						}
					} catch (EmailException e) {
						Response.setLogInfo(0,"�ʼ����ʹ���");
						e.printStackTrace();
					}
				}
			}else{
				Response.setLogInfo(0,"�û���������");
			}
		}
	}
	
}
