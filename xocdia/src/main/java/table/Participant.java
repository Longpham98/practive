package table;

import org.apache.log4j.Logger;

import com.athena.services.vo.UserGame;
import com.cubeia.firebase.api.common.Attribute;
import com.cubeia.firebase.api.game.GameDefinition;
import com.cubeia.firebase.api.game.activator.RequestCreationParticipant;
import com.cubeia.firebase.api.game.lobby.LobbyTableAttributeAccessor;
import com.cubeia.firebase.api.game.table.Table;
import com.cubeia.firebase.api.lobby.LobbyPath;

import logic.Board;

public class Participant implements RequestCreationParticipant {
	public static final Logger LOGGER = Logger.getLogger("xocdia");
	private int pid;
	private Attribute[] attributes;
	private UserGame ui;

	
	public Participant(int pid, Attribute[] attributes, UserGame ui) {
		super();
		this.pid = pid;
		this.attributes = attributes;
		this.ui = ui;
	}

	@Override
	public LobbyPath getLobbyPathForTable(Table arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTableName(GameDefinition arg0, Table arg1) {
		// TODO Auto-generated method stub
		return "Xocdia[" + arg1.getId() + "]";
	}

	@Override
	public void tableCreated(Table table, LobbyTableAttributeAccessor acc) {
		// TODO Auto-generated method stub
		LOGGER.info("participant: table created");
		Board board = new Board();
		board.setBoard(table.getId(), this.pid, this.attributes);
		
		table.getGameState().setState(board);
		
		
		
	}

	@Override
	public int[] modifyInvitees(int[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean reserveSeatsForInvitees() {
		// TODO Auto-generated method stub
		return false;
	}

}
