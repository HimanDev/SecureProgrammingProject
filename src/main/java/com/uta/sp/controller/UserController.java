package com.uta.sp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.uta.sp.dao.ProfessorDao;
import com.uta.sp.dao.StudentDao;
import com.uta.sp.dao.UserDao;
import com.uta.sp.dto.Professor;
import com.uta.sp.dto.Student;
import com.uta.sp.dto.User;
import com.uta.sp.helper.Constants;
import com.uta.sp.helper.Helper;

/**
 * Servlet implementation class UserController
 */
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG=Logger.getLogger(SPFilter.class);


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
				LOG.error("invalid login request from ip-address => "+request.getRemoteAddr());
				request.setAttribute("error", "username or password incorrect");
			} else if (dbUser.getLoginAttepmts() > 3) {
				LOG.error(dbUser.getLoginAttepmts() +" no of failed login attempt => "+request.getRemoteAddr());
				request.setAttribute("error", "maximus limit exceeded, please contact admin");
			} else if (!dbUser.check(clientUser)) {
				LOG.error("invalid login request for "+clientUser.getUserName()+" from ip-address => "+request.getRemoteAddr());
				dbUser.setLoginAttepmts(dbUser.getLoginAttepmts() + 1);
				userDao.update(dbUser);
				request.setAttribute("error", "username or password incorrect");

			} else {
				LOG.info("successfull login request for "+clientUser.getUserName()+" from ip-address => "+request.getRemoteAddr());
				dbUser.setLoginAttepmts(0);
				userDao.update(dbUser);
				request.setAttribute("error", "login successful");
				if(dbUser.getProfessorId()!=0) {
					Professor professor=new Professor();
					professor.setProfessorId(dbUser.getProfessorId());
					professor = new ProfessorDao().getOne(professor);
					HttpSession session = request.getSession();
					
					session.setAttribute(Constants.SP_USERNAME, professor.getName());
					session.setAttribute(Constants.SP_USERID, professor.getProfessorId());
					session.setAttribute(Constants.SP_USERTYPE, Constants.SP_USERTYPE_PROFESSOR);
					response.sendRedirect(request.getContextPath()+"/professor/dashboard");
				}else if(dbUser.getStudentId()!=0) {
					Student student=new Student();
					student.setStudentId(dbUser.getStudentId());
					student = new StudentDao().getOne(student);
					HttpSession session = request.getSession();
					
					session.setAttribute(Constants.SP_USERNAME, student.getName());
					session.setAttribute(Constants.SP_USERID, student.getStudentId());
					session.setAttribute(Constants.SP_USERTYPE, Constants.SP_USERTYPE_STUDENT);
					response.sendRedirect(request.getContextPath()+"/student/dashboard");
				}
				
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
