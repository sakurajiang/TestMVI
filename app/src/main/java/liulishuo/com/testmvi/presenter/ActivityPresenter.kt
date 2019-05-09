package liulishuo.com.testmvi.presenter

import android.util.Log
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import liulishuo.com.testmvi.interactor.ActivityInteractor
import liulishuo.com.testmvi.statemodel.ActivityStateModel
import liulishuo.com.testmvi.view.ActivityView

/**
 * Created by JDK on 2019/5/7.
 */
class ActivityPresenter:MviBasePresenter<ActivityView,ActivityStateModel>() {
    override fun bindIntents() {
        val answerIntent = intent(ActivityView::submitSubject)
            .subscribeOn(Schedulers.io())
            .doOnNext{ Log.e("sakurajiang","answerIntent$it")}
            .switchMap { ActivityInteractor.submitInteractor(it) }
            .observeOn(AndroidSchedulers.mainThread())
        subscribeViewState(answerIntent,ActivityView::renderToUI)
    }
}