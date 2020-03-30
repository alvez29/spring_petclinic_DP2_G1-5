<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
0
<petclinic:layout pageName="tournaments">
    <jsp:body>
        <h2>
            <c:if test="${pet['add']}">Add </c:if> Pet
        </h2>
        <form:form modelAttribute="pet" class="form-horizontal">
            <input type="hidden" name="id" value="${pet.id}"/>
            <div class="form-group has-feedback">
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${pet['add']}">
                            <button class="btn btn-default" type="submit">Add Dog</button>
                        </c:when>
                    </c:choose>
                </div> 
            </div>
        </form:form>
        <c:if test="${!pet['add']}">
        </c:if>
    </jsp:body>
</petclinic:layout>