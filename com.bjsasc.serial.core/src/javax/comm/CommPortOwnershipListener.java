package javax.comm;

import java.util.EventListener;

public interface CommPortOwnershipListener extends EventListener
{
  public static final int PORT_OWNED = 1;
  public static final int PORT_UNOWNED = 2;
  public static final int PORT_OWNERSHIP_REQUESTED = 3;

  public abstract void ownershipChange(int paramInt);
}

/* Location:           E:\project\szide_projects_3.7_20111017\com.bjsasc.Dsuflashwriter\comm.jar
 * Qualified Name:     javax.comm.CommPortOwnershipListener
 * JD-Core Version:    0.6.0
 */