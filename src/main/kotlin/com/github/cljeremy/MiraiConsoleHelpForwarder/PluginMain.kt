@file:Suppress("unused")

package com.github.cljeremy.MiraiConsoleHelpForwarder

import com.google.auto.service.AutoService
import net.mamoe.mirai.console.command.*
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.execute
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.unregister
import net.mamoe.mirai.console.permission.*
import net.mamoe.mirai.console.permission.PermissionService.Companion.grantPermission
import net.mamoe.mirai.console.plugin.jvm.JvmPlugin
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.contact.MemberPermission
import net.mamoe.mirai.event.broadcast
import net.mamoe.mirai.message.FriendMessageEvent
import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.TempMessageEvent
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.asMessageChain
import net.mamoe.mirai.utils.currentTimeMillis


@AutoService(JvmPlugin::class)
object MyPluginMain : KotlinPlugin(
    JvmPluginDescription(
        "com.github.cljeremy.mirai-console-help-forwarder",
        "0.1.0"
    )
) {

    override fun onEnable() {
        // override built-in help (unless in console)
        HelpCommand.register(override = true)
        // grant everybody top-level permission of plugin
        AbstractPermitteeId.AnyUser.grantPermission(parentPermission)
    }

    override fun onDisable() {
        HelpCommand.unregister()
    }
}

object HelpCommand : RawCommand(
    MyPluginMain, "help",
    description = "Help command to be forwarded"
) {
    override suspend fun CommandSender.onCommand(args: MessageChain) {
        // if executed from console, execute built-in help command
        if (user == null) {
            BuiltInCommands.HelpCommand.execute(this, args, false)
            return
        }
        val time = (currentTimeMillis / 1000).toInt()
        val verbatimCommand = "/help ${args.joinToString(" ")}"
        fun asMessageChain() = PlainText(verbatimCommand).asMessageChain()
        MyPluginMain.logger.info("Forwarding $verbatimCommand")
        // broadcast event to relay original command message
        val fromEvent = when (this) {
            is CommandSenderOnMessage<*> ->
                fromEvent
            is FriendCommandSender ->
                FriendMessageEvent(user, asMessageChain(), time)
            is MemberCommandSender ->
                GroupMessageEvent(name, permission as MemberPermission, user, asMessageChain(), time)
            is TempCommandSender ->
                TempMessageEvent(user, asMessageChain(), time)
            else -> {
                null
            }
        } ?: return
        MyPluginMain.logger.info(fromEvent.toString())
        fromEvent.broadcast()
    }
}