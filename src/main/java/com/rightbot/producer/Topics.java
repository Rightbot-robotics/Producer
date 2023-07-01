package com.rightbot.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Topics {

    private Topic1 topic1;
    private Topic2 topic2;
    private Topic3 topic3;
    private Topic4 topic4;
    private Topic5 topic5;
    private Topic6 topic6;

    public Topic1 getTopic1() {
        return topic1;
    }

    public void setTopic1(Topic1 topic1) {
        this.topic1 = topic1;
    }

    public Topic2 getTopic2() {
        return topic2;
    }

    public void setTopic2(Topic2 topic2) {
        this.topic2 = topic2;
    }

    public Topic3 getTopic3() {
        return topic3;
    }

    public void setTopic3(Topic3 topic3) {
        this.topic3 = topic3;
    }

    public Topic4 getTopic4() {
        return topic4;
    }

    public void setTopic4(Topic4 topic4) {
        this.topic4 = topic4;
    }

    public Topic5 getTopic5() {
        return topic5;
    }

    public void setTopic5(Topic5 topic5) {
        this.topic5 = topic5;
    }

    public Topic6 getTopic6() {
        return topic6;
    }

    public void setTopic6(Topic6 topic6) {
        this.topic6 = topic6;
    }

    // Inner classes for Topics

    public static class Topic1 {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    public static class Topic2 {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    public static class Topic3 {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    public static class Topic4 {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    public static class Topic5 {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    public static class Topic6 {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}

