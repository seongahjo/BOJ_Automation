package com.skel.algo.auto.question;

import java.util.Scanner;

public class Q1000 implements Question {
    @Override
    public String run(String input) {
        Scanner sc= new Scanner(input);
        int a=sc.nextInt();
        int b=sc.nextInt();
        return String.valueOf(a+b);
    }
}
