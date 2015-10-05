package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Messenger {

	ConnectionHandler connectionHandler;
	BiConsumer<String, Socket> onMessage = new BiConsumer<String, Socket>() {
		@Override
		public void accept(String message, Socket address) {
			System.out.println(address + " sent: "+ message );
		}
	};
	
	public Messenger(int port) throws IOException {
		connectionHandler = new ConnectionHandler(port, new Consumer<Socket>() {
			@Override
			public void accept(Socket t) {
				System.out.println("Accepted a new connection on port "+t.getPort());
				try{
					InputStream is = t.getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					String in = "";
					while((in = br.readLine()) != null){
						System.out.println("Received "+in);
						onMessage.accept(in, t);
					}
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
		});
	}
	
	public void start(){
		this.connectionHandler.start();
	}
	
	public void onMessage(BiConsumer<String, Socket> onMessage){
		this.onMessage = onMessage;
	}
}
