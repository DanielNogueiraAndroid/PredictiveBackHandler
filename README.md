## This is a repository with the intention to create a sample for this issue:
https://issuetracker.google.com/issues/365027664

### Steps to reproduce issue:

1 - Open the app\
2 - Navigate by clicking the navigate button until you get to item 3\
3 - Press the hardware back button 3 times very quickly

#### Expected:
The app should show the text "Compose 1!"

#### Actual:
The app will crash with an IndexOutOfBoundsException:

```
java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 1
    at jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
    at jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
    at jdk.internal.util.Preconditions.checkIndex(Preconditions.java:266)
    at java.util.Objects.checkIndex(Objects.java:359)
    at java.util.ArrayList.get(ArrayList.java:434)
    at androidx.navigation.compose.NavHostKt$NavHost$25$1.invokeSuspend(NavHost.kt:518)
    at androidx.navigation.compose.NavHostKt$NavHost$25$1.invoke(Unknown Source:8)
    at androidx.navigation.compose.NavHostKt$NavHost$25$1.invoke(Unknown Source:4)
    at androidx.activity.compose.OnBackInstance$job$1.invokeSuspend(PredictiveBackHandler.kt:160)
    at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
    at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:108)
    at androidx.compose.ui.platform.AndroidUiDispatcher.performTrampolineDispatch(AndroidUiDispatcher.android.kt:81)
    at androidx.compose.ui.platform.AndroidUiDispatcher.access$performTrampolineDispatch(AndroidUiDispatcher.android.kt:41)
    at androidx.compose.ui.platform.AndroidUiDispatcher$dispatchCallback$1.run(AndroidUiDispatcher.android.kt:57)
    at android.os.Handler.handleCallback(Handler.java:958)
    at android.os.Handler.dispatchMessage(Handler.java:99)
    at android.os.Looper.loopOnce(Looper.java:205)
    at android.os.Looper.loop(Looper.java:294)
    at android.app.ActivityThread.main(ActivityThread.java:8177)
    at java.lang.reflect.Method.invoke(Native Method)
    at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:552)
    at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:971)
    Suppressed: kotlinx.coroutines.internal.DiagnosticCoroutineContextException: [androidx.compose.ui.platform.MotionDurationScaleImpl@90c5694, androidx.compose.runtime.BroadcastFrameClock@71a253d, StandaloneCoroutine{Cancelling}@7a8a132, AndroidUiDispatcher@2960e83]
```

#### Note:
The current value of 1000 is arbitrary and just simulates some work being done:
```
Thread.sleep(1000) // Simulate work during back handler
```
This value can be changed, for example to 200 milliseconds and the app will still crash if the user navigates quickly.
