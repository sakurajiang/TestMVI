package liulishuo.com.testmvi.presenter

import android.util.Log
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import liulishuo.com.testmvi.interactor.FirstInteractor
import liulishuo.com.testmvi.statemodel.FragmentStateModel
import liulishuo.com.testmvi.view.FirstView
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by JDK on 2019/4/30.
 */
class FirstPresenter : MviBasePresenter<FirstView, FragmentStateModel>() {
    override fun bindIntents() {
        val loadQuestionIntent = intent(FirstView::loadQuestionData)
            .subscribeOn(Schedulers.io())
            .doOnNext { Log.e("sakurajiang", "loadQuestionIntent$it") }
            .switchMap { FirstInteractor.loadQuestionInteractor(it) }
            .observeOn(AndroidSchedulers.mainThread())
        val answerQuestionIntent = intent(FirstView::answerQuestionData)
            .subscribeOn(Schedulers.io())
            .doOnNext { Log.e("sakurajiang", "answerQuestionIntent$it") }
            .switchMap { FirstInteractor.answerQuestionInteractor(it) }
            .observeOn(AndroidSchedulers.mainThread())
        val allIntent = Observable.merge(loadQuestionIntent, answerQuestionIntent)
            .doOnNext { Log.e("sakurajiang", "allIntent$it") }
            .observeOn(AndroidSchedulers.mainThread())
        subscribeViewState(allIntent, FirstView::renderToUI)
    }
}