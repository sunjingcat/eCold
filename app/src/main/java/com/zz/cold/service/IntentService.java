package com.zz.cold.service;

import android.content.Context;
import android.util.Log;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.zz.cold.bean.EventBusSimpleInfo;
import com.zz.lib.commonlib.utils.CacheUtility;

import org.greenrobot.eventbus.EventBus;

public class IntentService extends GTIntentService {

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    // 处理透传消息
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        // 透传消息的处理，详看 SDK demo
        Log.e(TAG, "onReceiveMessageData -> " + msg);
    }

    // 接收 cid
    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
        CacheUtility.spSave("cId",clientid);
        EventBusSimpleInfo eventBusSimpleInfo = new EventBusSimpleInfo();
        eventBusSimpleInfo.setStringData("putCid");
        EventBus.getDefault().post(eventBusSimpleInfo);
    }

    // cid 离线上线通知
    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    // 各种事件处理回执
    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
        Log.e(TAG, "onReceiveMessageData -> " + cmdMessage);
    }

    // 通知到达，只有个推通道下发的通知会回调此方法
    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage msg) {
        Log.e(TAG, "onNotificationMessageArrived -> " + msg);
    }

    // 通知点击，只有个推通道下发的通知会回调此方法
    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage msg) {
        Log.e(TAG, "onNotificationMessageClicked -> " + msg);
    }
}