/*
Client Server Example
Protocol : TCP, UDP
Function : Echo, Daytime
2017.07.18
Client

*/

import java.util.Scanner;
import java.io.*;
import java.net.*;

public class Client {
	public static void main(String args[]) {
		String request;
		Scanner scan = new Scanner(System.in);
		
		while(true) {
			System.out.println("Enter Command");
			request = scan.nextLine();
			
			if(request.equals("quit")) {
				break;
			}
			
			if(request.equals("TCP:Echo")) {
				try {
					Socket socket = new Socket("127.0.0.1" , 5555);
					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					
					System.out.println("Enter String");
					String send = scan.nextLine();
										
					writer.write(send + '\n');
					writer.flush();
					
					String recv = reader.readLine();
					System.out.println("I got from server : " + recv);
					
					reader.close();
					writer.close();
					socket.close();
				}
				
				catch (Exception e ) {
					System.out.println("Exception caught in tcp echo");
				}
			}
			
			else if(request.equals("TCP:Daytime")) {
				try {
					Socket socket = new Socket("127.0.0.1" , 6666);
					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					
					writer.write("Give me daytime" + '\n');
					writer.flush();
					
					String recv = reader.readLine();
					System.out.println("I got from server : " + recv);
					
					reader.close();
					writer.close();
					socket.close();
				}
				
				catch (Exception e) {
					System.out.println("Exception caught in tcp daytime");
				}
			}
			
			else if(request.equals("UDP:Echo")) {
				try {
					System.out.println("Enter String");
					String send = scan.nextLine();
					
					DatagramSocket socket  = new DatagramSocket();
					InetAddress ip = InetAddress.getByName("127.0.0.1");
					
					byte[] data = send.getBytes();
					byte[] recv = new byte[256];
				
					DatagramPacket sendPacket = new DatagramPacket(data , data.length , ip , 7777);
					socket.send(sendPacket);
					
					DatagramPacket recvPacket = new DatagramPacket(recv, recv.length);
					socket.receive(recvPacket);
					
					System.out.println("I got from server : " + new String(recvPacket.getData()));
					
					socket.close();
				}
				
				catch (Exception e) {
					System.out.println("Exception caught in udp echo");
				}
			}
			
			else if(request.equals("UDP:Daytime")) {
				try {					
					DatagramSocket socket  = new DatagramSocket();
					InetAddress ip = InetAddress.getByName("127.0.0.1");
					String send = "Give me daytime";
					byte[] data = send.getBytes();
					byte[] recv = new byte[256];
				
					DatagramPacket sendPacket = new DatagramPacket(data , data.length , ip , 8888);
					socket.send(sendPacket);
					
					DatagramPacket recvPacket = new DatagramPacket(recv, recv.length);
					socket.receive(recvPacket);
					
					System.out.println("I got from server : " + new String(recvPacket.getData()));
					
					socket.close();
				}
				
				catch (Exception e) {
					System.out.println("Exception caught in udp daytime");
				}
			}
			
			else {
				System.out.println("Wrong Command");
			}
		}
		
		scan.close();
	}
}
