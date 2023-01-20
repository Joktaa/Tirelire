package fr.jorisrouziere.tirelire.room.models;

public enum PieceType {
    CENTIME_10(0.1), CENTIME_20(0.2), CENTIME_50(0.5), EURO_1(1), EURO_2(2);

    public final double value;

    PieceType(double type){
        this.value=type;
    }

    public double getValue() {
        return value;
    }

}

