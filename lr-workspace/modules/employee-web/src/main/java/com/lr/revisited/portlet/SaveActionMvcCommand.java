package com.lr.revisited.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.lr.revisited.constants.EmployeeControllerPortletKeys;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

@Component(
    immediate = true,
    property = {
        "javax.portlet.name=" + EmployeeControllerPortletKeys.EMPLOYEECONTROLLER,
        "mvc.command.name=saveEmployee"
    },
    service = MVCActionCommand.class
)
public class SaveActionMvcCommand extends BaseMVCActionCommand {
	
	 // URL del servicio web al que deseas hacer la solicitud POST
    public static final String serviceUrl = "https://8e7c6b8a-fc46-4674-a529-4ebec57295d3.mock.pstmn.io/customers";


    @Override
    protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) {
        try {
            processSaveAction(actionRequest);
        } catch (Exception e) {
            handleException(e);
        }
    }

    private void processSaveAction(ActionRequest actionRequest) throws IOException {
        String name = ParamUtil.get(actionRequest, "name", "");
        String address = ParamUtil.get(actionRequest, "address", "");
        String phoneNo = ParamUtil.get(actionRequest, "phoneNo", "");
        int age = ParamUtil.get(actionRequest, "age", 0);

        System.out.println(name + " - " + address + " - " + phoneNo + " - " + age);
        System.out.println("SaveActionMvcCommand.doProcessAction()");

        // Crear la conexi�n
        HttpURLConnection connection = createConnection(serviceUrl);

        // Configurar la conexi�n para realizar una solicitud POST
        configureConnectionForPost(connection);

        // Crear los datos que se enviar�n en la solicitud POST
        String postData = "name=" + name + "&address=" + address + "&phoneNo=" + phoneNo + "&age=" + age;
        byte[] postDataBytes = postData.getBytes(StandardCharsets.UTF_8);

        // Enviar los datos en el cuerpo de la solicitud
        sendDataInRequest(connection, postDataBytes);

        // Obtener y procesar la respuesta del servicio web
        processWebServiceResponse(connection);
    }

    private HttpURLConnection createConnection(String serviceUrl) throws IOException {
        URL url = new URL(serviceUrl);
        return (HttpURLConnection) url.openConnection();
    }

    private void configureConnectionForPost(HttpURLConnection connection) throws IOException {
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
    }

    private void sendDataInRequest(HttpURLConnection connection, byte[] postDataBytes) throws IOException {
        try (OutputStream os = connection.getOutputStream()) {
            os.write(postDataBytes);
            os.flush();
        }
    }

    private void processWebServiceResponse(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        System.out.println("Codigo de respuesta metodo: " + responseCode);

        try (InputStream is = connection.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // Procesar la respuesta para corregir los caracteres especiales
            String responseData = new String(response.toString().getBytes("ISO-8859-1"), "UTF-8");

            // Eliminar los signos de interrogaci�n para el parseo JSON
            responseData = responseData.replaceAll("\\?", "");

            // La variable 'response' ahora contiene la respuesta del servicio web
            System.out.println("Respuesta del servicio: " + responseData);
        }
    }

    private void handleException(Exception e) {
        // Manejar excepciones generales
        e.printStackTrace();
    }
}
