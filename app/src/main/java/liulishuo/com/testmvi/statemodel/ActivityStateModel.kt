package liulishuo.com.testmvi.statemodel

/**
 * Created by JDK on 2019/5/7.
 */
sealed class ActivityStateModel {
    object SubmitngSubject:ActivityStateModel()
    data class SubmitSubjectFail(val error:Throwable):ActivityStateModel()
    object SubmitSubjectSuccess:ActivityStateModel()
}