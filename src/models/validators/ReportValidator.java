package models.validators;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Report;
import utils.DBUtil;

public class ReportValidator {
	public static List<String> validate(Report r, Boolean reportDateDuplicateCheckFlag) {
		List<String> errors = new ArrayList<String>();

		String reportDate_error = _validateReportDate(r.getReport_date(), reportDateDuplicateCheckFlag);
		if(!reportDate_error.equals("")) {
			errors.add(reportDate_error);
		}

		String title_error = _validateTitle(r.getTitle());
		if(!title_error.equals("")) {
			errors.add(title_error);
		}

		String content_error = _validateContent(r.getContent());
		if(!content_error.equals("")) {
			errors.add(content_error);
		}

		return errors;
	}

	private static String _validateTitle(String title) {
		if(title == null || title.equals("")) {
			return "タイトルを入力してください。";
		}

		return "";
	}

	private static String _validateContent(String content) {
		if(content == null || content.equals("")) {
			return "内容を入力してください。";
		}

		return "";
	}

	private static String _validateReportDate(Date reportDate, Boolean reportDateDuplicateCheckFlag) {
		// 日報が重複していないかチェック
		if(reportDateDuplicateCheckFlag) {
			EntityManager em = DBUtil.createEntityManager();
			long report_date_count = (long) em.createNamedQuery("checkReportDate", Long.class)
					.setParameter("report_date", reportDate)
					.getSingleResult();
			em.close();
			if(report_date_count > 0) {
				return "すでに日報が作成されています。";
			}
		}

		return "";
	}
}
