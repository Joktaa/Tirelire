package fr.jorisrouziere.tirelire.room.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "pieces")
public class Pieces implements Serializable {

    @PrimaryKey
    private long id;

    private long deux;

    private long un;

    private long cinquante;

    private long vingt;

    private long dix;

    public long getId() {
        return id;
    }

    public long getDeux() {
        return deux;
    }

    public long getUn() {
        return un;
    }

    public long getCinquante() {
        return cinquante;
    }

    public long getVingt() {
        return vingt;
    }

    public long getDix() {
        return dix;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDeux(long deux) {
        this.deux = deux;
    }

    public void setUn(long un) {
        this.un = un;
    }

    public void setCinquante(long cinquante) {
        this.cinquante = cinquante;
    }

    public void setVingt(long vingt) {
        this.vingt = vingt;
    }

    public void setDix(long dix) {
        this.dix = dix;
    }

    public Double getSomme() {
        return deux * 2 + un + cinquante * 0.5 + vingt * 0.20 + dix * 0.10;
    }
}
