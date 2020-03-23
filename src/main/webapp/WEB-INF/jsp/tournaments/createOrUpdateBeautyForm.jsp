<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="beauty">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#date").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2><c:if test="${beauty['new']}">New </c:if>beauty</h2>

        <b>Beauty</b>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Name</th>
                <th>Date</th>
                <th>Capacity</th>
                <th>Breed Restriction</th>
                <th>Reward Money</th>
                <th>Place</th>
            </tr>
            </thead>
            <tr>
                <td><c:out value="${beauty.name}"/></td>
                <td><petclinic:localDate date="${beauty.date}" pattern="yyyy/MM/dd"/></td>
                <td><c:out value="${beauty.capacity}"/></td>
                <td><c:out value="${beauty.breedRestriction}"/></td>
                <td><c:out value="${beauty.rewardMoney}"/></td>
                <td><c:out value="${beauty.place}"/></td>
                <td><c:out value="${beauty.status}"/></td>
            </tr>
        </table>

        <form:form modelAttribute="beauty" class="form-horizontal">
            <div class="form-group has-feedback">
            	<petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="Date" name="date"/>
                <petclinic:inputField label="Capacity" name="capacity"/>
                <petclinic:selectField label="Breed Restriction" name="breedRestriction" size="5" names="${types}"/>
              	<petclinic:inputField label="Reward Money" name="rewardMoney"/>
                <petclinic:inputField label="Place" name="place"/>
                <c:if test="${!beauty['new']}">
                <petclinic:selectField label="Status" name="status" size="3" names="${statusTypes}"/>
                </c:if>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="petId" value="${beauty.id}"/>
                    <button class="btn btn-default" type="submit">Add Beauty</button>
                </div>
            </div>
        </form:form>

    </jsp:body>

</petclinic:layout>
