package com.buildsrc.task;

/**
 * Created by aotuman on 2018/1/3.
 */

public class ResourceInfo {
    public String name;
    public String path;
    public String values;

    public ResourceInfo(String name,String values,String path){
        this.name = name;
        this.path = path;
        this.values = values;
    }

    @Override
    public String toString() {
        return "name:"+name
                +"--values:"+values
                +"--path:"+path;
    }
}
