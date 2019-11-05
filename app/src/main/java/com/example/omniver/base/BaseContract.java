package com.example.omniver.base;

public interface BaseContract {
    public interface Presenter<T>{

        void setView(T view);

        void releaseView();
    }
    public interface View{

    }
}
