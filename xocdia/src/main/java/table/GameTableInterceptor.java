package table;

import org.apache.log4j.Logger;

import com.athena.services.api.ServiceContract;
import com.cubeia.firebase.api.game.table.InterceptionResponse;
import com.cubeia.firebase.api.game.table.SeatRequest;
import com.cubeia.firebase.api.game.table.Table;
import com.cubeia.firebase.api.game.table.TableInterceptor;

import logic.Board;

public class GameTableInterceptor implements TableInterceptor{
	public static final Logger LOGGER = Logger.getLogger("xocdia");
	private ServiceContract serviceContract;
	
	
	public GameTableInterceptor(ServiceContract serviceContract) {
		super();
		this.serviceContract = serviceContract;
	}

	@Override
	public InterceptionResponse allowJoin(Table table, SeatRequest request) {
		// TODO Auto-generated method stub
		LOGGER.info("allow join");
		try {
			Board board = (Board) table.getGameState().getState();
			return board.allowJoin(table, request, this.serviceContract);
			
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("error: " + e.getMessage());
			e.printStackTrace();
			return new InterceptionResponse(false, -2);
		}
		
	}

	@Override
	public InterceptionResponse allowLeave(Table table, int palyerId) {
		// TODO Auto-generated method stub
		LOGGER.info("allow leave");
		try {
			Board board = (Board) table.getGameState().getState();
			return board.allowLeave(table, palyerId, this.serviceContract);
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("error: " + e.getMessage());
			e.printStackTrace();
			return new InterceptionResponse(false, -2);
			
		}
	}

	@Override
	public InterceptionResponse allowReservation(Table table, SeatRequest request) {
		// TODO Auto-generated method stub
		return new InterceptionResponse(true, 0);
		
	}

}
