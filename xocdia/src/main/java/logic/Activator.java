package logic;

import org.apache.log4j.Logger;

import com.athena.services.api.ServiceContract;
import com.athena.services.vo.UserGame;
import com.cubeia.firebase.api.common.Attribute;
import com.cubeia.firebase.api.common.AttributeValue;
import com.cubeia.firebase.api.game.activator.ActivatorContext;
import com.cubeia.firebase.api.game.activator.CreationRequestDeniedException;
import com.cubeia.firebase.api.game.activator.GameActivator;
import com.cubeia.firebase.api.game.activator.RequestAwareActivator;
import com.cubeia.firebase.api.game.activator.RequestCreationParticipant;
import com.cubeia.firebase.api.routing.ActivatorAction;
import com.cubeia.firebase.api.routing.RoutableActivator;
import com.cubeia.firebase.api.server.SystemException;
import com.dst.GameUtil;
import com.dst.MessageUtil;
import com.dst.common.TableLobbyAttributes;
import com.dst.constants.ShanPlusConstant;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import event.EVT;
import table.Participant;

public class Activator implements GameActivator, RoutableActivator, RequestAwareActivator {

	public static final Logger LOGGER = Logger.getLogger("xocdia");

	public static int gameId;
	public static ActivatorContext context;

	public static final int MAX_PLAYER = 40;
	private final JsonParser parser = new JsonParser();
	public static ServiceContract serviceContract;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(ActivatorContext context) throws SystemException {
		// TODO Auto-generated method stub
		Activator.serviceContract = context.getServices().getServiceInstance(ServiceContract.class);
		Activator.context = context;
		gameId = context.getGameId();
		

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAction(ActivatorAction<?> action) {
		// TODO Auto-generated method stub
		LOGGER.info("begin function");

		try {
			String mess = (String) action.getData();
			LOGGER.info("activator: " + mess);

			JsonObject jo = (JsonObject) parser.parse(mess);

			String strui = serviceContract.getUserInfoByPid(jo.get("pid").getAsInt(), 0);

			if (strui.length() == 0) {
				Activator.serviceContract.sendErrorMsg(jo.get("pid").getAsInt(),
						MessageUtil.getMessageResourceBundle("strConnectGame"));
			} else {
				String evt = jo.get("evt").getAsString();
				UserGame ui = GameUtil.gson.fromJson(strui, UserGame.class);
				
			

				switch (evt) {
				case EVT.SELECT_G2:
					getMaskForView(ui.getPid());
					break;

				case EVT.SEARCHT:

					break;

				case EVT.CREATE_TABLE:
					int typeC = (jo.has("T")) ? jo.get("T").getAsInt() : 0;
					String P = (jo.has("P")) ? jo.get("P").getAsString() : "";
					String N = (jo.has("N")) ? jo.get("N").getAsString() : "";
					int mask = jo.get("M").getAsInt();
					
					autoCreateTableSiam(mask, jo.get("pid").getAsInt(), ui.getSource(), typeC, P, N);
					
					break;

				case EVT.PLAY_NOW_2:

					break;

				default:
					break;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.info(e.getMessage(), e);
		}

	}

	@Override
	public RequestCreationParticipant getParticipantForRequest(int pid, int seats, Attribute[] attributes)
			throws CreationRequestDeniedException {
		// TODO Auto-generated method stub
		UserGame ui = GameUtil.gson.fromJson(serviceContract.getUserInfoByPid(pid, 0), UserGame.class);
		
		return new Participant(pid, attributes, ui);
	}
	
	public void getMaskForView(int pid) {
		try {
			LOGGER.info("get mask for view");
			serviceContract.sendToClient(pid, "ltv", "");
			
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	public void autoCreateTableSiam(int mask, int pid, int source, int type, String P, String Name) {
		try {
			LOGGER.info("auto create table siam");
			Attribute[] temp = new Attribute[6];
			temp[0] = new Attribute(TableLobbyAttributes.GAME_ID, new AttributeValue(gameId));
			temp[1] = new Attribute(TableLobbyAttributes.NAME, new AttributeValue(Name));
			temp[2] = new Attribute(TableLobbyAttributes.MARK, new AttributeValue(mask));
			temp[3] = new Attribute(TableLobbyAttributes.TYPE, new AttributeValue(type));
			temp[4] = new Attribute(TableLobbyAttributes.PLAYER, new AttributeValue(MAX_PLAYER));
			temp[5] = new Attribute(TableLobbyAttributes.P, new AttributeValue(P));
			
			Activator.context.getTableFactory().createTable(MAX_PLAYER, getParticipantForRequest(pid, MAX_PLAYER, temp));
					
		} catch (CreationRequestDeniedException e) {
			// TODO: handle exception
			LOGGER.info(e.getMessage(), e);
		}
	}
	
	public static void confirmRoom(int pid, int roomId, int tableId, int oldRoom, int mark) {
		Activator.serviceContract.confirmSelectRoom_Only(pid, roomId, tableId, mark);
	}
	
	public static long getMaxBet(int mark) {
		return mark * ShanPlusConstant.MARK_BET;
	}

}
