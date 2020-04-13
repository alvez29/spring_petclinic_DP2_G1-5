<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="addHabilityResult">
    <h2>
        Add new Result
    </h2>
    <form:form modelAttribute="result" class="form-horizontal" id="add-result-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Time" name="time"/>
            <petclinic:inputField label="Low fails" name="lowFails"/>
            <petclinic:inputField label="Medium fails" name="MediumFails"/>
            <petclinic:inputField label="Big fails" name="BigFails"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                        <button class="btn btn-default" type="submit">Add Result</button>
            </div>
        </div>
    </form:form>
</petclinic:layout>
