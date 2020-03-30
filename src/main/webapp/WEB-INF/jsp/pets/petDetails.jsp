<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="pet">
	<h2>Pet Information</h2>

	<table class="table table-striped">
		<tr>
			<th>Owner</th>
			<td><c:out value="${pet.owner}" /></td>
		</tr>
		<tr>
			<th>Dog breed</th>
			<td><c:out value="${pet.petType}" /></td>
		</tr>
	</table>

</petclinic:layout>