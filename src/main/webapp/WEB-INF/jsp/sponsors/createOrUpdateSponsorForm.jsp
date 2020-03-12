<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="tournaments">
    <jsp:attribute name="customScript">
    </jsp:attribute>
    <jsp:body>
        <h2><c:if test="${sponsor['new']}">New </c:if>Sponsor</h2>
        <b>Sponsor</b>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Name</th>
                <th>Money</th>
                <th>URL</th>
            </tr>
            </thead>
            <tr>
                <td><c:out value="${sponsor.name}"/></td>
                <td><c:out value="${sponsor.money}"/></td>
                <td><c:out value="${sponsor.url}"/></td>
            </tr>
        </table>

        <form:form modelAttribute="sponsor" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="Money" name="money"/>
                <petclinic:inputField label="URL" name="url"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="petId" value="${sponsor.id}"/>
                    <button class="btn btn-default" type="submit">Add Sponsor</button>
                </div>
            </div>
        </form:form>
        <br/>
    </jsp:body>

</petclinic:layout>
