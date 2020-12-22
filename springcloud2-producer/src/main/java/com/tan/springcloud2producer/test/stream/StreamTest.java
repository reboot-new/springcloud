package com.tan.springcloud2producer.test.stream;

import cn.hutool.http.HttpUtil;
import com.tan.springcloud2producer.entity.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class StreamTest {
    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();

        list.add(new Student("张三",3));
        list.add(new Student("张三1",3));
        list.add(new Student("张三2",3));
        list.add(new Student("张三3",6));
        list.add(new Student("张三4",3));
        list.add(new Student("张三56",3));
        list.add(new Student("张三6",9));
        list.add(new Student("张三7",2));
        list.add(new Student("张三8",12));
        list.add(new Student("张三9",43));
        list.add(new Student("张三10",51));

        Student[] objects = list.stream().filter(s -> s.getAge() > 10).map(student -> {
            if (student.getName().startsWith("张三")) {
                student.setName("lisi");
            }
            return student;
        }).toArray(Student[] ::  new);
        Stream.of(objects).forEach(student -> System.out.println(student));


//        System.out.println(list);
    }
}
