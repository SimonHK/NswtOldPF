package com.nswt.framework.messages;

import com.nswt.framework.User;
import com.nswt.framework.utility.*;
import java.util.ArrayList;

// Referenced classes of package com.nswt.framework.messages:
//            StopThreadException

public abstract class LongTimeTask extends Thread
{

    private static Mapx map = new Mapx();
    private static long IDBase = System.currentTimeMillis();
    private static final int MaxListSize = 1000;
    private long id;
    private ArrayList list;
    protected int percent;
    protected String currentInfo;
    protected ArrayList Errors;
    private boolean stopFlag;
    private com.nswt.framework.User.UserData user;
    private String type;
    private long stopTime;

    public static LongTimeTask createEmptyInstance()
    {
        return new LongTimeTask(false) {

            public void execute()
            {
            }

        };
    }

    public static LongTimeTask getInstanceById(long id)
    {
        return (LongTimeTask)map.get(new Long(id));
    }

    public static void removeInstanceById(long id)
    {
        synchronized(com.nswt.framework.messages.LongTimeTask.class)
        {
            map.remove(new Long(id));
        }
    }

    public static String cancelByType(String type)
    {
        String message = "\u6CA1\u6709\u76F8\u5173\u7684\u6B63\u5728\u8FD0\u884C\u7684\u4EFB\u52A1!";
        LongTimeTask ltt = getInstanceByType(type);
        if(ltt != null)
        {
            ltt.stopTask();
            message = "\u4EFB\u52A1\u5DF1\u63A5\u6536\u5230\u4E2D\u6B62\u547D\u4EE4\uFF0C\u4F46\u8FD8\u9700" +
"\u8981\u505A\u4E00\u4E9B\u6E05\u7406\u5DE5\u4F5C\uFF0C\u8BF7\u7A0D\u7B49\u4E00\u4F1A" +
"!"
;
        }
        return message;
    }

    public static LongTimeTask getInstanceByType(String type)
    {
        if(StringUtil.isNotEmpty(type))
        {
            Object ks[] = map.keyArray();
            Object vs[] = map.valueArray();
            long current = System.currentTimeMillis();
            for(int i = 0; i < map.size(); i++)
            {
                LongTimeTask ltt = (LongTimeTask)vs[i];
                if(type.equals(ltt.getType()))
                {
                    if(current - ltt.stopTime > 60000L)
                    {
                        map.remove(ks[i]);
                        return null;
                    } else
                    {
                        return ltt;
                    }
                }
            }

        }
        return null;
    }

    public LongTimeTask()
    {
        this(true);
    }

    private LongTimeTask(boolean flag)
    {
        list = new ArrayList();
        Errors = new ArrayList();
        stopTime = System.currentTimeMillis() + 0x15f900L;
        if(flag)
        {
            setName("LongTimeTask Thread");
            synchronized(com.nswt.framework.messages.LongTimeTask.class)
            {
                id = IDBase++;
                map.put(new Long(id), this);
                clearStopedTask();
            }
        }
    }

    private void clearStopedTask()
    {
        synchronized(com.nswt.framework.messages.LongTimeTask.class)
        {
            long current = System.currentTimeMillis();
            Object ks[] = map.keyArray();
            Object vs[] = map.valueArray();
            for(int i = 0; i < map.size(); i++)
            {
                LongTimeTask ltt = (LongTimeTask)vs[i];
                if(current - ltt.stopTime > 60000L)
                {
                    map.remove(ks[i]);
                }
            }

        }
    }

    public long getTaskID()
    {
        return id;
    }

    public void info(String message)
    {
        LogUtil.getLogger().info(message);
        list.add(message);
        if(list.size() > 1000)
        {
            list.remove(0);
        }
    }

    public String[] getMessages()
    {
        String arr[] = new String[list.size()];
        for(int i = 0; i < arr.length; i++)
        {
            arr[i] = (String)list.get(i);
        }

        list.clear();
        return arr;
    }

    public void run() {
        if (StringUtil.isNotEmpty(this.type)) {
          LongTimeTask ltt = getInstanceByType(this.type);
          if ((ltt != null) && (ltt != this))
            return;
        }
        try
        {
          User.setCurrent(this.user);
          execute();
        } catch (StopThreadException ie) {
          interrupt();

          this.stopTime = System.currentTimeMillis(); } finally { this.stopTime = System.currentTimeMillis();
        }
      }

    public abstract void execute();

    public boolean checkStop()
    {
        return stopFlag;
    }

    public void stopTask()
    {
        clearStopedTask();
        stopFlag = true;
    }

    public int getPercent()
    {
        return percent;
    }

    public void setPercent(int percent)
    {
        this.percent = percent;
    }

    public void setCurrentInfo(String currentInfo)
    {
        this.currentInfo = currentInfo;
        LogUtil.info(currentInfo);
    }

    public String getCurrentInfo()
    {
        return currentInfo;
    }

    public void setUser(com.nswt.framework.User.UserData user)
    {
        this.user = user;
    }

    public void addError(String error)
    {
        Errors.add(error);
    }

    public void addError(String errors[])
    {
        for(int i = 0; i < errors.length; i++)
        {
            Errors.add(errors[i]);
        }

    }

    public String getAllErrors()
    {
        if(Errors.size() == 0)
        {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("\u5171\u6709" + Errors.size() + "\u4E2A\u9519\u8BEF:<br>");
        for(int i = 0; i < Errors.size(); i++)
        {
            sb.append(i + 1);
            sb.append(": ");
            sb.append(Errors.get(i));
            sb.append("<br>");
        }

        return sb.toString();
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    LongTimeTask(boolean flag, LongTimeTask longtimetask)
    {
        this(flag);
    }

}
