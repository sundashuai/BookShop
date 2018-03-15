package cn.itcast.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import cn.itcast.domain.User;
import cn.itcast.service.UserService;
import cn.itcast.utils.CommonsUtils;
import cn.itcast.utils.MailUtils;

public class RegisterServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		//��֤��У��
				//���ҳ���������֤
				String checkCode_client = request.getParameter("checkCode");
				//�������ͼƬ�����ֵ���֤��
				String checkCode_session = (String) request.getSession().getAttribute("checkcode_session");
				//�ȶ�ҳ��ĺ�����ͼƬ�����ֵ���֤���Ƿ�һ��
				if(!checkCode_session.equals(checkCode_client)){
					request.setAttribute("loginInfo", "������֤�벻��ȷ");
					request.getRequestDispatcher("/register.jsp").forward(request, response);
					return;
				}
		//��ñ�����
		Map<String, String[]> properties = request.getParameterMap();
		User user = new User();
		try {
			//�Լ�ָ��һ������ת��������Stringת��Date��
			ConvertUtils.register(new Converter() {
				@Override
				public Object convert(Class clazz, Object value) {
					//��stringת��date
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date parse = null;
					try {
						parse = format.parse(value.toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return parse;
				}
			}, Date.class);
			//ӳ���װ
			BeanUtils.populate(user, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		//private String uid;
		user.setUid(CommonsUtils.getUUID());
		//private String telephone;
		user.setTelephone(null);
		//private int state;//�Ƿ񼤻�
		user.setState(0);
		//private String code;//������
		String activeCode = CommonsUtils.getUUID();
		user.setCode(activeCode);
		
		
		//��user���ݸ�service��
		UserService service = new UserService();
		boolean isRegisterSuccess = service.regist(user);
		
		//�Ƿ�ע��ɹ�
		if(isRegisterSuccess){
			//���ͼ����ʼ�
			String emailMsg = "��ϲ��ע��ɹ���������������ӽ��м����˻�"
					+ "<a href='http://localhost:8080/BookShop/active?activeCode="+activeCode+"'>"
							+ "http://localhost:8080/BookShop/active?activeCode="+activeCode+"</a>";
			try {
				MailUtils.sendMail(user.getEmail(), emailMsg);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			
			
			//��ת��ע��ɹ�ҳ��
			response.sendRedirect(request.getContextPath()+"/registerSuccess.jsp");
		}else{
			//��ת��ʧ�ܵ���ʾҳ��
			response.sendRedirect(request.getContextPath()+"/registerFail.jsp");
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
