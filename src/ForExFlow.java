
/*
            ¢*€*€*€*€*€*€*€*€*€*€*¥*¥*¥*€*€*€*€*€*€*€*€*€*€*¢
            £                  ForEx Flow ©                 £
            £               Alpha version 0.1               £
            ¢*$*$*$*$*$*$*$*$*$*$*¥*¥*¥*$*$*$*$*$*$*$*$*$*$*¢

*/

import com.alurachallenges.devcode.APIConnect;
import models.ForExAPI;
import models.APICalcs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Scanner;

public class ForExFlow {
    public static void main(String[] args){
        Scanner dataEntryUser = new Scanner(System.in);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        double cantidad;
        String forex;
        String DivisaLocal;
        String ERapiKey = "58f3a1afc10418c0136106b1";
        String APIlink = "https://openexchangerates.org/api/currencies.json";
        String ExRateList = APIConnect.HTTPMethods(APIlink);

    // Parsear la respuesta a un JsonObject
        JsonObject ListaDivisas = JsonParser.parseString(ExRateList).getAsJsonObject();

        System.out.println("""
                ¢*€*€*€*€*€*€*€*€*€*€*¥*¥*¥*€*€*€*€*€*€*€*€*€*€*¢
                £                  ForEx Flow ©                 £
                £               Alpha version 0.1               £
                ¢*$*$*$*$*$*$*$*$*$*$*¥*¥*¥*$*$*$*$*$*$*$*$*$*$*¢
                
                Tu sitio de consultas
       
                ¢*€*€*€*€*€*€*€*€*€*€*¥*¥*¥*€*€*€*€*€*€*€*€*€*€*€*€*€*¢
                £  Bienvenid@ a nuestra plataforma de consultas       £
                £  Foreign Exchange Flow (Forex Flow) aún está        £
                £  en fase alpha, eso significa que pueden haber      £
                £  errores e inestabilidad en el uso.                 £
                £  Agradecemos que tenga paciencia y nos brinde       £
                £  su apoyo en el feedback si encuentra algún error,  £
                £  lo resolveremos lo más pronto posible.             £
                ¢*$*$*$*$*$*$*$*$*$*$*¥*¥*¥*$*$*$*$*$*$*$*$*$*$*$*$*$*¢
                """);

        System.out.println("""
                Listado de divisas más consultadas
            
                1. ARS   Pesos Argentina            8. COP   Pesos Colombia
                2. USD   Dólares americanos         9. CUP   Pesos Cuba
                3. BOB   Pesos Bolivia              10. EUR  Euros
                4. BRL   Reales Brasil              11. JPY  Yen Japón
                5. BTC   Bitcoins                   12. MXN  Pesos México
                6. CLP   Pesos Chile                13. PYG  Guaraní Paraguay
                7. CNY   Yuan China
                """);
        boolean keepRunning = true;
        while (keepRunning){
            boolean ProvenCurrency1 = false;
            boolean ProvenCurrency2 = false;
        do{
        // Solicita al usuario que ingrese la nomenclatura de la divisa
        System.out.println("Escriba la nomenclatura de su divisa que desea convertir: ");
        // Lee la entrada del usuario, la convierte a mayúsculas y la almacena en DivisaLocal
        DivisaLocal = dataEntryUser.nextLine().toUpperCase();

        // Verifica si el objeto JSON ListaDivisas contiene la clave ingresada por el usuario
        if (ListaDivisas.has(DivisaLocal)) {
            // Si la clave existe, establece ProvenCurrency1 en true
            ProvenCurrency1 = true;
        } else {
            // Si la nomenclatura no existe, imprime un mensaje de error
            System.out.println("Divisa No Encontrada. Intente nuevamente");
        }
    } while(!ProvenCurrency1);

        do{
        System.out.println("Escriba la nomenclatura de la divisa a la que desea cambiar: ");
        forex = dataEntryUser.nextLine().toUpperCase();
        if (ListaDivisas.has(forex)) {
            ProvenCurrency2 = true;
        } else {
            System.out.println("Disculpe, la divisa no fue encontrada. Intente nuevamente");
        }
    } while(!ProvenCurrency2);

        System.out.println("Escriba la cifra que desea convertir: ");
        cantidad = dataEntryUser.nextDouble();
        dataEntryUser.nextLine(); //Limpieza del buffer

    String SendQuery = "https://v6.exchangerate-api.com/v6/" + ERapiKey + "/pair/" + DivisaLocal + "/" + forex;

    String AttemptToNegotiate = APIConnect.HTTPMethods(SendQuery);
            //System.out.println("Respuesta API: " + AttemptToNegotiate); //depuracion: imprime respuesta de la api

    //Procesa la respuesta de la API y obtiene la tasa de conversión
            JsonObject jsonResponse = JsonParser.parseString(AttemptToNegotiate).getAsJsonObject();
            if (jsonResponse.has("conversion_rate")){ //verifica si la respuesta contiene la tasa de conversion
                double conversionRate = jsonResponse.get("conversion_rate").getAsDouble();
                //System.out.println("Tasa de Conversion " + conversionRate); //Depuracion imprime la tasa de conversion
                //crea un objeto ForExApi y APICalcs para calcular la conversión
                ForExAPI forexAPI = new ForExAPI(conversionRate);
                APICalcs rate = new APICalcs(forexAPI);

                System.out.println("Su divisa al día de hoy: " + rate.getOutput() + " " + forex);

                System.out.println("""
                   Quiere realizar otra consulta?
                   Escriba s para continuar / n para finalizar""");
                String SNFlag = dataEntryUser.nextLine().trim().toLowerCase();
                    if (SNFlag.equals("n")) {
                        keepRunning = false;

                        System.out.println("""
                                Gracias por utilizar nuestro servicio. Puede contactarme por nuestras redes
                                Github: https://github.com/SuperMarianWorld
                                Linkedin: https://www.linkedin.com/in/mariano-maldonado-810847288/
                                Alura: https://app.aluracursos.com/user/cyberwargamesproductions/
                                E-mail: cyberwargamesproductions@gmail.com
        
                                    { Dev</>Code } © Todos los derechos reservados""");
                    }
            }
        }
        dataEntryUser.close();
    }
}