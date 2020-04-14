<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="addBeautyResult">
    <h2>Add New Result</h2>
    <form:form modelAttribute="resultScore" class="form-horizontal" id="add-result-form">
        <div class="form-group has-feedback">
        	
            <petclinic:inputField label="Haircut" name="haircut"/>
        	<petclinic:inputField label="HaircutDif" name="haircutdif"/>
        	<petclinic:inputField label="Technique" name="technique"/>
        	<petclinic:inputField label="Posture" name="posture"/>        
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-default" type="submit">Add Result</button>
            </div>
        </div>
    </form:form>
</petclinic:layout>