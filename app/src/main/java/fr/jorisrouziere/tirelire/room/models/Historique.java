package fr.jorisrouziere.tirelire.room.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity(tableName = "historique")
public class Historique implements Serializable {
    public static long last_id = 0;

    @PrimaryKey
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

    public String getDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
        return date.format(formatter);
    }

    public String getMontantString() {
        return montant.toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public enum ActionType{
        WITHDRAW("Retrait"), DEPOSIT("dépot");

        private final String value;

        ActionType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
