package com.nswt.framework.license;

public abstract interface IProduct
{
  public abstract String getAppCode();

  public abstract String getAppName();

  public abstract float getMainVersion();

  public abstract float getMinorVersion();
}