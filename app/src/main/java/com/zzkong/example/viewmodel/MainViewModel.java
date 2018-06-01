package com.zzkong.example.viewmodel;

import com.zzkong.example.beans.GankBean;
import com.zzkong.example.repository.Repository;
import com.zzkong.example.ui.base.AppViewModel;
import com.zzkong.example.utils.RxLiveData;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by lico on 2018/6/1
 */
public class MainViewModel extends AppViewModel{

    private Repository mRepository;
    public RxLiveData<List<GankBean.Gank>> mRxLiveData;

    @Inject
    public MainViewModel(Repository repository){
        mRepository = repository;
        mRxLiveData = createRxLiveData();
    }

    public void getGank(String title , int page){
        mRxLiveData.execute(mRepository.getGankData(title, page), true);
    }
}
