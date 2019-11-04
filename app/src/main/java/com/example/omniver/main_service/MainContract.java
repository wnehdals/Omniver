package com.example.omniver.main_service;

import com.example.omniver.base.BaseContract;
import com.example.omniver.model.Climate;

public interface MainContract {

    interface View extends BaseContract.View{
        void onReceiveClimateData(Climate climate);
    }
    interface  Presenter extends BaseContract.Presenter<View>{
        @Override
        void setView(View view);

        @Override
        void releaseView();
    }
}
