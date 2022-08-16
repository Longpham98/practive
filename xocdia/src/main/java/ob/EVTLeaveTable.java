package ob;

public class EVTLeaveTable {
	private String evt;
	private int err_code;
	
	public static final int NORMAL = 0;
	public static final int DISCONECT = -1;
	public static final int OUT_OF_CHIP = -2;
	
	
	public EVTLeaveTable(String evt, int err_code) {
		this.evt = evt;
		this.err_code = err_code;
	}
	
	public void setEvt (String evt) {
		this.evt = evt;
	}
	public String getEvt () {
		return this.evt;
	}
	
	public void setErrCode(int err_code) {
		this.err_code = err_code;
	}
	public int getErrCode() {
		return this.err_code;
	}
}
