package com.zkp.breath.designpattern.mvp;

public class MvpPresenterImp implements IMvpPresenter {

    private final IMvpView iMvpView;
    private final IMvpModel iMvpModel;

    public MvpPresenterImp(IMvpView iMvpView) {
        this.iMvpView = iMvpView;
        iMvpModel = new MvpModelImp();
    }

    @Override
    public void requestData() {
        String data = iMvpModel.getData();
        iMvpView.refreshUi(data);
    }
}
