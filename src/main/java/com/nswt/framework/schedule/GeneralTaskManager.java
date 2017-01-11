package com.nswt.framework.schedule;

import com.nswt.framework.utility.Mapx;

public class GeneralTaskManager extends AbstractTaskManager
{
  Mapx taskMap = new Mapx();

  public synchronized void add(GeneralTask gt)
  {
    this.taskMap.put(new Long(gt.getID()), gt);
  }

  public synchronized void execute(long id) {
    GeneralTask gt = (GeneralTask)this.taskMap.get(new Long(id));
    if (gt != null)
      if (!(gt.isRunning()))
        gt.execute();

    else
      throw new RuntimeException("δ�ҵ�ID��Ӧ������!");
  }

  public boolean isRunning(long id)
  {
    GeneralTask gt = (GeneralTask)this.taskMap.get(new Long(id));
    if (gt != null)
      return gt.isRunning();

    throw new RuntimeException("δ�ҵ�ID��Ӧ������!");
  }

  public String getCode()
  {
    return "SYSTEM";
  }

  public String getName()
  {
    return "ϵͳ����";
  }

  public Mapx getUsableTasks() {
    Mapx map = new Mapx();
    Object[] vs = this.taskMap.valueArray();
    for (int i = 0; i < this.taskMap.size(); ++i) {
      GeneralTask gt = (GeneralTask)vs[i];
      map.put(new Long(gt.getID()), gt.getName());
    }
    return map;
  }

  public String getTaskCronExpression(long id) {
    GeneralTask gt = (GeneralTask)this.taskMap.get(new Long(id));
    return gt.getCronExpression();
  }

  public Mapx getConfigEnableTasks() {
    return null;
  }
}