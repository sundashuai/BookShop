package cn.itcast.web.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import cn.itcast.domain.Category;
import cn.itcast.domain.Product;
import cn.itcast.service.AdminService;
import cn.itcast.utils.BeanFactory;
import cn.itcast.utils.CommonsUtils;

public class AdminUpdateProductServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		//1����ȡ����
		Map<String, String[]> properties = request.getParameterMap();
		//2����װ����
		Product product = new Product();
		try {
			BeanUtils.populate(product, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

		//��λ��Product�Ѿ���װ���----���������ݷ�װ���
		//�ֶ����ñ���û������
		//2����private String pimage;
		product.setPimage("products/1/c_02.jpg");
		//3����private String pdate;//�ϼ�����
		product.setPdate(new Date());
		//4����private int pflag;//��Ʒ�Ƿ����� 0����δ�¼�
		product.setPflag(0);

		//3���������ݸ�service��
		AdminService service = (AdminService) BeanFactory.getBean("adminService");
		try {
			service.updateProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//��ת���б�ҳ��
		response.sendRedirect(request.getContextPath()+"/admin?method=findAllProducts");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
