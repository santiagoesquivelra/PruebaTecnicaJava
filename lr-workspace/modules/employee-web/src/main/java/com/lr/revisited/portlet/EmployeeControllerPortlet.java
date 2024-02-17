package com.lr.revisited.portlet;

import com.lr.revisited.constants.EmployeeControllerPortletKeys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;


import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;


/**
 * @author Sanks
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=EmployeeController",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + EmployeeControllerPortletKeys.EMPLOYEECONTROLLER,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)



public class EmployeeControllerPortlet extends MVCPortlet {

    private static final String apiUrl = "https://8e7c6b8a-fc46-4674-a529-4ebec57295d3.mock.pstmn.io/customers";
    private static final Logger logger = Logger.getLogger(EmployeeControllerPortlet.class.getName());

    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {

        try {
            // Crear la URL y abrir la conexión HTTP
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configurar la solicitud
            connection.setRequestMethod("GET");

            // Obtener la respuesta
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Leer la respuesta y procesarla
                processApiResponse(connection, renderRequest);

            } else {
                handleErrorResponse(responseCode);
            }

            // Cerrar la conexión
            connection.disconnect();
        } catch (Exception e) {
            handleException(e);
        }

        System.out.println("EmployeeControllerPortlet.doView()  -- >");

        super.doView(renderRequest, renderResponse);
    }

    private void processApiResponse(HttpURLConnection connection, RenderRequest renderRequest)
            throws IOException, JSONException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            String processedResponse = processResponseString(response.toString());
            JSONArray jsonArray = JSONFactoryUtil.createJSONArray(processedResponse);

            List<Customer> customerList = buildCustomerList(jsonArray);

            renderRequest.setAttribute("customerList", customerList);
            logCustomerList(customerList);
        }
    }

    private String processResponseString(String response) throws UnsupportedEncodingException {
        String processedResponse = new String(response.getBytes("ISO-8859-1"), "UTF-8");
        processedResponse = processedResponse.replace("?", "\"");
        processedResponse = processedResponse.replace("\"Calle 53 # 31A \" 56\"", "\"Calle 53 # 31A - 56\"");
        System.out.println("Respuesta del endpoint: " + processedResponse);
        return processedResponse;
    }

    private List<Customer> buildCustomerList(JSONArray jsonArray) {
        List<Customer> customerList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            Customer customer = new Customer();
            customer.setName(jsonObject.getString("name"));
            customer.setAge(jsonObject.getInt("age"));
            customer.setPhoneNo(jsonObject.getString("phoneNumber"));
            customer.setAddress(jsonObject.getString("address"));

            customerList.add(customer);
        }

        return customerList;
    }

    private void logCustomerList(List<Customer> customerList) {
        for (Customer customer : customerList) {
            System.out.println("Customer: " + customer.getName() + ", " +
                    customer.getAge() + ", " + customer.getPhoneNo() + ", " + customer.getAddress());
        }
    }

    private void handleErrorResponse(int responseCode) {
        System.out.println("Error al consumir la API. Código de respuesta: " + responseCode);
    }

    private void handleException(Exception e) {
        e.printStackTrace();
        logger.log(Level.SEVERE, "Error al procesar la solicitud", e);
    }
}
