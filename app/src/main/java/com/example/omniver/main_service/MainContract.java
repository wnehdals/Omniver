package com.example.omniver.main_service;

import com.example.omniver.base.BaseContract;

public interface MainContract {

    interface View extends BaseContract.View{

    }
    interface  Presenter extends BaseContract.Presenter<View>{
        @Override
        void setView(View view);

        @Override
        void releaseView();
    }
}
