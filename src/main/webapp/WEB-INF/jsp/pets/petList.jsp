<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="pet">
    <h2>Dogs</h2>
    <c:if test="${not empty tournamentId}">
    <p>Pick a Dog to add</p>
	</c:if>

    <table id="petTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Owner</th>
            <th style="width: 150px;">Dog</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pets}" var="pet">
            <tr>
                <td>
                	<c:choose>
    					<c:when test="${not empty tournamentId}">
        					<spring:url value="/tournaments/{tournamentId}/addpet/{petId}" var="petUrl">
                        	<spring:param name="eptId" value="${pet.id}"/>
                        	<spring:param name="tournamentId" value="${tournamentId}"/>
                    		</spring:url>
                    		<a href="${fn:escapeXml(petUrl)}"><c:out value="${pet.owner} ${pet.petType}"/></a>
    					</c:when>    
   						<c:otherwise>
				        	<spring:url value="/pet/{petId}" var="petUrl">
                        	<spring:param name="petId" value="${pet.id}"/>
                    		</spring:url>
                   	 		<a href="${fn:escapeXml(petUrl)}"><c:out value="${pet.owner} ${pet.petType}"/></a>
						</c:otherwise>
						</c:choose>               
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a class="btn btn-default" href='<spring:url value="/pet/new" htmlEscape="true"/>'>Add Dog</a>
</petclinic:layout>