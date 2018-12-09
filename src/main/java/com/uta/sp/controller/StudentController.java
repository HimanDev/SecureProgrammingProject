package com.uta.sp.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.uta.sp.dao.ProfessorDao;
import com.uta.sp.dao.StudentDao;
import com.uta.sp.helper.Constants;

/**
 * Servlet implementation class StudentController
 */
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String path = request.getRequestURI().substring(request.getContextPath().length());
		if ("/student/dashboard".equals(path)) {
			if (session.getAttribute(Constants.SP_USERNAME) != null
					&& Constants.SP_USERTYPE_STUDENT.equals(session.getAttribute(Constants.SP_USERTYPE))) {
				StudentDao dao = new StudentDao();
				Integer studentId = new Integer(session.getAttribute(Constants.SP_USERID).toString());
				List<Map<String, Object>> semList = dao.selectAllSem(studentId.intValue());
				request.setAttribute("data", semList);
				request.getRequestDispatcher("/student_dashboard.jsp").forward(request, response);

			}
		} else if ("/student/grades".equals(path)) {
			if (session.getAttribute(Constants.SP_USERNAME) != null
					&& Constants.SP_USERTYPE_STUDENT.equals(session.getAttribute(Constants.SP_USERTYPE))) {
				String id = request.getParameter("semid");
				if (id.matches("^[0-9]+$")) {
					int idInt = Integer.parseInt(id);
					Integer studentId = new Integer(session.getAttribute(Constants.SP_USERID).toString());
					StudentDao dao = new StudentDao();
					Integer profID = new Integer(session.getAttribute(Constants.SP_USERID).toString());
					List<Map<String, Object>> professorSemesterList = dao.selectGrade(studentId.intValue(), idInt);
					request.setAttribute("data", professorSemesterList);
					request.getRequestDispatcher("/s_view.jsp").forward(request, response);

				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
