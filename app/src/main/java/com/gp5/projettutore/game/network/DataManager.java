package com.gp5.projettutore.game.network;

import com.gp5.projettutore.game.entity.EntityPlayer;
import com.gp5.projettutore.game.main.Core;

/**
 * Created by Valentin on 15/11/2015.
 */
public class DataManager
{
    public static void sendPlayerData(EntityPlayer player)
    {
        //TODO
    }

    public static void receivePlayerData(EntityPlayer player)
    {
        Core.instance.getFriend().setX(player.getX());
        Core.instance.getFriend().setZ(player.getZ());
    }
}
