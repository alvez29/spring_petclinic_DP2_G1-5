<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="judge">
    <h2>Judges</h2>
    <c:if test="${not empty tournamentId}">
    <p>Pick a Judge to add</p>
	</c:if>

    <table id="judgeTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Name</th>
            <th style="width: 150px;">Contact</th>
            <th style="width: 120px">City</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${judges}" var="judge">
            <tr>
                <td>
                	<c:choose>
    					<c:when test="${not empty tournamentId}">
        					<spring:url value="/tournaments/{tournamentId}/addjudge/{judgeId}" var="judgeUrl">
                        	<spring:param name="judgeId" value="${judge.id}"/>
                        	<spring:param name="tournamentId" value="${tournamentId}"/>
                    		</spring:url>
                    		<a href="${fn:escapeXml(judgeUrl)}"><c:out value="${judge.firstName} ${judge.lastName}"/></a>
    					</c:when>    
   						<c:otherwise>
				        	<spring:url value="/judge/{judgeId}" var="judgeUrl">
                        	<spring:param name="judgeId" value="${judge.id}"/>
                    		</spring:url>
                   	 		<a href="${fn:escapeXml(judgeUrl)}"><c:out value="${judge.firstName} ${judge.lastName}"/></a>
						</c:otherwise>
						</c:choose>               
                </td>
                <td>
                    <c:out value="${judge.contact}"/>
                </td>
				<td>
                    <c:out value="${judge.city}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a class="btn btn-default" href='<spring:url value="/judge/new" htmlEscape="true"/>'>Add Judge</a>
</petclinic:layout>