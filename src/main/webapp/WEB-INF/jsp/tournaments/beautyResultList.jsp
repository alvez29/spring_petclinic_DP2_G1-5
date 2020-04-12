<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="beautyResult">
	<h2>Beauty Contest Results</h2>

	<table id="beautyResultTable" class="table table-striped">
		<thead>
			<tr>
				<th>Position</th>
				<th>Name</th>
				<th>Haircut</th>
				<th>Haircut Diff</th>
				<th>Technique</th>
				<th>Posture</th>
				<th>Total Points</th>

			</tr>
		</thead>
		<tbody>
			<c:set var="count" value="1" scope="page"/>
			
			<c:forEach items="${beautyResults}" var="resultScore">
				<tr>
					<!-- <td><spring:url value="/tournaments/{tournamentId}/beauty/result"
							var="tournamentUrl">
							<spring:param name="tournamentId" value="${tournament.id}" />
						</spring:url> <a href="${fn:escapeXml(tournamentUrl)}"><c:out
								value="${tournament.name}" /></a></td>  -->
					<td><c:out value="${count}" />º</td>
					<td><c:out value="${resultScore.pet.name}" /></td>
					<td><c:out value="${resultScore.haircut}" /></td>
					<td><c:out value="${resultScore.haircutdif}"/></td>
					<td><c:out value="${resultScore.technique}"/></td>
					<td><c:out value="${resultScore.posture}"/></td>
					<td><c:out value="${resultScore.totalPoints}"/></td>
					<c:set var="count" value="${count+1}" scope="page"/>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
</petclinic:layout>
