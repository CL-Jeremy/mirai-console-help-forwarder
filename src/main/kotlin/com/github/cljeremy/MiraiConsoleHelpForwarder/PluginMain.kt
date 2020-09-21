@file:Suppress("unused")

package com.github.cljeremy.MiraiConsoleHelpForwarder

import com.google.auto.service.AutoService
import net.mamoe.mirai.console.command.*
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.unregister
import net.mamoe.mirai.console.plugin.jvm.JvmPlugin
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.message.data.MessageChain


@AutoService(JvmPlugin::class)
object PluginMain : KotlinPlugin(
    JvmPluginDescription(
        "com.github.cljeremy.mirai-console-help-forwarder",
        "0.1.0"
    )
) {
    override fun onEnable() {
//        // grant everybody top-level permission of plugin
//        AbstractPermitteeId.AnyUser.grantPermission(parentPermission)
//        // override built-in help (unless in console)
        logger.warning("Disabling built-in help command")
        HelpCommand.register(override = true)
        HelpCommand.unregister()
    }

    override fun onDisable() {
        logger.warning("Enabling built-in help command")
        BuiltInCommands.HelpCommand.register()
    }
}

object HelpCommand : RawCommand(
    PluginMain, "help",
    description = "Help command to be forwarded"
) {
    override suspend fun CommandSender.onCommand(args: MessageChain) {
//        // if executed from console, execute built-in help command
//        if (isNotUser()) {
//            BuiltInCommands.HelpCommand.execute(this, args, false)
//            return
//        }
//        unregister()
//        PluginMain.logger.info("Forwarding ${
//            (PlainText("/${names.joinToString(" ")}") + args).joinToString(" ")
//        }")
//        // broadcast event to relay original command message
//        val fromEvent = when (this) {
//            !is CommandSenderOnMessage<*> ->
//                null
//            is FriendCommandSender ->
//                FriendMessageEvent(user, message, fromEvent.time)
//            is MemberCommandSender ->
//                GroupMessageEvent(name, user.permission, user, message, fromEvent.time)
//            is TempCommandSender ->
//                TempMessageEvent(user, message, fromEvent.time)
//            else -> null
//        } ?: return
//        PluginMain.logger.info(fromEvent.toString())
//        fromEvent.broadcast()
//        register(override = true)
    }
}