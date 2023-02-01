package fr.jorisrouziere.tirelire.room.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;

@Entity(tableName = "piece")
public class Piece implements Serializable {

    @PrimaryKey
    private double value;

    private int number;

    public Piece() {
    }

    public Piece(double value) {
        this.value = value;
    }

    public Piece(double value, int number) {
        this.value = value;
        this.number = number;
    }


    public double getValue() {
        return value;
    }

    public int getNumber() {
        return number;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getTotalValue() {
        MathContext m = new MathContext(4);
        BigDecimal _value = new BigDecimal(value);
        BigDecimal _number = new BigDecimal(number);

        return _value.multiply(_number,m).doubleValue();
    }
}
