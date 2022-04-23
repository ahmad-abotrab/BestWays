package com.company.Model;

import java.util.ArrayList;

public class ObjectInState implements Cloneable {
    String type;
    String code;
    Position position;
    Expelling expelling;
    Truck truck;


    public ObjectInState(ObjectInState objectInState) {
        this.type = objectInState.type;
        this.code = objectInState.code;
        this.truck = new Truck(objectInState.truck);
        this.position = new Position(objectInState.position);
        this.expelling = new Expelling(objectInState.expelling);
    }

    public ObjectInState() {
        this.truck = new Truck();
        this.expelling = new Expelling();
        this.type = "";
        this.position = new Position();
        this.code = "";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Expelling getExpelling() {
        return expelling;
    }

    public void setExpelling(Expelling expelling) {
        this.expelling = expelling;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    @Override
    public String toString() {
        return "ObjectInState{" +
                "type='" + type + '\'' +
                ", code='" + code + '\'' +
                ", position=" + position.toString() +
                ", expelling=" + expelling.toString() +
                ", truck=" + truck +
                '}' + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectInState)) return false;

        ObjectInState that = (ObjectInState) o;

        if (!getType().equals(that.getType())) return false;
        if (!getCode().equals(that.getCode())) return false;
        if (!getPosition().equals(that.getPosition())) return false;
        if (!getExpelling().equals(that.getExpelling())) return false;
        return getTruck().equals(that.getTruck());
    }

    @Override
    public int hashCode() {
        int result = getType().hashCode();
        result = 31 * result + getCode().hashCode();
        result = 31 * result + getPosition().hashCode();
        result = 31 * result + getExpelling().hashCode();
        result = 31 * result + getTruck().hashCode();
        return result;
    }
}
