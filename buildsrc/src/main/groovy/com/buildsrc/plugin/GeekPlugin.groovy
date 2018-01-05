package com.buildsrc.plugin

import com.buildsrc.task.GeekTask
import com.buildsrc.task.ResourceInfo
import org.gradle.api.Plugin
import org.gradle.api.Project

class GeekPlugin implements Plugin<Project> {
    private HashMap<String, ResourceInfo> stringMap = new HashMap<>()
    private HashMap<String, ResourceInfo> colorMap = new HashMap<>()
    private HashMap<String, ResourceInfo> dimensMap = new HashMap<>()
    private HashMap<String, ResourceInfo> stringError = new HashMap<>()
    private HashMap<String, ResourceInfo> colorError = new HashMap<>()
    private HashMap<String, ResourceInfo> dimensError = new HashMap<>()

    @Override
    void apply(Project project) {
        GeekTask task = project.getTasks().create("checkResources", GeekTask.class)
        task.doLast {
            System.out.println(task.getStringFlag())
            if (task.getStringFlag()) {
                new Thread(){
                    @Override
                    void run() {
                        filterValues(project, 0)
                        if (!stringError.isEmpty()) {
                            System.out.println("strings info is error")
                            writeObject(stringError,"stringError.txt")
                        } else {
                            File file = new File("ResourcesError"+File.separator+"stringError.txt")
                            if(file.exists()){
                                file.delete()
                            }
                            System.out.println("strings info is ok")
                        }
                        stringError.clear()
                        stringMap.clear()
                    }
                }.start()
            }
            if (task.getColorFlag()) {
                new Thread(){
                    @Override
                    void run() {
                        filterValues(project, 1)
                        if (!colorError.isEmpty()) {
                            System.out.println("colors info is error")
                            writeObject(colorError,"colorError.txt")
                        } else {
                            File file = new File("ResourcesError"+File.separator+"colorError.txt")
                            if(file.exists()){
                                file.delete()
                            }
                            System.out.println("colors info is ok")
                        }
                        colorError.clear()
                        colorMap.clear()
                    }
                }.start()
            }
            if (task.getDimensFlag()) {
                new Thread(){
                    @Override
                    void run() {
                        filterValues(project, 2)
                        if (!dimensError.isEmpty()) {
                            System.out.println("dimens info is error")
                            writeObject(dimensError,"dimenError.txt")
                        } else {
                            File file = new File("ResourcesError"+File.separator+"dimenError.txt")
                            if(file.exists()){
                                file.delete()
                            }
                            System.out.println("dimens info is ok")
                        }
                        dimensError.clear()
                        dimensMap.clear()
                    }
                }.start()
            }
        }
    }

    void filterValues(Project project, int type) {
        if (project.childProjects.size() > 1) {
            project.childProjects.each {
                filterValues(it.value, type)
            }
        } else {
            switch (type) {
                case 0:
                    filterString("${project.projectDir}/src/main/res/values/strings.xml")
                    break
                case 1:
                    filterColor("${project.projectDir}/src/main/res/values/colors.xml")
                    break
                case 2:
                    filterDimen("${project.projectDir}/src/main/res/values/dimens.xml")
                    break
            }
        }
    }

    void filterString(String filePath) {
        File file = new File(filePath)
        if (!file.exists()) {
            return
        }
        def list = new XmlParser().parse(filePath)
        list.string.each {
            String name = it.@name
            String values = it.text()
            if (!"app_name".equals(name)) {
                if (stringMap.containsKey(name)) {
                    ResourceInfo v = stringMap.get(name)
                    if (!values.equals(v.values)) {
                        stringError.put(v.values, v.toString())
                        stringError.put(values, new ResourceInfo(name, values, filePath).toString())
                    }
                } else {
                    stringMap.put(name, new ResourceInfo(name, values, filePath))
                }
            }
        }
    }

    void filterColor(String filePath) {
        File file = new File(filePath)
        if (!file.exists()) {
            return
        }
        def list = new XmlParser().parse(filePath)
        list.color.each {
            String name = it.@name
            String values = it.text()
//            System.out.println(name + ":" + values)
            if (colorMap.containsKey(name)) {
                ResourceInfo v = colorMap.get(name)
                if (!values.equals(v.values)) {
                    colorError.put(v.values, v.toString())
                    colorError.put(values, new ResourceInfo(name, values, filePath).toString())
                }
            } else {
                colorMap.put(name, new ResourceInfo(name, values, filePath))
            }
        }
    }

    void filterDimen(String filePath) {
        File file = new File(filePath)
        if (!file.exists()) {
            return
        }
        def list = new XmlParser().parse(filePath)
        list.dimen.each {
            String name = it.@name
            String values = it.text()
//            System.out.println(name + ":" + values)
            if (dimensMap.containsKey(name)) {
                ResourceInfo v = dimensMap.get(name)
                if (!values.equals(v.values)) {
                    dimensError.put(v.values, v.toString())
                    dimensError.put(values, new ResourceInfo(name, values, filePath).toString())
                }
            } else {
                dimensMap.put(name, new ResourceInfo(name, values, filePath))
            }
        }
    }

    void writeObject(HashMap<String,String> map,String path) {

        try {
            File fileDir = new File("ResourcesError")
            if(!fileDir.exists()){
                fileDir.mkdir()
            }
            FileOutputStream outStream = new FileOutputStream("ResourcesError"+File.separator+path)

            OutputStreamWriter objectOutputStream = new OutputStreamWriter(outStream,"utf-8")

            for (String value : map.values()) {
                objectOutputStream.write(value+"\t\n")
//                System.out.println(path + value)
            }
//            System.out.println("successful")
            objectOutputStream.flush()
            outStream.flush()
            outStream.close()
        } catch (FileNotFoundException e) {

            e.printStackTrace()

        } catch (IOException e) {

            e.printStackTrace()

        }

    }
}