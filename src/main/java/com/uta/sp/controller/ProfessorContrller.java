package com.uta.sp.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.uta.sp.dao.ProfessorDao;
import com.uta.sp.dao.UserDao;
import com.uta.sp.helper.Constants;

/**
 * Servlet implementation class ProfessorContrller
 */
public class ProfessorContrller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(ProfessorContrller.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProfessorContrller() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String path = request.getRequestURI().substring(request.getContextPath().length());
		LOG.info(path);
		if ("/professor/dashboard".equals(path)) {
			if (session.getAttribute(Constants.SP_USERNAME) != null
					&& Constants.SP_USERTYPE_PROFESSOR.equals(session.getAttribute(Constants.SP_USERTYPE))) {
				ProfessorDao dao = new ProfessorDao();
				Integer profID = new Integer(session.getAttribute(Constants.SP_USERID).toString());
				List<Map<String, Object>> professorSemesterList = dao.getProfessorSemester(profID.intValue());
				request.setAttribute("data", professorSemesterList);
				request.getRequestDispatcher("/professor_dashboard.jsp").forward(request, response);

			}
		} else if ("/professor/grades".equals(path)) {
			if (session.getAttribute(Constants.SP_USERNAME) != null
					&& Constants.SP_USERTYPE_PROFESSOR.equals(session.getAttribute(Constants.SP_USERTYPE))) {
				String id = request.getParameter("psid");
				if (id.matches("^[0-9]+$")) {
					int idInt = Integer.parseInt(id);
					ProfessorDao dao = new ProfessorDao();
					Integer profID = new Integer(session.getAttribute(Constants.SP_USERID).toString());
					List<Map<String, Object>> professorSemesterList = dao.getStudentAndGrade(idInt, profID);
					request.setAttribute("data", professorSemesterList);
					request.getRequestDispatcher("/p_view.jsp").forward(request, response);

				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("update user grade");
		HttpSession session = request.getSession();
		if (session.getAttribute(Constants.SP_USERNAME) != null
				&& Constants.SP_USERTYPE_PROFESSOR.equals(session.getAttribute(Constants.SP_USERTYPE))) {
			ProfessorDao dao = new ProfessorDao();
			Integer profID = new Integer(session.getAttribute(Constants.SP_USERID).toString());
			String id = request.getParameter("sid");
			String psid = request.getParameter("psid");
			String grade = request.getParameter("grade");
			if(id.matches("^[0-9]+$") &&
					grade.matches("^[A-D]{1}$")) {
				int sid = Integer.parseInt(id);
				new UserDao().updateGrade(sid, profID, grade);
				response.sendRedirect("/sp/professor/grades?psid="+psid);

			}

		}

	}

}
