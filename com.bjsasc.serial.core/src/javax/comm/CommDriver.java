package javax.comm;

public interface CommDriver
{
  public abstract void initialize();

  public abstract CommPort getCommPort(String paramString, int paramInt);
}

/* Location:           E:\project\szide_projects_3.7_20111017\com.bjsasc.Dsuflashwriter\comm.jar
 * Qualified Name:     javax.comm.CommDriver
 * JD-Core Version:    0.6.0
 */