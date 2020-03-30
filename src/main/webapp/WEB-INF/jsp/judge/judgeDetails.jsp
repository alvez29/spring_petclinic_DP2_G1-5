<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="judge">
	<h2>Judge Information</h2>

	<table class="table table-striped">
		<tr>
			<th>First Name</th>
			<td><c:out value="${judge.firstName}" /></td>
		</tr>
		<tr>
			<th>Last Name</th>
			<td><c:out value="${judge.lastName}" /></td>
		</tr>
		<tr>
			<th>Contact</th>
			<td><c:out value="${judge.contact}" /></td>
		</tr>
		<tr>
			<th>City</th>
			<td><c:out value="${judge.city}" /></td>
		</tr>
	</table>

</petclinic:layout>