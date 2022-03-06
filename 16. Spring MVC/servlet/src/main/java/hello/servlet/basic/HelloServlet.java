package hello.servlet.basic;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("HelloServlet.service");
		System.out.println("request = " + request);
		System.out.println("response = " + response);

		String username = request.getParameter("username");
		System.out.println("username = " + username);

		response.setContentType("text/plain"); //
		response.setCharacterEncoding("utf-8"); // 이 두개는 http 헤더에 들어갈 것이다.
		response.getWriter().write("hello " + username); // http 메시지 바디에 값이 들어간다.
	}
}
