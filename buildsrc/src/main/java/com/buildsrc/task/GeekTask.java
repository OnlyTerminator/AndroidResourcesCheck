package com.buildsrc.task;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

import groovy.util.XmlParser;

/**
 * Created by aotuman on 2018/1/3.
 */

public class GeekTask extends DefaultTask {
    private boolean strings;
    private boolean colors;
    private boolean dimens;
    public boolean getStringFlag() {
        return strings;
    }
    @Input
    public void checkString(boolean flag) {
        this.strings = flag;
    }

    public boolean getColorFlag() {
        return colors;
    }
    @Input
    public void checkColors(boolean flag) {
        this.colors = flag;
    }

    @Input
    public void checkDimens(boolean flag){
        this.dimens = flag;
    }

    public boolean getDimensFlag(){
        return dimens;
    }
    @TaskAction
    void sayGreeting() {
        System.out.printf("%s, %s!\n", getStringFlag(), getColorFlag());
    }

}
