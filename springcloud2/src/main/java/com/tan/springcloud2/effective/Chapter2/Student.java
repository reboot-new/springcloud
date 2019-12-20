package com.tan.springcloud2.effective.Chapter2;

public class Student extends Person {
    private String xuehao;
    private String nianji;
    private String banji;

    private Student(Builder builder) {
        super(builder);
        this.xuehao = builder.getXuehao();
        this.banji = builder.getBanji();
        this.nianji = builder.getNianji();
    }

    public static class Builder extends Person.Builder {
        public String getXuehao() {
            return xuehao;
        }

        public void setXuehao(String xuehao) {
            this.xuehao = xuehao;
        }

        private String xuehao;

        public String getNianji() {
            return nianji;
        }

        public Builder setNianji(String nianji) {
            this.nianji = nianji;
            return this;
        }

        public String getBanji() {
            return banji;
        }

        public Builder setBanji(String banji) {
            this.banji = banji;
            return this;
        }

        private String nianji;
        private String banji;

        public Builder(String xuehao, String name) {
            super(name);
            this.xuehao = xuehao;
        }

        @Override
        public Student build() {
            return new Student(this);
        }
    }

    public static void main(String[] args) {
        Student s = new Student.Builder("new1212", "tan_alpha")
                .setNianji("43")
                .build();
        System.out.println(s);
    }
}
