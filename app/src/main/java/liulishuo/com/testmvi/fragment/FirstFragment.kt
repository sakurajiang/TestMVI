package liulishuo.com.testmvi.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import liulishuo.com.testmvi.R
import liulishuo.com.testmvi.communication.CommunicationIntent
import liulishuo.com.testmvi.model.DataBean
import liulishuo.com.testmvi.presenter.FirstPresenter
import liulishuo.com.testmvi.statemodel.FragmentStateModel
import liulishuo.com.testmvi.view.FirstView

/**
 * Created by JDK on 2019/4/23.
 */
class FirstFragment : MviFragment<FirstView, FirstPresenter>(), FirstView {
    private lateinit var rootView: View
    private val publishSubject: PublishSubject<String> = PublishSubject.create()
    private val questionTextView: TextView by lazy { rootView.findViewById<TextView>(R.id.question_tv) }
    private val firstTextView: TextView by lazy { rootView.findViewById<TextView>(R.id.first_tv) }
    private val secondTextView: TextView by lazy { rootView.findViewById<TextView>(R.id.second_tv) }
    private val thirdTextView: TextView by lazy { rootView.findViewById<TextView>(R.id.third_tv) }
    private val fourthTextView: TextView by lazy { rootView.findViewById<TextView>(R.id.forth_tv) }
    private val loadingProgressBar: ProgressBar by lazy { rootView.findViewById<ProgressBar>(R.id.loading_pb) }
    private val errorImageView: ImageView by lazy { rootView.findViewById<ImageView>(R.id.error_iv) }
    private var isFirstShowFail = true

    override fun createPresenter(): FirstPresenter {
        return FirstPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.first_fragment_layout, container,false)
        initListener()
        return rootView
    }

    fun initListener() {
        firstTextView.setOnClickListener { publishSubject.onNext("first") }
        secondTextView.setOnClickListener { publishSubject.onNext("second") }
        thirdTextView.setOnClickListener { publishSubject.onNext("third") }
        fourthTextView.setOnClickListener { publishSubject.onNext("fourth") }
    }

    override fun loadQuestionData(): Observable<Long> {
        return CommunicationIntent.Activity2Fragemnt
    }

    override fun answerQuestionData(): Observable<Pair<Long,String>> {
        return publishSubject.zipWith(CommunicationIntent.Activity2Fragemnt, BiFunction<String, Long, Pair<Long, String>> { t1, t2 -> Pair(t2,t1) })
    }

    override fun renderToUI(fragmentStateModel: FragmentStateModel) {
        when (fragmentStateModel) {
            is FragmentStateModel.loadingData -> renderLoadingDataUI()
            is FragmentStateModel.loadFail -> renderLoadFailUI(fragmentStateModel.errorPair)
            is FragmentStateModel.loadSuccess -> renderLoadSuccessUI(fragmentStateModel.dataBean)
            is FragmentStateModel.AnswerSubjectFail -> renderAnswerSubjectFailUI(fragmentStateModel.error)
            is FragmentStateModel.AnswerSubjectSuccess -> renderAnswerSubjectSubccessUI(fragmentStateModel.pair)
        }
    }

    fun renderLoadingDataUI() {
        loadingProgressBar.visibility = View.VISIBLE
    }

    fun renderLoadFailUI(errorPair: Pair<Long, Throwable>) {
        errorImageView.visibility = View.VISIBLE
        if (isFirstShowFail) {
            errorPair.first.let { id -> errorImageView.setOnClickListener { CommunicationIntent.Activity2Fragemnt.onNext(id) } }
            isFirstShowFail = false
        }
        errorImageView.setImageResource(R.mipmap.ic_launcher)
    }

    fun renderLoadSuccessUI(dataBean: DataBean) {
        loadingProgressBar.visibility = View.GONE
        errorImageView.visibility = View.GONE
        questionTextView.text = dataBean.text
        firstTextView.text = dataBean.options[0]
        secondTextView.text = dataBean.options[1]
        thirdTextView.text = dataBean.options[2]
        fourthTextView.text = dataBean.options[3]
    }

    fun renderAnswerSubjectFailUI(error:Throwable){
        Toast.makeText(activity,error.message,Toast.LENGTH_SHORT).show()
    }

    fun renderAnswerSubjectSubccessUI(pair: Pair<Long,String>){
        CommunicationIntent.Fragment2Activity.onNext(pair)
    }
}