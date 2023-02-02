package fr.jorisrouziere.tirelire.mqtt;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttClient extends MqttAndroidClient {

    private static final String SERVER_URI = "tcp://eu.thethings.network:1883";
    private static final String SERVER_TOPIC = "mes_ruches/devices/capteur_temperature/up";
    private static final String CLIENT_ID = "mes_ruches";
    private static final String CLIENT_PWD = "ttn-account-v2.vC-aqMRnLLzGkNjODWgy81kLqzxBPAT8_mE-L7U2C_w";


    private static MqttClient client;

    public static MqttClient getInstance(Context context)
    {
        if (client == null){
            client = new MqttClient(context, SERVER_URI, CLIENT_ID);

            MqttConnectOptions options = generateMqttConnectionOption();

            try {
                client.connect(options, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        DisconnectedBufferOptions disconnectedBufferOptions = generateDisconnectedBufferOptions();
                        System.out.println("--- connected !");
                        try {
                            client.setBufferOpts(disconnectedBufferOptions);
                            client.subscribe(SERVER_TOPIC, 0, null, generateSubscribeActionListener());
                            client.setCallback(generateMqttCallbackExtended());

                        } catch (MqttException e) {
                            Toast t = new Toast( context);
                            t.setText("Error while sub");
                            t.show();
                            throw new RuntimeException(e);
                        }


                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Toast t = new Toast( context);
                        t.setText("COnnecting failed");
                        t.show();
                        throw new RuntimeException(exception);

                    }
                });

            } catch (MqttException e) {

                throw new RuntimeException(e);

            }
        }

        return client;
    }

    @NonNull
    private static IMqttActionListener generateSubscribeActionListener() {
        return new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {

            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

            }
        };
    }

    private static DisconnectedBufferOptions generateDisconnectedBufferOptions() {
        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
        disconnectedBufferOptions.setBufferEnabled(true);
        disconnectedBufferOptions.setBufferSize(100);
        disconnectedBufferOptions.setPersistBuffer(false);
        disconnectedBufferOptions.setDeleteOldestMessages(false);
        return disconnectedBufferOptions;
    }

    private MqttClient(Context context, String serverURI, String clientId) {
        super(context, serverURI, clientId);
    }

    private void handleResponse()
    {

    }

    private static MqttConnectOptions generateMqttConnectionOption()
    {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(false);
        options.setUserName(CLIENT_ID);
        options.setPassword(CLIENT_PWD.toCharArray());

        return options;
    }

    private static MqttCallbackExtended generateMqttCallbackExtended(){
        return new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s)
            {
                Log.w(TAG,"connectComplete");
            }

            @Override
            public void connectionLost(Throwable throwable)
            {
                Log.w(TAG,"connectionLost");
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception
            {
                Log.w(TAG, "messageArrived : " + mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken)
            {
                Log.w(TAG, "deliveryComplete");
            }
        };
    }
}
