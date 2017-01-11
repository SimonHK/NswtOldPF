package com.nswt.framework.messages;

import com.nswt.framework.utility.Mapx;

public abstract class MessageReceiver
{
  public String[] getMessageTypeNames()
  {
    return MessageBus.getMessageNames(this);
  }

  public abstract Mapx receive(Message paramMessage);
}