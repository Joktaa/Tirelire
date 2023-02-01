package fr.jorisrouziere.tirelire.MQTT;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;

import fr.jorisrouziere.tirelire.room.Repository;
import fr.jorisrouziere.tirelire.room.models.Historique;
import fr.jorisrouziere.tirelire.room.models.Piece;

public class Json {
    public static void readJson(String message, Context context) {
        Repository repository = Repository.getInstance(context);

        try {
            JSONObject obj = new JSONObject(message).getJSONObject("mobileApp");

            if (obj.getString("action").equals("answerHistoryServer")) {
                JSONArray arr = obj.getJSONArray("precise");
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject o = arr.getJSONObject(i);
                    JSONArray coins = o.getJSONArray("coins");
                    float somme = 0;
                    for (int j = 0; i < coins.length(); j++) {
                        JSONObject coin = coins.getJSONObject(j);
                        double type = coin.getDouble("type");
                        int number = coin.getInt("number");
                        somme += type * number;
                    }
                    repository.insertOneHistorique(new Historique(
                            LocalDateTime.parse(o.getString("date")),
                            Historique.ActionType.DEPOSIT.getValue(),
                            (double) somme));
                }
            }

            else if (obj.getString("action").equals("answerData")) {
                JSONArray arr = obj.getJSONArray("precise");
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject o = arr.getJSONObject(i);
                    JSONArray coins = o.getJSONArray("coins");
                    float somme = 0;
                    for (int j = 0; j < coins.length(); j++) {
                        JSONObject coin = coins.getJSONObject(j);
                        double type = coin.getDouble("type");
                        int number = coin.getInt("number");
                        somme += type * number;
                    }
                    repository.insertOneHistorique(new Historique(
                            LocalDateTime.parse(o.getString("date")),
                            Historique.ActionType.DEPOSIT.getValue(),
                            (double) somme));
                }
                JSONArray coins = obj.getJSONArray("coins");
                for (int k = 0; k < coins.length(); k++) {
                    JSONObject coin = coins.getJSONObject(k);
                    repository.insertOnePieces(new Piece(
                            coin.getDouble("type"),
                            coin.getInt("number")
                    ));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String withdraw(int CENTIME_10, int CENTIME_20, int CENTIME_50, int EURO_1, int EURO_2) {
        return new String("{\n" +
                "   \"server\":{\n" +
                "      \"action\":\"withdrawn\",\n" +
                "      \"withdrawnDetail\":\n" +
                "         {\n" +
                "            \"0.10\":\"" + CENTIME_10 + "\",\n" +
                "            \"0.20\":\"" + CENTIME_20 + "\",\n" +
                "            \"0.50\":\"" + CENTIME_50 + "\",\n" +
                "            \"1\":\"" + EURO_1 + "\",\n" +
                "            \"2\":\"" + EURO_2 + "\",\n" +
                "         }\n" +
                "      ,\n" +
                "      \"withdrawn\":\"float\"\n" +
                "   }\n" +
                "}");
    }

    public static String historic() {
        return new String("{\n" +
                "   \"server\":{\n" +
                "      \"action\":\"requestHistoryServer\",\n" +
                "   }\n" +
                "}");
    }

    public static String historic(long timestampLastUpdate) {
        return new String("{\n" +
                "   \"server\":{\n" +
                "      \"action\":\"requestHistoryServer\",\n" +
                "      \"timestampLastUpdate\":\""+timestampLastUpdate+"\"\n" +
                "   }\n" +
                "}");
    }

    public static String allData() {
        return new String("{\n" +
                "   \"server\":{\n" +
                "      \"action\":\"requestData\",\n" +
                "   }\n" +
                "}");
    }

    public static String allData(long timestampLastUpdate) {
        return new String("{\n" +
                "   \"server\":{\n" +
                "      \"action\":\"requestData\",\n" +
                "      \"timestampLastUpdate\":\""+timestampLastUpdate+"\"\n" +
                "   }\n" +
                "}");
    }
}
