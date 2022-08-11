package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.athena.services.api.ServiceContract;
import com.athena.services.vo.UserGame;
import com.cubeia.firebase.api.action.GameObjectAction;
import com.cubeia.firebase.api.common.Attribute;
import com.cubeia.firebase.api.game.table.InterceptionResponse;
import com.cubeia.firebase.api.game.table.SeatRequest;
import com.cubeia.firebase.api.game.table.Table;
import com.dst.GameUtil;
import com.dst.common.TableLobbyAttributes;
import com.dst.constants.ShanPlusConstant;

import event.EVT;
import ob.BlindTrans;
import ob.Card;
import ob.Field;
import ob.GameStatus;
import ob.LocalEVT;
import ob.ObjectPlayer;
import ob.Packet;
import ob.PlayFinishTrans;
import ob.PlaySend;
import ob.TableInfo;

public class Board implements Serializable {

	private final Object lock = new Object();
	public static final Logger LOGGER = Logger.getLogger("xocdia");
	private int tid;
	private Integer Pid;
	private boolean isStart;
	private int roomId = 0;
	private List<ObjectPlayer> listPlayer;
	private List<ObjectPlayer> listViewPlayer;
	
	private List<Card> listQuanBai; // list quan bai xoc dia

	private GameStatus status;
	private Field cell;
	private Integer Mark;
	private String tableName;
	private int AG_C; // so AG toi thieu
	private int player_C; // so nguoi choi toi da
	
	private boolean isFinished = false;
	private long timeToStart = 0;
	private long totalBet = 0;
	private long maxBet;

	private static final String TIME_TO_START = "timeToStart";

	private static final int ACTION_WAIT_START_TIME = 5000;
	private static final int ACTION_GAME_PLAY_TIME = 20000;
//	private static final int ACTION_GET_BET_TIME = 3000;
//	private static final int ACTION_SELECT_TIME = 3000;

	public Board() {
		this.Pid = 0;
		this.status = GameStatus.WAIT_FOR_START;
		this.listPlayer = new ArrayList<ObjectPlayer>();
		this.listViewPlayer = new ArrayList<ObjectPlayer>();
		this.tableName = "";
		this.Mark = 0;
	}

	public void setBoard(int tid, int pid, Attribute[] atts) {
		synchronized (lock) {
			setTid(tid);
			setPid(Pid);
			setPlayer_C(Activator.MAX_PLAYER);
			setTableName("");
			
			
			for(int i=0; i<atts.length; i++) {
				if(atts[i].name.equals(TableLobbyAttributes.MARK)) {
					setMark(atts[i].value.getIntValue());
				} else if(atts[i].name.equals(TableLobbyAttributes.PLAYER)) {
					setPlayer_C(atts[i].value.getIntValue());
				} else if(atts[i].name.equals(TableLobbyAttributes.NAME)) {
					setTableName(atts[i].value.getStringValue());
				} else if(atts[i].name.equals(TableLobbyAttributes.AG)) {
					setAG_C(atts[i].value.getIntValue());
				}
			}
			this.maxBet = Activator.getMaxBet(Mark);
		}

	}

	public InterceptionResponse allowJoin(Table table, SeatRequest request, ServiceContract serviceContract) {
		synchronized (lock) {
			try {
				if(table.getAttributeAccessor().getIntAttribute(TableLobbyAttributes.STARTED) == 1) {
					return new InterceptionResponse(false, -1);
				}
				if(request.getPlayerId() != getPid()) {
					// 
					return new InterceptionResponse(false, -2);
				}
				if(this.Pid == 0 || this.tid == 0) {
					return new InterceptionResponse(false, -3);
				}
			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error(e.getMessage(), e);
				return new InterceptionResponse(false, -4);
			}
			
			return new InterceptionResponse(true, 0);
		}

	}

	public InterceptionResponse allowLeave(Table table, int pid, ServiceContract serviceContract) {

		synchronized (lock) {
			try {
				if(status == GameStatus.WAIT_FOR_START) {
					return new InterceptionResponse(true, 0);	
				} else {
					for(int i=0; i<listViewPlayer.size(); i++) {
						if(listViewPlayer.get(i).getPid() == pid) {
							return new InterceptionResponse(true, 0);
						} 
					}
					return new InterceptionResponse(false, 0);
				}
				

			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error(e.getMessage(), e);
				return new InterceptionResponse(false, -2);

			}
		}

	}

