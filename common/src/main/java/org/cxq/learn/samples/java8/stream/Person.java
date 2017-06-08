package org.cxq.learn.samples.java8.stream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cxq on 2017/6/8.
 */
public class Person {
    private String name;
    private Date birthDate;

    public Person(String name, Date birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    static List<Person> genarateList(int num) {
        List<Person> people = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            people.add(new Person("cxq" + i, new Date()));
        }
        return people;
    }

    @Override
    public String toString() {
        return "name:" + this.name;
    }
}
