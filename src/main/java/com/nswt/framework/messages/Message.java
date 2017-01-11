package com.nswt.framework.messages;

import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.Mapx;

public class Message
{
  private String ID;
  private String name;
  private Transaction transaction;
  private Mapx content;

  public Mapx getContent()
  {
    return this.content;
  }

  public void setContent(Mapx content) {
    this.content = content;
  }

  public Transaction getTransaction() {
    return this.transaction;
  }

  public void setTransaction(Transaction transaction) {
    this.transaction = transaction;
  }

  public boolean isTransactional() {
    return (this.transaction != null);
  }

  public String getID() {
    return this.ID;
  }

  public void setID(String id) {
    this.ID = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }
}