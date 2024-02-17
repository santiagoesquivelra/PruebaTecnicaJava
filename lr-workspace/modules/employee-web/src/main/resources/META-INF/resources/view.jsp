<%@ include file="/init.jsp" %>

<p>
	<!-- <b><liferay-ui:message key="employeecontroller.caption"/></b> -->
</p>

<portlet:renderURL var="createEditRender"> 
	<portlet:param name="mvcRenderCommandName" value="/create/edit"/>
</portlet:renderURL>

<aui:button cssClass="btn btn-primary" name="Crear cliente" type="submit" href="${createEditRender}">
</aui:button>
<p><br>

<table class="table table-bordered">
    <thead>
        <tr>
            <th>Nombre</th>
            <th>Edad</th>
            <th>N�mero de Tel�fono</th>
            <th>Direcci�n</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="customer" items="${customerList}">
            <tr>
                <td>${customer.name}</td>
                <td>${customer.age}</td>
                <td>${customer.phoneNo}</td>
                <td>${customer.address}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
