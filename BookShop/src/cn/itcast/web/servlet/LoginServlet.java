package cn.itcast.web.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.itcast.domain.User;
import cn.itcast.service.UserService;

public class LoginServlet extends HttpServlet {

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
					request.getRequestDispatcher("/login.jsp").forward(request, response);
					return;
				}
		
		HttpSession session = request.getSession();
		
		//��ȡ����
		String username = request.getParameter("username");//���� ����
		String password = request.getParameter("password");
		
		UserService service = new UserService();
		User user = null;
		try {
			user = service.login(username,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		if(user!=null){
			//��¼�ɹ�
			//�ж��û��Ƿ�ѡ�Զ���¼
			String autoLogin = request.getParameter("autoLogin");
			if(autoLogin!=null){
				
				//�������������б���
				String username_code = URLEncoder.encode(username, "UTF-8");// %AE4%kfj
				
				Cookie cookie_username = new Cookie("cookie_username",username_code);
				Cookie cookie_password = new Cookie("cookie_password",password);
				//����cookie�ĳ־û�ʱ��
				cookie_username.setMaxAge(60*60);
				cookie_password.setMaxAge(60*60);
				//����cookie��Я��·��
				cookie_username.setPath(request.getContextPath());
				cookie_password.setPath(request.getContextPath());
				//����cookie
				response.addCookie(cookie_username);
				response.addCookie(cookie_password);
			}
			
			//����¼���û���user����浽session��
			session.setAttribute("user", user);
			//�ض�����ҳ
			response.sendRedirect(request.getContextPath());
			
		}else{
			//ʧ�� ת������¼ҳ�� �����ʾ��Ϣ
			request.setAttribute("loginInfo", "�û������������");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
		
	}
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}