package br.edu.ifpb.progdist.nfsprova;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) throws IOException {
        System.out.println("== Cliente ==");
        Socket socket = new Socket("127.0.0.1", 7001);

        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Digite o comando: ");
                String comando = scanner.nextLine();
                dos.writeUTF(comando);

                String resposta = dis.readUTF();
                System.out.println("Servidor: " + resposta);
            }
        }
    }
}
