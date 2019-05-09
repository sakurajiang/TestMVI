package liulishuo.com.testmvi.statemodel

import liulishuo.com.testmvi.model.DataBean

/**
 * Created by JDK on 2019/4/23.
 */
sealed class FragmentStateModel {
    object loadingData : FragmentStateModel()
    data class loadSuccess(val dataBean: DataBean) : FragmentStateModel()
    data class loadFail(val errorPair: Pair<Long,Throwable>) : FragmentStateModel()

    data class AnswerSubjectFail(val error:Throwable):FragmentStateModel()
    data class AnswerSubjectSuccess(val pair: Pair<Long,String>):FragmentStateModel()
}