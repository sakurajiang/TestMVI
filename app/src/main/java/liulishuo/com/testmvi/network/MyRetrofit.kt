package liulishuo.com.testmvi.network

import liulishuo.com.testmvi.constant.NetWorkConstant
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by JDK on 2019/4/30.
 */
object MyRetrofit {
    private val retrofit = Retrofit.Builder()
        .baseUrl(NetWorkConstant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    fun <T> getService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}