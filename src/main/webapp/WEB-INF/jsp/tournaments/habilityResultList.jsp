<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="results">
	<h2>Results</h2>

	<table id="habilityResultsTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 200px;">Position</th>
				<th style="width: 150px;">Name</th>
				<th style="width: 120px">Time</th>
				<th style="width: 120px">Low fails</th>
				<th style="width: 120px">Medium fails</th>
				<th style="width: 120px">Big fails</th>
				<th style="width: 120px">Time with penalties</th>

			</tr>
		</thead>
		<tbody>
		
			<c:set var="i" value="1"/>
			<c:forEach items="${results}" var="result">
			

				<tr>
					<td><c:out value="${i}"/>º</td>
					<c:set var="i" value="${i+1}"/>
					<td><c:out value="${result.pet.name}" /></td>
					<td><c:out value="${result.time}" /></td>
					<td><c:out value="${result.lowFails}" /></td>
					<td><c:out value="${result.mediumFails}" /></td>
					<td><c:out value="${result.bigFails}" /></td>
					<td><c:out value="${result.totalResult}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	
</petclinic:layout>
