package cn.itcast.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cn.itcast.domain.Category;
import cn.itcast.domain.Order;
import cn.itcast.domain.Product;
import cn.itcast.service.AdminService;
import cn.itcast.utils.BeanFactory;

public class AdminServlet extends BaseServlet {
	
	//���ݶ���id��ѯ���������Ʒ��Ϣ
	public void findOrderInfoByOid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//���oid
		String oid = request.getParameter("oid");
		
		//�ý���ϵķ�ʽ���б���----��web����service������
		//ʹ�ù���+����+�����ļ�
		AdminService service = (AdminService) BeanFactory.getBean("adminService");
		
		List<Map<String,Object>> mapList = service.findOrderInfoByOid(oid);
		
		Gson gson = new Gson();
		String json = gson.toJson(mapList);
		System.out.println(json);
		/*[
		 * 	{"shop_price":4499.0,"count":2,"pname":"���루Lenovo��С��V3000�����","pimage":"products/1/c_0034.jpg","subtotal":8998.0},
		 *  {"shop_price":2599.0,"count":1,"pname":"��Ϊ Ascend Mate7","pimage":"products/1/c_0010.jpg","subtotal":2599.0}
		 *]*/
		response.setContentType("text/html;charset=UTF-8");
		
		response.getWriter().write(json);
		
	}
	//����������
	public void findCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminService service = (AdminService) BeanFactory.getBean("adminService");
		List<Category> categoryList=service.findCategory();
		request.setAttribute("categoryList", categoryList);
		request.getRequestDispatcher("/admin/category/list.jsp").forward(request, response);
	}
	//���������Ʒ�б�
	public void findAllProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminService service = (AdminService) BeanFactory.getBean("adminService");
		List<Product> productsList = service.findAllProducts();
		request.setAttribute("productsList", productsList);
		request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
	}
	
	//������еĶ���
	public void findAllOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//������еĶ�����Ϣ----List<Order>
		
		AdminService service = (AdminService) BeanFactory.getBean("adminService");
		List<Order> orderList = service.findAllOrders();
		
		request.setAttribute("orderList", orderList);
		
		request.getRequestDispatcher("/admin/order/list.jsp").forward(request, response);
		
	}

	public void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//�ṩһ��List<Category> ת��json�ַ���
		AdminService service = (AdminService) BeanFactory.getBean("adminService");
		List<Category> categoryList = service.findAllCategory();
		
		Gson gson = new Gson();
		String json = gson.toJson(categoryList);
		
		response.setContentType("text/html;charset=UTF-8");
		
		response.getWriter().write(json);
		
	}
	


	
}
