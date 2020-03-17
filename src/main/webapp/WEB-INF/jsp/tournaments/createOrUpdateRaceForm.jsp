<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="race">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#date").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2><c:if test="${race['new']}">New </c:if>Race</h2>

        <b>Race</b>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Name</th>
                <th>Date</th>
                <th>Capacity</th>
                <th>Breed Restriction</th>
                <th>Reward Money</th>
                <th>Canodrome</th>
            </tr>
            </thead>
            <tr>
                <td><c:out value="${race.name}"/></td>
                <td><petclinic:localDate date="${race.date}" pattern="yyyy/MM/dd"/></td>
                <td><c:out value="${race.capacity}"/></td>
                <td><c:out value="${race.breedRestriction}"/></td>
                <td><c:out value="${race.rewardMoney}"/></td>
                <td><c:out value="${race.canodrome}"></c:out></td>
            </tr>
        </table>

        <form:form modelAttribute="race" class="form-horizontal">
            <div class="form-group has-feedback">
            	<petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="Date" name="date"/>
                <petclinic:inputField label="Capacity" name="capacity"/>
                <petclinic:selectField label="Breed Restriction" name="breedRestriction" size="5" names="${types}"/>
              	<petclinic:inputField label="Reward Money" name="rewardMoney"/>
                <petclinic:inputField label="Canodrome" name="canodrome"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="petId" value="${race.id}"/>
                    <button class="btn btn-default" type="submit">Add Race</button>
                </div>
            </div>
        </form:form>

    </jsp:body>

</petclinic:layout>
