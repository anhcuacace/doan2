package com.example.doan2;

public class Data {
    private int gas,temperature;

    public Data() {
    }

    public Data(int gas, int temperature) {
        this.gas = gas;
        this.temperature = temperature;
    }

    public int getGas() {
        return gas;
    }

    public void setGas(int gas) {
        this.gas = gas;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
}
