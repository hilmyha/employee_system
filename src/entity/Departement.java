/*
 * Copyright (c) 2022. Hilmy Ahmad Haidar. All Right Reserved.
 */

package entity;

public class Departement {
    private int id;
    private String name;
    private int honor;
    private int allowance;
    private int transport;

    public Departement() {
    }

    public Departement(int id, String name, int honor, int allowance, int transport) {
        this.id = id;
        this.name = name;
        this.honor = honor;
        this.allowance = allowance;
        this.transport = transport;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHonor() {
        return honor;
    }

    public void setHonor(int honor) {
        this.honor = honor;
    }

    public int getAllowance() {
        return allowance;
    }

    public void setAllowance(int allowance) {
        this.allowance = allowance;
    }

    public int getTransport() {
        return transport;
    }

    public void setTransport(int transport) {
        this.transport = transport;
    }

    @Override
    public String toString() {
        return name;
    }
}
