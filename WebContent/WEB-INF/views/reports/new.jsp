<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
	<c:param name="content">
		<c:choose>
			<c:when test="${report != null}">
				<h2>日報 新規登録ページ</h2>
				<form method="POST" action="<c:url value='/reports/create' />">
					<c:if test="${errors != null}">
    					<div id="flush_error">
        					入力内容にエラーがあります。<br />
        					<c:forEach var="error" items="${errors}">
            					・<c:out value="${error}" /><br />
        					</c:forEach>

    					</div>
					</c:if>
						<label for="report_date">日付</label><br />
						<input type="date" name="report_date" value="<fmt:formatDate value='${report.report_date}' pattern='yyyy-MM-dd' />" />
						<br /><br />

						<label for="name">氏名</label><br />
						<c:out value="${sessionScope.login_employee.name}" />
						<br /><br />

						<label for="title">タイトル</label><br />
						<input type="text" name="title" value="${report.title}" />
						<br /><br />

						<label for="title">始業時間</label><br />
						<input type="time" name="start_time" value="${report.start_time}" />
						<br /><br />

						<label for="title">終業時間</label><br />
						<input type="time" name="end_time" value="${report.end_time}" />
						<br /><br />

						<label for="content">内容</label><br />
						<textarea name="content" rows="10" cols="50">${report.content}</textarea>
						<br /><br />

						<label for="negotiations_status">商談状況</label><br />
						<select name="negotiations_status">
							<option value="1"<c:if test="${report.negotiations_status == 1}"> selected</c:if>>商談中</option>
    						<option value="2"<c:if test="${report.negotiations_status == 2}"> selected</c:if>>商談成立</option>
    						<option value="3"<c:if test="${report.negotiations_status == 3}"> selected</c:if>>商談不成立</option>
						</select>
						<br /><br />

						<label for="negotiations_content">商談状況内容</label><br />
						<textarea name="negotiations_content" rows="10" cols="50"></textarea>
						<br /><br />

						<input type="hidden" name="_token" value="${_token}" />
						<button type="submit">投稿</button>
				</form>
			</c:when>
			<c:otherwise>
				<h2>お探しのデータは見つかりませんでした。</h2>
			</c:otherwise>
		</c:choose>

		<p>
			<a href="<c:url value='/reports/index' />">一覧に戻る</a>
		</p>
	</c:param>
</c:import>