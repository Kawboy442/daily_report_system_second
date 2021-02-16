package controllers.reports;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Report;
import models.validators.ReportValidator;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsUpdateServlet
 */
@WebServlet("/reports/update")
public class ReportsUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReportsUpdateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String _token = (String)request.getParameter("_token");
		if(_token != null && _token.equals(request.getSession().getId())) {
			EntityManager em = DBUtil.createEntityManager();

			Report r = em.find(Report.class, (Integer)(request.getSession().getAttribute("report_id")));

			r.setTitle(request.getParameter("title"));
			r.setContent(request.getParameter("content"));

			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			try {
				java.util.Date startTime = (java.util.Date) inputFormat.parse(request.getParameter("start_time"));
				java.util.Date endTime = (java.util.Date) inputFormat.parse(request.getParameter("end_time"));
				java.sql.Timestamp startTimestamp = new java.sql.Timestamp(startTime.getTime());
				java.sql.Timestamp endTimestamp = new java.sql.Timestamp(endTime.getTime());
				r.setStart_time(startTimestamp);
				r.setEnd_time(endTimestamp);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			r.setNegotiations_status(Integer.parseInt(request.getParameter("negotiations_status")));
			r.setNegotiations_content(request.getParameter("negotiations_content"));

			r.setUpdated_at(new Timestamp(System.currentTimeMillis()));

			List<String> errors = ReportValidator.validate(r, false);
			if(errors.size() > 0) {
				em.close();

				request.setAttribute("_token", request.getSession().getId());
				request.setAttribute("report", r);
				request.setAttribute("errors", errors);

				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/edit.jsp");
				rd.forward(request, response);
			} else {
				em.getTransaction().begin();
				em.getTransaction().commit();
				em.close();
				request.getSession().setAttribute("flush", "更新が完了しました。");

				request.getSession().removeAttribute("report_id");

				response.sendRedirect(request.getContextPath() + "/reports/index");
			}
		}
	}

}
