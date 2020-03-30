<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="judge">
    <h2>
        <c:if test="${judge['new']}">New </c:if> Judge
    </h2>
    <form:form modelAttribute="judge" class="form-horizontal">
        <div class="form-group has-feedback">
            <petclinic:inputField label="First Name" name="firstName"/>
            <petclinic:inputField label="Last Name" name="lastName"/>
            <petclinic:inputField label="Contact" name="contact"/>
            <petclinic:inputField label="City" name="city"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${judge['new']}">
                        <button class="btn btn-default" type="submit">Add Judge</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Judge</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>