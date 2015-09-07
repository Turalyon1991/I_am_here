package alex.imhere.service.domain.channel;

import android.support.annotation.NonNull;

import alex.imhere.entity.DyingUser;
import alex.imhere.exception.ChannelException;
import alex.imhere.exception.ServerTunnelException;
import alex.imhere.service.domain.parser.JsonParser;
import alex.imhere.util.AbstractResumable;

public abstract class ServerTunnel extends AbstractResumable {
	JsonParser jsonParser;
	Channel serverChannel;
	private final Channel.EventListener listenerAdapter = new Channel.EventListener() {
		@Override
		public void onConnect(String channel, String greeting) {

		}

		@Override
		public void onDisconnect(String channel, String reason) {
			if (listener != null && isResumed()) {
				listener.onDisconnect(reason);
			}
		}

		@Override
		public void onReconnect(String channel, String reason) {

		}

		@Override
		public void onMessageRecieve(String channel, String message, String timetoken) {
			if (listener != null && isResumed()) {
				ServerTunnel.this.onMessageRecieve(message, timetoken);
			}
		}

		@Override
		public void onErrorOccur(String channel, String error) {
			if (listener != null && isResumed()) {
				ServerTunnel.this.disconnect();
				listener.onDisconnect(error);
			}
		}
	};
	EventListener listener;

	public ServerTunnel(Channel serverChannel, JsonParser jsonParser) {
		this.serverChannel = serverChannel;
		this.jsonParser = jsonParser;
	}

	public final void setListener(@NonNull EventListener listener) {
		this.listener = listener;
	}

	public final void clearListener() {
		listener = null;
	}

	@Override
	public void pause() {
		super.pause();
		serverChannel.pause();
	}

	@Override
	public void resume() {
		super.resume();
		serverChannel.resume();
	}

	public final void connect() throws ServerTunnelException {
		try {
			serverChannel.setListener(listenerAdapter);
			serverChannel.connect();
		} catch (ChannelException e) {
			e.printStackTrace();
			serverChannel.clearListener();
			throw new ServerTunnelException(e);
		}
	}
	public final void disconnect() {
		serverChannel.disconnect();
		serverChannel.clearListener();
	}

	public abstract void onMessageRecieve(String message, String timetoken);

	public interface EventListener {
		void onDisconnect(String reason);
		void onUserLogin(DyingUser dyingUser);
		void onUserLogout(DyingUser dyingUser);
	}
}