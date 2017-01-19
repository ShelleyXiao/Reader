package com.xyl.reader.http;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit.Ok3Client;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * User: ShaudXiao
 * Date: 2017-01-17
 * Time: 14:30
 * Company: zx
 * Description:
 * FIXME
 */


public class HttpUtils {

    private static final RestAdapter.LogLevel logLevel = RestAdapter.LogLevel.NONE;
    // gankio、豆瓣、动听（轮播图）
    private final static String API_GANKIO = "http://gank.io/api";
    private final static String API_DOUBAN = "https://api.douban.com";
    private final static String API_DONGTING = "http://api.dongting.com";

    private RestAdapter mDontingRestAdapter;
    private RestAdapter mGankIORestAdapter;
    private RestAdapter mDoubanRestAdaper;

    private Gson mGson;
    private Context mContext;

    private static HttpUtils sHttpUtils;
    private static RetrofitHttpClient sDongtingHttpClient;
    private static RetrofitHttpClient sGankIOHttpClient;
    private static RetrofitHttpClient sDouabnIOHttpClient;

    /*分页数据*/
    public static int pageCount = 10;
    public static int page = 20;

    public void setContext(Context context) {
        this.mContext = context;
    }

    public static HttpUtils getHttpUtils() {
        if(null == sHttpUtils) {
            synchronized (HttpUtils.class) {
                if(null == sHttpUtils) {
                    sHttpUtils = new HttpUtils();
                }
            }
        }

        return sHttpUtils;
    }

    public RetrofitHttpClient getDongtingHttpServer() {
        if(sDongtingHttpClient == null) {
            sDongtingHttpClient = getDontingRestAdapter().create(RetrofitHttpClient.class);
        }

        return sDongtingHttpClient;
    }

    public RestAdapter getDontingRestAdapter() {
        if(mDontingRestAdapter == null) {
            File cacheFile = new File( mContext.getApplicationContext().getCacheDir().getAbsolutePath(), "http_cache");
            int cacheSize = 10 * 1024 * 1024;
            Cache cache = new Cache(cacheFile, cacheSize);
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.cache(cache);
            okBuilder.readTimeout(20, TimeUnit.SECONDS);
            okBuilder.connectTimeout(10, TimeUnit.SECONDS);
            okBuilder.writeTimeout(20, TimeUnit.SECONDS);
            OkHttpClient client = okBuilder.build();

            RestAdapter.Builder raBuilder = new RestAdapter.Builder();
            raBuilder.setClient(new Ok3Client(client));
            raBuilder.setLogLevel(logLevel);
            raBuilder.setEndpoint(API_DONGTING);
            raBuilder.setConverter(new GsonConverter(getGson()));
            mDontingRestAdapter = raBuilder.build();
        }


        return mDontingRestAdapter;
    }

    public RetrofitHttpClient getGanIoHttpServer() {
        if(sGankIOHttpClient == null) {
            sGankIOHttpClient = getGankIoRestAdapter().create(RetrofitHttpClient.class);
        }

        return sGankIOHttpClient;
    }

    public RestAdapter getGankIoRestAdapter() {
        if(mGankIORestAdapter == null) {
            File cacheFile = new File( mContext.getApplicationContext().getCacheDir().getAbsolutePath(), "http_cache");
            int cacheSize = 10 * 1024 * 1024;
            Cache cache = new Cache(cacheFile, cacheSize);
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.cache(cache);
            okBuilder.readTimeout(20, TimeUnit.SECONDS);
            okBuilder.connectTimeout(10, TimeUnit.SECONDS);
            okBuilder.writeTimeout(20, TimeUnit.SECONDS);
            okBuilder.addInterceptor(new HttpLoggingInterceptor());
            OkHttpClient client = okBuilder.build();


            RestAdapter.Builder raBuilder = new RestAdapter.Builder();
            raBuilder.setClient(new Ok3Client(client));
            raBuilder.setLogLevel(logLevel);
            raBuilder.setEndpoint(API_GANKIO);
            raBuilder.setConverter(new GsonConverter(getGson()));
            mGankIORestAdapter = raBuilder.build();

        }

        return mGankIORestAdapter;
    }

    public RetrofitHttpClient getDonBanHttpServer() {
        if(sDouabnIOHttpClient == null) {
            sDouabnIOHttpClient = getDoubanRestAdapter().create(RetrofitHttpClient.class);
        }

        return sDouabnIOHttpClient;
    }

    public RestAdapter getDoubanRestAdapter() {
        if(mDoubanRestAdaper == null) {
            File cacheFile = new File( mContext.getApplicationContext().getCacheDir().getAbsolutePath(), "http_cache");
            int cacheSize = 10 * 1024 * 1024;
            Cache cache = new Cache(cacheFile, cacheSize);
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.cache(cache);
            okBuilder.readTimeout(20, TimeUnit.SECONDS);
            okBuilder.connectTimeout(10, TimeUnit.SECONDS);
            okBuilder.writeTimeout(20, TimeUnit.SECONDS);
            OkHttpClient client = okBuilder.build();

            RestAdapter.Builder raBuilder = new RestAdapter.Builder();
            raBuilder.setClient(new Ok3Client(client));
            raBuilder.setLogLevel(logLevel);
            raBuilder.setEndpoint(API_DOUBAN);
            raBuilder.setConverter(new GsonConverter(getGson()));
            mDoubanRestAdaper = raBuilder.build();

        }
        return mDoubanRestAdaper;
    }

    private Gson getGson() {
        if(mGson == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.setFieldNamingStrategy(new AnnotateNaming());
            builder.serializeNulls();
            builder.excludeFieldsWithModifiers(Modifier.TRANSIENT);
            mGson = builder.create();
        }

        return  mGson;
    }

    private static class AnnotateNaming implements FieldNamingStrategy {

        @Override
        public String translateName(Field f) {
            ParamNames a = f.getAnnotation(ParamNames.class);
            return a != null ? a.value() : FieldNamingPolicy.IDENTITY.translateName(f);
        }

    }

}
