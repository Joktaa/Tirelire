package fr.jorisrouziere.tirelire.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Historique {
    public static long last_id = 0;

    private long id;
    private LocalDateTime date;
    private String type;
    private Double montant;

    public Historique(LocalDateTime date, String type, Double montant) {
        this.id = last_id++;
        this.date = date;
        this.type = type;
        this.montant = montant;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }
    public String getDateString() {
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
            return date.format(formatter);
        } else {
            return date.toString();
        }
    }

    public String getType() {
        return type;
    }

    public Double getMontant() {
        return montant;
    }
    public String getMontantString() {
        return montant.toString();
    }
}
