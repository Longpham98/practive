package logic;

import org.apache.log4j.Logger;

import com.cubeia.firebase.api.action.GameDataAction;
import com.cubeia.firebase.api.action.GameObjectAction;
import com.cubeia.firebase.api.game.GameProcessor;
import com.cubeia.firebase.api.game.table.Table;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import event.EVT;
import ob.LocalEVT;

public class Processor implements GameProcessor {

	public static final Logger LOGGER = Logger.getLogger("xocdia");
	private final GameImpl game;
	private final JsonParser parse = new JsonParser();

	public Processor(GameImpl game) {
		super();
		this.game = game;
	}

	@Override
	public void handle(GameDataAction action, Table table) {
		// TODO Auto-generated method stub
		try {
			String mess = new String(action.getData().array());
			JsonObject jo = (JsonObject) parse.parse(mess);

			LOGGER.info("table: " + table.getId() + ",playerID: " + action.getPlayerId() + "message: " + mess);

			Board board = (Board) table.getGameState().getState();

			if (jo.get("evt") != null) {
				String evt = jo.get("evt").getAsString();

				switch (evt) {
				case EVT.DATA_READY:

					break;

				case EVT.DATA_BET:

					break;

				case EVT.DATA_FINISH:

					break;

				case EVT.DATA_START_GAME:

					break;

				default:
					break;
				}
			} else {
				LOGGER.error("error: " + mess);
			}

		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error(e.getMessage(), e);
		}
	}

	@Override
	public void handle(GameObjectAction action, Table table) {
		// TODO Auto-generated method stub

		try {

			Board board = (Board) table.getGameState().getState();
			LocalEVT le = (LocalEVT) action.getAttachment();

			LOGGER.info("table: " + table.getId() + ", " + le.getEvt());

			switch (le.getEvt()) {
			case EVT.OBJECT_AUTO_START:
				LOGGER.info("evt: OBJECT_AUTO_START");
				board.startGame(this.game.getServiceContract(), table, le.getPid());
				break;

			case EVT.OBJECT_FINISH:
				LOGGER.info("evt: finish");
				board.finishGame(table, this.game.getServiceContract());
				break;

			case EVT.OBJECT_DEAL:

				break;

			case EVT.OBJECT_SEND_TIME_OUT:

				break;

			case EVT.OBJECT_CHECK_BET:
				
				break;

			default:
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error(e.getMessage(), e);
		}
	}

}
