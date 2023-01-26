package fr.jorisrouziere.tirelire.room.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "piece")
public class Piece implements Serializable {

    @PrimaryKey
    private double value;

    public Piece() {
    }

    public Piece(double value) {
        this.value = value;
    }

    public Piece(double value, long number) {
        this.value = value;
        this.number = number;
    }

    private long number;

    public double getValue() {
        return value;
    }

    public long getNumber() {
        return number;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public double getTotalValue() {
        return value * number;
    }
}
