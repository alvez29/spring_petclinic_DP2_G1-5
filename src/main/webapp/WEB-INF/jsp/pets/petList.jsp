<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="pets">
    <h2>Pick a Dog to add</h2>

    <table id="petTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Dog</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pets}" var="pet">        
            <tr>
                <td>
        					<spring:url value="/tournaments/{tournamentId}/addpet/{petId}" var="petUrl">
                        	<spring:param name="petId" value="${pet.id}"/>
                        	<spring:param name="tournamentId" value="${tournamentId}"/>
                    		</spring:url>
                    		<a href="${fn:escapeXml(petUrl)}"><c:out value="${pet.type}: ${pet.name} - Owner: (${pet.owner.firstName} ${pet.owner.lastName}) "/></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>