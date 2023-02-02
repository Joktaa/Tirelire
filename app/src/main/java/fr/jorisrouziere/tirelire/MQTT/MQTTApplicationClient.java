package fr.jorisrouziere.tirelire.MQTT;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public final class MQTTApplicationClient {

    private static MQTTClient client;

    static public MQTTClient getInstance(Context context) {
        if (client == null){
            client = new MQTTClient(context, MQTTConstantsKt.MQTT_SERVER_URI, MQTTConstantsKt.MQTT_CLIENT_ID);
        }

        client.connect(MQTTConstantsKt.MQTT_USERNAME,MQTTConstantsKt.MQTT_PWD,client.getDefaultCbConnect(), new MessageHandler(context));
        client.subscribe(MQTTConstantsKt.MQTT_MAIN_TOPIC,MQTTConstantsKt.MQTT_QOS);
        return client;
    }

    //TODO add send here

    private static class MessageHandler implements MqttCallback
    {
        private Context context;

        public MessageHandler(Context context) {
            this.context = context;
        }

        @Override
        public void connectionLost(Throwable cause) {
            Log.d(this.getClass().getName(), "Connection lost ${cause.toString()}");
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            if(topic.equals(MQTTConstantsKt.MQTT_MAIN_TOPIC) && message != null)
            {
                Json.readJson(message.toString(), context);
                //put a toast for the withdrawn response
            }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {

        }
    }
}
