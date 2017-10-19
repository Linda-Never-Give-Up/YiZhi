package com.zyw.horrarndoo.yizhi.model.detail;

import android.support.annotation.NonNull;

import com.zyw.horrarndoo.sdk.base.BaseModel;
import com.zyw.horrarndoo.sdk.helper.RetrofitCreateHelper;
import com.zyw.horrarndoo.sdk.helper.RxHelper;
import com.zyw.horrarndoo.yizhi.api.DoubanApi;
import com.zyw.horrarndoo.yizhi.contract.detail.DoubanMovieDetailContract;
import com.zyw.horrarndoo.yizhi.model.bean.douban.MovieDetailBean;
import com.zyw.horrarndoo.yizhi.model.bean.douban.moviechild.PersonBean;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by Horrarndoo on 2017/10/18.
 * <p>
 */

public class DoubanMovieDetailModel extends BaseModel implements DoubanMovieDetailContract
        .IDoubanMovieDetailModel {
    @NonNull
    public static DoubanMovieDetailModel newInstance() {
        return new DoubanMovieDetailModel();
    }

    @Override
    public Observable<MovieDetailBean> getMovieDetail(String id) {
        return RetrofitCreateHelper.createApi(DoubanApi.class, DoubanApi.HOST).getMovieDetail(id)
                .compose(RxHelper.<MovieDetailBean>rxSchedulerHelper())
                .map(new Function<MovieDetailBean, MovieDetailBean>() {
                    @Override
                    public MovieDetailBean apply(MovieDetailBean bean) throws Exception {
                        //返回的数据没有person type，根据数组类型指定
                        for (PersonBean bean1 : bean.getCasts()) {
                            bean1.setType("主演");
                        }
                        for (PersonBean bean2 : bean.getDirectors()) {
                            bean2.setType("导演");
                        }
                        return bean;
                    }
                });
    }
}