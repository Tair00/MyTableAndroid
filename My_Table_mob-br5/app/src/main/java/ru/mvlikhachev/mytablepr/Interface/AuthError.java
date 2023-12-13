package ru.mvlikhachev.mytablepr.Interface;

 class AuthError {
    private int code;

    public AuthError(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}