package br.edu.ifpb.progdist.nfsprova;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class Servidor {

    private static List<String> arquivos = new ArrayList<>(Arrays.asList("arquivo1.txt", "arquivo2.txt"));

    public static void main(String[] args) throws IOException {
        System.out.println("== Servidor ==");
        ServerSocket serverSocket = new ServerSocket(7001);

        try (Socket socket = serverSocket.accept();
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {

            System.out.println("Cliente conectado: " + socket.getInetAddress());

            while (true) {
                String comando = dis.readUTF();
                System.out.println("Comando recebido: " + comando);
                String resposta = processarComando(comando);
                dos.writeUTF(resposta);
            }
        }
    }

    private static String processarComando(String comando) {
        try {
            String[] partes = comando.split(" ");
            switch (partes[0].toLowerCase()) {
                case "readdir":
                    return String.join(", ", arquivos);
                case "rename":
                    return rename(partes[1], partes[2]);
                case "create":
                    return create(partes[1]);
                case "remove":
                    return remove(partes[1]);
                default:
                    return "Comando inválido!";
            }
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }

    private static String rename(String antigo, String novo) {
        if (arquivos.contains(antigo)) {
            arquivos.remove(antigo);
            arquivos.add(novo);
            return "Arquivo renomeado com sucesso!";
        }
        return "Arquivo não encontrado!";
    }

    private static String create(String nome) {
        if (!arquivos.contains(nome)) {
            arquivos.add(nome);
            return "Arquivo criado com sucesso!";
        }
        return "Arquivo já existe!";
    }

    private static String remove(String nome) {
        if (arquivos.remove(nome)) {
            return "Arquivo removido com sucesso!";
        }
        return "Arquivo não encontrado!";
    }
}
