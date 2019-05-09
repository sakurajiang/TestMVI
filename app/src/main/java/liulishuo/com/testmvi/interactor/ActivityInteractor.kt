package liulishuo.com.testmvi.interactor

import io.reactivex.Observable
import liulishuo.com.testmvi.network.MyRetrofit
import liulishuo.com.testmvi.network.rfinterface.SubjectInterface
import liulishuo.com.testmvi.statemodel.ActivityStateModel
import java.util.*

/**
 * Created by JDK on 2019/5/7.
 */
object ActivityInteractor {
    fun submitInteractor(pair: Pair<Long,String>):Observable<ActivityStateModel>{
        return MyRetrofit.getService(SubjectInterface::class.java)
            .answerSubject(pair.first,pair.second)
            .map { it ->
                if(it.code()==200){
                    return@map ActivityStateModel.SubmitSubjectSuccess
                }else{
                    return@map ActivityStateModel.SubmitSubjectFail(Throwable("answer is fail"))
                }
            }.startWith(ActivityStateModel.SubmitngSubject)
            .onErrorReturn { e -> ActivityStateModel.SubmitSubjectFail(Throwable(e)) }
    }
}