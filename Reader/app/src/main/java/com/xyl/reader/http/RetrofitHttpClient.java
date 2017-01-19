package com.xyl.reader.http;

import com.xyl.reader.bean.FrontpageBean;
import com.xyl.reader.bean.GankIODataBean;
import com.xyl.reader.bean.GankIODayBean;
import com.xyl.reader.bean.HotMovieBean;
import com.xyl.reader.bean.MovieDetailBean;
import com.xyl.reader.bean.book.BookBean;
import com.xyl.reader.bean.book.BookDetailBean;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * User: ShaudXiao
 * Date: 2017-01-13
 * Time: 15:03
 * Company: zx
 * Description: API 请求Retrofit EndPoint
 * FIXME
 *
 */


public interface RetrofitHttpClient {

    /*
    *
    * 首页轮播图
    *
    * **/
    @GET("/frontpage/frontpage")
    Observable<FrontpageBean> getFrontpage();

    /**
     * Gank.io 数据
     *
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     *
     * eg. http://gank.io/api/data/Android/10/1
     *
     * */
    @GET("/{type}/{count}/{page}")
    Observable<GankIODataBean> getGankIoData(@Path("type") String id, @Path("page") int page, @Path("count") int count);

    /**
     ** Gank.io 数据
     *
     * 每日数据： http://gank.io/api/day/年/月/日
     *
     * eg. http://gank.io/api/day/2015/08/06
     *
     * */
    @GET("/day/{year}/{month}/{day}")
    Observable<GankIODayBean> getGankIoDayData(@Path("year") String year, @Path("month") String month, @Path("day") String day);

    /**
     * 豆瓣  电影数据
     *
     * 正在上映
     *
     * */
    @GET("/v2/movie/in_theaters")
    Observable<HotMovieBean> getHotMovie();

    /**
     * 豆瓣  电影数据
     *
     * Top 250
     *
     * @param start 从多少条开始
     * @param count 一次请求多少条数据
     * */
    @GET("/v2/movie/top250")
    Observable<HotMovieBean> getMovieTop250(@Query("start") String start, @Query("count") String count);

    /**
     * 豆瓣  电影数据
     *
     * 电影详情
     *
     * @param id  电影Bean中的id
     *
     *  eg. /v2/movie/subject/1764796
     * */
    @GET("/v2/movie/subject/{id}")
    Observable<MovieDetailBean> getMovieDeatil(@Path("id") String id);

    /**
     * 豆瓣  书籍数据
     *
     * 书籍详情
     *
     * @param id  id
     *
     *  eg
     * */
     @GET("/v2/book/{id}")
     Observable<BookDetailBean> getBookDeatil(@Path("id") String id);

    /**
     * 豆瓣  书籍数据
     *
     * 根据TAG 搜索数据
     *
     * @param tag  s搜索标签分类
     *  @param start  取结果的offset 默认为0
     *  @param  count	取结果的条数
     *
     * */
    @GET("v2/book/search")
    Observable<BookBean> getBook(@Query("tag") String tag, @Query("start") String start, @Query("count") String count);


}
