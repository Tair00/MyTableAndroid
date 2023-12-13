package ru.mvlikhachev.mytablepr.Domain;

public class DataHolder {
    private static DataHolder instance;
    private String email;

    private DataHolder() {
        // Приватный конструктор для предотвращения создания экземпляра класса извне
    }

    public static synchronized DataHolder getInstance() {
        if (instance == null) {
            instance = new DataHolder();
        }
        return instance;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}

