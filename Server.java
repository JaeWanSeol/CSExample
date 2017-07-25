/*
Client Server Example
Protocol : TCP, UDP
Function : Echo, Daytime
2017.07.18
Server

*/


import java.net.*;
import java.io.*;
import java.lang.Thread;
import java.util.Date;

public class Server {
	public static void main(String[] args) {
		Runnable tcpecho = new TcpEcho();
		Thread t1 = new Thread(tcpecho);
		t1.start();
		
		Runnable tcpdaytime = new TcpDaytime();
		Thread t2 = new Thread(tcpdaytime);
		t2.start();
		
		Runnable udpecho = new UdpEcho();
		Thread t3 = new Thread(udpecho);
		t3.start();
		
		Runnable udpdaytime = new UdpDaytime();
		Thread t4 = new Thread(udpdaytime);
		t4.start();
	}
}

//server never close now

class TcpEcho implements Runnable {
	@Override
	public void run() {
		try {
			ServerSocket server = new ServerSocket(5555);
			
			while(true) {
				Socket client = server.accept();
				Runnable r = new TcpEchoHandler(client);
				Thread t = new Thread(r);
				t.start();
			}
		}
		
		catch (Exception e){
			System.err.println("Exception caught in tcp echo");
		}
		
	}
}

class TcpEchoHandler implements Runnable {
	Socket client;
	
	public TcpEchoHandler(Socket client) {
		this.client = client;
	}
	
	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			
			String line = reader.readLine();
			System.out.println("I got from client : " + line);
			
			writer.write(line);
			writer.flush();
				
			reader.close();
			writer.close();
		}
		
		catch(Exception e) {
			System.out.println("Exception caught in tcp echo handler");
		}
		
		finally {
			try { client.close(); }
			catch (Exception e) {  }
		}
	}
}

class TcpDaytime implements Runnable {
	@Override
	public void run() {
		try {
			ServerSocket server = new ServerSocket(6666);
			
			while(true) {
				Socket client = server.accept();
				Runnable r = new TcpDaytimeHandler(client);
				Thread t = new Thread(r);
				t.start();
			}
		}
		
		catch (Exception e) {
			System.err.println("Exception caught in tcp daytime");
		}
	}
}

class TcpDaytimeHandler implements Runnable {
	Socket client;
	
	public TcpDaytimeHandler(Socket client) {
		this.client = client;
	}
	
	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			
			String line = reader.readLine();
			System.out.println("I got from client : " + line);
			
			Date now = new Date();
			
			writer.write(now.toString() + "\r\n");
			writer.flush();
				
			reader.close();
			writer.close();
		}
		
		catch(Exception e) {
			System.out.println("Exception caught in tcp daytime handler");
		}
		
		finally {
			try { client.close(); }
			catch (Exception e) {  }
		}
	}
}

class UdpEcho implements Runnable {
	@Override
	public void run() {
		try {
			DatagramSocket socket = new DatagramSocket(7777);
			
			while(true) {
				byte[] buf = new byte[256];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				
				Runnable r = new UdpEchoHandler(socket, packet);
				Thread t = new Thread(r);
				t.start();
			}
		}
		
		catch (Exception e) {
			System.out.println("Exception caught in udp echo");
		}
	}
}

class UdpEchoHandler implements Runnable {
	DatagramSocket socket;
	DatagramPacket packet;
	
	public UdpEchoHandler(DatagramSocket socket , DatagramPacket packet) {
		this.socket = socket;
		this.packet = packet;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("I got from client : " + new String(packet.getData()));
			byte[] data = packet.getData();
			DatagramPacket send = new DatagramPacket(data , data.length , packet.getAddress() , packet.getPort());
			socket.send(send);
		}
		
		catch (Exception e) {
			System.out.println("Exception caught in udp echo handler");
		}
	}
}

class UdpDaytime implements Runnable {
	@Override
	public void run() {
		try {
			DatagramSocket socket = new DatagramSocket(8888);
			
			while(true) {
				byte[] buf = new byte[256];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				
				Runnable r = new UdpDaytimeHandler(socket, packet);
				Thread t = new Thread(r);
				t.start();
			}
		}
		
		catch (Exception e) {
			System.out.println("Exception caught in udp daytime");
		}
	}
}

class UdpDaytimeHandler implements Runnable {
	DatagramSocket socket;
	DatagramPacket packet;
	
	public UdpDaytimeHandler(DatagramSocket socket , DatagramPacket packet) {
		this.socket = socket;
		this.packet = packet;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("I got from client : " + new String(packet.getData()));
			byte[] data = new byte[256];
			
			Date now = new Date();
			data = now.toString().getBytes();
			DatagramPacket send = new DatagramPacket(data , data.length , packet.getAddress() , packet.getPort());
			socket.send(send);
		}
		
		catch (Exception e) {
			System.out.println("Exception caught in udp daytime handler");
		}
	}
}