package table;

import org.apache.log4j.Logger;

import com.athena.services.api.ServiceContract;
import com.cubeia.firebase.api.action.GameObjectAction;
import com.cubeia.firebase.api.game.player.GenericPlayer;
import com.cubeia.firebase.api.game.player.PlayerStatus;
import com.cubeia.firebase.api.game.table.Table;
import com.cubeia.firebase.api.game.table.TableListener;

import logic.Board;
import ob.LocalEVT;

public class GameTableListener implements TableListener {
	private static final Logger LOGGER = Logger.getLogger("xocdia");
	private ServiceContract serviceContract;
	
	

	public GameTableListener(ServiceContract serviceContract) {
		super();
		this.serviceContract = serviceContract;
	}

	@Override
	public void playerJoined(Table table, GenericPlayer player) {
		// TODO Auto-generated method stub
		LOGGER.info("player joined");
		
		try {
			
			Board board = (Board) table.getGameState().getState();
			board.playerJoined(table, this.serviceContract, player.getPlayerId());
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("error listener join: " + e.getStackTrace(), e);
			e.printStackTrace();
			
		}
	}

	@Override
	public void playerLeft(Table table, int playerId) {
		// TODO Auto-generated method stub
		LOGGER.info("player leave");
		try {
			Board board = (Board) table.getGameState().getState();
			board.playerLeft(table, this.serviceContract, playerId);
			
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("error listener left: " + e.getStackTrace(), e);
			e.printStackTrace();
		}
		
		
		
	}

	@Override
	public void playerStatusChanged(Table table, int playerId, PlayerStatus status) {
		// TODO Auto-generated method stub
		LOGGER.info("player status changed");
		if(status.equals(PlayerStatus.DISCONNECTED) || status.equals(PlayerStatus.LEAVING) || status.equals(PlayerStatus.WAITING_REJOIN)) {
			GameObjectAction goa = new GameObjectAction(table.getId());
			LocalEVT le = new LocalEVT();
			le.setEvt("disltable");
			le.setPid(playerId);
			goa.setAttachment(le);
			table.getScheduler().scheduleAction(goa, 0);
			
		}
		
	}

	@Override
	public void seatReserved(Table table, GenericPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void watcherJoined(Table table, int playerId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void watcherLeft(Table table, int playerId) {
		// TODO Auto-generated method stub
		
	}
	
}
