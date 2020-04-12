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

			</tr>
		</thead>
		<tbody>
			<c:set var="i" value="1"/>
			<c:forEach items="${results}" var="result">
				<tr>
					<td><c:out value="${i}"/>º</td>
					<c:set var="i" value="${i+1}"/>
					<td><c:out value="${result.pet.name}" /></td>
					<td><c:out value="${result.time}"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>
