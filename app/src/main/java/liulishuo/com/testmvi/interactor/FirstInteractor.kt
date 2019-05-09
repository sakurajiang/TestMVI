package liulishuo.com.testmvi.interactor

import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import liulishuo.com.testmvi.model.DataBean
import liulishuo.com.testmvi.network.MyRetrofit
import liulishuo.com.testmvi.network.rfinterface.SubjectInterface
import liulishuo.com.testmvi.statemodel.FragmentStateModel

/**
 * Created by JDK on 2019/4/30.
 */
object FirstInteractor {
    fun decode(jsonString:String):DataBean{
        return Gson().fromJson(jsonString,DataBean::class.java)
    }

    fun loadQuestionInteractor(id:Long) : Observable<FragmentStateModel>{
        return MyRetrofit.getService(SubjectInterface::class.java)
            .getSubjectData(id)
            .map {it->
                if(it.code()==200){
                    return@map FragmentStateModel.loadSuccess(decode(it.body().string()))
                } else{
                    return@map FragmentStateModel.loadFail(Pair(id,Throwable("response code is not 200")))
                }
            }.startWith{Observable.just(FragmentStateModel.loadingData)}
            .onErrorReturn{FragmentStateModel.loadFail(Pair(id,Throwable(it)))}
    }

    fun answerQuestionInteractor(pair: Pair<Long,String>):Observable<FragmentStateModel>{
        return Observable.create<FragmentStateModel>{emitter ->
            emitter.onNext(FragmentStateModel.AnswerSubjectSuccess(pair))
        }.onErrorReturn { error -> FragmentStateModel.AnswerSubjectFail(Throwable(error)) }
    }
}