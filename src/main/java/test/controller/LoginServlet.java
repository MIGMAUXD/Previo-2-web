package test.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import test.dao.UserDao;

import java.io.IOException;

/**
 * Servlet implementation class BillsServlet
 */

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	    private UserDao userDAO = new UserDao(); // Instancia de tu clase UserDAO

	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        String username = request.getParameter("username");
	        String password = request.getParameter("pass");

	        // Verificación de credenciales utilizando la clase UserDAO
	        boolean isValidUser = userDAO.isValidUser(username, password);

	        if (isValidUser) {
	            // Credenciales válidas, redirigir a la página de éxito
	            response.sendRedirect("bill-list.jsp");
	        } else {
	            // Credenciales inválidas, redirigir a la página de error
	            response.sendRedirect("");
	        }
	    }
	}

