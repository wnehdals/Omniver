package com.example.omniver.evaluate_service;

import com.example.omniver.base.BaseContract;
import com.example.omniver.main_service.MainContract;
import com.example.omniver.model.Climate;

public interface EvaluationContract {
    interface View extends BaseContract.View{
        void executeSave(boolean state);
    }
    interface  Presenter extends BaseContract.Presenter<EvaluationContract.View>{
        @Override
        void setView(EvaluationContract.View view);

        @Override
        void releaseView();
    }
}
