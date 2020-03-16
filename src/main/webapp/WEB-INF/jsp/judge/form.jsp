<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="judge">
	<h2>Judge Information</h2>

	<table class="table table-striped">
		<tr>
			<th>Name</th>
			<td><b><c:out value="${judge.name}" /></b></td>
		</tr>
		<tr>
			<th>Contact</th>
			<td><c:out value="${judge.contact}" /></td>
		</tr>
		<tr>
			<th>City</th>
			<td><c:out value="${judge.city}" /> spectators</td>
		</tr>
	</table>

</petclinic:layout>