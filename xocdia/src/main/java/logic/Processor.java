package logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cubeia.firebase.api.action.GameDataAction;
import com.cubeia.firebase.api.action.GameObjectAction;
import com.cubeia.firebase.api.game.GameProcessor;
import com.cubeia.firebase.api.game.table.Table;
import com.dst.GameUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import event.EVT;
import ob.DataSend;
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
				case EVT.DATA_BET:
					LOGGER.info("DATA_BET: ");
					
					List<DataSend> listChoice = new ArrayList<>();
					DataSend [] arrReturn = GameUtil.gson.fromJson(jo.get("data"), DataSend [].class);
					
					for(int i=0; i< arrReturn.length; i++) {
						listChoice.add(arrReturn[i]);
					}

					board.Bet(table, action.getPlayerId(), listChoice);
					break;

				case EVT.DATA_AUTO_EXIT:
					LOGGER.info("exit table");
					board.autoExit(this.game.getServiceContract(), table, action.getPlayerId());
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

			default:
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error(e.getMessage(), e);
		}
	}

}
