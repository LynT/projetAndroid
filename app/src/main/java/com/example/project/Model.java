package com.example.project;

public class Model {
    int id;
    String Name;

    public Model(int id,String Name) {
        this.id = id;
        this.Name = Name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

}
