package liulishuo.com.testmvi.network.rfinterface

import io.reactivex.Observable
import liulishuo.com.testmvi.model.DataBean
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by JDK on 2019/4/30.
 */
interface SubjectInterface{
    @GET("/getSubjectData/{id}")
    fun getSubjectData(@Path("id")id:Long):Observable<Response>

    @POST("/postSubejctData")
    fun postSubejctData(@Body body: DataBean):Observable<ResponseBody>

    @POST("/postSubjectData/{id}")
    fun answerSubject(@Path("id")id: Long,
                      @Body body: String):Observable<Response>
}