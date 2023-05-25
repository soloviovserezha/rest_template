package org.example;

import org.example.config.MyConfig;
import org.example.model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class App {

    public static void main( String[] args ) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);

        Communication communication = context.getBean("communication", Communication.class);

        final String JSESSIONID = communication.getAllUsers()
                .getHeaders()
                .getValuesAsList("Set-Cookie")
                .get(0);

        User addUser = new User(3L, "James", "Brown", (byte) 26);
        User editUser = new User(3L, "Thomas", "Shelby", (byte) 26);

        String part1 = communication.addUser(addUser, JSESSIONID).getBody();
        String part2 = communication.editUser(editUser, JSESSIONID).getBody();
        String part3 = communication.deleteUser(editUser, JSESSIONID).getBody();

        String code = part1 + part2 + part3;

        System.out.println(code + "; length - " + code.length());

        context.close();
    }
}
