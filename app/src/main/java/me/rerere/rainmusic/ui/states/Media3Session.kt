package me.rerere.rainmusic.ui.states

import android.content.ComponentName
import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors

/**
 * 提供对应MediaSessionService的MediaController状态, 未加载完成时
 * state内为null
 *
 * @return MediaController状态(可空)
 */
@Composable
fun rememberMediaSessionPlayer(clazz: Class<out Any>): State<MediaController?> {
    val context = LocalContext.current
    val controller = remember(context) {
        mutableStateOf<MediaController?>(null)
    }
    DisposableEffect(context) {
        val builder = MediaController.Builder(
            context,
            SessionToken(
                context,
                ComponentName(
                    context,
                    clazz
                )
            )
        ).buildAsync()

        builder.addListener({
            controller.value = builder.get()
        }, MoreExecutors.directExecutor())

        onDispose {
            MediaController.releaseFuture(builder)
        }
    }
    return controller
}

fun Context.asyncGetSessionPlayer(clazz: Class<out Any>, handler: (MediaController) -> Unit) {
    val controller = MediaController.Builder(
        this,
        SessionToken(
            this,
            ComponentName(
                this,
                clazz
            )
        )
    ).buildAsync()

    controller.addListener({
         handler(controller.get())
    }, MoreExecutors.directExecutor())
}