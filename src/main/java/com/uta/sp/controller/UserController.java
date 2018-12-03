package com.uta.sp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uta.sp.dao.UserDao;
import com.uta.sp.dto.User;
import com.uta.sp.helper.Helper;

/**
 * Servlet implementation class UserController
 */
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			User clientUser = (User) Helper.getJavaObject(request, User.class);
			UserDao userDao = new UserDao();
			User dbUser = userDao.getOne(clientUser);
			if (dbUser == null) {
				request.setAttribute("error", "username or password incorrect");
			} else if (dbUser.getLoginAttepmts() > 3) {
				request.setAttribute("error", "maximus limit exceeded, please contact admin");
			} else if (!dbUser.check(clientUser)) {
				dbUser.setLoginAttepmts(dbUser.getLoginAttepmts() + 1);
				userDao.update(dbUser);
				request.setAttribute("error", "username or password incorrect");

			} else {
				dbUser.setLoginAttepmts(0);
				userDao.update(dbUser);
				request.setAttribute("error", "login successful");
			}
			request.setAttribute("userName", clientUser.getUserName());
			request.setAttribute("password", clientUser.getPassword());

			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
