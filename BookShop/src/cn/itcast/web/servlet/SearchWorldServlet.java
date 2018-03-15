package cn.itcast.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cn.itcast.service.ProductService;

public class SearchWorldServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ùؼ���
				String word = request.getParameter("word");
				
				//��ѯ�ùؼ��ֵ�������Ʒ
				ProductService service = new ProductService();
				List<Object> productList = null;
				try {
					productList = service.findProductByWord(word);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				//["xiaomi","huawei",""...]
				
				//ʹ��json��ת�����߽�����򼯺�ת��json��ʽ���ַ���
				/*JSONArray fromObject = JSONArray.fromObject(productList);
				String string = fromObject.toString();
				System.out.println(string);*/
				
				Gson gson = new Gson();
				String json = gson.toJson(productList);
				System.out.println(json);
				
				response.setContentType("text/html;charset=UTF-8");
				
				response.getWriter().write(json);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
