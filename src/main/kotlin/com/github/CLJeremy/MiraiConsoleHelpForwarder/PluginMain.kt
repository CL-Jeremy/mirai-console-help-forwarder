@file:Suppress("unused")

package com.github.CLJeremy.MiraiConsoleHelpForwarder

import com.google.auto.service.AutoService
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.unregister
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.permission.*
import net.mamoe.mirai.console.permission.PermissionService.Companion.grantPermission
import net.mamoe.mirai.console.plugin.jvm.JvmPlugin
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin


@AutoService(JvmPlugin::class)
object MyPluginMain : KotlinPlugin(
    JvmPluginDescription(
        "com.github.CLJeremy.mirai-console-help-forwarder",
        "0.1.0"
    )
) {

    override fun onEnable() {
        HelpCommand.register() // 注册指令
        AbstractPermitteeId.AnyContact.grantPermission(HelpCommand.permission)
    }

    override fun onDisable() {
        HelpCommand.unregister() // 取消注册指令
    }
}

// 简单指令
object HelpCommand : SimpleCommand(
    MyPluginMain, "help",
    description = "Help command to be forwarded"
) {
    @Handler
    suspend fun CommandSender.handle() { // 函数名随意, 但参数需要按顺序放置.
        val verbatimCommand = "/help"
        MyPluginMain.logger.info("Forwarding $verbatimCommand")
        sendMessage(verbatimCommand)
    }
}