	public void playerJoined(Table table, ServiceContract serviceContract, int pid) {
		synchronized (lock) {
			try {
				UserGame ui = GameUtil.gson.fromJson(serviceContract.getUserInfoByPid(pid, getTid()), UserGame.class);
				if (ui != null) {
					ui.setAG(serviceContract.updateAgJoinGame(pid));
					if (getPid() == pid && listPlayer.size() > 0) {
						listPlayer.get(0).setAG(ui.getAG());
						
						Packet packet = new Packet(EVT.CLIENT_CREATE_TABLE, GameUtil.gson.toJson(getTable()));
						table.getNotifier().notifyPlayer(pid, GameUtil.toDataAction(pid, table.getId(), packet));
						
						
						this.roomId = listPlayer.get(0).getRoomId();
						Activator.confirmRoom(pid, this.roomId, getTid(), ui.getRoomId(), this.Mark);
						
						table.getAttributeAccessor().setDateAttribute(TIME_TO_START, new Date());

						resetTable(table, serviceContract);

					}

				}

			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

	public void playerLeft(Table table, ServiceContract serviceContract, int pid) {
		synchronized (lock) {
			try {

			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error(e.getMessage(), e);
			}
		}

	}

	public void resetTable(Table table, ServiceContract serviceContract) {
		synchronized (lock) {
			try {
				this.status = GameStatus.WAIT_FOR_START;
				if (listPlayer.size() > 0) {
					
					GameObjectAction goa = new GameObjectAction(table.getId());
					LocalEVT local = new LocalEVT();
					local.setEvt(EVT.OBJECT_AUTO_START);
					local.setPid(listPlayer.get(0).getPid());
					goa.setAttachment(local);

					// cho 5s roi start game
					table.getScheduler().scheduleAction(goa, ACTION_WAIT_START_TIME);
					

				}

			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

	public void startGame(ServiceContract serviceContract, Table table, int pid) {
		synchronized (lock) {
			try {
				if (listPlayer.isEmpty()) {
					return;
				}

				table.getScheduler().cancelAllScheduledActions();
				status = GameStatus.STARTED;
				isFinished = false;
				

				LocalEVT local = new LocalEVT();
				local.setEvt(EVT.OBJECT_FINISH);
				local.setPid(pid);
				GameObjectAction goa = new GameObjectAction(table.getId());
				goa.setAttachment(local);
				// sau 20s se goi ham finishgame
				table.getScheduler().scheduleAction(goa, ACTION_GAME_PLAY_TIME);

				for(ObjectPlayer player : listPlayer) {
					// xac dinh tien cuoc
					Long tienCuoc = Mark.longValue() <= player.getAG().longValue() ? Mark.longValue() : player.getAG();
					
					Bet(table, player.getPid(), tienCuoc, player.getOption());
				}

				
				// finish game
//				finishTable(table, pid, ACTION_GAME_PLAY_TIME);
				

			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

	public void finishTable(Table table, int playerId, int timeFinish) {
		LOGGER.info("finish game!");

		LocalEVT local = new LocalEVT();
		local.setEvt(EVT.OBJECT_FINISH);
		local.setPid(playerId);

		GameObjectAction action = new GameObjectAction(table.getId());
		action.setAttachment(local);

		table.getScheduler().scheduleAction(action, timeFinish);

	}

	public void finishGame(Table table, ServiceContract serviceContract) {
		LOGGER.info("finish game!");
		synchronized (lock) {
			try {
				table.getScheduler().cancelAllScheduledActions();
				
				// xoc dia
				listQuanBai = getRandomCard();
				
				// code logic, tinh tien
				Field result = checkResult(listQuanBai);
				List<PlayFinishTrans> dataFinish = new ArrayList<>();
				
				for(int i=0; i<listPlayer.size(); i++) {
					ObjectPlayer playerSesKu = new ObjectPlayer();
					PlayFinishTrans playFinishTrans = new PlayFinishTrans();
					
					Long betAG = playerSesKu.getMarkCuoc();
					playFinishTrans.setN(playerSesKu.getUserName());
					playFinishTrans.setTypeScore(playerSesKu.getOption());
					
					if(playerSesKu.getOption() == result && (result == Field.BON_TRANG || result == Field.BON_DO)) {
						playFinishTrans.setM(playFinishTrans.getM() + 14 * betAG);
					}else if(playerSesKu.getOption() == result && (result == Field.BA_TRANG || result == Field.BA_DO)) {
						playFinishTrans.setM(playFinishTrans.getM() + 3 * betAG);
					}else if(playerSesKu.getOption() == Field.EVEN && (result == Field.BON_DO || result == Field.BON_TRANG || result == Field.EVEN)) {
						playFinishTrans.setM(playFinishTrans.getM() + betAG);
					}else if(playerSesKu.getOption() == Field.OLD && (result == Field.BA_DO || result == Field.BA_TRANG)) {
						playFinishTrans.setM(playFinishTrans.getM() + betAG);
					}else {
						playFinishTrans.setM(playFinishTrans.getM() - betAG);
					}
					
					
					dataFinish.add(playFinishTrans);
					
				}
				timeToStart = 5;
				table.getNotifier().notifyAllPlayers(GameUtil.toDataAction(0, getTid(), new Packet(EVT.CLIENT_FINISH, GameUtil.gson.toJson(dataFinish), timeToStart)));
				isFinished = true;
				
				// chuan bi sang van moi
				table.getAttributeAccessor().setDateAttribute(TIME_TO_START, new Date());
				resetTable(table, serviceContract);

			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error(e.getMessage());
			}
		}
	}
	
	public static Field checkResult(List<Card> listCard) {
		int countTrang = 0;
		
		for(int i=0; i<listCard.size(); i++) {
			if(listCard.get(i).getN() == 0) {
				countTrang ++;
			}
		}
		
		if(countTrang == 0) {
			return Field.BON_DO;
		} else if(countTrang == 1) {
			return Field.BA_DO;
		} else if(countTrang == 2) {
			return Field.EVEN;
		} else if(countTrang == 3) {
			return Field.BA_TRANG;
		} else if(countTrang == 4) {
			return Field.BON_TRANG;
		}
		
		return null;
	}
	
	private List<Card> getRandomCard(){
		synchronized (lock) {
			try {
				List<Card> arrReturn = new ArrayList<>();
				
				for(int i=1; i<=4; i++) {
					int randomNum = (int) Math.round(Math.random());
					Card card = new Card(randomNum);
					arrReturn.add(card);
				}
				return arrReturn;
				
			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error(e.getMessage(), e);
			}
			
			return null;
		}
	}

	public void Bet(Table table, int pid, Long tiencuoc, Field cell) {
		synchronized (lock) {
			try {
				if(status != GameStatus.STARTED) {
					return;
				}
				
				if (tiencuoc > this.maxBet) {
					tiencuoc = maxBet;
				}

				totalBet += tiencuoc;
				for (int i = 0; i < listPlayer.size(); i++) {
					if (listPlayer.get(i).getPid() == pid) {
						listPlayer.get(i).setMarkCuoc(tiencuoc);

						BlindTrans sender = new BlindTrans();
						sender.setN(listPlayer.get(i).getUserName());
						sender.setAG(tiencuoc);
						sender.setEvt(EVT.CLIENT_BET);
						sender.setOption(cell);

						table.getNotifier().notifyAllPlayers(GameUtil.toDataAction(pid, getTid(), sender));

					}
				}

			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
	
	private TableInfo getTable() {
		synchronized (lock) {
			try {
				TableInfo ti = new TableInfo();
				ti.setId(this.tid);
				ti.setN(this.tableName);
				ti.setM(this.Mark);
				if(this.Mark == 0) {
					ti.setAG(this.AG_C);
				} else {
					ti.setAG(this.AG_C / this.Mark);
				}
				ti.setS(this.player_C);
				for(int i=0; i<listPlayer.size(); i++) {
					ti.getArrPlayer().add(listPlayer.get(i).getItemPlayer());
				}
				
				return ti;
				
			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error(e.getMessage(), e);
			}
			return null;
		}
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public Integer getPid() {
		return Pid;
	}

	public void setPid(Integer pid) {
		Pid = pid;
	}

	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	public int getPlayer_C() {
		return player_C;
	}

	public void setPlayer_C(int player_C) {
		this.player_C = player_C;
	}

	public Integer getMark() {
		return Mark;
	}

	public void setMark(Integer mark) {
		Mark = mark;
	}
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String name) {
		this.tableName = name;
	}
	
	public int getAG_C() {
		return AG_C;
	}
	public void setAG_C(int ag_c) {
		this.AG_C = ag_c;
	}
	
	
	
	
	
}
