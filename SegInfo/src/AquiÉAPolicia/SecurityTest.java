package AquiÉAPolicia;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityTest {
    private static final String VIRUS_SIGN = "EQP3SS4P004VM7A"; 

    public static void main(String[] args) {
    	
        String caminho = "D:\\teste_segurança"; //pasta teste

        File pasta = new File(caminho);
        File[] files = pasta.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("Nenhum arquivo encontrado");
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                try {
                    if (isInfected(file)) {
                        System.out.println("PERIGO" + file.getName() + " CONTÉM VIRUS");
                    } else {
                        System.out.println(file.getName() + " SEGURO");
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private static boolean isInfected(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] dataBytes = new byte[1024];

            int bytesDecorridos;
            while ((bytesDecorridos = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, bytesDecorridos);
            }

            byte[] hashBytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }

            String fileHash = sb.toString();

            return fileHash.equals(VIRUS_SIGN);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}