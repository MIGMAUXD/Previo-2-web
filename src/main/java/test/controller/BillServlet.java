package test.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import test.dao.BillDao;
import test.modelo.Bill;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Servlet implementation class BillServlet
 */
public class BillServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private BillDao billDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BillServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		this.billDao = new BillDao();
	}


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertUser(request, response);
				break;
			case "/delete":
				deleteUser(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateUser(request, response);
				break;
			default:
				listBill(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("bill-form.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Bill existingUser = billDao.selectBill(id);
		request.setAttribute("bill", existingUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("bill-form.jsp");
		dispatcher.forward(request, response);

	}

	private void insertUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ParseException {
		Date date_bill = (Date) new SimpleDateFormat("yyyy-MM-dd").parse("date_bill");
		Integer user_id = Integer.parseInt(request.getParameter("user_id"));
		Integer value = Integer.parseInt(request.getParameter("value"));
		Integer type =Integer.parseInt(request.getParameter("type"));
		String obervation = request.getParameter("obervation");
		Bill newBill = new Bill(null, date_bill, user_id, value, type, obervation);
		billDao.insertBill(newBill);
		response.sendRedirect("list");
	}

	private void listBill(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Bill> listBill = billDao.selectAllUsers();
		request.setAttribute("listBill", listBill);
		RequestDispatcher dispatcher = request.getRequestDispatcher("bill-list.jsp");
		dispatcher.forward(request, response);
	}

	private void updateUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ParseException {
		int id = Integer.parseInt(request.getParameter("id"));
		Date date_bill = (Date) new SimpleDateFormat("yyyy-MM-dd").parse("date_bill");
		Integer user_id = Integer.parseInt(request.getParameter("user_id"));
		Integer value = Integer.parseInt(request.getParameter("value"));
		Integer type =Integer.parseInt(request.getParameter("type"));
		String obervation = request.getParameter("obervation");

		Bill book = new Bill(null, date_bill, user_id, value, type, obervation);

		billDao.updateBill(book);
		response.sendRedirect("list");
	}
	
	
	private void deleteUser(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, IOException {
		        int id = Integer.parseInt(request.getParameter("id"));
		        billDao.deleteBill(id);
		        response.sendRedirect("list");

		    }
	
}
