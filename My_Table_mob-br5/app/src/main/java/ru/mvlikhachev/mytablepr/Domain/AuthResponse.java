package ru.mvlikhachev.mytablepr.Domain;

class AuthResult<T> {
    private T data;

    public AuthResult(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public static class Authorized<T> extends AuthResult<T> {
        public Authorized(T data) {
            super(data);
        }
    }

    public static class Unauthorized<T> extends AuthResult<T> {
        public Unauthorized(T data) {
            super(data);
        }
    }


}