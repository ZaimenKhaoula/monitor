package Tasks;

public abstract class Task {
	protected String id;
	protected boolean finished;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public boolean equals(Object obj) {
	    if (obj instanceof Task) {
	         Task o = (Task) obj;
	        return (o.id.compareTo(this.id)==0);
	    }
	    return false;
	}
	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean b) {
		finished =b;
	}
}
