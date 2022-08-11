package logic;

import org.apache.log4j.Logger;

import com.athena.services.api.ServiceContract;
import com.cubeia.firebase.api.game.Game;
import com.cubeia.firebase.api.game.GameProcessor;
import com.cubeia.firebase.api.game.TableInterceptorProvider;
import com.cubeia.firebase.api.game.TableListenerProvider;
import com.cubeia.firebase.api.game.context.GameContext;
import com.cubeia.firebase.api.game.table.Table;
import com.cubeia.firebase.api.game.table.TableInterceptor;
import com.cubeia.firebase.api.game.table.TableListener;
import com.cubeia.firebase.api.server.SystemException;

import table.GameTableInterceptor;
import table.GameTableListener;

public class GameImpl implements Game , TableInterceptorProvider, TableListenerProvider{
	
	public static final Logger LOGGER = Logger.getLogger("xocdia");
	private ServiceContract serviceContract;
	
	public ServiceContract getServiceContract() {
		LOGGER.info("get ServiceContract");
		
		return serviceContract;
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(GameContext context) throws SystemException {
		// TODO Auto-generated method stub
		LOGGER.info("init function");
		
		this.serviceContract = context.getServices().getServiceInstance(ServiceContract.class);
		
	}

	@Override
	public GameProcessor getGameProcessor() {
		// TODO Auto-generated method stub
		LOGGER.info("get game processor");
		
		return new Processor(this);
	}

	@Override
	public TableInterceptor getTableInterceptor(Table table) {
		// TODO Auto-generated method stub
		LOGGER.info("get table interceptor");
		
		return new GameTableInterceptor(this.serviceContract);
	}

	@Override
	public TableListener getTableListener(Table table) {
		// TODO Auto-generated method stub
		LOGGER.info("get table listener");
		
		return new GameTableListener(this.serviceContract);
	}

}
