package liulishuo.com.testmvi.communication

import io.reactivex.subjects.PublishSubject

/**
 * Created by JDK on 2019/5/7.
 */
object CommunicationIntent {
    val Activity2Fragemnt:PublishSubject<Long> = PublishSubject.create()
    val Fragment2Activity:PublishSubject<Pair<Long,String>> = PublishSubject.create()
}