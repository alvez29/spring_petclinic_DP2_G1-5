<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="tournaments">
	<h2>Tournaments</h2>

	<table id="tournamentsTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 200px;">Name</th>
				<th style="width: 150px;">Date</th>
				<th style="width: 120px">Reward Money</th>
				<th style="width: 120px">Status</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${tournaments}" var="tournament">
				<tr>
					<td><spring:url value="/tournaments/{tournamentId}"
							var="tournamentUrl">
							<spring:param name="tournamentId" value="${tournament.id}" />
						</spring:url> <a href="${fn:escapeXml(tournamentUrl)}"><c:out
								value="${tournament.name}" /></a></td>
					<td><c:out value="${tournament.date}" /></td>
					<td><c:out value="${tournament.rewardMoney}" /></td>
					<td><c:out value="${tournament.status}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<table id="buttonsTournamentTable">
		<tr>
			<td align='center'><spring:url value="/race/new" var="addUrl">
				</spring:url> <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Add
					New Race</a></td>
			<td align='center'><spring:url value="/beauty/new" var="addUrl">
				</spring:url> <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Add
					New Beauty Contest</a></td>
			<td align='center'><spring:url value="/hability/new" var="addUrl">
				</spring:url> <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Add
					New Hability Contest</a></td>
		</tr>
	</table>
</petclinic:layout>
