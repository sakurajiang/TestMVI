package liulishuo.com.testmvi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.hannesdorfmann.mosby3.mvi.MviActivity
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import liulishuo.com.testmvi.communication.CommunicationIntent
import liulishuo.com.testmvi.fragment.FirstFragment
import liulishuo.com.testmvi.presenter.ActivityPresenter
import liulishuo.com.testmvi.statemodel.ActivityStateModel
import liulishuo.com.testmvi.view.ActivityView
import java.util.concurrent.TimeUnit

class MainActivity : MviActivity<ActivityView,ActivityPresenter>(),ActivityView {

    private var id:Long = 2
    private val clickPublishSubject:PublishSubject<Unit> = PublishSubject.create()
    private val submitBT:Button by lazy { findViewById<Button>(R.id.submit_bt) }
    private val loadingPB:ProgressBar by lazy { findViewById<ProgressBar>(R.id.loading_pb) }
    private val errorIV:ImageView by lazy { findViewById<ImageView>(R.id.error_iv) }

    override fun createPresenter(): ActivityPresenter {
        return ActivityPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListener()
        showFragmentAndTransferValue(id)
    }

    override fun onResume() {
        super.onResume()
        CommunicationIntent.Activity2Fragemnt.onNext(id)
    }

    fun initListener(){
        submitBT.setOnClickListener { clickPublishSubject.onNext(Unit)
        }
    }

    //之所以需要zip，是因为提交题目需要当做完题和点击提交按钮都进行的情况下才会开始提交答案。
    override fun submitSubject(): Observable<Pair<Long,String>> {
        return Observable.zip(CommunicationIntent.Fragment2Activity,clickPublishSubject, BiFunction<Pair<Long,String>,Unit,Pair<Long,String>> { t1, t2 ->
            t1
        })
    }

    override fun renderToUI(activityStateModel: ActivityStateModel) {
        when(activityStateModel){
            is ActivityStateModel.SubmitngSubject -> renderSubmitingSubject()
            is ActivityStateModel.SubmitSubjectFail -> renderSubmitSubjectFail(activityStateModel.error)
            is ActivityStateModel.SubmitSubjectSuccess -> renderSubmitSubjectSuccess()
        }
    }

    fun renderSubmitingSubject(){
        loadingPB.visibility = View.VISIBLE
    }

    fun renderSubmitSubjectFail(error:Throwable){
        loadingPB.visibility = View.GONE
        errorIV.visibility = View.VISIBLE
        errorIV.setImageResource(R.mipmap.ic_launcher)
        Toast.makeText(this ,error.message,Toast.LENGTH_SHORT).show()
    }

    fun renderSubmitSubjectSuccess(){
        id++
        showFragmentAndTransferValue(id)
    }

    fun showFragment(mId:Long){
        var fragment:Fragment = FirstFragment()
        if(mId == 2L){
            fragment = FirstFragment()
        }
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit()
    }

    fun showFragmentAndTransferValue(mId:Long){
        showFragment(mId)
    }
}
