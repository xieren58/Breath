package com.zkp.breath.review;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created b Zwp on 2019/8/23.
 */
public class SerializableDemo {

    public static void main(String[] args) {
        //Initializes The Object
        Bean bea = new Bean();
        bea.setName("name");
        bea.setId("id");
        System.out.println(bea);

        //Write Obj to File
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:/tempTR.txt"))) {
            oos.writeObject(bea);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Read Obj from File
        File file = new File("D:/tempTR.txt");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Bean bean = (Bean) ois.readObject();
            System.out.println(bean);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private static class Bean implements Serializable {

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Bean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
