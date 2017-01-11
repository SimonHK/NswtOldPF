package com.nswt.framework.script;

import com.nswt.framework.Page;

public class CheckScript extends Page
{
  public void check()
  {
    String script = $V("Script");
    String lang = $V("Language");
    ScriptEngine se = new ScriptEngine(0);
    try {
      se.compileFunction("Test", script);
    } catch (EvalException ex) {
      StringBuffer sb = new StringBuffer();
      sb.append("µÚ");
      sb.append(ex.getRowNo());
      sb.append("ÓÐÓï·¨´íÎó:<br><font color='red'>");
      sb.append(ex.getLineSource());
      sb.append("</font>");
      this.Response.setError(sb.toString());
      return;
    }
    this.Response.setStatus(1);
  }
}