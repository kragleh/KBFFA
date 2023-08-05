package com.kragleh.kbffa.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import com.kragleh.kbffa.KBFFA;
import com.kragleh.kbffa.db.PlayerStorage;
import com.kragleh.kbffa.util.MessageUtil;
import org.apache.commons.lang.CharUtils;
import org.bukkit.entity.Player;

@CommandAlias("pearl")
public class PearlCMD extends BaseCommand {

    @Default
    @CommandCompletion("<slot>")
    public void onDefault(Player player, String[] args) {
        if (args.length != 1) {
            player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("usage")));
            KBFFA.getMessages().getStringList("pearl.cmd").forEach((message) -> {
                player.sendMessage(MessageUtil.format(message));
            });
            return;
        }

        try {
            int slot = Integer.parseInt(args[0]);

            if (slot > 9 || slot < 3) {
                player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("pearl.invalid") + slot));
                return;
            }

            PlayerStorage.setPearl(player, (slot - 1));

            player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("pearl.success") + slot));
        } catch (NumberFormatException e) {
            player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("pearl.not-int")));
        }

    }

}
