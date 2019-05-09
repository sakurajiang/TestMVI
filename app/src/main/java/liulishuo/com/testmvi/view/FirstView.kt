package liulishuo.com.testmvi.view

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import liulishuo.com.testmvi.statemodel.FragmentStateModel

/**
 * Created by JDK on 2019/4/30.
 */
interface FirstView:MvpView {
    fun loadQuestionData():Observable<Long>
    fun answerQuestionData():Observable<Pair<Long,String>>
    fun renderToUI(fragmentStateModel: FragmentStateModel)
}