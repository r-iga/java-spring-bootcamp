package bootcamp.ex02;

/**
 * 演習 02-1: オブジェクト指向 — 解答例
 */

abstract class Animal {
    protected String name;
    protected int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public abstract String speak();

    public void introduce() {
        System.out.println("私は" + name + "、" + age + "歳です。" + speak());
    }
}

interface Trainable {
    void performTrick(String trickName);

    default void sit() {
        performTrick("お座り");
    }
}

class Dog extends Animal implements Trainable {
    public Dog(String name, int age) {
        super(name, age);
    }

    @Override
    public String speak() {
        return "ワンワン！";
    }

    @Override
    public void performTrick(String trickName) {
        System.out.println(name + "が" + trickName + "をします！");
    }
}

class Cat extends Animal {
    public Cat(String name, int age) {
        super(name, age);
    }

    @Override
    public String speak() {
        return "ニャーニャー！";
    }
}

public class Exercise02 {

    public static void main(String[] args) {
        var dog = new Dog("ポチ", 3);
        var cat = new Cat("タマ", 5);

        dog.introduce();
        cat.introduce();
        dog.sit();
        dog.performTrick("握手");

        System.out.println("--- 全動物の紹介 ---");
        Animal[] animals = {dog, cat};
        for (var animal : animals) {
            animal.introduce();
        }
    }
}
