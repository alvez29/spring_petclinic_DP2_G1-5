<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="tournaments">

	<h2>Tournament Information</h2>


	<table class="table table-striped">
		<tr>
			<th>Name</th>
			<td><b><c:out value="${tournament.name}" /></b></td>
		</tr>
		<tr>
			<th>Date</th>
			<td><c:out value="${tournament.date}" /></td>
		</tr>
		<tr>
			<th>Capacity</th>
			<td><c:out value="${tournament.capacity}" /> spectators</td>
		</tr>
		<tr>
			<th>Breed Restriction</th>
			<td><c:out value="${tournament.breedRestriction.name}" /></td>
		</tr>
		<tr>
			<th>First Prize</th>
			<td><c:out value="${tournament.firstClassified}" /> EUR</td>
		</tr>
		<tr>
			<th>Second Prize</th>
			<td><c:out value="${tournament.secondClassified}" /> EUR</td>
		</tr>
		<tr>
			<th>Third Prize</th>
			<td><c:out value="${tournament.thirdClassified}" /> EUR</td>
		</tr>
		<c:if test="${tournament['class']['name'] == 'org.springframework.samples.petclinic.model.Race'}">
		<tr>
			<th>Canodrome</th>
			<td><c:out value="${tournament.canodrome}"/></td>
		</tr>
		</c:if>
		<c:if test="${tournament['class']['name'] == 'org.springframework.samples.petclinic.model.Beauty'}">
		<tr>
			<th>Place</th>
			<td><c:out value="${tournament.place}"/></td>
		</tr>
		</c:if>
		
		<c:if test="${tournament['class']['name'] == 'org.springframework.samples.petclinic.model.Hability'}">
		<tr>
			<th>Circuit</th>
			<td><c:out value="${tournament.circuit}"/></td>
		</tr>
		</c:if>
		
		
	</table>



	<br />
	<br />
	<br />
	
	<h2>Participating Dogs</h2>

	<table class="table table-striped">
		<c:forEach var="pet" items="${tournament.pets}">
			<tr>
				<td valign="top">
					<dl class="dl-horizontal">
						<dt>Name</dt>
						<dd>
							<c:out value="${pet.name}" />
						</dd>
						<dt>Race</dt>
						<dd>
							<c:out value="${pet.type.name}" />
						</dd>
					</dl>
				</td>
			</tr>
		</c:forEach>
	</table>
	<spring:url value="/pet/tournament/{tournamentId}" var="addUrl">
		<spring:param name="tournamentId" value="${tournament.id}" />
	</spring:url>
	<a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Add New Dog</a>

	<br />
	<br />
	<br />
	<h2>Sponsors</h2>

	<table class="table table-striped">
		<c:forEach var="sponsor" items="${tournament.sponsors}">
			<tr>
				<td valign="top">
					<dl class="dl-horizontal">
						<dt>Name</dt>
						<dd>
							<c:out value="${sponsor.name}" />
						</dd>
						<dt>Money</dt>
						<dd>
							<c:out value="${sponsor.money}" />
						</dd>
						<dt>URL</dt>
						<dd>
							<c:out value="${sponsor.url}" />
						</dd>
					</dl>
				</td>
			</tr>
		</c:forEach>
	</table>
	<spring:url value="{tournamentId}/sponsors/add" var="addUrl">
		<spring:param name="tournamentId" value="${tournament.id}" />
	</spring:url>
	<a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Add New Sponsor</a>

</petclinic:layout>
