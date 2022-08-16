package ob;

import java.util.HashMap;
import java.util.Map;

import com.dst.GameUtil;

public class PlayerAction {
	private String action;
	private int userId;
	private int tableId;
	private Map<String, Object> options = new HashMap<>();
	
	public <T extends Object> PlayerAction(String act, int uid, int tid, T... option) {
		this.action = act;
		this.userId = uid;
		this.tableId = tid;
		
		for(int i=0; i<option.length; i++) {
			String key = i==0 ? "data" : "data" + i;
			options.put(key, option[i]);
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return GameUtil.gson.toJson(this);
	}
	
	public void setAction(String act) {
		this.action = act;
	}
	public String getAction() {
		return this.action;
	}
	
	public void setUserId(int uid) {
		this.userId = uid;
	}
	public int getUserId() {
		return this.userId;
	}
	
	public void setTableId(int tid) {
		this.tableId = tid;
	}
	public int getTableId() {
		return this.tableId;
	}
}
