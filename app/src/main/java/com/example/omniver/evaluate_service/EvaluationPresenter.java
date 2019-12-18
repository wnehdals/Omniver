package com.example.omniver.evaluate_service;

public class EvaluationPresenter implements EvaluationContract.Presenter{
    private EvaluationContract.View evaluationView;
    private EvaluationInteractor evaluationInteractor;

    public EvaluationPresenter(EvaluationContract.View evaluationView, EvaluationInteractor evaluationInteractor) {
        this.evaluationView = evaluationView;
        this.evaluationInteractor = evaluationInteractor;
    }

    @Override
    public void setView(EvaluationContract.View view) {
        this.evaluationView = view;
    }

    @Override
    public void releaseView() {

    }
    public void requestSave(String timeStemp,double tempAverage, String imgPath, float grade){
        evaluationView.executeSave(evaluationInteractor.processSave(timeStemp, tempAverage, imgPath, grade));
    }
}

