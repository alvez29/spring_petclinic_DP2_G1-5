<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="hability">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#date").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2><c:if test="${hability['new']}">New </c:if>hability</h2>

        <b>Hability</b>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Name</th>
                <th>Date</th>
                <th>Capacity</th>
                <th>Breed Restriction</th>
                <th>Reward Money</th>
                <th>Circuit</th>
            </tr>
            </thead>
            <tr>
                <td><c:out value="${hability.name}"/></td>
                <td><petclinic:localDate date="${hability.date}" pattern="yyyy/MM/dd"/></td>
                <td><c:out value="${hability.capacity}"/></td>
                <td><c:out value="${hability.breedRestriction}"/></td>
                <td><c:out value="${hability.rewardMoney}"/></td>
                <td><c:out value="${hability.circuit}"></c:out></td>
            </tr>
        </table>

        <form:form modelAttribute="hability" class="form-horizontal">
            <div class="form-group has-feedback">
            	<petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="Date" name="date"/>
                <petclinic:inputField label="Capacity" name="capacity"/>
                <petclinic:selectField label="Breed Restriction" name="breedRestriction" size="5" names="${types}"/>
              	<petclinic:inputField label="Reward Money" name="rewardMoney"/>
                <petclinic:inputField label="Circuit" name="circuit"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="petId" value="${hability.id}"/>
                    <button class="btn btn-default" type="submit">Add Hability</button>
                </div>
            </div>
        </form:form>

    </jsp:body>

</petclinic:layout>
