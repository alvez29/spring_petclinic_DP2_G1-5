<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="raceResult">
	<h2>Race Result</h2>

	<table id="raceResultsTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 200px;">Position</th>
				<th style="width: 150px;">Name</th>
				<th style="width: 120px">Time</th>
				<th style="width: 120px"> </th>
				
			</tr>
		</thead>
		<tbody>
			<c:set var="i" value="1" />
			<c:forEach items="${results}" var="result">
				<tr>
					<td><c:out value="${i}" />º</td>
					<c:set var="i" value="${i+1}" />
					<td><c:out value="${result.pet.name}" /></td>
					<td><c:out value="${result.time}" /></td>
					<td><spring:url
							value="/tournaments/race/{tournamentId}/result/{resultId}/delete"
							var="addUrl">
							<spring:param name="tournamentId" value="${tournamentId}" />
							<spring:param name="resultId" value="${result.id}" />
						</spring:url> <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Delete Result</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:if test="${empty results}">
		<h3 style="color:red">No data available</h3>
	</c:if>
</petclinic:layout>
