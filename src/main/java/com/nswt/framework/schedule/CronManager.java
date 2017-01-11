package com.nswt.framework.schedule;

import com.nswt.framework.Config;
import com.nswt.framework.utility.*;
import java.io.File;
import java.util.List;
import java.util.Timer;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

// Referenced classes of package com.nswt.framework.schedule:
//            CronMonitor, GeneralTaskManager, AbstractTaskManager, GeneralTask

public class CronManager
{

    private Timer mTimer;
    private CronMonitor mMonitor;
    private Mapx map;
    private static CronManager instance;
    private long interval;

    public static synchronized CronManager getInstance()
    {
        if(instance == null)
        {
            instance = new CronManager();
        }
        return instance;
    }

    public Mapx getManagers()
    {
        return map;
    }

    private CronManager()
    {
        map = new Mapx();
        init();
    }

    public void init()
    {
        if(!Config.isInstalled)
        {
            return;
        } else
        {
            loadConfig();
            mTimer = new Timer(true);
            mMonitor = new CronMonitor();
            mTimer.schedule(mMonitor, 0L, interval);
            LogUtil.info("----" + Config.getAppCode() + "(" + Config.getAppName() + "): CronManager Initialized----");
            return;
        }
    }

    private void loadConfig()
    {
        String path = Config.getContextRealPath() + "WEB-INF/classes/framework.xml";
        SAXReader reader = new SAXReader(false);
        try
        {
            Document doc = reader.read(new File(path));
            Element root = doc.getRootElement();
            Element cron = root.element("cron");
            List types = cron.elements();
            GeneralTaskManager gtm = new GeneralTaskManager();
            map.put(gtm.getCode(), gtm);
            for(int i = 0; i < types.size(); i++)
            {
                Element type = (Element)types.get(i);
                String tag = type.getName();
                if(tag.equals("config"))
                {
                    String name = type.attributeValue("name");
                    String value = type.getText();
                    if(name.equals("RefreshInterval"))
                    {
                        interval = Long.parseLong(value);
                    }
                } else
                if(tag.equals("taskManager"))
                {
                    String className = type.attributeValue("class");
                    try
                    {
                        Object o = Class.forName(className).newInstance();
                        if(o instanceof AbstractTaskManager)
                        {
                            AbstractTaskManager ctm = (AbstractTaskManager)o;
                            map.put(ctm.getCode(), ctm);
                        } else
                        {
                            throw new RuntimeException("\u6307\u5B9A\u7684\u7C7B" + className + "\u4E0D\u662FCronTaskManager\u7684\u5B50\u7C7B.");
                        }
                    }
                    catch(InstantiationException e)
                    {
                        e.printStackTrace();
                    }
                    catch(IllegalAccessException e)
                    {
                        e.printStackTrace();
                    }
                    catch(ClassNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                } else
                if(tag.equals("task"))
                {
                    String className = type.attributeValue("class");
                    String time = type.attributeValue("time");
                    try
                    {
                        Object o = Class.forName(className).newInstance();
                        if(o instanceof GeneralTask)
                        {
                            GeneralTask gt = (GeneralTask)Class.forName(className).newInstance();
                            if(StringUtil.isEmpty(time))
                            {
                                throw new RuntimeException("\u6307\u5B9A\u7684\u4EFB\u52A1\u7C7B" + className + "\u6CA1\u6709\u914D\u7F6Ecron\u8868\u8FBE\u5F0F.");
                            }
                            gt.cronExpression = time;
                            gtm.add(gt);
                        } else
                        {
                            throw new RuntimeException("\u6307\u5B9A\u7684\u7C7B" + className + "\u4E0D\u662FGeneralTask\u7684\u5B50\u7C7B.");
                        }
                    }
                    catch(InstantiationException e)
                    {
                        e.printStackTrace();
                    }
                    catch(IllegalAccessException e)
                    {
                        e.printStackTrace();
                    }
                    catch(ClassNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

        }
        catch(DocumentException e)
        {
            e.printStackTrace();
        }
    }

    public Mapx getTaskTypes()
    {
        Mapx rmap = new Mapx();
        Object vs[] = map.valueArray();
        for(int i = 0; i < map.size(); i++)
        {
            AbstractTaskManager ctm = (AbstractTaskManager)vs[i];
            if(!(ctm instanceof GeneralTaskManager))
            {
                rmap.put(ctm.getCode(), ctm.getName());
            }
        }

        return rmap;
    }

    public Mapx getConfigEnableTasks(String code)
    {
        AbstractTaskManager ctm = (AbstractTaskManager)map.get(code);
        if(ctm == null)
        {
            return null;
        } else
        {
            return ctm.getConfigEnableTasks();
        }
    }

    public AbstractTaskManager getCronTaskManager(String code)
    {
        return (AbstractTaskManager)map.get(code);
    }

    public void destory()
    {
        if(mMonitor != null)
        {
            mMonitor.destory();
            mTimer.cancel();
        }
    }

    public long getInterval()
    {
        return interval;
    }
}
