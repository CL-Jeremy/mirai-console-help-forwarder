import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.enable
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.load
import net.mamoe.mirai.console.pure.MiraiConsolePureLoader
import com.github.cljeremy.MiraiConsoleHelpForwarder.MyPluginMain

@net.mamoe.mirai.console.util.ConsoleExperimentalApi
suspend fun main() {
    MiraiConsolePureLoader.startAsDaemon()

    MyPluginMain.load() // 主动加载插件, Console 会调用 MyPluginMain.onLoad
    MyPluginMain.enable() // 主动启用插件, Console 会调用 MyPluginMain.onEnable

    MiraiConsole.job.join()
}
