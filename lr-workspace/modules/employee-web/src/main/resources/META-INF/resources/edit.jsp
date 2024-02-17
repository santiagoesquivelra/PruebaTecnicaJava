<%@ include file="/init.jsp" %>

<portlet:actionURL name="saveEmployee" var="saveEmployeeURL" >
	
</portlet:actionURL>
<h1>Crea una nueva persona</h1>

<aui:form name="fm" action="${saveEmployeeURL}">

 <aui:input label="Nombre" name="name" type="text">
 	<aui:validator name="required"/>
 	<aui:validator name="custom" errorMessage="El formato correcto es: Nombre Apellido (con la primera mayuscula y el espacio entre los nombres o apellidos)">
 		function(value, obj, ruleValue) {
 			return /^[A-Z][a-z]+( [A-Z][a-z]+){0,4}$/.test(value);
 		}
 	</aui:validator>
 	<aui:validator name="custom" errorMessage="Este campo no puede ser mayor a 50 caracteres">
 		function(value, obj, ruleValue) {
 			return value.length <= 50;
 		}
 	</aui:validator>
 </aui:input>
 
 <aui:input name="address" label="Direccion" type="text">
 	<aui:validator name="required"/>
 	<aui:validator name="custom" errorMessage="El formato de address debe ser: XXXX 99 # 99XXX - 99XXX">
 		function(value, obj, ruleValue) {
 			return /^\w{4} \d{2} # \d{2}\w{3} - \d{2}\w{3}$/.test(value);
 		}
 	</aui:validator>
 </aui:input>
 	
 <aui:input name="phoneNo" label="Numero de celular" type="number">
 	<aui:validator name="required"/>
 	<aui:validator name="custom" errorMessage="El formato de telefono debe ser: 9999999999">
 		function(value, obj, ruleValue) {
 			return /^\d{10}$/.test(value);
 		}
 	</aui:validator>
 </aui:input>
 	
 <aui:input name="age" label="Edad" type="number">
 	<aui:validator name="required"/>
 	<aui:validator name="custom" errorMessage="El campo age debe tener el formato 99">
        function(value, obj, ruleValue) {
            return /^\d{1,2}$/.test(value);
        }
    </aui:validator>
 </aui:input>
	
 <aui:button-row>
 	<aui:button cssClass="btn btn-primary" type="submit"/>
 </aui:button-row>

</aui:form>

