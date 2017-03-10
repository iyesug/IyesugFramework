package com.test.iyesug.iyesugframework.data.source;



/**
 * Created by Administrator on 2017/2/24.
 */
public class Respository implements MyDataSource {

    private LocalDatas localDatas;
    private RemoteDatas remoteDatas;

    public Respository() {
        localDatas=new LocalDatas();
        remoteDatas=new RemoteDatas();
    }

    @Override
    public void getDatas(int page, int size, LoadCallBack loadCallBack) {

        remoteDatas.getDatas(page,size,loadCallBack);
    }

    @Override
    public void getData(LoadCallBack loadCallBack) {
        remoteDatas.getData(loadCallBack);

    }
}
