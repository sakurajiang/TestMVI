package liulishuo.com.testmvi.view

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import liulishuo.com.testmvi.statemodel.ActivityStateModel

/**
 * Created by JDK on 2019/5/7.
 */
interface ActivityView:MvpView {
    fun submitSubject():Observable<Pair<Long,String>>
    fun renderToUI(activityStateModel: ActivityStateModel)
}